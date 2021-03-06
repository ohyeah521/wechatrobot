package cn.mandroid.wechatrobot.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.mandroid.wechatrobot.anotation.LayoutId;
import cn.mandroid.wechatrobot.model.common.Api;
import cn.mandroid.wechatrobot.ui.activity.wechatlogin.WechatLoginActivity;
import cn.mandroid.wechatrobot.utils.MLog;

/**
 * Created by wrBug on 2016/12/31.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected Context mContext;
    private static List<BaseActivity> mActivitylist = new ArrayList<>();
    protected T mPersenter;
    protected ActionBar mActionBar;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitylist.add(this);
        mContext = this;
        beforeInject();
        int layoutId = getLayoutId();
        super.setContentView(layoutId);
        ButterKnife.bind(this);
        mPersenter = setPresenter();
        if (mPersenter == null) {
            throw new NullPointerException("presenter is null");
        }
        afterView();
    }

    @Deprecated
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        throw new RuntimeException("use annotation LayoutId");
    }

    public int getLayoutId() {
        LayoutId layoutId = getClass().getAnnotation(LayoutId.class);
        if (layoutId != null) {
            return layoutId.value();
        }
        return -1;
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
    }

    protected abstract void afterView();

    protected void beforeInject() {
    }

    protected abstract T setPresenter();

    @Override
    public void exit() {
        for (BaseActivity activity : mActivitylist) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 设置子标题
     *
     * @param subTitle
     */
    @Override
    public void setActionBarSubTitle(String subTitle) {
        mActionBar.setSubtitle(subTitle);
    }

    @Override
    public void setActionBarSubTitle(int resId) {
        mActionBar.setSubtitle(resId);
    }

    @Override
    public void showLog(String log) {
        MLog.i(log);
    }

    @Override
    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    @Override
    public void setActionBarTitle(int resId) {
        mActionBar.setTitle(resId);
    }

    @Override
    public Context getActivity() {
        return mContext;
    }

    @Override
    public void showProgress() {

    }

    /**
     * 进入登录页
     */
    @Override
    public void enterLoginActivity() {
        Api.cleanCookie();
        startActivity(new Intent(this, WechatLoginActivity.class));
        finish();
    }

    public void showToast(String msg, CoordinatorLayout coordinatorLayout) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String msg) {
        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }
}
