package com.parttime.Activity.Company;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Fragment.User.JobNodeFragment;
import com.parttime.Fragment.User.UserCenterFragment_;
import com.parttime.R;
import com.parttime.UI.CompanyMenuBar;
import com.parttime.UI.Interface.MenuBarStatus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * 企业
 * 企业端主页
 */
@EActivity(R.layout.activity_main_company)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.company_menuBar)
    CompanyMenuBar mMenuBar;
    @ViewById(R.id.company_TopBar)
    Toolbar mTopbar;
    @ColorRes(R.color.white)
    int white;
    private Fragment[] fragments = new Fragment[2];
    private FragmentTransaction fragmentTransaction;


    @AfterViews
    void start(){
        switchPage(0);
        changeTitle(0);
        mTopbar.setTitleTextColor(white);
        mMenuBar.setMenuStatusListener(new MenuBarStatus() {
            @Override
            public void onMenuBarSwitchedDelegate(int index) {
                switchPage(index);
                changeTitle(index);
            }
        });
    }


    private void switchPage(int index){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragments[index] != null){
            fragmentTransaction.hide(fragments[BaseActivity.preIndex]);
            fragmentTransaction.show(fragments[index]);
        } else {
            fragments[index] = getFragmentPage(index);
            fragmentTransaction.add(R.id.User_main_frame,fragments[index]);
        }
        fragmentTransaction.commit();
    }

    private Fragment getFragmentPage(int index){
        switch (index){
            case 0:
                return new JobNodeFragment();
            case 1:
                return new UserCenterFragment_();
            default:return null;
        }
    }

    private void changeTitle(int index){
        if (index == 0){
            mTopbar.setTitle("首页");
        } else {
            mTopbar.setTitle("个人中心");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragments[0] == null && fragment instanceof JobNodeFragment){
            fragments[0] = fragment;
        } else if (fragments[1] == null && fragment instanceof UserCenterFragment_){
            fragments[1] = fragment;
        }
    }
}
