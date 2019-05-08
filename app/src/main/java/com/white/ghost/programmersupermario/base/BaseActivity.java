package com.white.ghost.programmersupermario.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle3.components.RxActivity;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.SuperMarioApp;
import com.white.ghost.programmersupermario.utils.StatusBarUtil;

import androidx.appcompat.app.AlertDialog;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Function: base
 * Author Name: Chris
 * Date: 2019/4/30 9:39
 */
public abstract class BaseActivity extends RxActivity {

    private Unbinder mBind;
    private AlertDialog mProgressDialog;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        StatusBarUtil.translucent(this);
        getResources();
        init();
    }


    /**
     * 设置布局
     */
    public abstract int getLayoutId();

    /**
     * 所有的初始化
     */
    public abstract void init();

    /**
     * 初始化views
     */
    public void initViews() {
    }

    /**
     * 初始化toolbar
     */
    public void initToolBar() {
    }

    /**
     * 获取一些intent或其他数据
     */
    public void getExtraData() {
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
     * 显示加载框
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new AlertDialog
                    .Builder(this, R.style.CustomAlertDialogTheme)
                    .create();
        }
        View view = View.inflate(this, R.layout.layout_progress_dialog, null);
        mProgressDialog.setView(view, 0, 0, 0, 0);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        Window window = mProgressDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 1f;
        window.setAttributes(layoutParams);
        mProgressDialog.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
    public void setData(BaseResponse baseResponse) {
    }

    /**
     * 展示空布局
     */
    public void showEmptyLayout() {
    }

    /**
     * 添加RxJava事件
     *
     * @param disposable 事件
     */
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //黄油刀unBind
        mBind.unbind();
        //清空所有事件
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
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
