package frckrawler;

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
 * DAO for table ROBOT_EVENT.
 */
public class RobotEventDao extends AbstractDao<RobotEvent, Long>
{

    public static final String TABLENAME = "ROBOT_EVENT";
    private DaoSession daoSession;

    ;
    private String selectDeep;


    public RobotEventDao(DaoConfig config)
    {
        super(config);
    }

    public RobotEventDao(DaoConfig config, DaoSession daoSession)
    {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists)
    {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'ROBOT_EVENT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'ROBOT_ID' INTEGER," + // 1: robotId
                "'EVENT_ID' INTEGER);"); // 2: eventId
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists)
    {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ROBOT_EVENT'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, RobotEvent entity)
    {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Long robotId = entity.getRobotId();
        if (robotId != null) {
            stmt.bindLong(2, robotId);
        }

        Long eventId = entity.getEventId();
        if (eventId != null) {
            stmt.bindLong(3, eventId);
        }
    }

    @Override
    protected void attachEntity(RobotEvent entity)
    {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset)
    {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public RobotEvent readEntity(Cursor cursor, int offset)
    {
        RobotEvent entity = new RobotEvent( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // robotId
                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // eventId
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, RobotEvent entity, int offset)
    {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRobotId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setEventId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(RobotEvent entity, long rowId)
    {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(RobotEvent entity)
    {
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
    protected boolean isEntityUpdateable()
    {
        return true;
    }

    protected String getSelectDeep()
    {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getRobotDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getEventDao().getAllColumns());
            builder.append(" FROM ROBOT_EVENT T");
            builder.append(" LEFT JOIN ROBOT T0 ON T.'ROBOT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN EVENT T1 ON T.'EVENT_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected RobotEvent loadCurrentDeep(Cursor cursor, boolean lock)
    {
        RobotEvent entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Robot robot = loadCurrentOther(daoSession.getRobotDao(), cursor, offset);
        entity.setRobot(robot);
        offset += daoSession.getRobotDao().getAllColumns().length;

        Event event = loadCurrentOther(daoSession.getEventDao(), cursor, offset);
        entity.setEvent(event);

        return entity;
    }

    public RobotEvent loadDeep(Long key)
    {
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
    public List<RobotEvent> loadAllDeepFromCursor(Cursor cursor)
    {
        int count = cursor.getCount();
        List<RobotEvent> list = new ArrayList<RobotEvent>(count);

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

    protected List<RobotEvent> loadDeepAllAndCloseCursor(Cursor cursor)
    {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<RobotEvent> queryDeep(String where, String... selectionArg)
    {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

    /**
     * Properties of entity RobotEvent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties
    {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RobotId = new Property(1, Long.class, "robotId", false, "ROBOT_ID");
        public final static Property EventId = new Property(2, Long.class, "eventId", false, "EVENT_ID");
    }

}
