package com.parttime.Activity.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import com.parttime.Activity.Company.MainActivity_;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Jumy on 15/12/9 下午4:59.
 * deadline is the first productivity
 * 启动界面
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity{


    /**
     * 在屏幕上添加一个转动的小菊花（传说中的Loading），默认为隐藏状态
     * 注意：务必保证此方法在setContentView()方法后调用，否则小菊花将会处于最底层，被屏幕其他View给覆盖
     *
     * @param activity                    需要添加菊花的Activity
     * @param customIndeterminateDrawable 自定义的菊花图片，可以为null，此时为系统默认菊花
     * @return {ProgressBar}    菊花对象
     */
    public static ProgressBar createProgressBar(Activity activity, Drawable customIndeterminateDrawable) {
        // activity根部的ViewGroup，其实是一个FrameLayout
        FrameLayout rootContainer = (FrameLayout) activity.findViewById(android.R.id.content);
        // 给progressbar准备一个FrameLayout的LayoutParams
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置对其方式为：屏幕居中对其
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;

        ProgressBar progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.GONE);
        progressBar.setLayoutParams(lp);
        // 自定义小菊花
        if (customIndeterminateDrawable != null) {
            progressBar.setIndeterminateDrawable(customIndeterminateDrawable);
        }
        // 将菊花添加到FrameLayout中
        rootContainer.addView(progressBar);
        return progressBar;
    }

    Intent mIntent;

    Context context = this;

    @AfterViews
    void start(){

        doDelay(2000);
    }

    private void init() {
        createProgressBar(this,null);
        User user = User.getCurrentUser(context,User.class);
        if (!Config.getLoginStatus()){
            LoginActivity_.intent(context).extra("backToPreActivity",false).start();
        } else {
            if (user.getType()){
                //company
                User.loginByAccount(this, user.getUsername(), Config.getInstance().get("password", ""), new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (user != null){
                            Config.setLoginStatus(true);
                            User.saveUserInfo(user);
                            MainActivity_.intent(context).start();
                        } else {
                            LoginActivity_.intent(context).extra("backToPreActivity",false).start();
                        }
                    }
                });
            } else {
                //person
                User.loginByAccount(this, user.getUsername(), Config.getInstance().get("password", ""), new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (user != null){
                            Config.setLoginStatus(true);
                            User.saveUserInfo(user);
                            com.parttime.Activity.User.MainActivity_.intent(context).start();
                        } else {
                            LoginActivity_.intent(context).extra("backToPreActivity",false).start();
                        }
                    }
                });
            }
        }
        finish();
    }


    private void doDelay(int delay){
        final Handler handler = new Handler();
        Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                init();
            }
        };
        handler.postDelayed(runnable,delay);
    }
}
