package com.parttime.Activity.User;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
                backToPreActivity();
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
                backToPreActivity();
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
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSubmit.setText("已报名");
                        //coding
                    }
                })
                .setNegativeButton("取消",null)
                .setMessage("确认报名")
                .show();
        dialog.setCanceledOnTouchOutside(true);
        //end
    }

}
