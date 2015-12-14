package com.parttime.Fragment.Company;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jumy on 15/12/14 上午11:50.
 * deadline is the first productivity
 */
@EFragment(R.layout.fragment_item_home_1)
public class HomeFragmentItem1 extends Fragment{

    @ViewById(R.id.fragment_home_refresh)
    PullToRefreshView mRefView;
    @ViewById(R.id.fragment_home_listView)
    RecyclerView mRecyclerView;

    JobListAdapter adapter = null;

    @AfterViews
    void start(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        initData();
        mRefView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefView.setRefreshing(false);
                        //coding
                        updateData();
                    }
                },1500);
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
            item.setGathering_location("学源街"+random.nextInt(10000)+"号");
            item.setId(random.nextInt(1000000000));
            item.setRemark("风筝在阴天搁浅，想念还在等待救援"+ random.nextInt(100));
            item.setCompanyId(random.nextInt(1000000000));
            item.setCompany("搁浅"+random.nextInt(100));
            item.setTimeLine("早9点 到 晚5点");
            item.setPay(random.nextInt(500)+"/天");
            int a = random.nextInt()%3;
            item.setSexExpected(a==0?"男":(a==1?"女":"性别不限"));
            item.setGathering_time(random.nextInt());
            item.setJobname("Hack it" + random.nextInt(50));
            item.setWorkLocation("学源街" + random.nextInt(10000)+"号");
            item.setTime_start(random.nextInt());
            item.setTime_end(random.nextInt());
            item.setWorkType("其他" + random.nextInt(100));
            item.setNumExpected(random.nextInt(100));
            item.setNumHave(random.nextInt(100));
            list.add(item);
        }
        adapter = new JobListAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    private void updateData() {
        Node item;
        List<Node> list = new ArrayList< >();
        Random random = new Random();
        int len = random.nextInt()%50;
        len = len<0? len*(-1) : len;
        for(int i=0;i<len;i++){
            item = new Node();
            item.setGathering_location("高沙社区"+random.nextInt(10000)+"号");
            item.setId(random.nextInt(1000000000));
            item.setRemark("风筝在阴天搁浅，想念还在等待救援"+ random.nextInt(100));
            item.setCompanyId(random.nextInt(1000000000));
            item.setCompany("搁浅"+random.nextInt(100));
            item.setTimeLine("早9点 到 晚5点");
            item.setPay(random.nextInt(500)+"/天");
            int a = random.nextInt()%3;
            item.setSexExpected(a==0?"男":(a==1?"女":"性别不限"));
            item.setGathering_time(random.nextInt(999999999));
            item.setJobname("发传单" + random.nextInt(50));
            item.setWorkLocation("高沙社区" + random.nextInt(10000)+"号");
            item.setTime_start(random.nextInt());
            item.setTime_end(random.nextInt());
            item.setWorkType("其他" + random.nextInt(100));
            item.setNumExpected(random.nextInt(100));
            item.setNumHave(random.nextInt(100));
            list.add(item);
        }
        adapter = new JobListAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

}
