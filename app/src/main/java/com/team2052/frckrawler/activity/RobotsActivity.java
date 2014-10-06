package com.team2052.frckrawler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.team2052.frckrawler.adapters.ListViewAdapter;
import com.team2052.frckrawler.listitems.ListItem;
import com.team2052.frckrawler.listitems.elements.SimpleListElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import frckrawler.Event;
import frckrawler.RobotEvent;
import frckrawler.RobotEventDao;

/**
 * @author Adam
 * @since 10/3/2014
 */
public class RobotsActivity extends ListActivity
{

    private Event mEvent;

    public static Intent newInstance(Context context, Event event)
    {
        Intent intent = new Intent(context, RobotsActivity.class);
        intent.putExtra(PARENT_ID, event.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mEvent = mDaoSession.getEventDao().load(getIntent().getLongExtra(PARENT_ID, 0));
    }

    @Override
    public void updateList()
    {
        new GetRobots().execute();
    }

    public class GetRobots extends AsyncTask<Void, Void, List<RobotEvent>>
    {

        @Override
        protected List<RobotEvent> doInBackground(Void... voids)
        {
            return mDaoSession.getRobotEventDao().queryBuilder().where(RobotEventDao.Properties.EventId.eq(mEvent.getId())).list();
        }

        @Override
        protected void onPostExecute(List<RobotEvent> robotEventses)
        {
            Collections.sort(robotEventses, new Comparator<RobotEvent>()
            {
                @Override
                public int compare(RobotEvent robotEvent, RobotEvent robotEvent2)
                {
                    return Double.compare(robotEvent.getRobot().getTeam().getNumber(), robotEvent2.getRobot().getTeam().getNumber());
                }
            });

            List<ListItem> listItems = new ArrayList<>();
            for (RobotEvent robotEvent : robotEventses) {
                listItems.add(new SimpleListElement(Long.toString(robotEvent.getRobot().getTeam().getNumber()), Long.toString(robotEvent.getRobot().getId())));
            }
            mListView.setAdapter(mAdapter = new ListViewAdapter(RobotsActivity.this, listItems));
        }
    }
}
