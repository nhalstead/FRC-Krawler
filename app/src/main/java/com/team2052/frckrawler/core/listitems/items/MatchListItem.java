package com.team2052.frckrawler.core.listitems.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.team2052.frckrawler.R;
import com.team2052.frckrawler.core.listitems.ListItem;
import com.team2052.frckrawler.core.ui.MatchView;
import com.team2052.frckrawler.db.Match;

/**
 * @author Adam
 */
public class MatchListItem implements ListItem {
    private final Match match;

    public MatchListItem(Match match) {
        this.match = match;
    }

    @Override
    public View getView(Context c, LayoutInflater inflater, View convertView) {
        if (convertView == null || !(convertView instanceof MatchView)) {
            convertView = inflater.inflate(R.layout.match_view, null);
        }
        MatchView matchView = (MatchView) convertView;
        matchView.init(match);
        return matchView;
    }
}
