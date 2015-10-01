package com.nautilus.demo.mywebapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FragmentWeb extends Fragment {

    public Context my_context;
    public View rootView;
    public ProgressDialog pd;
    public WebView webView;
    public SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        my_context = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_web, container, false);

        webView = (WebView) rootView.findViewById(R.id.webView);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        String type = getArguments().getString("type");
        String url = getArguments().getString("url");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.canGoBack();

        pd = new ProgressDialog(my_context);
        pd.setMessage("Please wait Loading...");

        if (type.equals("file")) {
            webView.loadUrl("file:///android_asset/" + url);
        } else if (type.equals("url")) {
            webView.loadUrl(url);
        }

        webView.setWebViewClient(new MyWebViewClient());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        return rootView;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            if (!pd.isShowing() && Boolean.valueOf(getString(R.string.webview_dialog_progress))) {
                pd.show();
            }

            if (Boolean.valueOf(getString(R.string.webview_material_progress))) {
                swipeContainer.setRefreshing(true);
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (pd.isShowing()) {
                pd.dismiss();
            }

            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webView.loadUrl("file:///android_asset/error.html");
        }
    }



}
