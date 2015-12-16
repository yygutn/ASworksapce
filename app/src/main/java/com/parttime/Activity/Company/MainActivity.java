package com.parttime.Activity.Company;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Fragment.Company.CompanyCenterFragment_;
import com.parttime.Fragment.Company.HomeFragment_;
import com.parttime.R;
import com.parttime.UI.CompanyMenuBar;
import com.parttime.UI.Interface.MenuBarStatus;
import com.parttime.UI.Interface.TopBarStatusRight;
import com.parttime.UI.TopBar;
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
    @ViewById(R.id.company_topBar)
    TopBar mTopbar;
    @ColorRes(R.color.white)
    int white;
    private Fragment[] fragments = new Fragment[2];
    private FragmentTransaction fragmentTransaction;

    Context context = this;

    @AfterViews
    void start(){
        deleteAllStackBesideTop();
        switchPage(0);
        changeTitle(0);
        mMenuBar.setMenuStatusListener(new MenuBarStatus() {
            @Override
            public void onMenuBarSwitchedDelegate(int index) {
                switchPage(index);
                changeTitle(index);
            }
        });
        mTopbar.setTopBarStatusRight(new TopBarStatusRight() {
            @Override
            public void onRightClickDelegate() {
                CompanyInfoDetailsActivity_.intent(context).start();
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
            fragmentTransaction.add(R.id.company_frame,fragments[index]);
        }
        fragmentTransaction.commit();
    }

    private Fragment getFragmentPage(int index){
        switch (index){
            case 0:
                return new HomeFragment_();
            case 1:
                return new CompanyCenterFragment_();
            default:return null;
        }
    }

    private void changeTitle(int index){
        if (index == 0){
            mTopbar.setTitle("首页");
            mTopbar.setRightInVisible();
        } else {
            mTopbar.setTitle("我");
            mTopbar.setRightTitle("企业资料");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragments[0] == null && fragment instanceof HomeFragment_){
            fragments[0] = fragment;
        } else if (fragments[1] == null && fragment instanceof CompanyCenterFragment_){
            fragments[1] = fragment;
        }
    }
}
