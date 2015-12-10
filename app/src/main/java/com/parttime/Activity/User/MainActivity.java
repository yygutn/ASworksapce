package com.parttime.Activity.User;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Fragment.User.JobNextFragment_;
import com.parttime.Fragment.User.JobNodeFragment_;
import com.parttime.Fragment.User.UserCenterFragment_;
import com.parttime.R;
import com.parttime.UI.Interface.MenuBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.UI.UserMenuBar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.User_main_menuBar)
    UserMenuBar mMenuBar;
    @ViewById(R.id.User_main_TopBar)
    TopBar mTopbar;
    @ColorRes(R.color.white)
    int white;
    private Fragment [] fragments = new Fragment[3];


    private FragmentTransaction fragmentTransaction;

    @AfterViews
    void start(){
        switchPage(0);
        changeTitle(0);
        mMenuBar.setMenuStatusListener(new MenuBarStatus() {
            @Override
            public void onMenuBarSwitchedDelegate(int index) {
                switchPage(index);
                changeTitle(index);
            }
        });
    }


    private void switchPage(int index){
        Log.w("Jumy",index+"");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragments[index] != null){
            fragmentTransaction.hide(fragments[BaseActivity.preIndex]);
            fragmentTransaction.show(fragments[index]);
        } else {
            fragments[index] = getFragmentPage(index);
            fragmentTransaction.add(R.id.User_main_frame,fragments[index]);
        }
        Log.w("Jumy",index+":::2");
        fragmentTransaction.commit();
    }

    private Fragment getFragmentPage(int index){
        switch (index){
            case 0:
                return new JobNodeFragment_();
            case 1:
                return new JobNextFragment_();
            case 2:
                return new UserCenterFragment_();
            default:return null;
        }
    }

    private void changeTitle(int index){
        if (index == 0){
            mTopbar.setTitle("今日兼职");
        } else if (index == 1){
            mTopbar.setTitle("预约");
        } else {
            mTopbar.setTitle("个人中心");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragments[0] == null && fragment instanceof JobNodeFragment_){
            fragments[0] = fragment;
        } else if (fragments[1] == null && fragment instanceof JobNextFragment_){
            fragments[1] = fragment;
        } else if (fragments[2] == null && fragment instanceof UserCenterFragment_){
            fragments[2] = fragment;
        }
    }
}
