package com.example.myservice;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wangqiubo on 2017/5/11.
 */

public class MyContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.myservice.MyContentProvider";
    private static final Uri BOOK_URI =  Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE =1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase mDB = null;

    static {
        uriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        uriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        mDB = new DBOpenHelper(getContext(), null, null, 0).getWritableDatabase();
        ininData();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Debug.waitForDebugger();
        int a = 0;
        Log.d("ContentProvider", "current Thread: " + Thread.currentThread());
        String tableName = getTableName(uri);
        if (null == tableName)
            return null;
        return mDB.query(tableName, projection, selection, selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (null == tableName)
            return null;
        long id = mDB.insert(tableName, null, values);
        this.getContext().getContentResolver().notifyChange(uri, null);
        Uri insertUri = ContentUris.withAppendedId(uri, id);
        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int rowId = mDB.update(tableName, values, selection, selectionArgs);
        return rowId;
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (uriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    public void ininData(){
        mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        mDB.execSQL("insert into book values(1,'android');");
        mDB.execSQL("insert into book values(2,'java');");
        mDB.execSQL("insert into book values(3,'html');");
        mDB.execSQL("insert into book values(4,'C#');");
        mDB.execSQL("insert into user values(1,'jack', 0);");
        mDB.execSQL("insert into user values(2,'tom', 1);");
    }
}
