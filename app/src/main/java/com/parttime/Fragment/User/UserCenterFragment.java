package com.parttime.Fragment.User;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.FeedBackActivity_;
import com.parttime.Activity.User.MyCollectionActivity_;
import com.parttime.Activity.User.MyPartTimeJobActivity_;
import com.parttime.Modules.Config;
import com.parttime.R;
import com.parttime.UI.CustomAlertDialog;
import com.parttime.UI.UserItem;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
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
        ArrayList<Integer> idlist1 = new ArrayList<>();
        idlist1.add(R.id.select_show);
        idlist1.add(R.id.select_ok);
        idlist1.add(R.id.select_cancel);
        CustomAlertDialog dialog = new CustomAlertDialog(getContext(),R.layout.dialog_quit,idlist1) {
            @Override
            protected void onclickCallback(int num) {
                cancel();
                if (num == 1){
                    //OK
                } else if (num == 2){
                    //cancel
                }
            }
        };
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private Intent createIntent(Class T){
        return new Intent(getContext(),T);
    }
}
