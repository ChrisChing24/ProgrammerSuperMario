package com.white.ghost.programmersupermario.module;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Function: 闪屏页
 * Author Name: Chris
 * Date: 2019/4/30 14:ms
 */

public class SplashScreenActivity extends BaseActivity {
    @BindView(R.id.banner_splash)
    Banner mBanner;
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private List<String> mImageList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void initViews() {
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.mipmap.marvel_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mIvSplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getImageList();
                initBanner();
            }
        }, 6000);

    }


    private void initBanner() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mImageList);
        //设置mBanner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(false);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当mBanner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //mBanner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    //获取json文件中的所有图片地址
    private void getImageList() {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("marvel_studios.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.optJSONArray("marvel_studios");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject imageObject = (JSONObject) jsonArray.get(i);
                    mImageList.add(imageObject.optString("image"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.fab_splash)
    public void onViewClicked(View view) {
        Snackbar.make(view, "确定进入登录页面吗？", Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.app_main_color))
                .setAction("确定", v ->
                        startActivity(new Intent(SplashScreenActivity.this,
                                LoginActivity.class)))
                .show();
    }
}
