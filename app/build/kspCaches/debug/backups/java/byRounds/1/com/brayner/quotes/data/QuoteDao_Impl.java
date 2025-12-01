package com.brayner.quotes.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QuoteDao_Impl implements QuoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<Quote> __insertionAdapterOfQuote;

  private final EntityInsertionAdapter<Interaction> __insertionAdapterOfInteraction;

  public QuoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`userId`,`age`,`lifeStage`,`gender`,`mbtiType`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindLong(2, entity.getAge());
        statement.bindString(3, entity.getLifeStage());
        statement.bindString(4, entity.getGender());
        statement.bindString(5, entity.getMbtiType());
      }
    };
    this.__insertionAdapterOfQuote = new EntityInsertionAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quotes` (`quoteId`,`text`,`author`,`category`,`lifeStageTarget`,`personalityTarget`,`genderTarget`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getQuoteId());
        statement.bindString(2, entity.getText());
        statement.bindString(3, entity.getAuthor());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getLifeStageTarget());
        statement.bindString(6, entity.getPersonalityTarget());
        statement.bindString(7, entity.getGenderTarget());
      }
    };
    this.__insertionAdapterOfInteraction = new EntityInsertionAdapter<Interaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `interactions` (`interactionId`,`userId`,`quoteId`,`liked`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Interaction entity) {
        statement.bindLong(1, entity.getInteractionId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getQuoteId());
        final int _tmp = entity.getLiked() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertQuotes(final List<Quote> quotes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuote.insert(quotes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertInteraction(final Interaction interaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfInteraction.insert(interaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUser(final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfLifeStage = CursorUtil.getColumnIndexOrThrow(_cursor, "lifeStage");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfMbtiType = CursorUtil.getColumnIndexOrThrow(_cursor, "mbtiType");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpLifeStage;
            _tmpLifeStage = _cursor.getString(_cursorIndexOfLifeStage);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            final String _tmpMbtiType;
            _tmpMbtiType = _cursor.getString(_cursorIndexOfMbtiType);
            _result = new User(_tmpUserId,_tmpAge,_tmpLifeStage,_tmpGender,_tmpMbtiType);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllQuotes(final Continuation<? super List<Quote>> $completion) {
    final String _sql = "SELECT * FROM quotes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Quote>>() {
      @Override
      @NonNull
      public List<Quote> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfLifeStageTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "lifeStageTarget");
          final int _cursorIndexOfPersonalityTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "personalityTarget");
          final int _cursorIndexOfGenderTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "genderTarget");
          final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Quote _item;
            final int _tmpQuoteId;
            _tmpQuoteId = _cursor.getInt(_cursorIndexOfQuoteId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpLifeStageTarget;
            _tmpLifeStageTarget = _cursor.getString(_cursorIndexOfLifeStageTarget);
            final String _tmpPersonalityTarget;
            _tmpPersonalityTarget = _cursor.getString(_cursorIndexOfPersonalityTarget);
            final String _tmpGenderTarget;
            _tmpGenderTarget = _cursor.getString(_cursorIndexOfGenderTarget);
            _item = new Quote(_tmpQuoteId,_tmpText,_tmpAuthor,_tmpCategory,_tmpLifeStageTarget,_tmpPersonalityTarget,_tmpGenderTarget);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getQuoteById(final int id, final Continuation<? super Quote> $completion) {
    final String _sql = "SELECT * FROM quotes WHERE quoteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Quote>() {
      @Override
      @Nullable
      public Quote call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfLifeStageTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "lifeStageTarget");
          final int _cursorIndexOfPersonalityTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "personalityTarget");
          final int _cursorIndexOfGenderTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "genderTarget");
          final Quote _result;
          if (_cursor.moveToFirst()) {
            final int _tmpQuoteId;
            _tmpQuoteId = _cursor.getInt(_cursorIndexOfQuoteId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpLifeStageTarget;
            _tmpLifeStageTarget = _cursor.getString(_cursorIndexOfLifeStageTarget);
            final String _tmpPersonalityTarget;
            _tmpPersonalityTarget = _cursor.getString(_cursorIndexOfPersonalityTarget);
            final String _tmpGenderTarget;
            _tmpGenderTarget = _cursor.getString(_cursorIndexOfGenderTarget);
            _result = new Quote(_tmpQuoteId,_tmpText,_tmpAuthor,_tmpCategory,_tmpLifeStageTarget,_tmpPersonalityTarget,_tmpGenderTarget);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserInteractions(final int userId,
      final Continuation<? super List<Interaction>> $completion) {
    final String _sql = "SELECT * FROM interactions WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Interaction>>() {
      @Override
      @NonNull
      public List<Interaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInteractionId = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "liked");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Interaction> _result = new ArrayList<Interaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Interaction _item;
            final int _tmpInteractionId;
            _tmpInteractionId = _cursor.getInt(_cursorIndexOfInteractionId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final int _tmpQuoteId;
            _tmpQuoteId = _cursor.getInt(_cursorIndexOfQuoteId);
            final boolean _tmpLiked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfLiked);
            _tmpLiked = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new Interaction(_tmpInteractionId,_tmpUserId,_tmpQuoteId,_tmpLiked,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Interaction>> getAllInteractions() {
    final String _sql = "SELECT * FROM interactions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"interactions"}, new Callable<List<Interaction>>() {
      @Override
      @NonNull
      public List<Interaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInteractionId = CursorUtil.getColumnIndexOrThrow(_cursor, "interactionId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfQuoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "quoteId");
          final int _cursorIndexOfLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "liked");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Interaction> _result = new ArrayList<Interaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Interaction _item;
            final int _tmpInteractionId;
            _tmpInteractionId = _cursor.getInt(_cursorIndexOfInteractionId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final int _tmpQuoteId;
            _tmpQuoteId = _cursor.getInt(_cursorIndexOfQuoteId);
            final boolean _tmpLiked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfLiked);
            _tmpLiked = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new Interaction(_tmpInteractionId,_tmpUserId,_tmpQuoteId,_tmpLiked,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
