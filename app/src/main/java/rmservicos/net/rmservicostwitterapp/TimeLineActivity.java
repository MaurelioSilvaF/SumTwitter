package rmservicos.net.rmservicostwitterapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends ListActivity {

    // Buttons of Activity (Lista and Map)
    private ImageButton btLista;
    private ImageButton btMapa;

    // List of Twitts for save coordinates, user and twit text
    ArrayList <Localizacoes> locais = new ArrayList<Localizacoes>();

    // Adapter to show TimeLine
    public final TweetViewFetchAdapter adapter =
            new TweetViewFetchAdapter<CompactTweetView>(
                    TimeLineActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        // Find buttons on Activity
        btLista = (ImageButton) findViewById(R.id.idListaList);
        btMapa = (ImageButton) findViewById(R.id.idMapaList);

        // Disable buttons
        btLista.setEnabled(false);
        btMapa.setEnabled(false);

        // Create variable to integrate with Twitter API
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

        // Create variable to verify Status of Service of API
        StatusesService statusesService = twitterApiClient.getStatusesService();

        // Load homeTimeLine (50 last twitts)
        statusesService.homeTimeline(150, null, null, false, false, false, false,
                new Callback<List<Tweet>>() {
                    // if return twitts, enable button MAP, save twitts with coordinates and
                    // set adapter with list for exhibit to user
                    @Override
                    public void success(Result<List<Tweet>> listResult) {
                        btMapa.setEnabled(listResult.data.size() > 0);
                        for (int i = 0; i < listResult.data.size(); i++) {

                            if (listResult.data.get(i).coordinates !=  null) {
                                Localizacoes local = new Localizacoes(
                                        listResult.data.get(i).getId(),
                                        listResult.data.get(i).coordinates.getLongitude(),
                                        listResult.data.get(i).coordinates.getLatitude(),
                                        listResult.data.get(i).user.name.toString(),
                                        listResult.data.get(i).text.toString(),
                                        listResult.data.get(i).user.profileImageUrl.toString()
                                        );
                                locais.add(local);
                            }
                        }

                        adapter.setTweets(listResult.data);
                        setListAdapter(adapter);
                    }

                    // If occurred a failure, show message
                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(getBaseContext(), "Occurred a error. Try again...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Open the activity for exhibit map with posts that has a coordinate
    public void AbreMapa(View v) {
        Intent it = new Intent(getBaseContext(), MapActivity.class);
        it.putExtra("Localizacoes", locais);
        startActivity(it);
        finish();
    }

}
