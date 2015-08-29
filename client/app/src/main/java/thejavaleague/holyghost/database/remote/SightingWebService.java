package thejavaleague.holyghost.database.remote;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import thejavaleague.holyghost.database.Sighting;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 8/29/15.
 */
public class SightingWebService {

    //    private static final String LOG_TAG = "WEB_SERVICE";
    private static final String COOKIES_HEADER = "Set-Cookie";

    private HttpURLConnection httpURLConnection;
    private SightingParser sightingParser;
    private static final String SIGHTINGS_URL = "http://192.168.9.75:3000/sightings";
    private static final int TIMEOUT = 15000;

    public String getJson(String urlString) {
        HttpURLConnection c;
        try {
            URL url = new URL(urlString);
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(TIMEOUT);
            c.setReadTimeout(TIMEOUT);
            String cookie = CookieManager.getInstance().getCookie(c.getURL().toString());
            if (cookie != null) { c.setRequestProperty("Cookie", cookie); }

            c.connect();

            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                for (String resCookie : c.getHeaderFields().get("Set-Cookie")) {
                    CookieManager.getInstance().setCookie(c.getURL().toString(), resCookie);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    private String getSightingUrl(int id) {
        return SIGHTINGS_URL + "/" + id;
    }

    public String getStringData(String address) throws IOException {

        InputStream in = getInputStream(address); //IOException
        String jsonSting = readInputStream(in); //IOException
        in.close(); //IOException
        httpURLConnection.disconnect();

        return jsonSting;
    }

    public InputStream getInputStream(String address) throws IOException {
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
        httpURLConnection = (HttpURLConnection) url.openConnection(); //IOException
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(httpURLConnection.getURL().toString());
        if (cookie != null) {
            httpURLConnection.setRequestProperty("Cookie", cookie);
        }

        httpURLConnection.connect();

        List<String> cookieList = httpURLConnection.getHeaderFields().get("Set-Cookie");
        if (cookieList != null) {
            for (String cookieTemp : cookieList) {
                cookieManager.setCookie(httpURLConnection.getURL().toString(), cookieTemp);
            }
        }

        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        String query = generateQuery(getUserNameAndPassword());
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();


        httpURLConnection.connect();

        return new BufferedInputStream(httpURLConnection.getInputStream()); //IOException
    }

    public String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int b;
        while ((b = inputStream.read()) != -1) {
            stringBuilder.append((char) b);
        }
        return stringBuilder.toString();
    }

    public void saveSighting(Sighting sighting) {

    }

    private List<NameValuePair> getUserNameAndPassword() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "test"));
        params.add(new BasicNameValuePair("password", "test"));
        return params;
    }

    private String generateQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}