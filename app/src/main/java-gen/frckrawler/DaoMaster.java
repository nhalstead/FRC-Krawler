package frckrawler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import frckrawler.GameDao;
import frckrawler.EventDao;
import frckrawler.TeamDao;
import frckrawler.UserDao;
import frckrawler.ContactDao;
import frckrawler.MatchDao;
import frckrawler.RobotDao;
import frckrawler.RobotEventDao;
import frckrawler.MetricDao;
import frckrawler.RobotPhotoDao;
import frckrawler.MatchDataDao;
import frckrawler.PitDataDao;
import frckrawler.MatchCommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        GameDao.createTable(db, ifNotExists);
        EventDao.createTable(db, ifNotExists);
        TeamDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        ContactDao.createTable(db, ifNotExists);
        MatchDao.createTable(db, ifNotExists);
        RobotDao.createTable(db, ifNotExists);
        RobotEventDao.createTable(db, ifNotExists);
        MetricDao.createTable(db, ifNotExists);
        RobotPhotoDao.createTable(db, ifNotExists);
        MatchDataDao.createTable(db, ifNotExists);
        PitDataDao.createTable(db, ifNotExists);
        MatchCommentDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        GameDao.dropTable(db, ifExists);
        EventDao.dropTable(db, ifExists);
        TeamDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        ContactDao.dropTable(db, ifExists);
        MatchDao.dropTable(db, ifExists);
        RobotDao.dropTable(db, ifExists);
        RobotEventDao.dropTable(db, ifExists);
        MetricDao.dropTable(db, ifExists);
        RobotPhotoDao.dropTable(db, ifExists);
        MatchDataDao.dropTable(db, ifExists);
        PitDataDao.dropTable(db, ifExists);
        MatchCommentDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(GameDao.class);
        registerDaoClass(EventDao.class);
        registerDaoClass(TeamDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(ContactDao.class);
        registerDaoClass(MatchDao.class);
        registerDaoClass(RobotDao.class);
        registerDaoClass(RobotEventDao.class);
        registerDaoClass(MetricDao.class);
        registerDaoClass(RobotPhotoDao.class);
        registerDaoClass(MatchDataDao.class);
        registerDaoClass(PitDataDao.class);
        registerDaoClass(MatchCommentDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
