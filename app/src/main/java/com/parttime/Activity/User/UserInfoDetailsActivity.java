package com.parttime.Activity.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.ChangePassActivity_;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 个人用户
 * 详细信息
 */
@EActivity(R.layout.activity_user_info_details)
public class UserInfoDetailsActivity extends BaseActivity {


    @ViewById(R.id.user_info_details_head)
    SimpleDraweeView mHead;
    @ViewById(R.id.user_info_details_nick)
    TextView mNickname;
    @ViewById(R.id.user_info_details_remark)
    TextView mRemark;
    @ViewById(R.id.user_info_details_ContactMethod)
    TextView mPhone;

    Bundle bundle;

    @AfterViews
    void start() {
        User user = User.getUserInfo(this);
        mHead.setImageURI(Uri.parse(user.getHead()));
        mNickname.setText(user.getNickname());
        mRemark.setText(user.getRemark());
        mPhone.setText(user.getMobilePhoneNumber());
    }

    @Click({R.id.user_info_details_ll1, R.id.user_info_details_ll2, R.id.user_info_details_ll3,
            R.id.user_info_details_ll4, R.id.user_info_details_ll5, R.id.user_info_details_ll6,
            R.id.user_info_details_ll7})
    void lvClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_details_ll1: {
                //HEAD IMAGE
                break;
            }
            case R.id.user_info_details_ll2: {
                ChangeInfoActivity_.intent(this).extra("key",111).startForResult(111);
                break;
            }
            case R.id.user_info_details_ll3: {
                ChangeInfoActivity_.intent(this).extra("key",222).startForResult(222);
                break;
            }
            case R.id.user_info_details_ll4: {
                break;
            }
            case R.id.user_info_details_ll5: {
                break;
            }
            case R.id.user_info_details_ll6: {
                ChangeInfoActivity_.intent(this).extra("key",333).startForResult(333);
                break;
            }
            case R.id.user_info_details_ll7: {
                //change password
                ChangePassActivity_.intent(this).start();
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        String text = data.getStringExtra("text");
        if (StringUtil.isNullOrEmpty(text)){
            return;
        }
        if (requestCode == 111 && resultCode == 1110){
            mNickname.setText(text);
        } else if (requestCode == 222 && resultCode == 2220){
            mRemark.setText(text);
        } else if (requestCode == 333 && resultCode == 3330){
            mPhone.setText(text);
        }

    }
}
