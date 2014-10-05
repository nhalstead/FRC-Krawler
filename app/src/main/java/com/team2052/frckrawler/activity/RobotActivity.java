package com.team2052.frckrawler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.team2052.frckrawler.database.models.Robot;
import com.team2052.frckrawler.database.models.RobotPhoto;
import com.team2052.frckrawler.fragment.NeedSyncFragment;
import com.team2052.frckrawler.fragment.PhotosFragment;

/**
 * @author Adam
 */
public class RobotActivity extends ViewPagerActivity
{
    private Robot mRobot;

    public static Intent newInstance(Context context, long rKey)
    {
        Intent intent = new Intent(context, RobotActivity.class);
        intent.putExtra(PARENT_ID, rKey);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mRobot = Robot.load(Robot.class, getIntent().getLongExtra(PARENT_ID, 0));
        super.onCreate(savedInstanceState);
    }

    @Override
    public PagerAdapter setAdapter()
    {
        return new RobotViewPagerAdapter(getSupportFragmentManager());
    }

    public class RobotViewPagerAdapter extends FragmentPagerAdapter
    {
        private String[] HEADERS = new String[]{"Info", "Events", "Photos"};

        public RobotViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return HEADERS[position];
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0:
                    return new NeedSyncFragment();
                case 1:
                    return new NeedSyncFragment();
                case 2:
                    return PhotosFragment.newInstance(mRobot);
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return HEADERS.length;
        }
    }
}
