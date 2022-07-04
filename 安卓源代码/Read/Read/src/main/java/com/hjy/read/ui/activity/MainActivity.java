package com.hjy.read.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.hjy.read.R;
import com.hjy.read.loginregisterdemo.LoginActivity;
import com.hjy.read.ui.adapter.ViewPagerFgAdapter;
import com.hjy.read.ui.base.BasePresenter;
import com.hjy.read.ui.base.MVPBaseActivity;
import com.hjy.read.ui.base.MVPBaseFragment;
import com.hjy.read.ui.fragment.DailyFragment;
import com.hjy.read.ui.fragment.ZhihuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主界面，包含了三个Fragment，但是我觉得在 Fragment 的加载和销毁的处理上，做的不好，希望大家可以有好的建议提供给我
 */

public class MainActivity extends MVPBaseActivity {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.content_viewPager)
    ViewPager content_viewPager;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
    }

    //初始化Tab滑动
    public void initTabView(){
        List<MVPBaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new DailyFragment());
        content_viewPager.setOffscreenPageLimit(2);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        content_viewPager.setAdapter(new ViewPagerFgAdapter(getSupportFragmentManager(), fragmentList,"main_view_pager"));//给ViewPager设置适配器
        tabLayout.setupWithViewPager(content_viewPager);//将TabLayout和ViewPager关联起来
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.today_github){
            String github_trending = "https://github.com/trending";
            startActivity(GankWebActivity.newIntent(this,github_trending));
            return true;
        }else if(item.getItemId() == R.id.about_me){
            startActivity(new Intent(this,AboutMeActivity.class));
            return true;}
        else if(item.getItemId() == R.id.login_demo){
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
