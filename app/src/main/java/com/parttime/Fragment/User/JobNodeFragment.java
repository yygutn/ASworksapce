package com.parttime.Fragment.User;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.parttime.Activity.User.JobDetailsActivity_;
import com.parttime.Adapter.Base.BaseRecViewClickStatus;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.Utils.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.fragment_jobnode_list)
public class JobNodeFragment extends Fragment {
    @ViewById(R.id.fragment_listView)
    RecyclerView mListView;
    @ViewById(R.id.fragment_refreshView)
    PullToRefreshView mRecView;

    JobListAdapter adapter = new JobListAdapter(null);

    private int page = 0;//当前请求的页数

    private int number = 10;//每次请求的数量

    @AfterViews
    void start() {
        updateList(0);
        mListView.setAdapter(adapter);
        mRecView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecView.setRefreshing(false);
                        //coding
                        updateList(page);
                    }
                }, 1500);
            }
        });
        adapter.setRecViewClickStatus(new BaseRecViewClickStatus<Node>() {
            @Override
            public void onItemClickDelegate(View view, Node item) {
                Intent mIntent = new Intent(getContext(), JobDetailsActivity_.class);
                mIntent.putExtra(Node.TAG, item);
                startActivity(mIntent);
            }
        });
    }

    /**
     *伪造数据
     */
    private void initData() {
        Node item;
        SimpleDateFormat format;
        Random random = new Random();
        int len = random.nextInt() % 50;
        len = len < 0 ? len * (-1) : len;
        for (int i = 0; i < len; i++) {
            item = new Node();
            if (random.nextBoolean()) {
                item.setJobname("发传单" + random.nextInt(50));
            }
            else {
                item.setJobname("送外卖" + random.nextInt(50));
            }
            item.setWorkLocation("二十五号大街" + random.nextInt(10000) + "号");
            item.setPay(random.nextInt(500) + "/天");
            item.setCompanyId(3);
            item.setCompany("搁浅" + random.nextInt(100));
            int a = random.nextInt() % 3;
            item.setSexExpected(a == 0 ? "男" : (a == 1 ? "女" : "性别不限"));
            item.setGathering_location("二十五号大街" + random.nextInt(10000) + "号");
            item.setRemark("风筝在阴天搁浅，想念还在等待救援" + random.nextInt(100));
            format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            format.format(date);
            long aa;
            aa = date.getTime();
            item.setGathering_time((int) aa / 1000);
            item.setTimeLine("早9点 到 晚5点");
            item.setTime_start(random.nextInt());
            item.setTime_end(random.nextInt());
            item.setWorkType("其他" + random.nextInt(100));
            item.setNumExpected(20 + random.nextInt(100));
            item.setNumHave(0 + random.nextInt(20));
            String s = "2015-12-2";
            if (random.nextBoolean()) {
                s += 5;
            }
            else {
                s += 6;
            }
            try {
                item.setWorkTime(new BmobDate(format.parse(s)));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            item.save(getContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    Log.w("jumy","success");
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.w("jumy","fail");
                }
            });
        }
    }

    void updateList(int pages) {
        page = pages;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "2015-12-25 00:00:00";
        String e = "2015-12-26 00:00:00";
        BmobQuery<Node> query = new BmobQuery<>();
        BmobQuery<Node> q3 = new BmobQuery<>();
        BmobQuery<Node> q4 = new BmobQuery<>();
        List<BmobQuery<Node>> andQuerys = new ArrayList<BmobQuery<Node>>();
        try {
            q3.addWhereGreaterThanOrEqualTo("workTime",new BmobDate(format.parse(s)));
            q4.addWhereLessThan("workTime",new BmobDate(format.parse(e)));
        }
        catch (ParseException e1) {
            e1.printStackTrace();
        }
        andQuerys.add(q3);
        andQuerys.add(q4);
        query.order("-updatedAt");
        query.addWhereEqualTo("status", true);
        query.and(andQuerys);
        query.setLimit(number);
        query.setSkip(number * pages);
        query.findObjects(getContext(), new FindListener<Node>() {
            @Override
            public void onSuccess(List<Node> list) {
                Log.w("Jumy", "返回的数据大小：" + list.size());
                if (list.size() > 0) {
                    adapter.addList(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.showToast("网络异常，请重新尝试");
            }
        });
        page++;
    }
}
