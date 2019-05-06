package com.white.ghost.programmersupermario.module;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.base.BaseActivity;
import com.white.ghost.programmersupermario.base.BaseResponse;
import com.white.ghost.programmersupermario.bean.LoginBean;
import com.white.ghost.programmersupermario.network.ApiBase;
import com.white.ghost.programmersupermario.network.api.MainService;
import com.white.ghost.programmersupermario.utils.CommonUtil;
import com.white.ghost.programmersupermario.utils.ConstantUtil;
import com.white.ghost.programmersupermario.utils.DeviceUtil;
import com.white.ghost.programmersupermario.utils.SpUtil;
import com.white.ghost.programmersupermario.utils.StatusBarUtil;
import com.white.ghost.programmersupermario.utils.ToastUtil;
import com.white.ghost.programmersupermario.widget.CustomTextWatcher;

import java.util.Map;

import androidx.collection.ArrayMap;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Function:登录页面
 * Author Name: Chris
 * Date: 2019/4/29 17:25
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    TextInputEditText mEtUsername;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    private boolean isUsernameRight;
    private boolean isPasswordRight;
    private String mUsername;
    private String mPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        //解决6.0以上状态栏白色图标看不清
        StatusBarUtil.setStatusBarLightMode(this);
        initViews();
    }

    @Override
    public void initViews() {
        mUsername = SpUtil.getString(ConstantUtil.Key.USERNAME, "");
        mPassword = SpUtil.getString(ConstantUtil.Key.PASSWORD, "");
        mEtUsername.setText(mUsername);
        mEtUsername.setSelection(mUsername.length());
        mEtPassword.setText(mPassword);
        mEtPassword.setSelection(mPassword.length());
        isUsernameRight = !TextUtils.isEmpty(mUsername);
        isPasswordRight = !TextUtils.isEmpty(mPassword);
        //设置文字改变监听
        mEtUsername.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 20 || s.length() < 1) {
                    mTilUsername.setError("账号长度为1-20位");
                    isUsernameRight = false;
                } else {
                    mTilUsername.setError("");
                    isUsernameRight = true;
                }
                mUsername = mEtUsername.getText().toString().trim();
            }
        });
        mEtPassword.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 16 || s.length() < 6) {
                    mTilPassword.setError("密码长度为6-16位");
                    isPasswordRight = false;
                } else {
                    mTilPassword.setError("");
                    isPasswordRight = true;
                }
                mPassword = mEtPassword.getText().toString().trim();
            }
        });
    }


    @OnClick({R.id.btn_login, R.id.rl_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (!isUsernameRight) {
                    ToastUtil.ShortToast("请按提示输入账号");
                } else if (!isPasswordRight) {
                    ToastUtil.ShortToast("请按提示输入密码");
                } else {
                    login();
                }
                break;
            case R.id.rl_login:
                DeviceUtil.hideKeyBoard(this, view);
                break;
        }
    }

    private void login() {
        if (!CommonUtil.isNetworkAvailable(this)) {
            ToastUtil.ShortToast("当前网络不可用，请检查网络");
            return;
        }

        showProgressDialog();
        Map<String, String> map = new ArrayMap<>();
        map.put("username", mUsername);
        map.put("password", mPassword);
        map.put("v", ConstantUtil.sVersion);
        map.put("device", "android");
        map.put("device_token", DeviceUtil.getDeviceId(this));
        ApiBase.createApi(MainService.class)
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        hideProgressDialog();
                        SpUtil.put(ConstantUtil.Key.USERNAME, mUsername);
                        SpUtil.put(ConstantUtil.Key.PASSWORD, mPassword);
                        if (loginBean.getErrorResponse() != null) {
                            BaseResponse.ErrorResponse errorResponse = loginBean.getErrorResponse();
                            ToastUtil.ShortToast(errorResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideProgressDialog();
                    }
                });
    }


}
