package thejavaleague.holyghost.database.remote;

import thejavaleague.holyghost.database.Sighting;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Chris on 8/29/15.
 */
public class SightingWebService {

//    private static final String LOG_TAG = "WEB_SERVICE";

    private HttpURLConnection urlConnection;
    private SightingParser sightingParser;

    public Sighting getSightings() {

        return null;
    }

    public String getStringData(String address) throws IOException{

        InputStream in = getInputStream(address); //IOException
        String jsonSting = readInputStream(in); //IOException
        in.close(); //IOException
        urlConnection.disconnect();

        return jsonSting;
    }

    public InputStream getInputStream(String address) throws IOException{
        //set errorCode back to nothing so we don't get false negative
        try {
            // encoding special characters like space in the user input place
            address = URLEncoder.encode(address, "utf-8");
        } catch (UnsupportedEncodingException e) {
            address = address.replaceAll(" ", "%20");
        }

        //HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        //HttpPost httppost = new HttpPost("http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=false");
        URL url = new URL(address); //Malformed URL Exception
        urlConnection = (HttpURLConnection) url.openConnection(); //IOException

        return new BufferedInputStream(urlConnection.getInputStream()); //IOException
    }

   public String readInputStream(InputStream inputStream) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        int b;
        while ((b = inputStream.read()) != -1) {
            stringBuilder.append((char) b);
        }
        return stringBuilder.toString();
    }

}