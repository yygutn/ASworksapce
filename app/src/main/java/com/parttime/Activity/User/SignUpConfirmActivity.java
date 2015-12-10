package com.parttime.Activity.User;

import android.view.View;
import android.widget.TextView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 个人
 * 报名确认
 */
@EActivity(R.layout.activity_sign_up_confirm)
public class SignUpConfirmActivity extends BaseActivity {
    @ViewById(R.id.sign_up_jobName)
    TextView mJobName;
    @ViewById(R.id.sign_up_gatheringTime)
    TextView mGatheringTime;
    @ViewById(R.id.sign_up_workTime)
    TextView mWorkTime;
    @ViewById(R.id.sign_up_confirm)
    TextView mSubmit;

    @ViewById(R.id.sign_up_top)
    TopBar mTopbar;

    Node mNode;

    @AfterViews
    void start(){
        mNode = (Node) getIntent().getSerializableExtra(Node.TAG);
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                Back();
            }
        });
        mTopbar.setTitle("报名确认");
        initView();
    }

    private void initView() {
        if (mNode == null) {
            return;
        }
        mJobName.setText(mNode.getJobname());
        mGatheringTime.setText(StringUtil.getSecondTotime(mNode.getGathering_time()));
        mWorkTime.setText(mNode.getTimeLine());
    }

    @Click({R.id.sign_up_confirm,R.id.sign_up_cancel})
    void click(View view){
        switch (view.getId()){
            case R.id.sign_up_cancel:{
                Back();
                break;
            }
            case R.id.sign_up_confirm:{
                doSignUp();
                break;
            }
        }
    }

    private void doSignUp() {
        //coding
        //end
        mSubmit.setText("已报名");
    }

}
