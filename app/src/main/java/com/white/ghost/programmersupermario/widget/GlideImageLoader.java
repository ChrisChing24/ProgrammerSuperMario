package com.white.ghost.programmersupermario.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.white.ghost.programmersupermario.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Function:用于banner中Glide加载图片
 * Author Name: Chris
 * Date: 2019/5/5 16:19
 * Copyright © 2006-2018 高顿网校, All Rights Reserved.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.marvel_studios_logo)
                .centerCrop()
                .into(imageView);
    }
}
