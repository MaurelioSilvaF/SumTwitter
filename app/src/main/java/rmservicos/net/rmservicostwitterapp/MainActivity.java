package rmservicos.net.rmservicostwitterapp;

import android.app.Activity;
import android.os.Bundle;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "J0m1Qs0Vehj8H8ZnKh1ZISSQX";
    private static final String TWITTER_SECRET = "TUECgF6pLmYhjv3msXRxxCbNyPfFnbJ9SX4GdoUjol66WBF7ax";

    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Class of Authentication
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        // Find the loginButton for assign Callback function
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            // if Sucesss on login to Twitter, finish this Activity and open TimeLineActivity
            @Override
            public void success(Result<TwitterSession> result) {
                finish();
                Intent it = new Intent(getBaseContext(), TimeLineActivity.class);
                startActivity(it);
            }
            // if failure on login to Twitter, show message with text "Try again..."
            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getBaseContext(), "Try again...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Assign function on loginButton Activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void AbreTwit(View v) {
        Intent it = new Intent(getBaseContext(), MapActivity.class);
        startActivity(it);
    }

}