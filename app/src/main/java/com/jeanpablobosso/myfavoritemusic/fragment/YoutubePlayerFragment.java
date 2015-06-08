package com.jeanpablobosso.myfavoritemusic.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.jeanpablobosso.myfavoritemusic.R;
import com.jeanpablobosso.myfavoritemusic.util.Configuration;

/**
 * Created by Jean Pablo Bosso on 05/06/2015.
 */
public class YoutubePlayerFragment extends Fragment implements YouTubePlayer.OnInitializedListener{

    YouTubePlayerSupportFragment  youtubePlayerFragment;
    private YouTubePlayer activePlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentYoutubeView = inflater.inflate(R.layout.fragment_youtube, container, false);
        return fragmentYoutubeView;

    }

    @Override
    public void onResume() {

        youtubePlayerFragment = new YouTubePlayerSupportFragment();
        youtubePlayerFragment.setRetainInstance(false);
        youtubePlayerFragment.initialize(Configuration.DEVELOPER_KEY, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_player, youtubePlayerFragment);
        fragmentTransaction.commit();


        super.onResume();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
        activePlayer = youTubePlayer;

        if (!wasRestored) {
            activePlayer.loadVideo(Configuration.YOUTUBE_VIDEO_CODE);
            activePlayer.play();
            activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

        }

        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

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
}
