package com.parttime.Fragment.User;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.parttime.Activity.User.JobDetailsActivity_;
import com.parttime.Adapter.Base.BaseRecViewClickStatus;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.fragment_jobnode_list)
public class JobNodeFragment extends Fragment {
    @ViewById(R.id.fragment_listView)
    RecyclerView mListView;
    @ViewById(R.id.fragment_refreshView)
    PullToRefreshView mRecView;

    JobListAdapter adapter = null;

    @AfterViews
    void start(){
        initData();
        mRecView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecView.setRefreshing(false);
                        //coding
                        updateList();
                    }
                },1500);
            }
        });
        adapter.setRecViewClickStatus(new BaseRecViewClickStatus<Node>() {
            @Override
            public void onItemClickDelegate(View view, Node item) {
                Intent mIntent = new Intent(getContext(), JobDetailsActivity_.class);
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
        mListView.setAdapter(adapter);
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
        mListView.setAdapter(adapter);
    }
}
