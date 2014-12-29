package com.team2052.frckrawler.db;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table EVENT.
 */
public class Event implements java.io.Serializable {

    private Long id;
    private String name;
    private Long gameId;
    private String location;
    private java.util.Date date;
    private String fmsid;

    /**
     * Used to resolve relations
     */
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    private transient EventDao myDao;

    private Game game;
    private Long game__resolvedKey;


    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, String name, Long gameId, String location, java.util.Date date, String fmsid) {
        this.id = id;
        this.name = name;
        this.gameId = gameId;
        this.location = location;
        this.date = date;
        this.fmsid = fmsid;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getFmsid() {
        return fmsid;
    }

    public void setFmsid(String fmsid) {
        this.fmsid = fmsid;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    public Game getGame() {
        Long __key = this.gameId;
        if (game__resolvedKey == null || !game__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GameDao targetDao = daoSession.getGameDao();
            Game gameNew = targetDao.load(__key);
            synchronized (this) {
                game = gameNew;
                game__resolvedKey = __key;
            }
        }
        return game;
    }

    public void setGame(Game game) {
        synchronized (this) {
            this.game = game;
            gameId = game == null ? null : game.getId();
            game__resolvedKey = gameId;
        }
    }

    /**
     * Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context.
     */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context.
     */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context.
     */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

}
