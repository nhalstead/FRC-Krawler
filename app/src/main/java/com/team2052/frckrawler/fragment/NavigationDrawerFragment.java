package com.team2052.frckrawler.fragment;

import android.app.*;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;

import com.team2052.frckrawler.*;
import com.team2052.frckrawler.adapters.NavDrawerAdataper;
import com.team2052.frckrawler.listitems.*;

import java.util.*;

public class NavigationDrawerFragment extends Fragment
{
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    public final List<ListItem> NAV_ITEMS = new ArrayList<>();
    private NavDrawerAdataper navAdapter;
    private ListView drawerListView;
    private View fragmentContainerView;
    private DrawerLayout drawerLayout;
    private boolean useActionBarToggle;
    private ActionBarDrawerToggle drawerToggle;
    private boolean userLearnedDrawer;
    private NavigationDrawerListener listener;
    private boolean fromSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences scoutPrefs = getActivity().getSharedPreferences(GlobalValues.PREFS_FILE_NAME, 0);
        //Deny the scout to access all the items
        NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_scout, "Scout", R.drawable.icon_scout_selector));
        if (!scoutPrefs.getBoolean(GlobalValues.IS_SCOUT_PREF, false)) {
            NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_server, "Server", R.drawable.icon_bluetooth_selector));
            NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_teams, "Teams", R.drawable.icon_team_selector));
            NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_users, "Users", R.drawable.icon_user_selector));
            NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_games, "Games", R.drawable.icon_game_selector));
            NAV_ITEMS.add(new NavDrawerItem(R.id.nav_item_options, "Options", R.drawable.icon_settings_selector));
        }
        userLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        fromSavedInstanceState = (savedInstanceState == null);
        navAdapter = new NavDrawerAdataper(getActivity(), NAV_ITEMS);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        drawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectItem(position);
            }
        });
        drawerListView.setAdapter(navAdapter);

        return drawerListView;
    }

    private void selectItem(int position)
    {
        if (drawerListView != null) {
            drawerListView.setItemChecked(position, true);
            navAdapter.setItemSelected(position);
        }
        NavDrawerItem item = navAdapter.getItem(position);
        listener.onNavDrawerItemClicked(item);
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(fragmentContainerView);
        }
    }

    public void setItemSelected(int itemId)
    {
        if (drawerListView != null) {
            int position = navAdapter.getPositionForId(itemId);
            drawerListView.setItemChecked(position, true);
        }
    }

    public boolean isDrawerOpen()
    {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, boolean encourageLearning, boolean useActionBarToggle)
    {
        fragmentContainerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;
        this.useActionBarToggle = useActionBarToggle;
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        if (this.useActionBarToggle) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            drawerToggle = new ActionBarDrawerToggle(
                    getActivity(), /* host Activity */
                    this.drawerLayout, /* DrawerLayout object */
                    R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                    R.string.drawer_open, /* "open drawer" description for accessibility */
                    R.string.drawer_close /* "close drawer" description for accessibility */
            )
            {
                @Override
                public void onDrawerClosed(View drawerView)
                {
                    super.onDrawerClosed(drawerView);
                    if (!isAdded()) {
                        return;
                    }
                    NavigationDrawerFragment.this.listener.onNavDrawerClosed();
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }

                @Override
                public void onDrawerOpened(View drawerView)
                {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) {
                        return;
                    }
                    if (!userLearnedDrawer) {
                        userLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }
                    NavigationDrawerFragment.this.listener.onNavDrawerOpened();
                    getActivity().invalidateOptionsMenu();
                }
            };
            this.drawerLayout.setDrawerListener(this.drawerToggle);
            this.drawerLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    NavigationDrawerFragment.this.drawerToggle.syncState();
                }
            });
        } else {
            drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener()
            {
                @Override
                public void onDrawerOpened(View drawerView)
                {
                    if (!isAdded()) {
                        return;
                    }
                    if (!NavigationDrawerFragment.this.userLearnedDrawer) {
                        NavigationDrawerFragment.this.userLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }
                    NavigationDrawerFragment.this.listener.onNavDrawerOpened();
                    getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView)
                {
                    if (!isAdded()) {
                        return;
                    }
                    NavigationDrawerFragment.this.listener.onNavDrawerClosed();
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }
            });
        }
        if (encourageLearning && !userLearnedDrawer && !fromSavedInstanceState) {
            this.drawerLayout.openDrawer(fragmentContainerView);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof NavigationDrawerListener) {
            this.listener = (NavigationDrawerListener) activity;
        } else {
            throw new IllegalStateException("Activities must implement NavigationDrawerListener");
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        if (drawerLayout != null && isDrawerOpen()) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setTitle(R.string.app_name);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (useActionBarToggle && drawerToggle != null) {
            return drawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    public interface NavigationDrawerListener
    {
        /**
         * Called when a NavDrawerItem in the navigation drawer is clicked
         *
         * @param item The item that was clicked
         */
        public void onNavDrawerItemClicked(NavDrawerItem item);

        /**
         * Called when the drawer is opened.
         */
        public void onNavDrawerOpened();

        /**
         * CAlled when the drawer is opened.
         */
        public void onNavDrawerClosed();
    }
}
