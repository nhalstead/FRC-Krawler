package com.team2052.frckrawler.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.team2052.frckrawler.R;
import com.team2052.frckrawler.adapters.ListViewAdapter;
import com.team2052.frckrawler.database.models.User;
import com.team2052.frckrawler.fragment.dialog.AddUserDialogFragment;
import com.team2052.frckrawler.listitems.ListItem;
import com.team2052.frckrawler.listitems.UserListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 8/25/2014.
 */
public class UsersFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addbutton, menu);
        //Change the icon
        menu.findItem(R.id.add_action).setIcon(R.drawable.ic_action_add_person);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_action) {
            new AddUserDialogFragment().show(getChildFragmentManager(), "addUser");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateList() {
        new GetUsersTask().execute();
    }

    private class GetUsersTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... params) {
            return new Select().from(User.class).execute();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            List<ListItem> userList = new ArrayList<>();
            for (User user : users) {
                userList.add(new UserListItem(user));
            }
            mListView.setAdapter(mAdapter = new ListViewAdapter(getActivity(), userList));
        }
    }
}
