package dwai.yhack.captor.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import dwai.yhack.captor.R;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();

    }

    public void startCardBoard(View view) {
        startActivity(new Intent(MyActivity.this, ArActivity.class));
    }

    public void startHistory(View view) {
        startActivity(new Intent(MyActivity.this, HistoryActivity.class));
    }
}
