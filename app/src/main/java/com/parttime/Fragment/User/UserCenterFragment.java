package com.parttime.Fragment.User;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.FeedBackActivity_;
import com.parttime.Activity.Common.LoginActivity_;
import com.parttime.Activity.User.MyCollectionActivity_;
import com.parttime.Activity.User.MyPartTimeJobActivity_;
import com.parttime.Activity.User.UserInfoDetailsActivity_;
import com.parttime.Modules.Config;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.UserItem;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.*;

import java.util.List;

/**
 * Created by Jumy on 15/12/7 下午2:00.
 * deadline is the first productivity
 */
@EFragment(R.layout.fragment_user_center)
public class UserCenterFragment extends Fragment{
    private static final String TAG = UserCenterFragment.class.getSimpleName();

    @ViewsById({R.id.user_center_job,R.id.user_center_wallet,R.id.user_center_collection,R.id.user_center_feedback})
    List<UserItem> mItemList;

    @ViewById(R.id.user_center_head)
    SimpleDraweeView mHead;
    @ViewById(R.id.user_center_nickname)
    TextView mNickName;

    User user;

    @AfterViews
    void start(){
        String urlstr = "";
        user = User.getCurrentUser(getContext(),User.class);
        if (user!=null) {
            urlstr = user.getHead();
        }
        if (StringUtil.isNullOrEmpty(urlstr)) {
            urlstr = Config.getInstance().get("head", getResources().getString(R.string.defaulthead));
        }
        Log.w(TAG,urlstr);
        Uri uri = Uri.parse(urlstr);
        mHead.setImageURI(uri);
        String nick = "";
        if (user!=null){
            nick = user.getNickname();
        }
        if (StringUtil.isNullOrEmpty(nick)){
            nick = Config.getInstance().get("nickname", "");
        }
        mNickName.setText(nick);
        InitItems();
    }

    private void InitItems() {
        if (mItemList == null) {
            return;
        }
        mItemList.get(0).setIcon(R.mipmap.work);
        mItemList.get(0).setContent("我的兼职");
        mItemList.get(1).setIcon(R.mipmap.wallet);
        mItemList.get(1).setContent("我的钱包");
        mItemList.get(2).setIcon(R.mipmap.collection);
        mItemList.get(2).setContent("我的收藏");
        mItemList.get(3).setIcon(R.mipmap.words);
        mItemList.get(3).setContent("意见反馈");

    }


    @Click({R.id.user_center_job,R.id.user_center_wallet,R.id.user_center_collection,
            R.id.user_center_feedback,R.id.user_center_quit,R.id.user_center_nickname,R.id.user_center_head})
    void click(View view){
        switch (view.getId()){
            case R.id.user_center_head:
            case R.id.user_center_nickname:{
                UserInfoDetailsActivity_.intent(this).start();
                break;
            }
            case R.id.user_center_job:{
                MyPartTimeJobActivity_.intent(getContext()).start();
                break;
            }
            case R.id.user_center_wallet:{
                break;
            }
            case R.id.user_center_collection:{
                MyCollectionActivity_.intent(getContext()).start();
                break;
            }
            case R.id.user_center_feedback:{
                FeedBackActivity_.intent(getContext()).start();
                break;
            }
            case R.id.user_center_quit:{
                readyToQuit();
                break;
            }
            default:break;
        }
    }

    private void readyToQuit() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("确认退出账号")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        mHead.setImageURI(Uri.parse(User.getCurrentUser(getContext(),User.class).getHead()));
        mNickName.setText(User.getCurrentUser(getContext(),User.class).getNickname());
    }
}
