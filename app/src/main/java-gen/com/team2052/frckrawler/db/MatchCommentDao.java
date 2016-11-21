package com.team2052.frckrawler.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "MATCH_COMMENT".
 */
public class MatchCommentDao extends AbstractDao<MatchComment, Long> {

    public static final String TABLENAME = "MATCH_COMMENT";
    private DaoSession daoSession;
    private Query<MatchComment> event_MatchCommentListQuery;
    private Query<MatchComment> robot_MatchCommentListQuery;
    private String selectDeep;

    public MatchCommentDao(DaoConfig config) {
        super(config);
    }

    public MatchCommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"MATCH_COMMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MATCH_NUMBER\" INTEGER," + // 1: match_number
                "\"MATCH_TYPE\" INTEGER," + // 2: match_type
                "\"ROBOT_ID\" INTEGER," + // 3: robot_id
                "\"EVENT_ID\" INTEGER," + // 4: event_id
                "\"COMMENT\" TEXT," + // 5: comment
                "\"LAST_UPDATED\" INTEGER);"); // 6: last_updated
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MATCH_COMMENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MatchComment entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Long match_number = entity.getMatch_number();
        if (match_number != null) {
            stmt.bindLong(2, match_number);
        }

        Integer match_type = entity.getMatch_type();
        if (match_type != null) {
            stmt.bindLong(3, match_type);
        }

        Long robot_id = entity.getRobot_id();
        if (robot_id != null) {
            stmt.bindLong(4, robot_id);
        }

        Long event_id = entity.getEvent_id();
        if (event_id != null) {
            stmt.bindLong(5, event_id);
        }

        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(6, comment);
        }

        java.util.Date last_updated = entity.getLast_updated();
        if (last_updated != null) {
            stmt.bindLong(7, last_updated.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MatchComment entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Long match_number = entity.getMatch_number();
        if (match_number != null) {
            stmt.bindLong(2, match_number);
        }

        Integer match_type = entity.getMatch_type();
        if (match_type != null) {
            stmt.bindLong(3, match_type);
        }

        Long robot_id = entity.getRobot_id();
        if (robot_id != null) {
            stmt.bindLong(4, robot_id);
        }

        Long event_id = entity.getEvent_id();
        if (event_id != null) {
            stmt.bindLong(5, event_id);
        }

        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(6, comment);
        }

        java.util.Date last_updated = entity.getLast_updated();
        if (last_updated != null) {
            stmt.bindLong(7, last_updated.getTime());
        }
    }

    @Override
    protected final void attachEntity(MatchComment entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public MatchComment readEntity(Cursor cursor, int offset) {
        MatchComment entity = new MatchComment( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // match_number
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // match_type
                cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // robot_id
                cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // event_id
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // comment
                cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)) // last_updated
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, MatchComment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMatch_number(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setMatch_type(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setRobot_id(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setEvent_id(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setComment(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLast_updated(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
    }

    @Override
    protected final Long updateKeyAfterInsert(MatchComment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(MatchComment entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MatchComment entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Internal query to resolve the "matchCommentList" to-many relationship of Event.
     */
    public List<MatchComment> _queryEvent_MatchCommentList(Long event_id) {
        synchronized (this) {
            if (event_MatchCommentListQuery == null) {
                QueryBuilder<MatchComment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Event_id.eq(null));
                event_MatchCommentListQuery = queryBuilder.build();
            }
        }
        Query<MatchComment> query = event_MatchCommentListQuery.forCurrentThread();
        query.setParameter(0, event_id);
        return query.list();
    }

    /**
     * Internal query to resolve the "matchCommentList" to-many relationship of Robot.
     */
    public List<MatchComment> _queryRobot_MatchCommentList(Long robot_id) {
        synchronized (this) {
            if (robot_MatchCommentListQuery == null) {
                QueryBuilder<MatchComment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Robot_id.eq(null));
                robot_MatchCommentListQuery = queryBuilder.build();
            }
        }
        Query<MatchComment> query = robot_MatchCommentListQuery.forCurrentThread();
        query.setParameter(0, robot_id);
        return query.list();
    }

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getRobotDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getEventDao().getAllColumns());
            builder.append(" FROM MATCH_COMMENT T");
            builder.append(" LEFT JOIN ROBOT T0 ON T.\"ROBOT_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN EVENT T1 ON T.\"EVENT_ID\"=T1.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected MatchComment loadCurrentDeep(Cursor cursor, boolean lock) {
        MatchComment entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Robot robot = loadCurrentOther(daoSession.getRobotDao(), cursor, offset);
        entity.setRobot(robot);
        offset += daoSession.getRobotDao().getAllColumns().length;

        Event event = loadCurrentOther(daoSession.getEventDao(), cursor, offset);
        entity.setEvent(event);

        return entity;
    }

    public MatchComment loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString()};
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     */
    public List<MatchComment> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MatchComment> list = new ArrayList<MatchComment>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<MatchComment> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<MatchComment> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

    /**
     * Properties of entity MatchComment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Match_number = new Property(1, Long.class, "match_number", false, "MATCH_NUMBER");
        public final static Property Match_type = new Property(2, Integer.class, "match_type", false, "MATCH_TYPE");
        public final static Property Robot_id = new Property(3, Long.class, "robot_id", false, "ROBOT_ID");
        public final static Property Event_id = new Property(4, Long.class, "event_id", false, "EVENT_ID");
        public final static Property Comment = new Property(5, String.class, "comment", false, "COMMENT");
        public final static Property Last_updated = new Property(6, java.util.Date.class, "last_updated", false, "LAST_UPDATED");
    }

}
