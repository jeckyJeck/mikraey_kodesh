package com.JK.mikraey_kodesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TryingActivity extends AppCompatActivity {

    WebView webView, webView2;
    androidx.appcompat.widget.Toolbar mBottomToolbar;
    androidx.appcompat.widget.Toolbar mToolBar;
    Handler handler = new Handler();
    dataManage dm = new dataManage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trying);
        setTheme(R.style.Theme_Material3_DayNight_NoActionBar);

        //mBottomToolbar = findViewById(R.id.bottom_toolbar);
        mToolBar      = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);


        webView = (WebView) findViewById(R.id.webview);
        webView2 = (WebView) findViewById(R.id.webview2);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//                // Add a scroll listener to the WebView
//                webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                    private int mPreviousScrollY = 0;
//                    private boolean mIsHidden = false;
//                    private static final int HIDE_THRESHOLD = 403;
//                    int gap;
//                    float currentBarY;
//
//                    @Override
//                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                        gap = scrollY - oldScrollY;
//
//                        if (scrollY > mPreviousScrollY) {
//                            // User is scrolling down
//                            currentBarY = mBottomToolbar.getTranslationY();
//                            while ((mBottomToolbar.getTranslationY() - currentBarY) < gap) {
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mBottomToolbar.animate().translationY(mBottomToolbar.getTranslationY() + 1);
//                                    }
//                                }, 100);
////                                mBottomToolbar.animate().translationY(mBottomToolbar.getTranslationY() + 10);
//
//                            }
//                        }
//                        mPreviousScrollY = scrollY;
//
//
//
////                        if (scrollY < oldScrollY) {
////                            // User is scrolling up, show the toolbars
////                            if (mIsHidden) {
////                                // Move the toolbars by the exact difference in pixels between the current and previous scroll positions
////                                mToolBar.animate().translationY(0 - (scrollY - oldScrollY));
////                                mBottomToolbar.animate().translationY(- (scrollY - oldScrollY));
////                                mIsHidden = false;
////                            }
////                        } else if (scrollY > oldScrollY) {
////                            // User is scrolling down
////                            if (!mIsHidden && scrollY > HIDE_THRESHOLD) {
////                                // Hide the toolbars after scrolling down by a certain amount
////                                // Move the toolbars by the exact difference in pixels between the current and previous scroll positions
////                                mToolBar.animate().translationY(0 - (scrollY - oldScrollY));
////                                mBottomToolbar.animate().translationY(mBottomToolbar.getHeight() + (scrollY - oldScrollY));
////                                mIsHidden = true;
////                            }
////                        }
////                        // Update the previous scroll position
////                        mPreviousScrollY = scrollY;
//                    }
//
//                });
//            }
//        });

        webView.loadDataWithBaseURL("file:///android_asset/",getChapterText(3,3), "text/html; charset=utf-8", "UTF-8",null);
        webView2.loadDataWithBaseURL("file:///android_asset/",getChapterText(3,4), "text/html; charset=utf-8", "UTF-8",null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_textmain, menu);
        mToolBar.setTitle("it doesn't really matter");

        return super.onCreateOptionsMenu(menu);
    }

    private String getChapterText(int book, int chapter){
        String text = "אירעה שגיאה, ניתן לדווח ב";
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is;
        int size;
        BufferedReader buffer;
        try
        {
            is = getAssets().open(dm.getURL(book, chapter, true));
            size = is.available();
            buffer = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = buffer.readLine()) != null){
                stringBuilder.append(line);
            }
            text = stringBuilder.toString();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }
}