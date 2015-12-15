package com.parttime.Activity.User;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.parttime.Adapter.Base.BaseRecViewClickStatus;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 个人用户
 * 我的收藏－兼职
 */
@EActivity(R.layout.activity_my_collection)
public class MyCollectionActivity extends BaseActivity {

    @ViewById(R.id.collection_top)
    TopBar mTopbar;
    @ViewById(R.id.collection_refresh)
    PullToRefreshView mRefresh;
    @ViewById(R.id.collection_recView)
    RecyclerView mRecView;

    JobListAdapter adapter = null;

    private Context context = this;

    @AfterViews
    void start(){
        mTopbar.setTitle("我的收藏");
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        mRefresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefresh.setRefreshing(false);
                        //coding
                        updateList();
                    }
                },1500);
            }
        });
        mRecView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        initData();
        adapter.setRecViewClickStatus(new BaseRecViewClickStatus<Node>() {
            @Override
            public void onItemClickDelegate(View view, Node item) {
                //coding
                Intent mIntent = new Intent(context, JobDetailsActivity_.class);
                mIntent.putExtra(Node.TAG,item);
                startActivity(mIntent);
            }
        });
    }

    private void initData(){
        Node item;
        List<Node> list = new ArrayList<>();
        Random random = new Random();
        int len = random.nextInt()%50;
        len = len<0? len*(-1) : len;
        for(int i=0;i<len;i++){
            item = new Node();
            item.setGathering_location("二十五号大街"+random.nextInt(10000)+"号");
            item.setId(random.nextInt(1000000000));
            item.setRemark("风筝在阴天搁浅，想念还在等待救援"+ random.nextInt(100));
            item.setCompanyId(random.nextInt(1000000000));
            item.setCompany("搁浅"+random.nextInt(100));
            item.setTimeLine("早9点 到 晚5点");
            item.setPay(random.nextInt(500)+"/天");
            int a = random.nextInt()%3;
            item.setSexExpected(a==0?"男":(a==1?"女":"性别不限"));
            item.setGathering_time(random.nextInt());
            item.setJobname("LOL代练" + random.nextInt(50));
            item.setWorkLocation("二十五号大街" + random.nextInt(10000)+"号");
            item.setTime_start(random.nextInt());
            item.setTime_end(random.nextInt());
            item.setWorkType("其他" + random.nextInt(100));
            item.setNumExpected(random.nextInt(100));
            item.setNumHave(random.nextInt(100));
            list.add(item);
        }
        adapter = new JobListAdapter(list);
        mRecView.setAdapter(adapter);
    }

    @UiThread
    void updateList(){
        Node item;
        List<Node> list = new ArrayList< >();
        Random random = new Random();
        int len = random.nextInt()%50;
        len = len<0? len*(-1) : len;
        for(int i=0;i<len;i++){
            item = new Node();
            item.setGathering_location("一号大街"+random.nextInt(10000)+"号");
            item.setId(random.nextInt(1000000000));
            item.setRemark("风筝在阴天搁浅，想念还在等待救援"+ random.nextInt(100));
            item.setCompanyId(random.nextInt(1000000000));
            item.setCompany("搁浅"+random.nextInt(100));
            item.setTimeLine("早9点 到 晚5点");
            item.setPay(random.nextInt(500)+"/天");
            int a = random.nextInt()%3;
            item.setSexExpected(a==0?"男":(a==1?"女":"性别不限"));
            item.setGathering_time(random.nextInt());
            item.setJobname("发传单" + random.nextInt(50));
            item.setWorkLocation("一号大街" + random.nextInt(10000)+"号");
            item.setTime_start(random.nextInt());
            item.setTime_end(random.nextInt());
            item.setWorkType("其他" + random.nextInt(100));
            item.setNumExpected(random.nextInt(100));
            item.setNumHave(random.nextInt(100));
            list.add(item);
        }
        adapter = new JobListAdapter(list);
        mRecView.setAdapter(adapter);
    }

}
