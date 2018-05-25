package com.moovit.flickrgallery.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.ui.base.BaseFragment;

public class PhotoPageFragment extends BaseFragment {

    private static final String ARG_URL = "ARG_URL";

    private String mUrl;
    private WebView mWebView;

    public static PhotoPageFragment newInstance(String url) {
        PhotoPageFragment fragment = new PhotoPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARG_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_page, parent, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void setUp(View view) {
        mWebView = view.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {
                if ( isVisible() ) {
                    if ( progress == 100 ) {
                        hideLoading();
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                if ( getActivity() != null ) {
                    getActivity().setTitle(title);
                }
            }
        });

        mWebView.loadUrl(mUrl);
    }

}
