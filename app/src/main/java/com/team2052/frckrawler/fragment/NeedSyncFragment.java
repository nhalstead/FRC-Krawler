package com.team2052.frckrawler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;

import com.team2052.frckrawler.R;

/**
 * @author Adam
 */
public class NeedSyncFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sync_needed, null);
    }
}
