package com.white.ghost.programmersupermario.module.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.bean.LoginBean;
import com.white.ghost.programmersupermario.module.main.fragment.HomePageFragment;
import com.white.ghost.programmersupermario.module.main.fragment.MineFragment;
import com.white.ghost.programmersupermario.module.main.fragment.StudentFragment;
import com.white.ghost.programmersupermario.utils.ConstantUtil;

import java.lang.reflect.Method;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * Function:主页面
 * Author Name: Chris
 * Date: 2019/4/29 17:25
 */
public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private Fragment[] mFragments;
    private HomePageFragment mHomePageFragment;
    private StudentFragment mStudentFragment;
    private MineFragment mMineFragment;
    /**
     * 当前已经选中的tab下标
     */
    private int mCurrentTabIndex;
    /**
     * 点击去选中的tab下标
     */
    private int mSelectIndex;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        initToolBar();
        initViews();
        initFragment();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.home_page);
        mToolbar.setNavigationIcon(R.mipmap.ic_main_navigation);
        setSupportActionBar(mToolbar);
        //显示箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(this);
        //设置侧滑栏的开启关闭监听
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        drawerToggle.syncState();//使Navigation的图标和箭头切换保持同步
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public void initViews() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initFragment() {
        mHomePageFragment = HomePageFragment.newInstance();
        mStudentFragment = StudentFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        mFragments = new Fragment[]{mHomePageFragment, mStudentFragment, mMineFragment};
        //添加第一个展示的fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_content, mHomePageFragment)
                .show(mHomePageFragment)
                .commit();
    }


    //点击导航按钮打开侧边栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //设置右上角显示菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // TODO: 2019/5/14 设置searchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    //设置menu弹出item的icon显示
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    @SuppressLint("PrivateApi") Method method = menu.getClass()
                            .getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    //右上角弹出菜单item点击
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.main_toolbar_message:
                break;
            case R.id.main_toolbar_quick_meeting:
                break;
        }
        return true;
    }

    //底部导航栏和drawerLayout中navigationView的菜单item点击
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_bottom_home:
                if (mHomePageFragment == null) {
                    mHomePageFragment = HomePageFragment.newInstance();
                }
                mSelectIndex = 0;
                switchFragment(item, R.string.home_page);
                break;
            case R.id.main_bottom_student:
                if (mStudentFragment == null) {
                    mStudentFragment = StudentFragment.newInstance();
                }
                mSelectIndex = 1;
                switchFragment(item, R.string.category);
                break;
            case R.id.main_bottom_mine:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                }
                mSelectIndex = 2;
                switchFragment(item, R.string.mine);
                break;
        }
        return false;
    }

    /**
     * 切换显示fragment
     *
     * @param item       底部导航栏
     * @param titleResId toolbar title
     */
    private void switchFragment(MenuItem item, int titleResId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[mCurrentTabIndex]);
        if (!mFragments[mSelectIndex].isAdded()) {
            transaction.add(R.id.fl_content, mFragments[mSelectIndex]);
        }
        transaction.show(mFragments[mSelectIndex]).commit();
        mCurrentTabIndex = mSelectIndex;
        item.setChecked(true);
        mToolbar.setTitle(titleResId);
    }


    public static void launch(Activity activity, Class<MainActivity> aClass,
                              LoginBean.ResponseBean.SchoolBean schoolBean,
                              LoginBean.ResponseBean.TeacherInfoBean teacherInfoBean) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra(ConstantUtil.Key.SCHOOL_INFO, schoolBean);
        intent.putExtra(ConstantUtil.Key.TEACHER_INFO, teacherInfoBean);
        activity.startActivity(intent);
    }
}
