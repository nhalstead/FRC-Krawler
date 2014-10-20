package com.team2052.frckrawler.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.team2052.frckrawler.R;
import com.team2052.frckrawler.view.SlidingTabLayout;

/**
 * @author Adam
 */
public abstract class ViewPagerActivity extends DatabaseActivity
{
    protected ViewPager mPager;
    protected SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)            {
                return position % 2 == 0 ? getResources().getColor(R.color.red900) : Color.YELLOW;
            }
        });
        onPreLoadViewPager();
        mPager.setAdapter(setAdapter());
        mTabs.setViewPager(mPager);
    }

    public void onPreLoadViewPager()
    {
    }

    /**
     * @return The adapter that you want to attach to the ViewPager and the Tabs
     */
    public abstract PagerAdapter setAdapter();
}
