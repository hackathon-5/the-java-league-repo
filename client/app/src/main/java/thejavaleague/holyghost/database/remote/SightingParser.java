package thejavaleague.holyghost.database.remote;

import android.util.JsonReader;
import android.util.JsonToken;
import com.google.android.gms.maps.model.LatLng;
import thejavaleague.holyghost.database.Sighting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 8/29/15.
 */
public class SightingParser {

    public List<Sighting> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readSightingsArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Sighting> readSightingsArray(JsonReader reader) throws IOException {
        List<Sighting> Sightings = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            Sightings.add(readSighting(reader));
        }
        reader.endArray();
        return Sightings;
    }

    public Sighting readSighting(JsonReader reader) throws IOException {
        String name = null;
        String description = null;
        LatLng location = null;
        int rating = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if (key.equals("title")) {
                name = reader.nextString();
            } else if (key.equals("description")) {
                description = reader.nextString();
            } else if (key.equals("location") && reader.peek() != JsonToken.NULL) {
                location = readDoublesArray(reader);
            } else if (key.equals("rating")) {
                rating = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Sighting(name, description, location, rating);
    }

    public LatLng readDoublesArray(JsonReader reader) throws IOException {
        List doubles = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return new LatLng((double) doubles.get(0), (double) doubles.get(1));
    }
}
