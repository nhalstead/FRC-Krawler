package com.team2052.frckrawler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.team2052.frckrawler.GlobalValues;
import com.team2052.frckrawler.R;
import com.team2052.frckrawler.adapters.ListViewAdapter;
import com.team2052.frckrawler.database.CompiledMetricValue;
import com.team2052.frckrawler.db.Event;
import com.team2052.frckrawler.db.Metric;
import com.team2052.frckrawler.listitems.ListItem;
import com.team2052.frckrawler.listitems.elements.SimpleListElement;
import com.team2052.frckrawler.util.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam
 */
public class SummaryDataActivity extends ListActivity {
    public static String EVENT_ID = "EVENT_ID";
    private Event mEvent;
    private Metric mMetric;

    public static Intent newInstance(Context context, Metric metric, Event event) {
        Intent intent = new Intent(context, SummaryDataActivity.class);
        intent.putExtra(PARENT_ID, metric.getId());
        intent.putExtra(EVENT_ID, event.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvent = mDaoSession.getEventDao().load(getIntent().getLongExtra(EVENT_ID, 0));
        mMetric = mDaoSession.getMetricDao().load(getIntent().getLongExtra(PARENT_ID, 0));
        if (getActionBar() != null) {
            setActionBarTitle(getString(R.string.Summary));
            setActionBarSubtitle(mMetric.getName());
        }
    }

    @Override
    public void updateList() {
        new GetCompiledData().execute();
    }


    public class GetCompiledData extends AsyncTask<Void, Void, List<CompiledMetricValue>> {

        final float compileWeight;

        public GetCompiledData(){
            this.compileWeight = getSharedPreferences(GlobalValues.PREFS_FILE_NAME, 0).getFloat(GlobalValues.PREFS_COMPILE_WEIGHT, 1.0f);

        }
        @Override
        protected List<CompiledMetricValue> doInBackground(Void... params) {
            return Utilities.MetricCompiler.getCompiledMetric(mEvent, mMetric, mDaoSession, compileWeight);
        }

        @Override
        protected void onPostExecute(List<CompiledMetricValue> compiledMetricValues) {
            List<ListItem> listItems = new ArrayList<>();
            for (CompiledMetricValue metricValue : compiledMetricValues) {
                listItems.add(new SimpleListElement(metricValue.robot.getTeam().getNumber() + " - " + metricValue.compiledValue, Long.toString(metricValue.robot.getTeam().getNumber())));
            }
            mListView.setAdapter(mAdapter = new ListViewAdapter(SummaryDataActivity.this, listItems));
        }
    }
}
