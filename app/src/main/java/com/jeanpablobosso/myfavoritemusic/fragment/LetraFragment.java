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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
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


public class LetraFragment extends Fragment{
    YouTubePlayerSupportFragment youtubePlayerFragment;

    TextView status = null;
    WebView video;

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

        video = (WebView) letraFragmentView .findViewById(R.id.videoViewLetra);


        return letraFragmentView;
    }

    @Override
    public void onResume() {
        video.getSettings().setJavaScriptEnabled(true);
        //video.addJavascriptInterface(this, "FragmentYoutube");
        video.getSettings().setPluginState(WebSettings.PluginState.ON);
        // video.getSettings().setUserAgent(USER_MOBILE);
        video.setWebChromeClient(new WebChromeClient() {
        });

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = getHTML("5SJoKBS237g");
        video.loadDataWithBaseURL("", html, mimeType, encoding, "");


        super.onResume();
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


}
