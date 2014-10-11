package frckrawler;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import frckrawler.MatchComment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MATCH_COMMENT.
*/
public class MatchCommentDao extends AbstractDao<MatchComment, Void> {

    public static final String TABLENAME = "MATCH_COMMENT";

    /**
     * Properties of entity MatchComment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property RobotId = new Property(0, Long.class, "robotId", false, "ROBOT_ID");
        public final static Property MatchId = new Property(1, Long.class, "matchId", false, "MATCH_ID");
        public final static Property Comment = new Property(2, String.class, "comment", false, "COMMENT");
    };

    private DaoSession daoSession;


    public MatchCommentDao(DaoConfig config) {
        super(config);
    }
    
    public MatchCommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MATCH_COMMENT' (" + //
                "'ROBOT_ID' INTEGER," + // 0: robotId
                "'MATCH_ID' INTEGER," + // 1: matchId
                "'COMMENT' TEXT);"); // 2: comment
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
 
        Long robotId = entity.getRobotId();
        if (robotId != null) {
            stmt.bindLong(1, robotId);
        }
 
        Long matchId = entity.getMatchId();
        if (matchId != null) {
            stmt.bindLong(2, matchId);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(3, comment);
        }
    }

    @Override
    protected void attachEntity(MatchComment entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public MatchComment readEntity(Cursor cursor, int offset) {
        MatchComment entity = new MatchComment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // robotId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // matchId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // comment
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MatchComment entity, int offset) {
        entity.setRobotId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMatchId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setComment(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(MatchComment entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(MatchComment entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getRobotDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getMatchDao().getAllColumns());
            builder.append(" FROM MATCH_COMMENT T");
            builder.append(" LEFT JOIN ROBOT T0 ON T.'ROBOT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN MATCH T1 ON T.'MATCH_ID'=T1.'_id'");
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

        Match match = loadCurrentOther(daoSession.getMatchDao(), cursor, offset);
        entity.setMatch(match);

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
        
        String[] keyArray = new String[] { key.toString() };
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
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
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
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MatchComment> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
