package com.hjy.read.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hjy.read.R;
import com.hjy.read.api.ApiService;
import com.hjy.read.bean.zhihu.NewsTimeLine;
import com.hjy.read.ui.adapter.ZhihuListAdapter;
import com.hjy.read.ui.base.BasePresenter;
import com.hjy.read.ui.view.IZhihuFgView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ZhihuFgPresenter extends BasePresenter<IZhihuFgView> {

    private Context context;
    private IZhihuFgView zhihuFgView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private NewsTimeLine timeLine;
    private ZhihuListAdapter adapter;
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多
    private Handler mHandler = new Handler();

    public ZhihuFgPresenter(Context context) {
        this.context = context;
    }

    public void getLatestNews() {
        zhihuFgView = getView();
        if (zhihuFgView != null) {
            mRecyclerView = zhihuFgView.getRecyclerView();
            layoutManager = zhihuFgView.getLayoutManager();

            ApiService.getZhihuApiSingleton().getLatestNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine, context, zhihuFgView, mRecyclerView);
                    },this::loadError);
        }
    }

    private void getBeforeNews(String time) {
        zhihuFgView = getView();
        if (zhihuFgView != null) {
            mRecyclerView = zhihuFgView.getRecyclerView();
            layoutManager = zhihuFgView.getLayoutManager();

            ApiService.getZhihuApiSingleton().getBeforetNews(time)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine,context,zhihuFgView,mRecyclerView);
                    },this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    String time;
    private void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context, IZhihuFgView iZhihuFgView, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (time == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                iZhihuFgView.setDataRefresh(false);
                return;
            }
             else {
                timeLine.getStories().addAll(newsTimeLine.getStories());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = newsTimeLine;
            adapter = new ZhihuListAdapter(context, timeLine);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        iZhihuFgView.setDataRefresh(false);
        time = newsTimeLine.getDate();
    }

    /**
     * recyclerView Scroll listener , maybe in here is wrong ?
     */
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                            .findLastVisibleItemPosition();
                    if (layoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(adapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()) {
                        adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        adapter.updateLoadStatus(adapter.LOAD_MORE);
                        mHandler.postDelayed(() -> getBeforeNews(time), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        mHandler.removeCallbacksAndMessages(null);
    }
}
