package com.team2052.frckrawler.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.team2052.frckrawler.fragment.ScoutMatchFragment;
import com.team2052.frckrawler.fragment.ScoutPitFragment;

/**
 * @author adam
 * @since 12/27/14.
 */
public class ScoutPagerAdapter extends FragmentPagerAdapter {
    public final String[] headers = {"Match Scouting", "Pit Scouting"};

    public ScoutPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return headers[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ScoutMatchFragment();
            case 1:
                return new ScoutPitFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return headers.length;
    }
}

