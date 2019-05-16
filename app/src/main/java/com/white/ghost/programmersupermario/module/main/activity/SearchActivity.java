package com.white.ghost.programmersupermario.module.main.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.adapter.SearchAdapter;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.bean.SearchBean;
import com.white.ghost.programmersupermario.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private static final String TAG = "SearchActivity";
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private List<SearchBean> mSearchList = new ArrayList<>();
    private SearchAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        initToolBar();
        initRecyclerView();
        loadData();
    }

    @Override
    public void initToolBar() {
        // TODO: 2019/5/16 searchView展开搜索的时候toolbar也会变的很大
        mToolbar.setTitle(R.string.search);
        setSupportActionBar(mToolbar);
        //显示箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SearchAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {
        Observable.just(24)
                .delay(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(integer -> {
                    Log.i(TAG, "doOnNextThread = " + Thread.currentThread());
                    for (int i = 0; i < integer; i++) {
                        SearchBean searchBean = new SearchBean();
                        if (i % 2 == 0) {
                            searchBean.setName("十一月的肖邦");
                        } else if (i % 3 == 0) {
                            searchBean.setName("范特西");
                        } else {
                            searchBean.setName("依然范特西");
                        }
                        mSearchList.add(searchBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
        search(newText);
        return true;
    }

    private void search(String text) {
        List<SearchBean> searchList = new ArrayList<>();
        Observable.just(searchList)
                .debounce(300, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(list -> {
                    //just中的对象会传递到doOnNext，但是doOnNext操作完成并不会传递到subscribe的onNext
                    for (int i = 0; i < mSearchList.size(); i++) {
                        SearchBean searchBean = mSearchList.get(i);
                        if (!TextUtils.isEmpty(text) && searchBean.getName().contains(text)) {
                            searchList.add(searchBean);
                        }
                    }
                    Log.i(TAG, "doOnNext = " + searchList.size());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (searchList.size() == 0) {
                        mEmptyView.setEmptyText("没搜索到结果哦")
                                .setEmptyImage(R.mipmap.ic_empty)
                                .show();
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    mAdapter.update(searchList);
                });
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
            mSearchAutoComplete.setText("");
            mSearchView.setIconified(true);
        } else {//否则直接退出页面
            super.onBackPressed();
        }
    }
}
