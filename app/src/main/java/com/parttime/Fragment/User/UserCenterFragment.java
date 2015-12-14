package com.parttime.Fragment.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.FeedBackActivity_;
import com.parttime.Activity.User.MyCollectionActivity_;
import com.parttime.Activity.User.MyPartTimeJobActivity_;
import com.parttime.Modules.Config;
import com.parttime.R;
import com.parttime.UI.UserItem;
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

    @AfterViews
    void start(){
        String urlstr = Config.getInstance().get("head",getResources().getString(R.string.defaulthead));
        Log.w("Jumy",urlstr);
        Uri uri = Uri.parse(urlstr);
        mHead.setImageURI(uri);
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
            R.id.user_center_feedback,R.id.user_center_quit})
    void click(View view){
        switch (view.getId()){
            case R.id.user_center_job:{
                startActivity(createIntent(MyPartTimeJobActivity_.class));
                break;
            }
            case R.id.user_center_wallet:{
                break;
            }
            case R.id.user_center_collection:{
                startActivity(createIntent(MyCollectionActivity_.class));
                break;
            }
            case R.id.user_center_feedback:{
                startActivity(createIntent(FeedBackActivity_.class));
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
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private Intent createIntent(Class T){
        return new Intent(getContext(),T);
    }
}
