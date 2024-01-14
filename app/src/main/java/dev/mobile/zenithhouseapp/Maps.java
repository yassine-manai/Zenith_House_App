package dev.mobile.zenithhouseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class Maps extends Fragment {

    private WebView webView;

    public Maps() {
        // Required empty public constructor
    }

    public static Maps newInstance() {
        return new Maps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String googleMapsUrl = "https://www.google.com/maps";
        webView.loadUrl(googleMapsUrl);

        webView.setWebViewClient(new WebViewClient());

        return view;
    }
}
