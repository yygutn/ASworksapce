package com.parttime.Activity.Common;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;
import com.parttime.Activity.User.MainActivity_;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.Utils.StringUtil;
import com.parttime.Utils.ToastUtil;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 公共
 * 注册账户－User||Company
 */
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewById(R.id.register_submit)
    TextView mSubmit;
    @ViewById(R.id.register_company)
    CheckBox isCompamy;
    @ViewById(R.id.register_user)
    CheckBox isUser;
    @ViewById(R.id.register_username)
    EditText mUername;
    @ViewById(R.id.register_email)
    EditText mEmail;
    @ViewById(R.id.register_pass)
    EditText mPass;
    @ViewById(R.id.register_pass_confirm)
    EditText mPassVerf;

    Boolean flag = false;

    Intent mIntent;


    @Click({R.id.register_user,R.id.register_company,R.id.register_submit})
    void click(View view){
        switch (view.getId()){
            case R.id.register_company:{
                checkBoxChecked(true);
                break;
            }
            case R.id.register_user:{
                checkBoxChecked(false);
                break;
            }
            case R.id.register_submit:{
                doRegister();
                break;
            }
            default:break;
        }
    }

    private void doRegister() {
        String username,password,passVerf,email;
        username = mUername.getText().toString();
        email = mEmail.getText().toString();
        password = mPass.getText().toString();
        passVerf = mPassVerf.getText().toString();

        if (StringUtil.isNullOrEmpty(username)){
            ToastUtil.showToast("用户名不能为空！");
            return;
        }
        if (StringUtil.isNullOrEmpty(email)){
            ToastUtil.showToast("邮箱不能为空");
            return;
        }
        if (StringUtil.isNullOrEmpty(password)){
            ToastUtil.showToast("密码不能为空");
            return;
        }
        if (!password.equals(passVerf)){
            ToastUtil.showToast("两次输入密码不一致");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setType(flag);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("注册成功");
                if (flag){
                    creatrIntent(com.parttime.Activity.Company.MainActivity_.class);
                } else {
                    creatrIntent(MainActivity_.class);
                }
                startActivity(mIntent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.showToast("注册失败:"+s);
            }
        });

    }

    private void checkBoxChecked(Boolean flag){
        if (flag){
            isCompamy.setChecked(true);
            isUser.setChecked(false);
        } else {
            isCompamy.setChecked(false);
            isUser.setChecked(true);
        }
        this.flag = flag;
    }
    private void creatrIntent(Class T){
        mIntent = new Intent(this,T);
    }


}
