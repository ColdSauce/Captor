package dwai.yhack.captor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.URLUtil;

import com.att.android.speech.ATTSpeechActivity;
import com.att.android.speech.ATTSpeechResult;
import com.att.android.speech.ATTSpeechResultListener;
import com.att.android.speech.ATTSpeechService;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dwai.yhack.captor.R;
import dwai.yhack.captor.ui.ar.Renderer;
import dwai.yhack.captor.ui.widget.CardboardOverlayView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArActivity extends CardboardActivity {

    @InjectView(R.id.overlay)
    CardboardOverlayView overlayView;

    @InjectView(R.id.cardboard_view)
    CardboardView cardboardView;
    private String MY_ACTIVITY = "ARActivity";
    private Renderer mRenderer;

    private String oauthToken = null;
    private Vibrator mVibrator;
    private int mScore = 0;
    private static final int SPEECH_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ar);

        // Inject views
        ButterKnife.inject(this);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Associate a CardboardView.StereoRenderer with cardboardView.
        mRenderer = new Renderer(this);
        cardboardView.setRenderer(mRenderer);

        // Associate the cardboardView with this activity.
        setCardboardView(cardboardView);

        overlayView.show3DToast("Welcome to Captor!");

        // Set the OAuth token that was fetched in the background.

        // Specify the speech context for this app.






        final SpeechRecognizer speechRec = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "VoiceIME");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000L);

        speechRec.startListening(intent);
        speechRec.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                String someString = "";
                List<String> listOfWords = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                someString = listOfWords.get(0);
                try{

                   someString =  URLEncoder.encode(someString,"UTF-8");

                }
                catch(Exception e){
                    e.printStackTrace();
                }


                new PostStuff().execute("http://captor.thupukair.com",someString);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                speechRec.stopListening();
                speechRec.setRecognitionListener(this);
                speechRec.startListening(intent);
            }

            public void onPartialResults(Bundle partialResults) {
                // WARNING: The following is specific to Google Voice Search
                ArrayList<String> results =
                        partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String b = "";
                if (results != null)
                    for (String p : results) {
                        b += p;
                    }
                overlayView.show3DToast(b);
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    private class PostStuff extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {

            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HistoryActivity.URL + "/api/data?username=" + getMy10DigitPhoneNumber() + "&string="  + strings[1]);

            try {
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        }

    }


    public String getMy10DigitPhoneNumber() {
        String s = getPhoneNumber();
        return s != null && s.length() > 1 ? s.substring(1) : null;
    }
    private String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        return mPhoneNumber;
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
