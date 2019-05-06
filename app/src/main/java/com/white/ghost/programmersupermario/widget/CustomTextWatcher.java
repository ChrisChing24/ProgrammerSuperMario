package com.white.ghost.programmersupermario.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Function: 实现EditText文字监听，避免每次重写三个方法
 * Author Name: Chris
 * Date: 2019/5/6 13:14
 */

public class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
