package com.team2052.frckrawler.fragment.scout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.team2052.frckrawler.R;
import com.team2052.frckrawler.bluetooth.SyncHandler;
import com.team2052.frckrawler.events.scout.ScoutSyncCancelledEvent;
import com.team2052.frckrawler.events.scout.ScoutSyncErrorEvent;
import com.team2052.frckrawler.events.scout.ScoutSyncStartEvent;
import com.team2052.frckrawler.events.scout.ScoutSyncSuccessEvent;
import com.team2052.frckrawler.fragment.ViewPagerFragment;

import de.greenrobot.event.EventBus;

public class ScoutFragment extends ViewPagerFragment
{

    public static final int REQUEST_BT_ENABLE = 1;
    private Menu mOptionsMenu;
    private SyncHandler mSyncHandler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSyncHandler = SyncHandler.getInstance(getActivity());
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                mSyncHandler.startScoutSync();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.scout_main_sync, menu);
        mOptionsMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public PagerAdapter setAdapter()
    {
        return new ScoutPagerAdapter(getChildFragmentManager());
    }

    @SuppressWarnings("unused")
    public void onEvent(ScoutSyncCancelledEvent event)
    {
        setProgress(false);
    }

    @SuppressWarnings("unused")
    public void onEvent(ScoutSyncErrorEvent event)
    {
        setProgress(false);
    }

    @SuppressWarnings("unused")
    public void onEvent(ScoutSyncStartEvent event)
    {
        setProgress(true);
    }

    @SuppressWarnings("unused")
    public void onEvent(ScoutSyncSuccessEvent event)
    {
        setProgress(false);
        //TODO LOGIN
        /*scoutLoginName = new EditText(getActivity());
        scoutLoginName.setHint("Name");*/
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login");
        builder.setView(scoutLoginName);
        builder.setPositiveButton("Login", new UserDialogListener());
        builder.setNegativeButton("Cancel", new UserDialogListener());
        builder.show();*/
    }

    private void setProgress(boolean toggle)
    {
        if (toggle) {
            mOptionsMenu.findItem(R.id.menu_sync).setActionView(R.layout.actionbar_progress);
        } else {
            mOptionsMenu.findItem(R.id.menu_sync).setActionView(null);
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public class ScoutPagerAdapter extends FragmentPagerAdapter
    {
        public final String[] headers = {"Match Scouting", "Pit Scouting"};

        public ScoutPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return headers[position];
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ScoutMatchFragment();
                    break;
                case 1:
                    fragment = new ScoutPitFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount()
        {
            return headers.length;
        }
    }
}
