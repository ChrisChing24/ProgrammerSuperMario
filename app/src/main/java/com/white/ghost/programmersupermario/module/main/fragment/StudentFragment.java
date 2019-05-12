package com.white.ghost.programmersupermario.module.main.fragment;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseLazyFragment;

/**
 * Function: 学生界面
 * Author Name: Chris
 * Date: 2019/5/12 10:34
 * Copyright © 2006-2018 高顿网校, All Rights Reserved.
 */
public class StudentFragment extends BaseLazyFragment {

    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragement_student;
    }

    @Override
    public void initViews() {

    }
}
