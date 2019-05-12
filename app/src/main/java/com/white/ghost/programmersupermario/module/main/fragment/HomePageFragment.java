package com.white.ghost.programmersupermario.module.main.fragment;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseLazyFragment;

/**
 * Function: 主页fragment
 * Author Name: Chris
 * Date: 2019/5/12 10:33
 * Copyright © 2006-2018 高顿网校, All Rights Reserved.
 */
public class HomePageFragment extends BaseLazyFragment {

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initViews() {

    }
}
