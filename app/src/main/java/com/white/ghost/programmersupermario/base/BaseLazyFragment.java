package com.white.ghost.programmersupermario.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.SuperMarioApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Function:BaseFragment 懒加载
 * Author Name: Chris
 * Date: 2019/4/30 9:54
 */
public abstract class BaseLazyFragment extends RxFragment {

    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;
    private Unbinder mBind;
    private AlertDialog mProgressDialog;
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mBind = ButterKnife.bind(view);
        initViews();
        isViewCreated = true;
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    public abstract int getLayoutId();

    public abstract void initViews();

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
                    .Builder(getContext(), R.style.CustomAlertDialogTheme)
                    .create();
        }
        View view = View.inflate(getContext(), R.layout.layout_progress_dialog, null);
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
    public void onDestroyView() {
        super.onDestroyView();
        //黄油刀unBind
        mBind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空所有事件
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        //添加leakCanary监测
        SuperMarioApp.getRefWatcher().watch(this);
    }
}
