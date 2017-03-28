package com.team2052.frckrawler.db;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig gameDaoConfig;
    private final DaoConfig eventDaoConfig;
    private final DaoConfig teamDaoConfig;
    private final DaoConfig metricDaoConfig;
    private final DaoConfig matchDaoConfig;
    private final DaoConfig matchDatumDaoConfig;
    private final DaoConfig matchCommentDaoConfig;
    private final DaoConfig robotDaoConfig;
    private final DaoConfig robotEventDaoConfig;
    private final DaoConfig pitDatumDaoConfig;
    private final DaoConfig serverLogEntryDaoConfig;

    private final GameDao gameDao;
    private final EventDao eventDao;
    private final TeamDao teamDao;
    private final MetricDao metricDao;
    private final MatchDao matchDao;
    private final MatchDatumDao matchDatumDao;
    private final MatchCommentDao matchCommentDao;
    private final RobotDao robotDao;
    private final RobotEventDao robotEventDao;
    private final PitDatumDao pitDatumDao;
    private final ServerLogEntryDao serverLogEntryDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        gameDaoConfig = daoConfigMap.get(GameDao.class).clone();
        gameDaoConfig.initIdentityScope(type);

        eventDaoConfig = daoConfigMap.get(EventDao.class).clone();
        eventDaoConfig.initIdentityScope(type);

        teamDaoConfig = daoConfigMap.get(TeamDao.class).clone();
        teamDaoConfig.initIdentityScope(type);

        metricDaoConfig = daoConfigMap.get(MetricDao.class).clone();
        metricDaoConfig.initIdentityScope(type);

        matchDaoConfig = daoConfigMap.get(MatchDao.class).clone();
        matchDaoConfig.initIdentityScope(type);

        matchDatumDaoConfig = daoConfigMap.get(MatchDatumDao.class).clone();
        matchDatumDaoConfig.initIdentityScope(type);

        matchCommentDaoConfig = daoConfigMap.get(MatchCommentDao.class).clone();
        matchCommentDaoConfig.initIdentityScope(type);

        robotDaoConfig = daoConfigMap.get(RobotDao.class).clone();
        robotDaoConfig.initIdentityScope(type);

        robotEventDaoConfig = daoConfigMap.get(RobotEventDao.class).clone();
        robotEventDaoConfig.initIdentityScope(type);

        pitDatumDaoConfig = daoConfigMap.get(PitDatumDao.class).clone();
        pitDatumDaoConfig.initIdentityScope(type);

        serverLogEntryDaoConfig = daoConfigMap.get(ServerLogEntryDao.class).clone();
        serverLogEntryDaoConfig.initIdentityScope(type);

        gameDao = new GameDao(gameDaoConfig, this);
        eventDao = new EventDao(eventDaoConfig, this);
        teamDao = new TeamDao(teamDaoConfig, this);
        metricDao = new MetricDao(metricDaoConfig, this);
        matchDao = new MatchDao(matchDaoConfig, this);
        matchDatumDao = new MatchDatumDao(matchDatumDaoConfig, this);
        matchCommentDao = new MatchCommentDao(matchCommentDaoConfig, this);
        robotDao = new RobotDao(robotDaoConfig, this);
        robotEventDao = new RobotEventDao(robotEventDaoConfig, this);
        pitDatumDao = new PitDatumDao(pitDatumDaoConfig, this);
        serverLogEntryDao = new ServerLogEntryDao(serverLogEntryDaoConfig, this);

        registerDao(Game.class, gameDao);
        registerDao(Event.class, eventDao);
        registerDao(Team.class, teamDao);
        registerDao(Metric.class, metricDao);
        registerDao(Match.class, matchDao);
        registerDao(MatchDatum.class, matchDatumDao);
        registerDao(MatchComment.class, matchCommentDao);
        registerDao(Robot.class, robotDao);
        registerDao(RobotEvent.class, robotEventDao);
        registerDao(PitDatum.class, pitDatumDao);
        registerDao(ServerLogEntry.class, serverLogEntryDao);
    }

    public void clear() {
        gameDaoConfig.clearIdentityScope();
        eventDaoConfig.clearIdentityScope();
        teamDaoConfig.clearIdentityScope();
        metricDaoConfig.clearIdentityScope();
        matchDaoConfig.clearIdentityScope();
        matchDatumDaoConfig.clearIdentityScope();
        matchCommentDaoConfig.clearIdentityScope();
        robotDaoConfig.clearIdentityScope();
        robotEventDaoConfig.clearIdentityScope();
        pitDatumDaoConfig.clearIdentityScope();
        serverLogEntryDaoConfig.clearIdentityScope();
    }

    public GameDao getGameDao() {
        return gameDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public TeamDao getTeamDao() {
        return teamDao;
    }

    public MetricDao getMetricDao() {
        return metricDao;
    }

    public MatchDao getMatchDao() {
        return matchDao;
    }

    public MatchDatumDao getMatchDatumDao() {
        return matchDatumDao;
    }

    public MatchCommentDao getMatchCommentDao() {
        return matchCommentDao;
    }

    public RobotDao getRobotDao() {
        return robotDao;
    }

    public RobotEventDao getRobotEventDao() {
        return robotEventDao;
    }

    public PitDatumDao getPitDatumDao() {
        return pitDatumDao;
    }

    public ServerLogEntryDao getServerLogEntryDao() {
        return serverLogEntryDao;
    }

}
