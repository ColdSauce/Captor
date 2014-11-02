package dwai.yhack.captor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import dwai.yhack.captor.R;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        startActivity(new Intent(MyActivity.this, HistoryActivity.class));

    }
}
