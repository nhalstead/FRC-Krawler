package com.team2052.frckrawler.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table PICK_LIST.
 */
public class PickListDao extends AbstractDao<PickList, Long> {

    public static final String TABLENAME = "PICK_LIST";
    private Query<PickList> event_PickListListQuery;

    ;

    public PickListDao(DaoConfig config) {
        super(config);
    }

    public PickListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'PICK_LIST' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'EVENT_ID' INTEGER," + // 2: eventId
                "'DATA' TEXT);"); // 3: data
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PICK_LIST'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, PickList entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }

        Long eventId = entity.getEventId();
        if (eventId != null) {
            stmt.bindLong(3, eventId);
        }

        String data = entity.getData();
        if (data != null) {
            stmt.bindString(4, data);
        }
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
    public PickList readEntity(Cursor cursor, int offset) {
        PickList entity = new PickList( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // eventId
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // data
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, PickList entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEventId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setData(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(PickList entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(PickList entity) {
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

    /**
     * Internal query to resolve the "pickListList" to-many relationship of Event.
     */
    public List<PickList> _queryEvent_PickListList(Long eventId) {
        synchronized (this) {
            if (event_PickListListQuery == null) {
                QueryBuilder<PickList> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.EventId.eq(null));
                event_PickListListQuery = queryBuilder.build();
            }
        }
        Query<PickList> query = event_PickListListQuery.forCurrentThread();
        query.setParameter(0, eventId);
        return query.list();
    }

    /**
     * Properties of entity PickList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property EventId = new Property(2, Long.class, "eventId", false, "EVENT_ID");
        public final static Property Data = new Property(3, String.class, "data", false, "DATA");
    }

}
