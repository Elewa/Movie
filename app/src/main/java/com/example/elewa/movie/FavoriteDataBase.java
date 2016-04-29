package com.example.elewa.movie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.elewa.movie.DataInformation.MovieInfo;

import java.util.ArrayList;

/**
 * Created by elewa on 4/28/16.
 */
public class FavoriteDataBase {


    private FilmHelper NHelper;
    private SQLiteDatabase mDatabase;

    public FavoriteDataBase(Context context) {
        NHelper = new FilmHelper(context);
        mDatabase = NHelper.getWritableDatabase();
    }

    // ------------------ insert single row each time -----------------------------------
    public void insertMovies(MovieInfo movieInfo) {

        //create a sql prepared statement
        String sql = "INSERT INTO " + FilmHelper.TABLE_FILMS + " VALUES (?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        statement.bindLong(2, movieInfo.getId());
        statement.bindString(3, movieInfo.getPoster_path());
        statement.bindString(4, movieInfo.getOriginal_title());
        statement.bindString(5, String.valueOf(movieInfo.getRelease_date()));
        statement.bindDouble(6, movieInfo.getVote_average());
        statement.bindString(7, movieInfo.getOverview());

        statement.execute();


        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    //------------------------- Select All data from DataBase--------------------------------------
    public ArrayList<MovieInfo> readMovies() {
        ArrayList<MovieInfo> listMovies = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {
                FilmHelper.COLUMN_ID,
                FilmHelper.COLUMN_IMAGE,
                FilmHelper.COLUMN_TITLe,
                FilmHelper.COLUMN_DATE,
                FilmHelper.COLUMN_RAtE,
                FilmHelper.COLUMN_OVERVIEW,

        };
        Cursor cursor = mDatabase.query(FilmHelper.TABLE_FILMS, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                //create a new FILms object and retrieve the data from the cursor to be stored in this FILms object
                MovieInfo info = new MovieInfo();
                //each step is a 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank FILms object to contain our data
                info.setId(cursor.getInt(cursor.getColumnIndex(FilmHelper.COLUMN_ID)));
                info.setPoster_path(cursor.getString(cursor.getColumnIndex(FilmHelper.COLUMN_IMAGE)));
                info.setOriginal_title(cursor.getString(cursor.getColumnIndex(FilmHelper.COLUMN_TITLe)));
                info.setRelease_date(cursor.getString(cursor.getColumnIndex(FilmHelper.COLUMN_DATE)));
                info.setVote_average(cursor.getDouble(cursor.getColumnIndex(FilmHelper.COLUMN_RAtE)));
                info.setOverview(cursor.getString(cursor.getColumnIndex(FilmHelper.COLUMN_OVERVIEW)));

                listMovies.add(info);
            }
            while (cursor.moveToNext());
        }
        return listMovies;
    }

    //---------------------------------Check if movie is Excite or not ------------------------------------
    public boolean MovieExcit(String name) {
        ArrayList<MovieInfo> listMovies = new ArrayList<>();
        String selectionArgs[] = {name};
        String[] columns = {
                FilmHelper.COLUMN_ID
        };

        Cursor cursor = mDatabase.query(FilmHelper.TABLE_FILMS, columns, FilmHelper.COLUMN_TITLe + " =?", selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                //create a new FILms object and retrieve the data from the cursor to be stored in this FILms object
                MovieInfo info = new MovieInfo();
                info.setId(cursor.getInt(cursor.getColumnIndex(FilmHelper.COLUMN_ID)));
                listMovies.add(info);
            }
            while (cursor.moveToNext());
        }
        if (listMovies.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //------------------------- Delete selected Row form DataBase ---------------------------
    public int delete(String name) {

        String selectionArgs[] = {name};
        String[] columns = {
                FilmHelper.COLUMN_ID
        };
        int count = mDatabase.delete(FilmHelper.TABLE_FILMS, FilmHelper.COLUMN_TITLe + " =?", selectionArgs);
        return count;
    }


    // --------------------- conductor Value and Create table in one Class --------------------------
    private static class FilmHelper extends SQLiteOpenHelper {

        public static final String TABLE_FILMS = "Films";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_TITLe = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_RAtE = "RATE";
        public static final String COLUMN_OVERVIEW = "overview";


        private static final String CREATE_TABLE_FILMS = "CREATE TABLE " + TABLE_FILMS + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID + " INTEGER," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_TITLe + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_RAtE + " INTEGER," +
                COLUMN_OVERVIEW + " TEXT" +
                ");";

        private static final String DB_NAME = "Films_db";
        private static final int DB_VERSION = 2;
        private Context mContext;

        public FilmHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL(CREATE_TABLE_FILMS);

            } catch (SQLiteException exception) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(" DROP TABLE " + TABLE_FILMS + " IF EXISTS;");
                onCreate(db);
            } catch (SQLiteException exception) {

            }
        }
    }

}
