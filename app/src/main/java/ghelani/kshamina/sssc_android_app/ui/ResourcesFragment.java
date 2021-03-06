package ghelani.kshamina.sssc_android_app.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ghelani.kshamina.sssc_android_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment {
    private WebView webView;

    public ResourcesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resources, container, false);

        webView = view.findViewById(R.id.webView);
        webView.loadUrl("https://sssc.carleton.ca/resources");

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        return view;
    }
}
