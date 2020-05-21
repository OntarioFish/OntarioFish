package android.example.ontariofish;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "OntarioFishDb.db";
    public static final String FISH_TABLE = "FishInfo";
    public static final String FISH_REGULATIONS_TABLE = "FishRegulations";
    public static final String FISHINFOCOL1 = "NAME";
    public static final String FISHINFOCOL2 = "LOCATION";
    public static final String FISHREGULATIONSCOL1 = "NAME";
    public static final String FISHREGULATIONSCOL2 = "LAKE";


    //Creates the database; actual processes hidden in library
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    //Creates Fish Info and Fish Regulations tables on creation of a database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FISH_TABLE + " (NAME TEXT, LOCATION TEXT)");
        db.execSQL("CREATE TABLE " + FISH_REGULATIONS_TABLE + " (NAME TEXT, LAKE TEXT)");

    }


    //If database gets upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FISH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FISH_REGULATIONS_TABLE);
        onCreate(db);

    }

    //Inserts into the Fish Info table
    public boolean insertDataFishInfo(String name, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISHINFOCOL1, name);
        contentValues.put(FISHINFOCOL2, location);
        long result = db.insert(FISH_TABLE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Inserts into the regulations table
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

    //Deletes a table: NO EASY WAY TO RECREATE
    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}