package com.white.ghost.programmersupermario.module;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.utils.CommonUtil;
import com.white.ghost.programmersupermario.utils.ConstantUtil;
import com.white.ghost.programmersupermario.utils.DisplayUtil;
import com.white.ghost.programmersupermario.utils.SpUtil;
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
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Function: 闪屏页
 * Author Name: Chris
 * Date: 2019/4/30 14:33
 */
public class SplashScreenActivity extends BaseActivity {
    @BindView(R.id.banner_splash)
    Banner mBanner;
    @BindView(R.id.fab_splash)
    FloatingActionButton mFabSplash;
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_splash_countdown_time)
    TextView mTvCountdownTime;
    private static final String TAG = "SplashScreenActivity";
    private static final int COUNTDOWN_MAX_VALUE = 6;
    private List<String> mImageList = new ArrayList<>();
    private boolean isFirstEnter;
    private Disposable mDisposable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void init() {
        getExtraData();
        initViews();
        startCountdown();
        getImageList();
        initBanner();
    }

    @Override
    public void getExtraData() {
        ConstantUtil.sVersion = CommonUtil.getLocalVersion(this);
        isFirstEnter = SpUtil.getBoolean(ConstantUtil.Key.IS_FIRST_ENTER, true);
        if (isFirstEnter) {
            SpUtil.putBoolean(ConstantUtil.Key.IS_FIRST_ENTER, false);
        }
    }

    @Override
    public void initViews() {
        Glide.with(this)
                .load(R.mipmap.avengers_bg)
                .centerCrop()
                .into(mIvSplash);
        //设置背景
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(DisplayUtil.dp2px(this, 25));
        drawable.setStroke(DisplayUtil.dp2px(this, 1),
                getResources().getColor(R.color.text_black));
        mTvCountdownTime.setBackground(drawable);
    }

    private void startCountdown() {
        //设置倒计时
        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(COUNTDOWN_MAX_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    long time = COUNTDOWN_MAX_VALUE - (aLong + 1);
                    Log.i(TAG, String.valueOf(time));
                    if (time > 0) {
                        mTvCountdownTime.setText(String.format(Locale.getDefault(),
                                "跳过 %ds", time));
                    } else {
                        if (isFirstEnter) {
                            mIvSplash.setVisibility(View.GONE);
                            mTvCountdownTime.setVisibility(View.GONE);
                            mBanner.setVisibility(View.VISIBLE);
                            mFabSplash.setVisibility(View.VISIBLE);
                        } else {
                            startActivity(new Intent(SplashScreenActivity.this,
                                    LoginActivity.class));
                            finish();
                        }
                    }
                });
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

    /**
     * 获取json文件中的所有图片地址
     */
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

    @OnClick({R.id.tv_splash_countdown_time, R.id.fab_splash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_splash_countdown_time://跳过
                startActivity(new Intent(SplashScreenActivity.this,
                        LoginActivity.class));
                finish();
                break;
            case R.id.fab_splash:
                Snackbar.make(mFabSplash, "确定进入登录页面吗？", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.app_main_color))
                        .setAction("确定", (View v) -> {
                            startActivity(new Intent(SplashScreenActivity.this,
                                    LoginActivity.class));
                            finish();
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


}
