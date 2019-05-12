package com.white.ghost.programmersupermario.module.main.fragment;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseLazyFragment;

/**
 * Function: 我的界面
 * Author Name: Chris
 * Date: 2019/5/12 10:34
 * Copyright © 2006-2018 高顿网校, All Rights Reserved.
 */
public class MineFragment extends BaseLazyFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {

    }
}
