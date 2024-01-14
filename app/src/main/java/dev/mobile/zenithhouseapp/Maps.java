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

        String googleMapsUrl = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10445.52452424111!2d10.26063440590648!3d36.759277194370206!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12fd49fa15643927%3A0xad64c8c462b52435!2sInstitut%20Sup%C3%A9rieur%20des%20Etudes%20Technologiques%20de%20Rades!5e0!3m2!1sfr!2stn!4v1705212761524!5m2!1sfr!2stn";
        webView.loadDataWithBaseURL(null, "<html><body><iframe width=\"100%\" height=\"100%\" src=\"" + googleMapsUrl + "\" frameborder=\"0\" allowfullscreen loading=\"fast\"></iframe></body></html>", "text/html", "UTF-8", null);


        webView.setWebViewClient(new WebViewClient());

        return view;
    }
}
