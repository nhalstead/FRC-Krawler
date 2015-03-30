package com.team2052.frckrawler.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table MATCH_DATA.
 */
public class MatchData implements java.io.Serializable {

    private Long id;
    private long robotId;
    private long metricId;
    private long matchId;
    private long eventId;
    private long userId;
    private String data;

    public MatchData() {
    }

    public MatchData(Long id) {
        this.id = id;
    }

    public MatchData(Long id, long robotId, long metricId, long matchId, long eventId, long userId, String data) {
        this.id = id;
        this.robotId = robotId;
        this.metricId = metricId;
        this.matchId = matchId;
        this.eventId = eventId;
        this.userId = userId;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRobotId() {
        return robotId;
    }

    public void setRobotId(long robotId) {
        this.robotId = robotId;
    }

    public long getMetricId() {
        return metricId;
    }

    public void setMetricId(long metricId) {
        this.metricId = metricId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
