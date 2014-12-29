package com.team2052.frckrawler.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table ROBOT.
 */
public class RobotDao extends AbstractDao<Robot, Long> {

    public static final String TABLENAME = "ROBOT";
    private DaoSession daoSession;
    ;
    private String selectDeep;


    public RobotDao(DaoConfig config) {
        super(config);
    }

    public RobotDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'ROBOT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'TEAM_ID' INTEGER," + // 1: teamId
                "'GAME_ID' INTEGER," + // 2: gameId
                "'COMMENTS' TEXT," + // 3: comments
                "'OPR' REAL);"); // 4: opr
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ROBOT'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Robot entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Long teamId = entity.getTeamId();
        if (teamId != null) {
            stmt.bindLong(2, teamId);
        }

        Long gameId = entity.getGameId();
        if (gameId != null) {
            stmt.bindLong(3, gameId);
        }

        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(4, comments);
        }

        Double opr = entity.getOpr();
        if (opr != null) {
            stmt.bindDouble(5, opr);
        }
    }

    @Override
    protected void attachEntity(Robot entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Robot readEntity(Cursor cursor, int offset) {
        Robot entity = new Robot( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // teamId
                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // gameId
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // comments
                cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4) // opr
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Robot entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTeamId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setGameId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setComments(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOpr(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(Robot entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(Robot entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTeamDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getGameDao().getAllColumns());
            builder.append(" FROM ROBOT T");
            builder.append(" LEFT JOIN TEAM T0 ON T.'TEAM_ID'=T0.'NUMBER'");
            builder.append(" LEFT JOIN GAME T1 ON T.'GAME_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected Robot loadCurrentDeep(Cursor cursor, boolean lock) {
        Robot entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Team team = loadCurrentOther(daoSession.getTeamDao(), cursor, offset);
        entity.setTeam(team);
        offset += daoSession.getTeamDao().getAllColumns().length;

        Game game = loadCurrentOther(daoSession.getGameDao(), cursor, offset);
        entity.setGame(game);

        return entity;
    }

    public Robot loadDeep(Long key) {
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
    public List<Robot> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Robot> list = new ArrayList<Robot>(count);

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

    protected List<Robot> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<Robot> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

    /**
     * Properties of entity Robot.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TeamId = new Property(1, Long.class, "teamId", false, "TEAM_ID");
        public final static Property GameId = new Property(2, Long.class, "gameId", false, "GAME_ID");
        public final static Property Comments = new Property(3, String.class, "comments", false, "COMMENTS");
        public final static Property Opr = new Property(4, Double.class, "opr", false, "OPR");
    }

}
