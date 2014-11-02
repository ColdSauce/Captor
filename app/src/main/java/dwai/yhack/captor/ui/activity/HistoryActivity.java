package dwai.yhack.captor.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dwai.yhack.captor.R;
import uk.co.ribot.easyadapter.EasyAdapter;

public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Medium.ttf");
        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        HistoryActivity.setAppFont(mContainer, mFont, true);
        ListView lv = ((ListView)findViewById(R.id.rootListView));
        final LinearLayout slideView = ((LinearLayout)findViewById(R.id.slideView));
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
               R.drawable.kitten);
        List<OneItem> items = new ArrayList<OneItem>();
        for(int i = 0; i < 10;i++){
            OneItem o = new OneItem(icon,"bobbysdfkjsbdflkjsbdfljkasbdflkjbsdfjhsbdfkjbsddfkjhbsddfkjhadsfkjhdsfkjadsfkjdfkjdsfkjsdfkjhdsfkjhadsfkjhbsdfkjhbasdfkjhbasdfkjhbadsfkjadsfkjhsdfkjhadsfkjhbadsfkjadsfkjadsfkjadsfkjadsfkjadsfkjadsfkjadsf".substring(0,150) + "..." + i, new DatePosted().setYear(2).setMonth(3).setDay(23).setHour(12).setMinute(13).setSecond(24));
            items.add(o);
        }
         Collections.sort(items);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animation slide =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                slideView.startAnimation(slide);
            }
        });
        lv.setAdapter(new EasyAdapter<OneItem>(this, HistoryViewHolder.class, items));

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



    public static final void setAppFont(ViewGroup mContainer, Typeface mFont, boolean reflect)
    {
        if (mContainer == null || mFont == null) return;
        final int mCount = mContainer.getChildCount();
// Loop through all of the children.
        for (int i = 0; i < mCount; ++i)
        {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView)
            {
// Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            }
            else if (mChild instanceof ViewGroup)
            {
// Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont,true);
            }
            else if (reflect)
            {
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
