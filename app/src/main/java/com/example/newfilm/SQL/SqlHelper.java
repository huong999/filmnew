package com.example.newfilm.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newfilm.Model.Account;
import com.example.newfilm.Model.Video;

import java.util.ArrayList;
import java.util.List;

import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_PHONE;
import static com.example.newfilm.Model.AttributeVideo.DATE;
import static com.example.newfilm.Model.AttributeVideo.DESCRIPTION;
import static com.example.newfilm.Model.AttributeVideo.ID;
import static com.example.newfilm.Model.AttributeVideo.IDACC;
import static com.example.newfilm.Model.AttributeVideo.KIND;
import static com.example.newfilm.Model.AttributeVideo.PLAYLISTID;
import static com.example.newfilm.Model.AttributeVideo.PUBLISHEDAT;
import static com.example.newfilm.Model.AttributeVideo.TITLE;
import static com.example.newfilm.Model.AttributeVideo.URL;
import static com.example.newfilm.Model.AttributeVideo.VIDEOID;

public class SqlHelper extends
        SQLiteOpenHelper {

    private static final String TAG = "SqlHelper";
    static final String DB_NAME = "NEWFILM.db";
    static final String DB_TABLE_ALL_FILM = "ALLFILM";
    static final String DB_TABLE_HISTORY = "LichSu";
    static final String DB_TABLE_XEM_SAU = "XemSau";
    static final String DB_TABLE_ACCOUNT = "Account";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    public SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryCreateTableAccount = "CREATE TABLE "+ DB_TABLE_ACCOUNT +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "phone Text," +
                "password Text," +
                "fullname Text,"+
                "address Text)";
        db.execSQL(queryCreateTableAccount);

        String queryCreateTableAllfilm = "CREATE TABLE "+ DB_TABLE_ALL_FILM +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "publishedAt Text," +
                "title Text," +
                "description Text," +
                "url Text,"+
                "kind Text,"+
                "videoID Text,"+
                "playlistId Text)";
        db.execSQL(queryCreateTableAllfilm);
        String queryCreateTableHistory = "CREATE TABLE "+ DB_TABLE_HISTORY +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idAcc INTEGER,"+
                "publishedAt Text," +
                "title Text," +
                "description Text," +
                "url Text,"+
                "kind Text,"+
                "videoID Text,"+
                "playlistId Text,"+
                "date Text)";
        db.execSQL(queryCreateTableHistory);

        String queryCreateTableXemSau = "CREATE TABLE "+ DB_TABLE_XEM_SAU +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idAcc INTEGER,"+
                "publishedAt Text," +
                "title Text," +
                "description Text," +
                "url Text,"+
                "kind Text,"+
                "videoID Text,"+
                "playlistId Text,"+
                "date Text)";
        db.execSQL(queryCreateTableXemSau);
    }


    public void InsertFilmToAllFilm(Video video) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(PUBLISHEDAT, video.getPublishedAt());
        contentValues.put(TITLE, video.getTitle());
        contentValues.put(DESCRIPTION, video.getDescription());
        contentValues.put(URL, video.getUrl());
        contentValues.put(KIND, video.getKind());
        contentValues.put(VIDEOID, video.getVideoID());
        contentValues.put(PLAYLISTID, video.getPlaylistId());
        sqLiteDatabase.insert(DB_TABLE_ALL_FILM, null, contentValues);
    }
    public List<Video> getAllFilm(){
        List<Video> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ALL_FILM,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String publishedAt =cursor.getString(cursor.getColumnIndex(PUBLISHEDAT));
            String title =cursor.getString(cursor.getColumnIndex(TITLE));
            String description =cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String url =cursor.getString(cursor.getColumnIndex(URL));
            String kind =cursor.getString(cursor.getColumnIndex(KIND));
            String videoID =cursor.getString(cursor.getColumnIndex(VIDEOID));
            String playlistId =cursor.getString(cursor.getColumnIndex(PLAYLISTID));
            //String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, int id) {
            list.add(new Video(publishedAt,title,description,url,kind,videoID,playlistId,id));
        }
        return list;
    }
    public int deleteItemInFilm(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_ALL_FILM, "id=?", new String[]{String.valueOf(id)});
    }

    public void InsertFilmToHistory(Video video) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(PUBLISHEDAT, video.getPublishedAt());
        contentValues.put(TITLE, video.getTitle());
        contentValues.put(DESCRIPTION, video.getDescription());
        contentValues.put(URL, video.getUrl());
        contentValues.put(KIND, video.getKind());
        contentValues.put(VIDEOID, video.getVideoID());
        contentValues.put(PLAYLISTID, video.getPlaylistId());
        contentValues.put(DATE, video.getDate());
        contentValues.put(IDACC, video.getIdAcc());
        sqLiteDatabase.insert(DB_TABLE_HISTORY, null, contentValues);
    }
    public List<Video> getAllFilmHistory(){
        List<Video> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
            while (cursor.moveToNext()){

            String publishedAt =cursor.getString(cursor.getColumnIndex(PUBLISHEDAT));
            String title =cursor.getString(cursor.getColumnIndex(TITLE));
            String description =cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String url =cursor.getString(cursor.getColumnIndex(URL));
            String kind =cursor.getString(cursor.getColumnIndex(KIND));
            String videoID =cursor.getString(cursor.getColumnIndex(VIDEOID));
            String playlistId =cursor.getString(cursor.getColumnIndex(PLAYLISTID));
            String date =cursor.getString(cursor.getColumnIndex(DATE));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            int idAcc =cursor.getInt(cursor.getColumnIndex(IDACC));

            list.add(new Video(publishedAt,title,description,url,kind,videoID,playlistId,date,id,idAcc));
        }
        return list;
    }
    public int deleteItemInHistory(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_HISTORY, "id=?", new String[]{String.valueOf(id)});
    }

    public void InsertFilmToXemSau(Video video) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(PUBLISHEDAT, video.getPublishedAt());
        contentValues.put(TITLE, video.getTitle());
        contentValues.put(DESCRIPTION, video.getDescription());
        contentValues.put(URL, video.getUrl());
        contentValues.put(KIND, video.getKind());
        contentValues.put(VIDEOID, video.getVideoID());
        contentValues.put(PLAYLISTID, video.getPlaylistId());
        contentValues.put(DATE, video.getDate());
        contentValues.put(IDACC, video.getIdAcc());
        sqLiteDatabase.insert(DB_TABLE_XEM_SAU, null, contentValues);
    }
    public List<Video> getAllFilmXemSau(){
        List<Video> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_XEM_SAU,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){

            String publishedAt =cursor.getString(cursor.getColumnIndex(PUBLISHEDAT));
            String title =cursor.getString(cursor.getColumnIndex(TITLE));
            String description =cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String url =cursor.getString(cursor.getColumnIndex(URL));
            String kind =cursor.getString(cursor.getColumnIndex(KIND));
            String videoID =cursor.getString(cursor.getColumnIndex(VIDEOID));
            String playlistId =cursor.getString(cursor.getColumnIndex(PLAYLISTID));
            String date =cursor.getString(cursor.getColumnIndex(DATE));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            int idAcc =cursor.getInt(cursor.getColumnIndex(IDACC));

            list.add(new Video(publishedAt,title,description,url,kind,videoID,playlistId,date,id,idAcc));
        }
        return list;
    }
    public int deleteItemInXemSau(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_XEM_SAU, "id=?", new String[]{String.valueOf(id)});
    }



    public void InsertAccount(Account account) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("phone", account.getPhone());
        contentValues.put("password", account.getPassword());
        contentValues.put("fullname", account.getFullName());
        contentValues.put("address", account.getAddress());
        sqLiteDatabase.insert(DB_TABLE_ACCOUNT, null, contentValues);
    }
    public List<Account> getAllAccount(){
        List<Account> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        //cursor con trỏ dữ liệu
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ACCOUNT,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String phone =cursor.getString(cursor.getColumnIndex("phone"));
            String password =cursor.getString(cursor.getColumnIndex("password"));
            String fullname =cursor.getString(cursor.getColumnIndex("fullname"));
            String address =cursor.getString(cursor.getColumnIndex("address"));
            list.add(new Account(id,phone,password,fullname,address));
        }
        return list;
    }


    public void upDatePass(String sdt, String pass){
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("password",pass);
        sqLiteDatabase.update(DB_TABLE_ACCOUNT,contentValues,"phone" + " = ?",new String[] {sdt});
         sqLiteDatabase.close();
    }










    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }


}
