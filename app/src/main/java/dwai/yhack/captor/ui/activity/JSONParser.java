package dwai.yhack.captor.ui.activity;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Fisher on 11/1/14.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = null;

    public JSONArray getJsonFromUrl(String url) {

        // Make request
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch(ClientProtocolException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                Log.d("Bob", line);
                sb.append(line);
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            // try parsing the string to a JSON object
            jObj = new JSONArray(json);
        } catch(Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return json string
        //Log.d("Bob", jObj.toString())

        return jObj;
    }
}
