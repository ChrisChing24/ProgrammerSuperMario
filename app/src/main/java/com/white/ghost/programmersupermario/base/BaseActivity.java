package com.white.ghost.programmersupermario.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.trello.rxlifecycle3.components.RxActivity;
import com.white.ghost.programmersupermario.SuperMarioApp;
import com.white.ghost.programmersupermario.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Function: base
 * Author Name: Chris
 * Date: 2019/4/30 9:39
 */
public abstract class BaseActivity extends RxActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        StatusBarUtil.translucent(this);
        getResources();
        initViews();
    }


    /**
     * 设置布局
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     */
    public abstract void initViews();

    /**
     * 初始化toolbar
     */
    public void initToolBar() {
    }

    /**
     * 初始化其他各种view
     */
    public void initOtherView() {
    }


    /**
     * 加载数据
     */
    public void loadData() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void setData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        //添加leakCanary监测
        SuperMarioApp.getRefWatcher().watch(this);
    }

    //设置android app 的字体大小不受系统字体大小改变的影响
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
