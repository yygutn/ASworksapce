package com.parttime.Activity.User;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.*;

/**
 * 个人用户
 * 修改个人信息
 */
@EActivity(R.layout.activity_change_info)
public class ChangeInfoActivity extends BaseActivity {

    @ViewById(R.id.user_info_change_topBar)
    TopBar mTopbar;
    @ViewById(R.id.user_info_change_edit)
    EditText mText;
    @ViewById(R.id.user_info_change_submit)
    TextView mSubmit;

    int requestCode;

    @AfterViews
    void start(){
        requestCode = getIntent().getIntExtra("key",0);
        Log.w("Jumy","requestCode: "+requestCode);
        if (requestCode == 0){
            backToPreActivity();
        }
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        if (requestCode == 111){
            mTopbar.setTitle("修改昵称");
            mText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        } else if (requestCode == 222){
            mTopbar.setTitle("修改个性签名");
            mText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        } else if (requestCode == 333){
            mTopbar.setTitle("修改手机");
            mText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        }
    }

    @Click(R.id.user_info_change_submit)
    void click(){
        String text = mText.getText().toString();
        Intent data = new Intent();
        if (requestCode == 333 && !StringUtil.isMobileValid(text)){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("请输入有效手机号码")
                    .setPositiveButton("确定",null)
                    .show();
            dialog.setCanceledOnTouchOutside(true);
            return;
        }
        data.putExtra("text",text);
        setResult(requestCode*10,data);
        backToPreActivity();
    }
}
