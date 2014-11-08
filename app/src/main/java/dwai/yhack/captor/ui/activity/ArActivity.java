package dwai.yhack.captor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    //private String MY_ACTIVITY = "ARActivity";
    private Renderer mRenderer;

    private Vibrator mVibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ar);

        ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC,true);
        // Inject views
        ButterKnife.inject(this);


        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Associate a CardboardView.StereoRenderer with cardboardView.
        mRenderer = new Renderer(this);
        cardboardView.setRenderer(mRenderer);

        // Associate the cardboardView with this activity.
        setCardboardView(cardboardView);

        overlayView.show3DToast("Welcome to Captor!");






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
                overlayView.show3DToast(listOfWords.get(0));
                Bitmap b = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);

                new PostMoreStuff().execute(encodeTobase64(b));

                overlayView.getmLeftView().getImageView().draw(c);

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
                if (results != null) {
                    if(results.size() > 6){
                        results.clear();
                    }
                    for (String p : results) {
                        b += p;
                    }
                }
                overlayView.show3DToast(b);
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    private class PostMoreStuff extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(final String... strings) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://65b62e4e.ngrok.com/api/img?username="  + getMy10DigitPhoneNumber());

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("baseEncoded", strings[0]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
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

}
