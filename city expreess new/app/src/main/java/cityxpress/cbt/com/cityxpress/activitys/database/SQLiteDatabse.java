package cityxpress.cbt.com.cityxpress.activitys.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 8/13/2018.
 */

public class SQLiteDatabse extends SQLiteOpenHelper {

    public static final String DatabaseName = "StationAdress";
    public static final String TableName = "Adress";
    public static final String Stationnm = "Stationnm";
    public static final String stnLat = "stnLat";
    public static final String stnLon = "stnLon";
    public static final String destination_Tabel = "destination_Tabel";
    public static final String destination_Name = "destination_Name";
    public static final String history_Tabel = "history_Tabel";
    public static final String sourcetodest = "sourcetodest";

    public static final String station = "station";
    public static final String stationname = "stationname";
    public static final String stationcodecode = "stationcodecode";
    public static final String fevstation = "fevstation";
    public static final String local_History_Tabel = "local_History_Tabel";

    public SQLiteDatabse(Context context) {
        super(context, DatabaseName, null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TableName + "(stnnmae text,stnlat double,stnlon double)");
        db.execSQL("create table " + destination_Tabel + "( id INTEGER PRIMARY KEY AUTOINCREMENT,destination_Name text unique)");
        db.execSQL("create table " + history_Tabel + "( id INTEGER PRIMARY KEY AUTOINCREMENT,sourcetodest text unique)");
        db.execSQL("create table " + station + "( id INTEGER PRIMARY KEY AUTOINCREMENT,stationname text ,stationcodecode text)");
        db.execSQL("create table " + fevstation + "( id INTEGER PRIMARY KEY AUTOINCREMENT,stationname text)");
        db.execSQL("create table " + local_History_Tabel + "( id INTEGER PRIMARY KEY AUTOINCREMENT,sourcetodest text unique)");

        Log.e("aaa", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        db.execSQL("DROP TABLE IF EXISTS " + destination_Tabel);
        db.execSQL("DROP TABLE IF EXISTS " + history_Tabel);
        db.execSQL("DROP TABLE IF EXISTS " + station);
        db.execSQL("DROP TABLE IF EXISTS " + fevstation);
        onCreate(db);
    }


    public boolean insertStationData(String stnnm, String lat, String lon) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Stationnm, stnnm);
        contentValues.put(stnLat, lat);
        contentValues.put(stnLon, lon);

        long result = db.insert(TableName, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getStationAdress() {

        SQLiteDatabase db = this.getWritableDatabase();
        //    Cursor res = db.rawQuery("select * from " + TABLE_NOTIFICATION + " where refe_idf=" + ref_id, null);
        Cursor res = db.rawQuery("select * from " + TableName, null);

        return res;

    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TableName);

        return true;
    }

    public boolean addDestination(String dest) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(destination_Name, dest);
            long result = db.insert(destination_Tabel, null, contentValues);
            if (result == -1) {
                return false;
            }
            return true;

        } catch (Exception e) {
            Log.e("aaa", e.getStackTrace() + "");
        }
        return true;

    }

    public Cursor getDestination() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + destination_Tabel + " ORDER BY id DESC limit 5 ", null);

        return res;
    }

    public boolean addHistory(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sourcetodest, s);
        long result = db.insert(history_Tabel, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getHistory() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + history_Tabel + " ORDER BY id DESC ", null);

        return res;
    }

    public void removeFavorate(String s) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + history_Tabel + " WHERE " + sourcetodest + "= '" + s + "'");


    }

    public void addStation(String sname, String scode) {
        try {
            Log.e("database", "sname=" + sname + "  " + "scode=" + scode);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(stationname, sname);
            contentValues.put(stationcodecode, scode);
            long result = db.insert(station, null, contentValues);
            if (result == -1) {
                Log.e("database", "false");
            } else {
                Log.e("database", "true");
            }

        } catch (Exception e) {
            Log.e("aaa", e.getStackTrace() + "");
        }
    }


    public Cursor getStation() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + station, null);

        return res;
    }

    public Cursor getStationByStationata(String strname) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + station + " where " + stationname + " LIKE '%" + strname + "%'", null);

        return res;

    }
    public void addFevStation(String s)
    {
        Log.e("database", "in add fev station");

        try {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stationname", s);
        long result = db.insert(fevstation, null, contentValues);
        if (result == -1) {
            Log.e("database", "false");
        } else {
            Log.e("database", "true");
        }

    } catch (Exception e) {
        Log.e("aaa", e.getStackTrace() + "");
    }
    }

    public Cursor getFevStation() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + fevstation , null);

        return res;

    }


    public void deleteFevStation(String s) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + fevstation + " WHERE " + stationname + "= '" + s + "'");
        Log.e("local","delete called");
    }

    public boolean addLocalHistory(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sourcetodest, s);
        long result = db.insert(local_History_Tabel, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getFevLocal() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + local_History_Tabel , null);

        return res;
    }

    public Cursor getStationAll() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + station , null);

        return res;

    }
}
