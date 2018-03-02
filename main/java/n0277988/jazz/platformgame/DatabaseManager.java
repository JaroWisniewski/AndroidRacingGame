package n0277988.jazz.platformgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaros on 01/03/2018.
 *
 * Database Manager using SQLite for storing the scores
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Score.db";
    private static final String TABLE_NAME = "Scoreboard_table";
    private static final String NAME = "NAME";
    private static final String SCORE = "SCORE";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE_NAME + "(NAME TEXT, SCORE DOUBLE(2))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, double score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME, name);
        content.put(SCORE, score);
        long result = db.insert(TABLE_NAME, null, content);
        if(result == -1)
        {
            return false;
        }
        else
        {return true;}
    }

    public Cursor scoreCard(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor score = db.rawQuery("SELECT NAME, SCORE FROM " + TABLE_NAME + " ORDER BY SCORE ASC LIMIT 10", null);
        return score;
    }
}
