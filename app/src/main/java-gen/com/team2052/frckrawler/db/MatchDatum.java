package com.team2052.frckrawler.db;

import org.greenrobot.greendao.annotation.*;

import com.team2052.frckrawler.db.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "MATCH_DATUM".
 */
@Entity(active = true)
public class MatchDatum implements java.io.Serializable {

    @Id
    private Long id;
    private long event_id;
    private long robot_id;
    private long metric_id;
    private int match_type;
    private long match_number;
    private java.util.Date last_updated;
    private String data;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient MatchDatumDao myDao;

    @ToOne(joinProperty = "event_id")
    private Event event;

    @Generated
    private transient Long event__resolvedKey;

    @ToOne(joinProperty = "robot_id")
    private Robot robot;

    @Generated
    private transient Long robot__resolvedKey;

    @ToOne(joinProperty = "metric_id")
    private Metric metric;

    @Generated
    private transient Long metric__resolvedKey;

    @Generated
    public MatchDatum() {
    }

    public MatchDatum(Long id) {
        this.id = id;
    }

    @Generated
    public MatchDatum(Long id, long event_id, long robot_id, long metric_id, int match_type, long match_number, java.util.Date last_updated, String data) {
        this.id = id;
        this.event_id = event_id;
        this.robot_id = robot_id;
        this.metric_id = metric_id;
        this.match_type = match_type;
        this.match_number = match_number;
        this.last_updated = last_updated;
        this.data = data;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMatchDatumDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public long getRobot_id() {
        return robot_id;
    }

    public void setRobot_id(long robot_id) {
        this.robot_id = robot_id;
    }

    public long getMetric_id() {
        return metric_id;
    }

    public void setMetric_id(long metric_id) {
        this.metric_id = metric_id;
    }

    public int getMatch_type() {
        return match_type;
    }

    public void setMatch_type(int match_type) {
        this.match_type = match_type;
    }

    public long getMatch_number() {
        return match_number;
    }

    public void setMatch_number(long match_number) {
        this.match_number = match_number;
    }

    public java.util.Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(java.util.Date last_updated) {
        this.last_updated = last_updated;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Event getEvent() {
        long __key = this.event_id;
        if (event__resolvedKey == null || !event__resolvedKey.equals(__key)) {
            __throwIfDetached();
            EventDao targetDao = daoSession.getEventDao();
            Event eventNew = targetDao.load(__key);
            synchronized (this) {
                event = eventNew;
            	event__resolvedKey = __key;
            }
        }
        return event;
    }

    @Generated
    public void setEvent(Event event) {
        if (event == null) {
            throw new DaoException("To-one property 'event_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.event = event;
            event_id = event.getId();
            event__resolvedKey = event_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Robot getRobot() {
        long __key = this.robot_id;
        if (robot__resolvedKey == null || !robot__resolvedKey.equals(__key)) {
            __throwIfDetached();
            RobotDao targetDao = daoSession.getRobotDao();
            Robot robotNew = targetDao.load(__key);
            synchronized (this) {
                robot = robotNew;
            	robot__resolvedKey = __key;
            }
        }
        return robot;
    }

    @Generated
    public void setRobot(Robot robot) {
        if (robot == null) {
            throw new DaoException("To-one property 'robot_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.robot = robot;
            robot_id = robot.getId();
            robot__resolvedKey = robot_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Metric getMetric() {
        long __key = this.metric_id;
        if (metric__resolvedKey == null || !metric__resolvedKey.equals(__key)) {
            __throwIfDetached();
            MetricDao targetDao = daoSession.getMetricDao();
            Metric metricNew = targetDao.load(__key);
            synchronized (this) {
                metric = metricNew;
            	metric__resolvedKey = __key;
            }
        }
        return metric;
    }

    @Generated
    public void setMetric(Metric metric) {
        if (metric == null) {
            throw new DaoException("To-one property 'metric_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.metric = metric;
            metric_id = metric.getId();
            metric__resolvedKey = metric_id;
        }
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
