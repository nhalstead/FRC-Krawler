package com.team2052.frckrawler.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.team2052.frckrawler.R;
import com.team2052.frckrawler.bluetooth.scout.LoginHandler;
import com.team2052.frckrawler.database.DBManager;
import com.team2052.frckrawler.db.Event;
import com.team2052.frckrawler.db.Match;
import com.team2052.frckrawler.db.MatchComment;
import com.team2052.frckrawler.db.MatchDao;
import com.team2052.frckrawler.db.MatchData;
import com.team2052.frckrawler.db.MatchDataDao;
import com.team2052.frckrawler.db.Metric;
import com.team2052.frckrawler.db.MetricDao;
import com.team2052.frckrawler.db.Robot;
import com.team2052.frckrawler.db.RobotDao;
import com.team2052.frckrawler.db.Team;
import com.team2052.frckrawler.events.scout.ScoutSyncSuccessEvent;
import com.team2052.frckrawler.util.Utilities;
import com.team2052.frckrawler.view.metric.MetricWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.event.EventBus;

/**
 * @author Adam
 */
public class ScoutMatchFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    @InjectView(R.id.match_number)
    Spinner mMatchSpinner;
    @InjectView(R.id.team)
    Spinner mAllianceSpinner;
    @InjectView(R.id.metricWidgetList)
    LinearLayout mMetricList;
    private Event mEvent;
    private List<Match> mMatches;
    private List<Team> mTeams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
    }

    private void loadAllData(Event event) {
        if (event == null) {
            setErrorVisible(true);
            return;
        }
        mEvent = event;

        new GetAllMatches().execute();
        //new GetAllRobotsTask().execute();
    }

    private void setErrorVisible(boolean visible) {
        if (visible) {
            getView().findViewById(R.id.error).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.scroll_view).setVisibility(View.GONE);
        } else {
            getView().findViewById(R.id.error).setVisibility(View.GONE);
            getView().findViewById(R.id.scroll_view).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.scout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (mAllianceSpinner.getSelectedItem() != null && mMatchSpinner.getSelectedItem() != null) {
                new SaveAllMetrics().execute();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scouting_match, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mMatchSpinner.setOnItemSelectedListener(this);
        loadAllData(Utilities.ScoutUtil.getScoutEvent(getActivity(), mDaoSession));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Match match = mMatches.get(mMatchSpinner.getSelectedItemPosition());
        mTeams = DBManager.getInstance(getActivity(), mDaoSession).getTeamsForMatch(match);
        List<String> teamNumbers = new ArrayList<>();
        for (Team team : mTeams) {
            teamNumbers.add(team.getName() + ", " + team.getNumber());
        }
        mAllianceSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Arrays.copyOf(teamNumbers.toArray(), teamNumbers.size(), String[].class)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    public void onEvent(ScoutSyncSuccessEvent event) {
        loadAllData(Utilities.ScoutUtil.getScoutEvent(getActivity(), mDaoSession));
    }

    public class GetAllRobotsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mTeams = mDaoSession.getTeamDao().loadAll();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            List<String> teamNumbers = new ArrayList<>();
            for (Team team : mTeams) {
                teamNumbers.add(team.getName() + ", " + team.getNumber());
            }
            mAllianceSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Arrays.copyOf(teamNumbers.toArray(), teamNumbers.size(), String[].class)));
        }
    }

    public class GetAllMatches extends AsyncTask<Void, Void, List<Match>> {

        @Override
        protected List<Match> doInBackground(Void... params) {
            return mDaoSession.getMatchDao().queryBuilder().orderAsc(MatchDao.Properties.Number).where(MatchDao.Properties.EventId.eq(mEvent.getId())).list();
        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            mMatches = matches;
            List<String> matchNumbers = new ArrayList<>();
            for (Match match : matches) {
                matchNumbers.add("Match #" + match.getNumber());
            }
            mMatchSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, matchNumbers));
            new GetAllMetrics().execute();
        }
    }

    public class GetAllMetrics extends AsyncTask<Void, Void, List<Metric>> {

        @Override
        protected List<Metric> doInBackground(Void... params) {
            QueryBuilder<Metric> metricQueryBuilder = mDaoSession.getMetricDao().queryBuilder();
            metricQueryBuilder.where(MetricDao.Properties.GameId.eq(mEvent.getGame().getId()));
            metricQueryBuilder.where(MetricDao.Properties.Category.eq(Utilities.MetricUtil.MetricType.MATCH_PERF_METRICS.ordinal()));
            return metricQueryBuilder.list();
        }

        @Override
        protected void onPostExecute(List<Metric> metrics) {
            mMetricList.removeAllViews();
            for (Metric metric : metrics) {
                mMetricList.addView(MetricWidget.createWidget(getActivity(), metric));
            }
            setErrorVisible(false);
        }
    }

    public class SaveAllMetrics extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            LoginHandler loginHandler = LoginHandler.getInstance(getActivity(), mDaoSession);

            if (!loginHandler.isLoggedOn() && !loginHandler.loggedOnUserStillExists()) {
                loginHandler.login();
            }

            //Get data from view
            Team team = mTeams.get(mAllianceSpinner.getSelectedItemPosition());
            Match match = mMatches.get(mMatchSpinner.getSelectedItemPosition());

            Robot robot = mDaoSession.getRobotDao().queryBuilder().where(RobotDao.Properties.TeamId.eq(team.getNumber())).where(RobotDao.Properties.GameId.eq(mEvent.getGameId())).unique();

            List<MetricWidget> widgets = new ArrayList<>();
            //Get Widgets
            for (int i = 0; i < mMetricList.getChildCount(); i++) {
                widgets.add((MetricWidget) mMetricList.getChildAt(i));
            }
            //Insert Metric Data
            for (MetricWidget widget : widgets) {
                QueryBuilder<MatchData> matchDataQueryBuilder = mDaoSession.getMatchDataDao().queryBuilder();
                matchDataQueryBuilder.where(MatchDataDao.Properties.RobotId.eq(robot.getId()));
                matchDataQueryBuilder.where(MatchDataDao.Properties.MetricId.eq(widget.getMetric().getId()));
                matchDataQueryBuilder.where(MatchDataDao.Properties.MatchId.eq(match.getId()));
                if (matchDataQueryBuilder.list().size() <= 0)
                    mDaoSession.getMatchDataDao().insert(new MatchData(widget.getValues(), robot.getId(), widget.getMetric().getId(), match.getId(), loginHandler.getLoggedOnUser().getId()));
            }
            //Only save if we have something.
            if (((EditText) getView().findViewById(R.id.comments)).getText().toString().length() != 0) {
                mDaoSession.insert(new MatchComment(match.getId(), ((EditText) getView().findViewById(R.id.comments)).getText().toString(), robot.getId(), mEvent.getId(), team.getNumber()));
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            Toast.makeText(getActivity(), aVoid == 0 ? "Save Complete!" : "Cannot Save Match Data. Match Is Already Saved", Toast.LENGTH_LONG).show();
        }
    }
}
