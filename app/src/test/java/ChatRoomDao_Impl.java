import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Chat;
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDao;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatRoomDao_Impl implements ChatRoomDao {
    private final RoomDatabase __db;

    private final EntityInsertionAdapter<Chat> __insertionAdapterOfChat;

    private final EntityDeletionOrUpdateAdapter<Chat> __updateAdapterOfChat;

    public ChatRoomDao_Impl(@NonNull final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfChat = new EntityInsertionAdapter<Chat>(__db) {
            @Override
            @NonNull
            protected String createQuery() {
                return "INSERT OR ABORT INTO `Chat` (`id`,`message`) VALUES (nullif(?, 0),?)";
            }

            @Override
            protected void bind(@NonNull final SupportSQLiteStatement statement,
                                @NonNull final Chat entity) {
                statement.bindLong(1, entity.getId());
                statement.bindString(2, entity.getMessage());
            }
        };
        this.__updateAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
            @Override
            @NonNull
            protected String createQuery() {
                return "UPDATE OR ABORT `Chat` SET `id` = ?,`message` = ? WHERE `id` = ?";
            }

            @Override
            protected void bind(@NonNull final SupportSQLiteStatement statement,
                                @NonNull final Chat entity) {
                statement.bindLong(1, entity.getId());
                statement.bindString(2, entity.getMessage());
                statement.bindLong(3, entity.getId());
            }
        };
    }

    @Override
    public void createChat(final Chat chat) {
        __db.assertNotSuspendingTransaction();
        __db.beginTransaction();
        try {
            __insertionAdapterOfChat.insert(chat);
            __db.setTransactionSuccessful();
        } finally {
            __db.endTransaction();
        }
    }

    @Override
    public int updateChat(final Chat contact) {
        __db.assertNotSuspendingTransaction();
        int _total = 0;
        __db.beginTransaction();
        try {
            _total += __updateAdapterOfChat.handle(contact);
            __db.setTransactionSuccessful();
            return _total;
        } finally {
            __db.endTransaction();
        }
    }

    @Override
    public Chat readChat(final int id) {
        final String _sql = "SELECT *FROM chat WHERE id = ?";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
        int _argIndex = 1;
        _statement.bindLong(_argIndex, id);
        __db.assertNotSuspendingTransaction();
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
            final Chat _result;
            if (_cursor.moveToFirst()) {
                final int _tmpId;
                _tmpId = _cursor.getInt(_cursorIndexOfId);
                final String _tmpMessage;
                _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
                _result = new Chat(_tmpId, _tmpMessage);
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    @Override
    public List<Chat> readAllChats() {
        final String _sql = "SELECT * FROM chat ORDER BY message";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
        __db.assertNotSuspendingTransaction();
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
            final List<Chat> _result = new ArrayList<Chat>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                final Chat _item;
                final int _tmpId;
                _tmpId = _cursor.getInt(_cursorIndexOfId);
                final String _tmpMessage;
                _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
                _item = new Chat(_tmpId, _tmpMessage);
                _result.add(_item);
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    @NonNull
    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
