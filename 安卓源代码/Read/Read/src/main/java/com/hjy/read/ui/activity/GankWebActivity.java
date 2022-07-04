package com.hjy.read.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hjy.read.R;
import com.hjy.read.ui.base.MVPBaseActivity;
import com.hjy.read.ui.presenter.GankWebPresenter;
import com.hjy.read.ui.view.IGankWebView;

import butterknife.Bind;


public class GankWebActivity extends MVPBaseActivity<IGankWebView, GankWebPresenter> implements IGankWebView {

    public static final String GANK_URL = "gank_url";

    @Bind(R.id.pb_progress)
    ProgressBar pb_progress;
    @Bind(R.id.url_web)
    WebView url_web;

    private String gank_url;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseIntent();
        if (gank_url.contains("app3.qdaily.com/app3")) {
            gank_url = gank_url.replace("app3.qdaily.com/app3", "m.qdaily.com/mobile");
        }
        mPresenter.setWebView(gank_url);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, GankWebActivity.class);
        intent.putExtra(GankWebActivity.GANK_URL, url);
        return intent;
    }

    /**
     * 得到Intent传递的数据
     */
    @Override
    protected GankWebPresenter createPresenter() {
        return new GankWebPresenter(this);
    }
    private void parseIntent() {
        gank_url = getIntent().getStringExtra(GANK_URL);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public ProgressBar getProgressBar() {
        return pb_progress;
    }

    @Override
    public WebView getWebView() {
        return url_web;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        url_web.destroy();
    }

}
