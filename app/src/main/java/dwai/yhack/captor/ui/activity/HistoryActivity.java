package dwai.yhack.captor.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dwai.yhack.captor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.ribot.easyadapter.EasyAdapter;

public class HistoryActivity extends Activity {

    public static final String URL = "http://captor.thupukari.com";

    private static final String TAG_TEXT = "text";
    private static final String TAG_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Medium.ttf");
        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        HistoryActivity.setAppFont(mContainer, mFont, true);



        new JSONParse().execute();
    }

    private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        return mPhoneNumber;
    }

    public String getMy10DigitPhoneNumber() {
        String s = getPhoneNumber();
        return s != null && s.length() > 1 ? s.substring(1) : null;
    }

    private class JSONParse extends AsyncTask<String, String, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONArray json = jParser.getJsonFromUrl(URL + "/api/data?username=" + getMy10DigitPhoneNumber());
            Log.d("Joe", URL + "/api/data?username=" + getMy10DigitPhoneNumber());
            //System.out.println(json + "555");
            Log.d("Joe", json + "555");
            return json;
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            //pDialog.dismiss();
            Log.d("Joe", "hi");
            List<OneItem> items = new ArrayList<OneItem>();
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.kitten);
            final LinearLayout slideView = ((LinearLayout) findViewById(R.id.slideView));
            try {
                for (int i = 0; i < json.length(); i++) {
                    String dateString = json.getJSONObject(i).getString("date");
//
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm+s:SSSS");
//                    Date date = format.parse(dateString);
//                    format = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
//                    dateString = format.format(date);

                    OneItem o = new OneItem(icon, json.getJSONObject(i).getString("text") + "..." , new DatePosted().setYear(2).setMonth(3).setDay(23).setHour(12).setMinute(13).setSecond(24));
                    items.add(o);
                }
                Collections.sort(items);
                ListView lv = ((ListView) findViewById(R.id.rootListView));
                lv.setAdapter(new EasyAdapter<OneItem>(getApplicationContext(), HistoryViewHolder.class, items));
            } catch (Exception e) {
                Log.d("Joe", "hello");
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static final void setAppFont(ViewGroup mContainer, Typeface mFont, boolean reflect) {
        if (mContainer == null || mFont == null) return;
        final int mCount = mContainer.getChildCount();
        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i) {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            } else if (mChild instanceof ViewGroup) {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont, true);
            } else if (reflect) {
                try {
                    Method mSetTypeface = mChild.getClass().getMethod("setTypeface", Typeface.class);
                    mSetTypeface.invoke(mChild, mFont);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
