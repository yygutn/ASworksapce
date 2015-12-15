package com.parttime.Activity.Common;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Company.MainActivity_;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.Interface.TopBarStatusRight;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import com.parttime.Utils.ToastUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 公共
 * 登录实现
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById(R.id.login_logo)
    SimpleDraweeView mLogo;
    @ViewById(R.id.login_username)
    EditText mUserName;
    @ViewById(R.id.login_password)
    EditText mPass;
    @ViewById(R.id.login_topBar)
    TopBar mTopbar;

    Intent mIntent;

    @AfterViews
    void start(){
        mTopbar.setTitle("登录");
        Log.w("Jumy","backIcon is :"+getIntent().getBooleanExtra("backToPreActivity",false));
        if (getIntent().getBooleanExtra("backToPreActivity",false)) {
            mTopbar.setBackIconVisible();
        }
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        mTopbar.setRightTitle("注册");
        mTopbar.setTopBarStatusRight(new TopBarStatusRight() {
            @Override
            public void onRightClickDelegate() {
                skipToRegister();
            }
        });
    }

    /**
     * 跳转到登录注册
     */
    private void skipToRegister() {
        mIntent = new Intent(this,RegisterActivity_.class);
        startActivity(mIntent);
    }


    @Click(R.id.login_confirm)
    void click(){
        final String username,pass;
        username = mUserName.getText().toString();
        pass = mPass.getText().toString();
        BmobUser.loginByAccount(this, username, pass, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null){
                    ToastUtil.showToast("登录成功");
                    if (StringUtil.isNullOrEmpty(user.getHead())) {
                        user.setHead(getResources().getString(R.string.defaulthead));
                    }
                    User.saveUserInfo(user);
                    Config.getInstance().set("password",pass);
                    doSwitchMain(user.getType());
                } else {
                    ToastUtil.showToast("登录失败:"+e.getMessage());
                }
            }
        });
    }

    private void doSwitchMain(Boolean type) {
        if (type){
            //company
            mIntent = new Intent(this, MainActivity_.class);
        } else {
            //person
            mIntent = new Intent(this, com.parttime.Activity.User.MainActivity_.class);
        }
        Config.setLoginStatus(true);
        startActivity(mIntent);
        finish();
    }
}
