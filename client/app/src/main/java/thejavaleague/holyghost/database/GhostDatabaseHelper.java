package thejavaleague.holyghost.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chris on 8/29/15.
 */
public class GhostDatabaseHelper extends SQLiteOpenHelper implements DatabaseOperations{

    public static final int DATABASE_VERSION = 1;

    private final String CREATE_SIGHTINGS_TABLE = "create table "+ TABLE_SIGHTINGS + " (" + ROW_ID + " integer primary key autoincrement, "
            + TITLE + " text, " + DESCRIPTION + " text, " + LATITUDE + " integer, " + LONGITUDE + " integer, " + RATING + " integer);";

    public GhostDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SIGHTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
