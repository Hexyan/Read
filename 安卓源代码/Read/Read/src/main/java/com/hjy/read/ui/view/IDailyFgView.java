package com.hjy.read.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public interface IDailyFgView {

    void setDataRefresh(Boolean refresh);
    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
}
