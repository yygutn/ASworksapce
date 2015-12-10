package com.parttime.Activity.User;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import com.parttime.Utils.ToastUtil;
import org.androidannotations.annotations.*;

import java.util.List;

/**
 * 个人
 * 工作详情 && 企业详情
 */
@EActivity(R.layout.activity_job_details)
public class JobDetailsActivity extends BaseActivity {

    @ViewById(R.id.work_details_topBar)
    TopBar mTopbar;
    @ViewById(R.id.work_details_id)
    TextView mJobId;
    @ViewById(R.id.work_details_name)
    TextView mJobName;
    @ViewById(R.id.work_details_type)
    TextView mJobType;
    @ViewById(R.id.work_details_number)
    TextView mNumber;
    @ViewById(R.id.work_details_expected)
    TextView mExpected;
    @ViewById(R.id.work_details_signUp_deadline)
    TextView mDeadLine;
    @ViewById(R.id.work_details_pay)
    TextView mPay;
    @ViewById(R.id.work_details_company)
    TextView mCompany;
    @ViewsById({R.id.details_v1, R.id.details_v2, R.id.details_v3, R.id.details_v4})
    List<TextView> mRequirements;
    @ViewById(R.id.work_details_jiheTime)
    TextView mGatheringTime;
    @ViewById(R.id.work_details_jiheLoc)
    TextView mGatheringLoc;
    @ViewById(R.id.work_details_date)
    TextView mWorkDate;
    @ViewById(R.id.list_item_workTimeRange)
    TextView mTimeRange;
    @ViewById(R.id.work_details_location)
    TextView mJobLocation;
    @ViewById(R.id.work_details_info)
    TextView mJobIntroduce;

    @ViewById(R.id.topBar_rightTV)
    TextView mRight;

    Node mNode;

    @AfterViews
    void start() {
        mNode = (Node) getIntent().getSerializableExtra(Node.TAG);
        InitViews();
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                Back();
            }
        });
        mTopbar.setTitle("兼职详情");
        mTopbar.setRightTitle("收藏兼职");
    }

    private void InitViews() {
        if (mNode == null) {
            return;
        }
        mJobId.setText(mNode.getId() + "");//信息编号
        mJobName.setText(mNode.getJobname());//兼职名称
        mJobType.setText(mNode.getWorkType());//兼职类型
        mNumber.setText(mNode.getNumHave() + "");
        mExpected.setText(mNode.getNumExpected() + "");
        mDeadLine.setText(mNode.getSignUpDeadLine());//报名截止
        mPay.setText(mNode.getPay());//报酬
        mCompany.setText(mNode.getCompany());//所属公司
        boolean flag1, flag2, flag3, flag4;
        flag1 = flag2 = flag3 = flag4 = false;
        if (!StringUtil.isNullOrEmpty(mNode.getV1())) {
            flag1 = true;
            mRequirements.get(0).setText(mNode.getV1());
        }
        if (!StringUtil.isNullOrEmpty(mNode.getV2())) {
            flag2 = true;
            mRequirements.get(1).setVisibility(View.VISIBLE);
            mRequirements.get(1).setText(mNode.getV2());
        }
        if (!StringUtil.isNullOrEmpty(mNode.getV3())) {
            flag3 = true;
            mRequirements.get(2).setVisibility(View.VISIBLE);
            mRequirements.get(2).setText(mNode.getV3());
        }
        if (!StringUtil.isNullOrEmpty(mNode.getV4())) {
            flag4 = true;
            mRequirements.get(3).setVisibility(View.VISIBLE);
            mRequirements.get(3).setText(mNode.getV4());
        }
        if (!(flag1 && flag2 && flag3 && flag4)) {
            mRequirements.get(0).setText("无要求");
        }
        mGatheringTime.setText(StringUtil.getSecondTotime(mNode.getGathering_time()));//集合时间
        mGatheringLoc.setText(mNode.getGathering_location());//集合地点
        mWorkDate.setText(mNode.getWorktimerange());//工作日期
        mTimeRange.setText(mNode.getTimeLine());//工作时间
        mJobLocation.setText(mNode.getWorkLocation());//工作地点
        mJobIntroduce.setText(mNode.getRemark());//工作简介
    }


    @Click({R.id.work_details_signUp,R.id.topBar_rightTV})
    void click(View view) {
        switch (view.getId()){
            case R.id.work_details_signUp:{
                Intent mIntent = new Intent(this, SignUpConfirmActivity_.class);
                mIntent.putExtra(Node.TAG, mNode);
                startActivity(mIntent);
                break;
            }
            case R.id.topBar_rightTV:{
                doCollectionOrNot();
                break;
            }
            default:break;
        }
    }

    private void doCollectionOrNot() {
        ToastUtil.showToast("aaaa");
    }
}
