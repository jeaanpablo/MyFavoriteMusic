package com.jeanpablobosso.myfavoritemusic.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.jeanpablobosso.myfavoritemusic.R;
import com.jeanpablobosso.myfavoritemusic.util.JSONParser;
import com.jeanpablobosso.myfavoritemusic.util.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jean Pablo Bosso on 05/06/2015.
 */


public class LetraFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerSupportFragment youtubePlayerFragment;

    TextView status = null;
    private YouTubePlayer activePlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View letraFragmentView = inflater.inflate(R.layout.fragment_letra, container, false);

        String uri = Uri.parse("http://api.vagalume.com.br/search.php").buildUpon().
                appendQueryParameter("mus", "Fear Of The Dark")

                .appendQueryParameter("art", "Iron Maiden")

                .build().toString();


        if (uri != null) {

            new VagalumeAsyncTask().execute(uri);

        }

        this.status = (TextView) letraFragmentView.findViewById(R.id.status);


        return letraFragmentView;
    }

    @Override
    public void onResume() {

        youtubePlayerFragment = new YouTubePlayerSupportFragment();
        youtubePlayerFragment.setRetainInstance(false);
        youtubePlayerFragment.initialize(Configuration.DEVELOPER_KEY, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_player_letra, youtubePlayerFragment);
        fragmentTransaction.commit();


        super.onResume();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        activePlayer = youTubePlayer;

        if (!wasRestored) {
            activePlayer.loadVideo(Configuration.YOUTUBE_VIDEO_CODE);
            activePlayer.play();
            activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

        }

        activePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {
                activePlayer.play();
            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    private class VagalumeAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            return jParser.getJSONFromUrl(params[0]);
        }


        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);

            try {
                JSONObject mus = (JSONObject) json.getJSONArray("mus").get(0);
                status.setText(mus.getString("text"));
            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }


}
