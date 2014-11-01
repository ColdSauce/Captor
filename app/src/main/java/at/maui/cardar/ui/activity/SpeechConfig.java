package at.maui.cardar.ui.activity;

/**
 * Created by Stefan on 11/1/2014.
 */
public class SpeechConfig {
    private SpeechConfig() {} // can't instantiate
    /** The URL of AT&T Speech API. **/
    static String serviceUrl() {
        return "https://api.att.com/speech/v3/speechToText";
    }
    /** The URL of AT&T Speech API OAuth service. **/
    static String oauthUrl() {
        return "https://api.att.com/oauth/token";
    }
    /** The OAuth scope of AT&T Speech API. **/
    static String oauthScope() {
        return "SPEECH";
    }
    /** Unobfuscates the OAuth client_id credential for the application. **/
    static String oauthKey() {
// TODO: Replace this with code to unobfuscate your OAuth client_id.
        return "qdnraef5jxczfp1lqroxfxt9rlrjduif";
    }
    /** Unobfuscates the OAuth client_secret credential for the application. **/
    static String oauthSecret() {
// TODO: Replace this with code to unobfuscate your OAuth client_secret.
        return "xjz6n3649zaogz03keqgkn2qgoxoe8ty";
    }
}
