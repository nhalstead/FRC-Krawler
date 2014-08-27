package com.team2052.frckrawler.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.team2052.frckrawler.R;
import com.team2052.frckrawler.activity.dialog.AddGameDialogActivity;
import com.team2052.frckrawler.adapters.ListViewAdapter;
import com.team2052.frckrawler.database.DBManager;
import com.team2052.frckrawler.database.structures.Game;
import com.team2052.frckrawler.listitems.GameListItem;
import com.team2052.frckrawler.listitems.ListItem;

import java.util.ArrayList;

public class GamesFragment extends Fragment {
    private DBManager dbManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_games, null);
        this.dbManager = DBManager.getInstance(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetGamesTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addbutton, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_metric_action) {
            Intent i = new Intent(getActivity(), AddGameDialogActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetGamesTask extends AsyncTask<Void, Void, Game[]> {

        @Override
        protected Game[] doInBackground(Void... params) {
            return dbManager.getAllGames();
        }

        @Override
        protected void onPostExecute(Game[] games) {
            ListView listView = (ListView) getView().findViewById(R.id.games_list);
            ArrayList<ListItem> element = new ArrayList<ListItem>();
            for (Game game : games) {
                element.add(new GameListItem(game, GamesFragment.this));
            }
            final ListViewAdapter adapter = new ListViewAdapter(getActivity(), element);
            listView.setAdapter(adapter);
        }
    }
}
