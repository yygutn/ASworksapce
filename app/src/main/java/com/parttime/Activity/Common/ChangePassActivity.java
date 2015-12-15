package com.parttime.Activity.Common;

import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.UpdateListener;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import com.parttime.Utils.ToastUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_change_pass)
public class ChangePassActivity extends BaseActivity {


    @ViewById(R.id.resetPWD_top)
    TopBar mTopbar;
    @ViewById(R.id.resetPWD_submit)
    Button mSubmit;
    @ViewById(R.id.password)
    EditText mPass;
    @ViewById(R.id.oldPassword)
    EditText mOldPass;
    @ViewById(R.id.query_password)
    EditText mPassConfirm;

    String oldpass,pass,passverf;

    @AfterViews
    void start(){
        mTopbar.setTitle("重置密码");
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
    }

    @Click(R.id.resetPWD_submit)
    void submitClick(){
        oldpass = mOldPass.getText().toString();
        pass = mPass.getText().toString();
        passverf = mPassConfirm.getText().toString();
        if (!Config.getInstance().get("password","").equals(oldpass)){
            ToastUtil.showToast("旧密码输入错误");
        }
        else if (!StringUtil.isPassValid(pass)){
            ToastUtil.showToast("请检查输入的密码是否合法");
        }
        else if (!pass.equals(passverf)){
            ToastUtil.showToast("请确认两次输入的密码一致");
        }
        else {
            //coding
            User.updateCurrentUserPassword(this,oldpass,pass,new UpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtil.showToast("修改密码成功");
                    backToPreActivity();
                }

                @Override
                public void onFailure(int i, String s) {
                    ToastUtil.showToast("修改密码失败:"+s);
                }
            });
        }
    }

}
