package com.team2052.frckrawler.db;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MATCH_DATA".
 */
public class MatchData implements java.io.Serializable {

    private Long id;
    private long event_id;
    private long robot_id;
    private Long user_id;
    private long metric_id;
    private int match_type;
    private long match_number;
    private java.util.Date last_updated;
    private String data;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient MatchDataDao myDao;

    private Event event;
    private Long event__resolvedKey;

    private Robot robot;
    private Long robot__resolvedKey;

    private User user;
    private Long user__resolvedKey;

    private Metric metric;
    private Long metric__resolvedKey;


    public MatchData() {
    }

    public MatchData(Long id) {
        this.id = id;
    }

    public MatchData(Long id, long event_id, long robot_id, Long user_id, long metric_id, int match_type, long match_number, java.util.Date last_updated, String data) {
        this.id = id;
        this.event_id = event_id;
        this.robot_id = robot_id;
        this.user_id = user_id;
        this.metric_id = metric_id;
        this.match_type = match_type;
        this.match_number = match_number;
        this.last_updated = last_updated;
        this.data = data;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMatchDataDao() : null;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
    public Event getEvent() {
        long __key = this.event_id;
        if (event__resolvedKey == null || !event__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EventDao targetDao = daoSession.getEventDao();
            Event eventNew = targetDao.load(__key);
            synchronized (this) {
                event = eventNew;
            	event__resolvedKey = __key;
            }
        }
        return event;
    }

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
    public Robot getRobot() {
        long __key = this.robot_id;
        if (robot__resolvedKey == null || !robot__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RobotDao targetDao = daoSession.getRobotDao();
            Robot robotNew = targetDao.load(__key);
            synchronized (this) {
                robot = robotNew;
            	robot__resolvedKey = __key;
            }
        }
        return robot;
    }

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
    public User getUser() {
        Long __key = this.user_id;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
            	user__resolvedKey = __key;
            }
        }
        return user;
    }

    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            user_id = user == null ? null : user.getId();
            user__resolvedKey = user_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Metric getMetric() {
        long __key = this.metric_id;
        if (metric__resolvedKey == null || !metric__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MetricDao targetDao = daoSession.getMetricDao();
            Metric metricNew = targetDao.load(__key);
            synchronized (this) {
                metric = metricNew;
            	metric__resolvedKey = __key;
            }
        }
        return metric;
    }

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

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
