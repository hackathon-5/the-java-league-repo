package thejavaleague.holyghost.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import thejavaleague.holyghost.R;

import java.util.ArrayList;

/**
 * Created by Chris on 8/29/15.
 */
public class GhostDatabase implements DatabaseOperations {

    private Context appContext;
    private SQLiteDatabase database;

    public GhostDatabase(Context context) {
        appContext = context;
        GhostDatabaseHelper helper = new GhostDatabaseHelper(appContext);
        database = helper.getWritableDatabase();
    }

    public ArrayList<Sighting> getSightings() {
        try {
            ArrayList<Sighting> sightings = new ArrayList<>();
            Cursor c = database.query(true, TABLE_SIGHTINGS, null, null, null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                sightings.add(new Sighting(c));
            }
            c.close();
            return sightings;
        } catch (CursorIndexOutOfBoundsException c) {
            Toast.makeText(appContext, R.string.errorReadingFromDatabase, Toast.LENGTH_SHORT).show();
            c.printStackTrace();
        }
        return null;
    }

    public long addSighting(Sighting sighting) {
        ContentValues values = new ContentValues();
        values.put(TITLE, sighting.getTitle());
        values.put(DESCRIPTION, sighting.getDescription());
        values.put(LATITUDE, sighting.getLocation().latitude);
        values.put(LONGITUDE, sighting.getLocation().longitude);
        values.put(RATING, sighting.getRating());
        return database.insert(TABLE_SIGHTINGS, null, values);
    }
}
