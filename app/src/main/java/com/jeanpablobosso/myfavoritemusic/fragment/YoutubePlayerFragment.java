package com.jeanpablobosso.myfavoritemusic.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.jeanpablobosso.myfavoritemusic.R;
import com.jeanpablobosso.myfavoritemusic.util.Configuration;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Jean Pablo Bosso on 05/06/2015.
 */
public class YoutubePlayerFragment extends Fragment{
    WebView video;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentYoutubeView = inflater.inflate(R.layout.fragment_youtube, container, false);

        video = (WebView) fragmentYoutubeView.findViewById(R.id.videoView);

        return fragmentYoutubeView;

    }

    @Override
    public void onResume() {
        video.getSettings().setJavaScriptEnabled(true);
        video.getSettings().setPluginState(WebSettings.PluginState.ON);
        video.setWebChromeClient(new WebChromeClient() {
        });

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = getHTML("5SJoKBS237g");
        video.loadDataWithBaseURL("", html, mimeType, encoding, "");

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(video, (Object[]) null);
        } catch(ClassNotFoundException cnfe) {

        } catch(NoSuchMethodException nsme) {

        } catch(InvocationTargetException ite) {

        } catch (IllegalAccessException iae) {

        }



    }

    public String getHTML(String videoId) {

        String html =
                "<iframe class=\"youtube-player\" "
                        + "style=\"border: 0; width: 100%; height: 95%;"
                        + "padding:0px; margin:0px\" "
                        + "id=\"ytplayer\" type=\"text/html\" "
                        + "src=\"http://www.youtube.com/embed/" + videoId
                        + "?fs=0\" frameborder=\"0\" " + "allowfullscreen autobuffer "
                        + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
        return html;
    }
    @JavascriptInterface
    public void getCurrentTime(){
        Log.i("Script", "Nome:");
    }


}
