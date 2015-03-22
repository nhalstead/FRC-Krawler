package com.team2052.frckrawler.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.team2052.frckrawler.db.MatchComment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MATCH_COMMENT.
*/
public class MatchCommentDao extends AbstractDao<MatchComment, Long> {

    public static final String TABLENAME = "MATCH_COMMENT";

    /**
     * Properties of entity MatchComment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MatchId = new Property(1, Long.class, "matchId", false, "MATCH_ID");
        public final static Property RobotId = new Property(2, Long.class, "robotId", false, "ROBOT_ID");
        public final static Property EventId = new Property(3, Long.class, "eventId", false, "EVENT_ID");
        public final static Property Comment = new Property(4, String.class, "comment", false, "COMMENT");
    };

    private Query<MatchComment> match_MatchCommentListQuery;
    private Query<MatchComment> event_MatchCommentListQuery;
    private Query<MatchComment> robot_MatchCommentListQuery;

    public MatchCommentDao(DaoConfig config) {
        super(config);
    }
    
    public MatchCommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MATCH_COMMENT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'MATCH_ID' INTEGER," + // 1: matchId
                "'ROBOT_ID' INTEGER," + // 2: robotId
                "'EVENT_ID' INTEGER," + // 3: eventId
                "'COMMENT' TEXT);"); // 4: comment
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MATCH_COMMENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MatchComment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long matchId = entity.getMatchId();
        if (matchId != null) {
            stmt.bindLong(2, matchId);
        }
 
        Long robotId = entity.getRobotId();
        if (robotId != null) {
            stmt.bindLong(3, robotId);
        }
 
        Long eventId = entity.getEventId();
        if (eventId != null) {
            stmt.bindLong(4, eventId);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(5, comment);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MatchComment readEntity(Cursor cursor, int offset) {
        MatchComment entity = new MatchComment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // matchId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // robotId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // eventId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // comment
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MatchComment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMatchId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setRobotId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setEventId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setComment(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MatchComment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MatchComment entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "matchCommentList" to-many relationship of Match. */
    public List<MatchComment> _queryMatch_MatchCommentList(Long matchId) {
        synchronized (this) {
            if (match_MatchCommentListQuery == null) {
                QueryBuilder<MatchComment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MatchId.eq(null));
                match_MatchCommentListQuery = queryBuilder.build();
            }
        }
        Query<MatchComment> query = match_MatchCommentListQuery.forCurrentThread();
        query.setParameter(0, matchId);
        return query.list();
    }

    /** Internal query to resolve the "matchCommentList" to-many relationship of Event. */
    public List<MatchComment> _queryEvent_MatchCommentList(Long eventId) {
        synchronized (this) {
            if (event_MatchCommentListQuery == null) {
                QueryBuilder<MatchComment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.EventId.eq(null));
                event_MatchCommentListQuery = queryBuilder.build();
            }
        }
        Query<MatchComment> query = event_MatchCommentListQuery.forCurrentThread();
        query.setParameter(0, eventId);
        return query.list();
    }

    /** Internal query to resolve the "matchCommentList" to-many relationship of Robot. */
    public List<MatchComment> _queryRobot_MatchCommentList(Long robotId) {
        synchronized (this) {
            if (robot_MatchCommentListQuery == null) {
                QueryBuilder<MatchComment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RobotId.eq(null));
                robot_MatchCommentListQuery = queryBuilder.build();
            }
        }
        Query<MatchComment> query = robot_MatchCommentListQuery.forCurrentThread();
        query.setParameter(0, robotId);
        return query.list();
    }

}
