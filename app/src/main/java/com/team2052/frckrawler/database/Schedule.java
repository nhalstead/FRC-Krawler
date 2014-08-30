package com.team2052.frckrawler.database;

import com.team2052.frckrawler.database.models.Event;
import com.team2052.frckrawler.database.models.Match;

import java.util.List;

/**
 * @author Adam
 */
public class Schedule {
    public Event event;
    public List<Match> matches;

    public Schedule(Event event, List<Match> matches) {
        this.event = event;
        this.matches = matches;
    }
}
