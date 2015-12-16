package com.parttime.Fragment.Company;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.FeedBackActivity_;
import com.parttime.Activity.Common.LoginActivity_;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.UserItem;
import com.parttime.Utils.StringUtil;
import com.parttime.Utils.ToastUtil;
import org.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.fragment_company_center)
public class CompanyCenterFragment extends Fragment {

    @ViewsById({R.id.company_center_confirm,R.id.company_center_feedback,R.id.company_center_share})
    List<UserItem> mItems;

    @ViewById(R.id.company_center_name)
    TextView mComName;
    @ViewById(R.id.company_center_nickname)
    TextView mNick;
    @ViewById(R.id.company_center_phone)
    TextView mPhone;
    @ViewById(R.id.company_center_register_time)
    TextView mRegisterTime;
    @ViewById(R.id.company_center_logo)
    SimpleDraweeView mLogo;

    Intent mIntent;

    @AfterViews
    void start(){
        initViews();
    }

    private void initViews() {
        String urlstr = Config.getInstance().get("head",getResources().getString(R.string.defaulthead));
        Uri uri = Uri.parse(urlstr);
        mLogo.setImageURI(uri);
        mItems.get(0).setContent("企业认证");
        mItems.get(0).setIcon(R.mipmap.evaluation);
        mItems.get(1).setContent("意见反馈");
        mItems.get(1).setIcon(R.mipmap.me_opinion);
        mItems.get(2).setContent("分享下载");
        mItems.get(2).setIcon(R.mipmap.me_share);
    }

    @Click({R.id.company_center_confirm,R.id.company_center_feedback,R.id.company_center_share,R.id.company_center_quit})
    void itemClick(View view){
        switch (view.getId()){
            case R.id.company_center_confirm:{
                ToastUtil.showToast("敬请等待");
                break;
            }
            case R.id.company_center_feedback:{
                FeedBackActivity_.intent(getContext()).start();
                break;
            }
            case R.id.company_center_share:{
                ToastUtil.showToast("敬请等待");
                break;
            }
            case R.id.company_center_quit:{
                doQuit();
                break;
            }
            default:break;
        }
    }

    private void doQuit() {
        //coding
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("确认退出账号")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清除账号信息
                        Config.setLoginStatus(false);
                        doClear();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void doClear() {
        User.logOut(getContext());
        LoginActivity_.intent(getContext()).extra("backToPreActivity",false).start();
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = User.getCurUser(getContext());
        if (!StringUtil.isNullOrEmpty(user.getHead())) {
            mLogo.setImageURI(Uri.parse(user.getHead()));
        }
    }
}
