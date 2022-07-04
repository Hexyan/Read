package com.hjy.read.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import com.hjy.read.R;
import com.hjy.read.ui.base.BasePresenter;
import com.hjy.read.ui.base.MVPBaseActivity;


public class AboutMeActivity extends MVPBaseActivity {



    public CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about_me;
    }

    /**
     * 初始化ToolBar
     */
    private void initToolbar() {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("关于");
    }
    }

