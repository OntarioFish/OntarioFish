/*Class manages database and database functions specific to OntarioFish
***DO NOT EDIT***
Bruce Stuff
 */

package android.example.ontariofish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Strings are initialized to make code more readable later on
    public static final String DATABASE_NAME = "OntarioFish.db";

    public static final String FISH_REGULATIONS_TABLE = "FishRegulations";
    public static final String FISHREGULATIONSCOL1 = "REGION";
    public static final String FISHREGULATIONSCOL2 = "NAME";
    public static final String FISHREGULATIONSCOL3 = "SEASON";
    public static final String FISHREGULATIONSCOL4 = "LIMITS";

    public static final String FISH_EXCEPTIONS_TABLE = "FishExceptions";
    public static final String FISHEXCEPTIONSCOL1 = "REGION";
    public static final String FISHEXCEPTIONSCOL2 = "NAME";
    public static final String FISHEXCEPTIONSCOL3 = "LAKE";
    public static final String FISHEXCEPTIONSCOL4 = "INFO";
    public static final String FISHEXCEPTIONSCOL5 = "SEASON";
    public static final String FISHEXCEPTIONSCOL6 = "LIMITS";

    public static final String WATERBODY_EXCEPTIONS_TABLE = "WaterbodyExceptions";
    public static final String WATERBODYEXCEPTIONSCOL1 = "INFO1";
    public static final String WATERBODYEXCEPTIONSCOL2 = "INFO2";
    public static final String WATERBODYEXCEPTIONSCOL3 = "INFO3";
    public static final String WATERBODYEXCEPTIONSCOL4 = "INFO4";
    public static final String WATERBODYEXCEPTIONSCOL5 = "INFO5";
    public static final String WATERBODYEXCEPTIONSCOL6 = "INFO6";
    public static final String WATERBODYEXCEPTIONSCOL7 = "INFO7";
    public static final String WATERBODYEXCEPTIONSCOL8 = "INFO8";
    public static final String WATERBODYEXCEPTIONSCOL9 = "INFO9";
    public static final String WATERBODYEXCEPTIONSCOL10 = "INFO10";
    public static final String WATERBODYEXCEPTIONSCOL11 = "INFO11";
    public static final String WATERBODYEXCEPTIONSCOL12 = "INFO12";
    public static final String WATERBODYEXCEPTIONSCOL13 = "INFO13";
    public static final String WATERBODYEXCEPTIONSCOL14 = "INFO14";
    public static final String WATERBODYEXCEPTIONSCOL15 = "INFO15";



    //Creates the database; actual processes hidden in library
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    //Creates Fish Info and Fish Regulations tables on creation of a database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FISH_REGULATIONS_TABLE + " (REGION TEXT, NAME TEXT, SEASON TEXT, LIMITS TEXT)");
        db.execSQL("CREATE TABLE " + FISH_EXCEPTIONS_TABLE + " (REGION TEXT, NAME TEXT, LAKE TEXT, INFO TEXT, SEASON TEXT, LIMITS TEXT)");
        db.execSQL("CREATE TABLE " + WATERBODY_EXCEPTIONS_TABLE + "(INFO1, INFO2, INFO3, INFO4, INFO5, INFO6, INFO7, INFO8, INFO9, INFO10, INFO11, INFO12, INFO13, INFO14, INFO15)");
    }


    //If database gets upgraded, moves everything correctly
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FISH_REGULATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FISH_EXCEPTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WATERBODY_EXCEPTIONS_TABLE);
        onCreate(db);

    }

    //Inserts all info into the FishRegulations table
    public boolean insertDataFishRegulations(String region, String name, String season, String limits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISHREGULATIONSCOL1, region);
        contentValues.put(FISHREGULATIONSCOL2, name);
        contentValues.put(FISHREGULATIONSCOL3, season);
        contentValues.put(FISHREGULATIONSCOL4, limits);
        long result = db.insert(FISH_REGULATIONS_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Inserts a row into the fishExceptions table
    public boolean insertDataFishExceptions(String region, String name, String lake, String info, String season, String limits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISHEXCEPTIONSCOL1, region);
        contentValues.put(FISHEXCEPTIONSCOL2, name);
        contentValues.put(FISHEXCEPTIONSCOL3, lake);
        contentValues.put(FISHEXCEPTIONSCOL4, info);
        contentValues.put(FISHEXCEPTIONSCOL5, season);
        contentValues.put(FISHEXCEPTIONSCOL6, limits);
        long result = db.insert(FISH_EXCEPTIONS_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    public boolean insertDataWaterbodyExceptions(String info1, String info2, String info3, String info4, String info5, String info6, String info7, String info8, String info9, String info10, String info11, String info12, String info13, String info14, String info15){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WATERBODYEXCEPTIONSCOL1, info1);
        contentValues.put(WATERBODYEXCEPTIONSCOL2, info2);
        contentValues.put(WATERBODYEXCEPTIONSCOL3, info3);
        contentValues.put(WATERBODYEXCEPTIONSCOL4, info4);
        contentValues.put(WATERBODYEXCEPTIONSCOL5, info5);
        contentValues.put(WATERBODYEXCEPTIONSCOL6, info6);
        contentValues.put(WATERBODYEXCEPTIONSCOL7, info7);
        contentValues.put(WATERBODYEXCEPTIONSCOL8, info8);
        contentValues.put(WATERBODYEXCEPTIONSCOL9, info9);
        contentValues.put(WATERBODYEXCEPTIONSCOL10, info10);
        contentValues.put(WATERBODYEXCEPTIONSCOL11, info11);
        contentValues.put(WATERBODYEXCEPTIONSCOL12, info12);
        contentValues.put(WATERBODYEXCEPTIONSCOL13, info13);
        contentValues.put(WATERBODYEXCEPTIONSCOL14, info14);
        contentValues.put(WATERBODYEXCEPTIONSCOL15, info15);
        long result = db.insert(WATERBODY_EXCEPTIONS_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Returns an array consisting of fish name, overview, appearance, size, and habitat
    public String[] getRegulationsInfo(String region, String fish){
        String[] regulationsArray = {"0","0"};
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT * FROM " + FISH_REGULATIONS_TABLE + " WHERE NAME = '" + fish + "' AND REGION = '" + region + "'", null);

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */
            res.moveToFirst();
            regulationsArray[0] = (res.getString(2));
            regulationsArray[1] = (res.getString(3));

            return regulationsArray;

        } catch(Exception e){
            return regulationsArray;
        }
    }

    //Returns all information about species exceptions
    public String[] getExceptionsInfo(String region, String name, String lake){
        String[] exceptionsArray = {"0","0","0"};
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT * FROM " + FISH_EXCEPTIONS_TABLE + " WHERE REGION = '" + region + "' AND NAME = '" + name + "' AND LAKE = '" + lake + "'", null);

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */
            res.moveToFirst();
            exceptionsArray[0] = (res.getString(3));
            exceptionsArray[1] = (res.getString(4));
            exceptionsArray[2] = (res.getString(5));

            return exceptionsArray;

        } catch(Exception e){
            return exceptionsArray;
        }
    }


    //Returns names of fish in a list based on their region
    public List<String> getRegulationsFish(String region){
        List<String> regulationsFish = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT NAME FROM " + FISH_REGULATIONS_TABLE + " WHERE REGION = '" + region + "'" , null);

            res.moveToFirst();
            while (!res.isAfterLast()) {
                regulationsFish.add(res.getString(0));

                res.moveToNext();
            }

            return regulationsFish;

        } catch(Exception e){
            return regulationsFish;
        }
    }

    public List<String> getExceptionsLake(String region, String name){
        List<String> exceptionsLake = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT LAKE FROM " + FISH_EXCEPTIONS_TABLE + " WHERE REGION = '" + region + "' AND NAME = '" + name + "'" , null);

            res.moveToFirst();
            while (!res.isAfterLast()) {
                exceptionsLake.add(res.getString(0));

                res.moveToNext();
            }

            return exceptionsLake;

        } catch(Exception e){
            return exceptionsLake;
        }
    }


    public String[][] getWaterbodyInfo(String region){
        String[][] exceptionsArray = new String[20][15];
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT * FROM " + WATERBODY_EXCEPTIONS_TABLE + " WHERE INFO1 = '" + region + "'", null);

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */
            int i  = 0;
            res.moveToFirst();
            while (!res.isAfterLast()) {
                exceptionsArray[i][0] = "0";
                exceptionsArray[i][1] = res.getString(1);
                exceptionsArray[i][2] = res.getString(2);
                exceptionsArray[i][3] = res.getString(3);
                exceptionsArray[i][4] = res.getString(4);
                exceptionsArray[i][5] = res.getString(5);
                exceptionsArray[i][6] = res.getString(6);
                exceptionsArray[i][7] = res.getString(7);
                exceptionsArray[i][8] = res.getString(8);
                exceptionsArray[i][9] = res.getString(9);
                exceptionsArray[i][10] = res.getString(10);
                exceptionsArray[i][11] = res.getString(11);
                exceptionsArray[i][12] = res.getString(12);
                exceptionsArray[i][13] = res.getString(13);
                exceptionsArray[i][14] = res.getString(14);

                i++;
                res.moveToNext();
            }

            return exceptionsArray;

        } catch(Exception e){
            return exceptionsArray;
        }
    }


    //Deletes a table: NO EASY WAY TO RECREATE
    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }


}