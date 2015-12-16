package com.parttime.Activity.Company;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.Interface.TopBarStatusRight;
import com.parttime.UI.TopBar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * 企业
 * 企业详细信息
 */
@EActivity(R.layout.activity_company_info_details)
public class CompanyInfoDetailsActivity extends BaseActivity {

    @ViewById(R.id.company_info_topBar)
    TopBar mTopbar;

    @ViewById(R.id.company_info_logo)
    SimpleDraweeView mLogo;
    @ViewById(R.id.company_info_name)
    TextView mName;
    @ViewById(R.id.company_info_hr)
    TextView mHr;
    @ViewById(R.id.company_info_phone)
    TextView mPhone;
    @ViewById(R.id.company_info_TEL)
    TextView mTEL;
    @ViewById(R.id.company_info_location)
    TextView mLoc;
    @ViewById(R.id.company_info_remark)
    TextView mRemark;

    Context context = this;

    @AfterViews
    void start(){
        mTopbar.setTitle("企业资料");
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        initViews();
        mTopbar.setRightTitle("修改资料");
        mTopbar.setTopBarStatusRight(new TopBarStatusRight() {
            @Override
            public void onRightClickDelegate() {
                CompanyInfoChangeActivity_.intent(context).startForResult(110);
            }
        });
    }


    private void initViews() {
        BmobQuery<User> query = new BmobQuery<>();
        final User[] user = new User[1];
        query.addWhereEqualTo("username", Config.getInstance().get("username","0"));
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list != null){
                    user[0] = list.get(0);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        if (user[0] == null){
            user[0] = User.getCurrentUser(context,User.class);
        }
        if (user[0] == null){
            user[0] = User.getUserInfo(context);
        }
        if (user[0] != null){
            User.saveUserInfo(user[0]);
        }

        mLogo.setImageURI(Uri.parse(user[0].getHead()));
        mName.setText(user[0].getCompanyName());
        mHr.setText(user[0].getBoss());
        mPhone.setText(user[0].getMobilePhoneNumber());
        mTEL.setText(user[0].getTEL());
        mLoc.setText(user[0].getLocation());
        mRemark.setText(user[0].getRemark());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 1110){
            mLogo.setImageURI(Uri.parse(User.getCurrentUser(context,User.class).getHead()));
        }
    }
}
