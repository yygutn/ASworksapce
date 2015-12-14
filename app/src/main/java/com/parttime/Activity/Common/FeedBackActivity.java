package com.parttime.Activity.Common;

import com.parttime.BaseLibs.BaseActivity;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 公共
 * 意见反馈
 */
@EActivity(R.layout.activity_feed_back)
public class FeedBackActivity extends BaseActivity {

    @ViewById(R.id.feedback_topbar)
    TopBar mTopbar;


    @AfterViews
    void start(){
        mTopbar.setTitle("意见反馈");
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                Back();
            }
        });
    }
}
