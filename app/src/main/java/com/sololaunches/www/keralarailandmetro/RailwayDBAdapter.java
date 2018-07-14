package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by hp on 7/1/2017.
 */

public class RailwayDBAdapter extends SQLiteOpenHelper {

    private Context contextC;
    DBInserter dbinserter;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Kerala_Railway.db";


    public RailwayDBAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        this.contextC = context;
        dbinserter = new DBInserter();
        dbinserter.insertAllValues(context, db);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        // Context con  = getApplicationContext();
        String query = QueryCreator.getCreateRailwayMainQuery();                //Railway Main table
        String queryMetro = QueryCreator.getCreateMetroQuery();
        // Railway secondary table

        db.execSQL(query);
        db.execSQL(queryMetro);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor checkDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from RL_STATIONS_TRAIN_MAIN";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getMetroFares(String dest, String source) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select " + source + " from METRO_MAIN WHERE STATION='" + dest + "' AND TRAIN IS NULL";
        Log.d("get Query ", "getMetroFares: " + query);
        //( MAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT , TRAIN_NUMBER TEXT,TRAIN_NAME
        Cursor cur = db.rawQuery(query, null);
        Log.d("get Result ", "getMetroFares: " + cur);

        return cur;

    }


    public Cursor getMetroTimeTables() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from METRO_MAIN WHERE STATION IS NULL";
        Log.d("get Query ", "getMetro Timetable: " + query);
        //( MAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT , TRAIN_NUMBER TEXT,TRAIN_NAME
        Cursor cur = db.rawQuery(query, null);
        Log.d("get Result ", "Timetable: " + cur.getColumnNames());
        return cur;
    }


    public Cursor getAllTrainListNew() {
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "Select * from RL_STATIONS_TRAIN_MAIN";
        //( MAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT , TRAIN_NUMBER TEXT,TRAIN_NAME
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getAllTrainList(String dest, String source) {
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "Select * from RL_STATIONS_TRAIN_MAIN WHERE " + dest + " IS NOT NULL AND " + source + " IS NOT NULL";
        //( MAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT , TRAIN_NUMBER TEXT,TRAIN_NAME
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getTimeTableForStation(String station) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from RL_STATIONS_TRAIN_MAIN";
        Cursor cur = db.rawQuery(query, null);
        return cur;

    }

    public Cursor getTrainJourney(String train) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from RL_STATIONS_TRAIN_MAIN WHERE TRAIN_NUMBER='" + train + "'";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getAllTrainListForUpdate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from RL_STATIONS_TRAIN_MAIN";// WHERE UPDATED_ON != '" + updatedDate + "'";

        Log.d(" check Query", "run: " + query);

        Cursor cur = db.rawQuery(query, null);
        Log.d(" check Query", "cur: " + cur);

        return cur;
    }


    public class DBInserter {
        public void insertAllValues(Context context, SQLiteDatabase db) {


            String day = KeralaRailConvenience.getDayOfWeek();

            Cursor cur = checkDatabase();


            if (cur.getCount() > 0) {

                return;

            } else {

                try {


                    int result = 0;

                    // Open the resource
                    InputStream insertsStream = context.getResources().openRawResource(R.raw.inser_main);
                    BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

                    // Iterate through lines (assuming each insert has its own line and theres no other stuff)
                    while (insertReader.ready()) {
                        String insertStmt = insertReader.readLine();
                        Log.d("INSERTING", "onCreate: " + insertStmt);
                        db.execSQL(insertStmt);
                        result++;
                    }
                    insertReader.close();

                    // returning number of inserted rows

                    Log.e("Error ", "onCreate: " + result);
                } catch (Exception e) {
                    Log.e("Error ", "onCreate: " + e);
                }
            }

        }


    }


    public void updateScheduleTrain(String query) {


        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
        } catch (SQLException e) {
            Log.d("Cud not UPDATE", "updateScheduleTrain: ");
        }


    }

}
