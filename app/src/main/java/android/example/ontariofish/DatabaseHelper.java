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

public class DatabaseHelper extends SQLiteOpenHelper {

    //Strings are initialized to make code more readable later on
    public static final String DATABASE_NAME = "OntarioFish.db";
    public static final String FISH_TABLE = "FishInfo";
    public static final String FISH_REGULATIONS_TABLE = "FishRegulations";
    public static final String FISHINFOCOL1 = "NAME";
    public static final String FISHINFOCOL2 = "OVERVIEW";
    public static final String FISHINFOCOL3 = "APPEARANCE";
    public static final String FISHINFOCOL4 = "SIZE";
    public static final String FISHINFOCOL5 = "HABRAN";
    public static final String FISHREGULATIONSCOL1 = "NAME";
    public static final String FISHREGULATIONSCOL2 = "LAKE";


    //Creates the database; actual processes hidden in library
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    //Creates Fish Info and Fish Regulations tables on creation of a database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FISH_TABLE + " (NAME TEXT UNIQUE, OVERVIEW TEXT, APPEARANCE TEXT, SIZE TEXT, HABRAN TEXT)");
        db.execSQL("CREATE TABLE " + FISH_REGULATIONS_TABLE + " (NAME TEXT, LAKE TEXT)");

    }


    //If database gets upgraded, moves everything correctly
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FISH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FISH_REGULATIONS_TABLE);
        onCreate(db);

    }

    //Inserts all fish info into the FishInfo table
    public boolean insertDataFishInfo(String name, String overview, String appearance, String size, String habran){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISHINFOCOL1, name);
        contentValues.put(FISHINFOCOL2, overview);
        contentValues.put(FISHINFOCOL3, appearance);
        contentValues.put(FISHINFOCOL4, size);
        contentValues.put(FISHINFOCOL5, habran);
        long result = db.insert(FISH_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Inserts all info into the FishRegulations table
    public boolean insertDataFishRegulations(String name, String lake){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISHREGULATIONSCOL1, name);
        contentValues.put(FISHREGULATIONSCOL2, lake);
        long result = db.insert(FISH_REGULATIONS_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Returns an array consisting of fish name, overview, appearance, size, and habitat
    public String[] getInfo(String fish){
        String[] fishArray = {"0","0","0","0","0"};
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //SQL query, gets all info where the fish name is the name passed into the function
            Cursor res = db.rawQuery("SELECT * FROM " + FISH_TABLE + " WHERE NAME = '" + fish + "'", null);

            /*Since only one column is read (IN THIS CASE), we do not need a while loop to loop
            through all the rows */
            res.moveToFirst();
            fishArray[0] = (res.getString(0));
            fishArray[1] = (res.getString(1));
            fishArray[2] = (res.getString(2));
            fishArray[3] = (res.getString(3));
            fishArray[4] = (res.getString(4));

            return fishArray;

        } catch(Exception e){
            return fishArray;
        }
    }

    //Deletes a table: NO EASY WAY TO RECREATE
    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}