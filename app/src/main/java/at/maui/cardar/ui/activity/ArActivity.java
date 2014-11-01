package at.maui.cardar.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.att.android.speech.ATTSpeechActivity;
import com.att.android.speech.ATTSpeechResult;
import com.att.android.speech.ATTSpeechResultListener;
import com.att.android.speech.ATTSpeechService;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardDeviceParams;
import com.google.vrtoolkit.cardboard.CardboardView;

import java.util.ArrayList;
import java.util.Collections;

import at.maui.cardar.R;
import at.maui.cardar.ui.ar.Renderer;
import at.maui.cardar.ui.widget.CardboardOverlayView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * Created by maui on 02.07.2014.
 */
public class ArActivity extends CardboardActivity {

    @InjectView(R.id.overlay)
    CardboardOverlayView overlayView;

    @InjectView(R.id.cardboard_view)
    CardboardView cardboardView;
    private String MY_ACTIVITY = "ARActivity";
    private Renderer mRenderer;

    private String oauthToken = null;
    private ATTSpeechService s;
    private Vibrator mVibrator;
    private int mScore = 0;
    private static final int SPEECH_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ar);

        // Inject views
        ButterKnife.inject(this);
        s = ATTSpeechService.getSpeechService(this);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Associate a CardboardView.StereoRenderer with cardboardView.
        mRenderer = new Renderer(this);
        cardboardView.setRenderer(mRenderer);

        // Associate the cardboardView with this activity.
        setCardboardView(cardboardView);

        overlayView.show3DToast("Welcome to Captor!");

        validateOAuth();
        Log.d(MY_ACTIVITY,oauthToken + "");
        s.setSpeechContext("General");
        // Set the OAuth token that was fetched in the background.

        // Specify the speech context for this app.


        s.setXArgs(
                Collections.singletonMap("ClientScreen", "main"));


        s.setSpeechResultListener(new SpeechResultListener());
        s.setMaxRecordingTime(Integer.MAX_VALUE);



    }

    public class SpeechResultListener implements ATTSpeechResultListener{

        @Override
        public void onResult(ATTSpeechResult attSpeechResult) {

            Log.d(MY_ACTIVITY, attSpeechResult.getTextStrings().toString());
        }
    }

    public void startL(){
        s.startListening();

    }

    private void validateOAuth() {
        SpeechAuth auth =
                SpeechAuth.forService(SpeechConfig.oauthUrl(), SpeechConfig.oauthScope(),
                        SpeechConfig.oauthKey(), SpeechConfig.oauthSecret());
        auth.fetchTo(new OAuthResponseListener());
    }

    private class OAuthResponseListener implements SpeechAuth.Client {
        public void
        handleResponse(String token, Exception error) {
            Log.d(MY_ACTIVITY,token);
            if (token != null) {
                oauthToken = token;

                s.setBearerAuthToken(oauthToken);
                startL();
            } else {
                Log.v("SimpleSpeech", "OAuth error: " + error);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == SPEECH_REQUEST_CODE) {
            System.out.println("worked");
            if (resultCode == RESULT_OK) {
                // Retrieve text recognized from speech
                ArrayList nbest = resultIntent.getStringArrayListExtra(ATTSpeechActivity.EXTRA_RESULT_TEXT_STRINGS);

                if ((nbest != null) && (nbest.size() > 0)) {
                    String text = nbest.get(0).toString();
                    Log.v(MY_ACTIVITY, "Recognition result: " + text);
                } else
                    Log.v(MY_ACTIVITY, "Speech was silent or not recognized.");
            } else if (resultCode == RESULT_CANCELED)
                Log.v(MY_ACTIVITY, "User canceled.");
            else {
                String error = resultIntent.getStringExtra(ATTSpeechActivity.EXTRA_RESULT_ERROR_MESSAGE);
                Log.v(MY_ACTIVITY, "Recognition failed with error: " + error);
            }
        }
    }


}
