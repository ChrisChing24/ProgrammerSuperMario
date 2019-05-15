package com.white.ghost.programmersupermario.module.main.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.widget.EmptyView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Function:搜索页面
 * Author Name: Chris
 * Date: 2019/5/14 14:01
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        initToolBar();
        initViews();
        initRecyclerView();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.search);
        setSupportActionBar(mToolbar);
        //显示箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void initViews() {
        mEmptyView.setEmptyText("没搜索到结果哦")
                .setEmptyImage(R.mipmap.ic_empty)
                .show();
    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) searchItem.getActionView();
        //一开始处于展开状态
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("请输入要搜索的内容");
        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        //设置提示文字颜色
        mSearchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        //设置内容文字颜色
        mSearchAutoComplete.setTextColor(ContextCompat.getColor(this, R.color.white));
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //当有提交按钮的时候，点击按钮的事件
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //输入框文字变化的监听
        return true;
    }

    //返回键的点击
    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        //如果此时搜索框展开，点返回则收起搜索框
        if (mSearchAutoComplete.isShown()) {
            mSearchView.setIconified(true);
        } else {//否则直接退出页面
            super.onBackPressed();
        }
    }
}
