package com.team2052.frckrawler.models;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.team2052.frckrawler.models.Season;
import com.team2052.frckrawler.models.Event;
import com.team2052.frckrawler.models.Team;
import com.team2052.frckrawler.models.Metric;
import com.team2052.frckrawler.models.MatchDatum;
import com.team2052.frckrawler.models.MatchComment;
import com.team2052.frckrawler.models.Robot;
import com.team2052.frckrawler.models.RobotEvent;
import com.team2052.frckrawler.models.PitDatum;
import com.team2052.frckrawler.models.ServerLogEntry;

import com.team2052.frckrawler.models.SeasonDao;
import com.team2052.frckrawler.models.EventDao;
import com.team2052.frckrawler.models.TeamDao;
import com.team2052.frckrawler.models.MetricDao;
import com.team2052.frckrawler.models.MatchDatumDao;
import com.team2052.frckrawler.models.MatchCommentDao;
import com.team2052.frckrawler.models.RobotDao;
import com.team2052.frckrawler.models.RobotEventDao;
import com.team2052.frckrawler.models.PitDatumDao;
import com.team2052.frckrawler.models.ServerLogEntryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig seasonDaoConfig;
    private final DaoConfig eventDaoConfig;
    private final DaoConfig teamDaoConfig;
    private final DaoConfig metricDaoConfig;
    private final DaoConfig matchDatumDaoConfig;
    private final DaoConfig matchCommentDaoConfig;
    private final DaoConfig robotDaoConfig;
    private final DaoConfig robotEventDaoConfig;
    private final DaoConfig pitDatumDaoConfig;
    private final DaoConfig serverLogEntryDaoConfig;

    private final SeasonDao seasonDao;
    private final EventDao eventDao;
    private final TeamDao teamDao;
    private final MetricDao metricDao;
    private final MatchDatumDao matchDatumDao;
    private final MatchCommentDao matchCommentDao;
    private final RobotDao robotDao;
    private final RobotEventDao robotEventDao;
    private final PitDatumDao pitDatumDao;
    private final ServerLogEntryDao serverLogEntryDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        seasonDaoConfig = daoConfigMap.get(SeasonDao.class).clone();
        seasonDaoConfig.initIdentityScope(type);

        eventDaoConfig = daoConfigMap.get(EventDao.class).clone();
        eventDaoConfig.initIdentityScope(type);

        teamDaoConfig = daoConfigMap.get(TeamDao.class).clone();
        teamDaoConfig.initIdentityScope(type);

        metricDaoConfig = daoConfigMap.get(MetricDao.class).clone();
        metricDaoConfig.initIdentityScope(type);

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

        seasonDao = new SeasonDao(seasonDaoConfig, this);
        eventDao = new EventDao(eventDaoConfig, this);
        teamDao = new TeamDao(teamDaoConfig, this);
        metricDao = new MetricDao(metricDaoConfig, this);
        matchDatumDao = new MatchDatumDao(matchDatumDaoConfig, this);
        matchCommentDao = new MatchCommentDao(matchCommentDaoConfig, this);
        robotDao = new RobotDao(robotDaoConfig, this);
        robotEventDao = new RobotEventDao(robotEventDaoConfig, this);
        pitDatumDao = new PitDatumDao(pitDatumDaoConfig, this);
        serverLogEntryDao = new ServerLogEntryDao(serverLogEntryDaoConfig, this);

        registerDao(Season.class, seasonDao);
        registerDao(Event.class, eventDao);
        registerDao(Team.class, teamDao);
        registerDao(Metric.class, metricDao);
        registerDao(MatchDatum.class, matchDatumDao);
        registerDao(MatchComment.class, matchCommentDao);
        registerDao(Robot.class, robotDao);
        registerDao(RobotEvent.class, robotEventDao);
        registerDao(PitDatum.class, pitDatumDao);
        registerDao(ServerLogEntry.class, serverLogEntryDao);
    }

    public void clear() {
        seasonDaoConfig.clearIdentityScope();
        eventDaoConfig.clearIdentityScope();
        teamDaoConfig.clearIdentityScope();
        metricDaoConfig.clearIdentityScope();
        matchDatumDaoConfig.clearIdentityScope();
        matchCommentDaoConfig.clearIdentityScope();
        robotDaoConfig.clearIdentityScope();
        robotEventDaoConfig.clearIdentityScope();
        pitDatumDaoConfig.clearIdentityScope();
        serverLogEntryDaoConfig.clearIdentityScope();
    }

    public SeasonDao getSeasonDao() {
        return seasonDao;
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
