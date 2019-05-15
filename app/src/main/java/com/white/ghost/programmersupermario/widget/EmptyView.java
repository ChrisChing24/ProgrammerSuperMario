package com.white.ghost.programmersupermario.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.utils.DisplayUtil;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * Function:空页面展示
 * Author Name: Chris
 * Date: 2019/5/15 10:24
 */
public class EmptyView extends LinearLayout {

    private ImageView mIvEmpty;
    private TextView mTvEmpty;
    private String mImageUrl;
    private int mImageResId;
    private String mEmptyText;
    private int mEmptyTextResId;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //设置布局方向
        setOrientation(VERTICAL);
        //添加图片
        mIvEmpty = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //设置layout_gravity
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        addView(mIvEmpty, layoutParams);
        //添加文字
        mTvEmpty = new TextView(getContext());
        mTvEmpty.setTextSize(15);
        mTvEmpty.setTextColor(ContextCompat.getColor(getContext(), R.color.text_gray));
        //设置gravity
        mTvEmpty.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = DisplayUtil.dp2px(getContext(), 10);
        addView(mTvEmpty, params);
    }

    public EmptyView setEmptyImage(String imageUrl) {
        this.mImageUrl = imageUrl;
        return this;
    }

    public EmptyView setEmptyImage(@DrawableRes int imageResId) {
        this.mImageResId = imageResId;
        return this;
    }

    public EmptyView setEmptyText(String emptyText) {
        this.mEmptyText = emptyText;
        return this;
    }

    public EmptyView setEmptyText(@StringRes int emptyTextResId) {
        this.mEmptyTextResId = emptyTextResId;
        return this;
    }

    public void show() {
        if (!TextUtils.isEmpty(mEmptyText)) {
            mTvEmpty.setText(mEmptyText);
        } else if (mEmptyTextResId > 0) {
            mTvEmpty.setText(mEmptyTextResId);
        } else {
            mTvEmpty.setText(R.string.no_data);
        }

        if (!TextUtils.isEmpty(mImageUrl)) {
            Glide.with(getContext())
                    .load(mImageUrl)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty)
                    .into(mIvEmpty);
        } else if (mImageResId > 0) {
            Glide.with(getContext())
                    .load(mImageResId)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty)
                    .into(mIvEmpty);
        } else {
            Glide.with(getContext())
                    .load(R.mipmap.ic_empty)
                    .centerCrop()
                    .into(mIvEmpty);
        }

        setVisibility(VISIBLE);
    }
}
