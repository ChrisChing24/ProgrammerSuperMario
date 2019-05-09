package com.white.ghost.programmersupermario.module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.bean.LoginBean;
import com.white.ghost.programmersupermario.utils.ConstantUtil;

import java.io.Serializable;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private LoginBean.ResponseBean.SchoolBean mSchoolBean;
    private LoginBean.ResponseBean.TeacherInfoBean mTeacherInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        getExtraData();
        initToolBar();
        initViews();
    }

    @Override
    public void getExtraData() {
        if (getIntent().getExtras() != null) {
            mSchoolBean = (LoginBean.ResponseBean.SchoolBean) getIntent()
                    .getSerializableExtra(ConstantUtil.Key.SCHOOL_INFO);
            mTeacherInfoBean = (LoginBean.ResponseBean.TeacherInfoBean) getIntent()
                    .getSerializableExtra(ConstantUtil.Key.TEACHER_INFO);
        }
    }

    @Override
    public void initViews() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(R.string.home_page);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);
    }

    //设置右上角显示菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    //右上角弹出菜单item点击
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
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
                break;
            case R.id.main_bottom_student:
                break;
            case R.id.main_bottom_mine:
                break;
        }
        return false;
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

    public static void launch(Activity activity, Class<MainActivity> aClass,
                              LoginBean.ResponseBean.SchoolBean schoolBean,
                              LoginBean.ResponseBean.TeacherInfoBean teacherInfoBean) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra(ConstantUtil.Key.SCHOOL_INFO, schoolBean);
        intent.putExtra(ConstantUtil.Key.TEACHER_INFO, teacherInfoBean);
        activity.startActivity(intent);
    }
}
