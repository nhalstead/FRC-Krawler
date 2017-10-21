package com.team2052.frckrawler.models;

import org.greenrobot.greendao.annotation.*;

import java.util.List;

import com.team2052.frckrawler.models.DaoSession;

import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "ROBOT".
 */
@Entity(active = true)
public class Robot implements java.io.Serializable {

    @Id(autoincrement = true)
    @Unique
    private Long id;
    private long team_id;
    private long season_id;
    private String data;
    private String comments;
    private java.util.Date last_updated;

    /**
     * Used to resolve relations
     */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient RobotDao myDao;

    @ToOne(joinProperty = "season_id")
    private Season season;

    @Generated
    private transient Long season__resolvedKey;

    @ToOne(joinProperty = "team_id")
    private Team team;

    @Generated
    private transient Long team__resolvedKey;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "robot_id")
    })
    private List<RobotEvent> robotEventList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "robot_id")
    })
    private List<MatchDatum> matchDatumList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "robot_id")
    })
    private List<PitDatum> pitDatumList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "robot_id")
    })
    private List<MatchComment> matchCommentList;

    @Generated
    public Robot() {
    }

    public Robot(Long id) {
        this.id = id;
    }

    @Generated
    public Robot(Long id, long team_id, long season_id, String data, String comments, java.util.Date last_updated) {
        this.id = id;
        this.team_id = team_id;
        this.season_id = season_id;
        this.data = data;
        this.comments = comments;
        this.last_updated = last_updated;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRobotDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(long season_id) {
        this.season_id = season_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public java.util.Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(java.util.Date last_updated) {
        this.last_updated = last_updated;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Season getSeason() {
        long __key = this.season_id;
        if (season__resolvedKey == null || !season__resolvedKey.equals(__key)) {
            __throwIfDetached();
            SeasonDao targetDao = daoSession.getSeasonDao();
            Season seasonNew = targetDao.load(__key);
            synchronized (this) {
                season = seasonNew;
                season__resolvedKey = __key;
            }
        }
        return season;
    }

    @Generated
    public void setSeason(Season season) {
        if (season == null) {
            throw new DaoException("To-one property 'season_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.season = season;
            season_id = season.getId();
            season__resolvedKey = season_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Team getTeam() {
        long __key = this.team_id;
        if (team__resolvedKey == null || !team__resolvedKey.equals(__key)) {
            __throwIfDetached();
            TeamDao targetDao = daoSession.getTeamDao();
            Team teamNew = targetDao.load(__key);
            synchronized (this) {
                team = teamNew;
                team__resolvedKey = __key;
            }
        }
        return team;
    }

    @Generated
    public void setTeam(Team team) {
        if (team == null) {
            throw new DaoException("To-one property 'team_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.team = team;
            team_id = team.getNumber();
            team__resolvedKey = team_id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<RobotEvent> getRobotEventList() {
        if (robotEventList == null) {
            __throwIfDetached();
            RobotEventDao targetDao = daoSession.getRobotEventDao();
            List<RobotEvent> robotEventListNew = targetDao._queryRobot_RobotEventList(id);
            synchronized (this) {
                if (robotEventList == null) {
                    robotEventList = robotEventListNew;
                }
            }
        }
        return robotEventList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetRobotEventList() {
        robotEventList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<MatchDatum> getMatchDatumList() {
        if (matchDatumList == null) {
            __throwIfDetached();
            MatchDatumDao targetDao = daoSession.getMatchDatumDao();
            List<MatchDatum> matchDatumListNew = targetDao._queryRobot_MatchDatumList(id);
            synchronized (this) {
                if (matchDatumList == null) {
                    matchDatumList = matchDatumListNew;
                }
            }
        }
        return matchDatumList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetMatchDatumList() {
        matchDatumList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<PitDatum> getPitDatumList() {
        if (pitDatumList == null) {
            __throwIfDetached();
            PitDatumDao targetDao = daoSession.getPitDatumDao();
            List<PitDatum> pitDatumListNew = targetDao._queryRobot_PitDatumList(id);
            synchronized (this) {
                if (pitDatumList == null) {
                    pitDatumList = pitDatumListNew;
                }
            }
        }
        return pitDatumList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetPitDatumList() {
        pitDatumList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<MatchComment> getMatchCommentList() {
        if (matchCommentList == null) {
            __throwIfDetached();
            MatchCommentDao targetDao = daoSession.getMatchCommentDao();
            List<MatchComment> matchCommentListNew = targetDao._queryRobot_MatchCommentList(id);
            synchronized (this) {
                if (matchCommentList == null) {
                    matchCommentList = matchCommentListNew;
                }
            }
        }
        return matchCommentList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetMatchCommentList() {
        matchCommentList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

}
