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


    //Creates the database; actual processes hidden in library
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    //Creates Fish Info and Fish Regulations tables on creation of a database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FISH_REGULATIONS_TABLE + " (REGION TEXT, NAME TEXT, SEASON TEXT, LIMITS TEXT)");
        db.execSQL("CREATE TABLE " + FISH_EXCEPTIONS_TABLE + " (REGION TEXT, NAME TEXT, LAKE TEXT, INFO TEXT, SEASON TEXT, LIMITS TEXT)");
    }


    //If database gets upgraded, moves everything correctly
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FISH_REGULATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FISH_EXCEPTIONS_TABLE);
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
        String[] exceptionsArray = {"0","0","0","0","0","0"};
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT * FROM " + FISH_EXCEPTIONS_TABLE + " WHERE REGION = '" + region + "' AND NAME = '" + name + "' AND LAKE = '" + lake + "'", null);

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */
            res.moveToFirst();
            exceptionsArray[0] = (res.getString(0));
            exceptionsArray[1] = (res.getString(1));
            exceptionsArray[2] = (res.getString(2));
            exceptionsArray[3] = (res.getString(3));
            exceptionsArray[4] = (res.getString(4));
            exceptionsArray[5] = (res.getString(5));

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

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */

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

    //Deletes a table: NO EASY WAY TO RECREATE
    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}