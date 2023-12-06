import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Generated;

import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDao;
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDaoDatabase;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatRoomDaoDatabase_Impl extends ChatRoomDaoDatabase {
    private volatile ChatRoomDao _chatRoomDao;

    @Override
    @NonNull
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
        final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
            @Override
            public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `Chat` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `message` TEXT NOT NULL)");
                db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
                db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '48c0bee6ab48abc27ffaa6a105426d40')");
            }

            @Override
            public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
                db.execSQL("DROP TABLE IF EXISTS `Chat`");
                final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onDestructiveMigration(db);
                    }
                }
            }

            @Override
            public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onCreate(db);
                    }
                }
            }

            @Override
            public void onOpen(@NonNull final SupportSQLiteDatabase db) {
                mDatabase = db;
                internalInitInvalidationTracker(db);
                final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onOpen(db);
                    }
                }
            }

            @Override
            public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
                DBUtil.dropFtsSyncTriggers(db);
            }

            @Override
            public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
            }

            @Override
            @NonNull
            public RoomOpenHelper.ValidationResult onValidateSchema(
                    @NonNull final SupportSQLiteDatabase db) {
                final HashMap<String, TableInfo.Column> _columnsChat = new HashMap<String, TableInfo.Column>(2);
                _columnsChat.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
                _columnsChat.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
                final HashSet<TableInfo.ForeignKey> _foreignKeysChat = new HashSet<TableInfo.ForeignKey>(0);
                final HashSet<TableInfo.Index> _indicesChat = new HashSet<TableInfo.Index>(0);
                final TableInfo _infoChat = new TableInfo("Chat", _columnsChat, _foreignKeysChat, _indicesChat);
                final TableInfo _existingChat = TableInfo.read(db, "Chat");
                if (!_infoChat.equals(_existingChat)) {
                    return new RoomOpenHelper.ValidationResult(false, "Chat(com.laura.onemessagechat.model.Chat).\n"
                            + " Expected:\n" + _infoChat + "\n"
                            + " Found:\n" + _existingChat);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "48c0bee6ab48abc27ffaa6a105426d40", "821192f89b041e0eea31d0281fa0999a");
        final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
        final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
        return _helper;
    }

    @Override
    @NonNull
    protected InvalidationTracker createInvalidationTracker() {
        final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
        final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
        return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Chat");
    }

    @Override
    public void clearAllTables() {
        super.assertNotMainThread();
        final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            _db.execSQL("DELETE FROM `Chat`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    @Override
    @NonNull
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
        _typeConvertersMap.put(ChatRoomDao.class, ChatRoomDao_Impl.getRequiredConverters());
        return _typeConvertersMap;
    }

    @Override
    @NonNull
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
        return _autoMigrationSpecsSet;
    }

    @Override
    @NonNull
    public List<Migration> getAutoMigrations(
            @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        final List<Migration> _autoMigrations = new ArrayList<Migration>();
        return _autoMigrations;
    }

    @Override
    public ChatRoomDao getContactRoomDao() {
        if (_chatRoomDao != null) {
            return _chatRoomDao;
        } else {
            synchronized (this) {
                if (_chatRoomDao == null) {
                    _chatRoomDao = new ChatRoomDao_Impl(this);
                }
                return _chatRoomDao;
            }
        }
    }

    @NonNull
    @Override
    public ChatRoomDao getChatRoomDao() {
        return null;
    }
}
