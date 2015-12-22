package com.parttime.Fragment.Company;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.Modules.Node;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.Utils.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

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

    JobListAdapter adapter = new JobListAdapter(null);

    private int page = 0;//当前请求的页数

    private int number = 10;//每次请求的数量

    @AfterViews
    void start(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        updateData(page);
        mRecyclerView.setAdapter(adapter);
        mRefView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefView.setRefreshing(false);
                        //coding
                        updateData(page);
                    }
                },1500);
            }
        });
    }

    private void updateData(int pages) {
        page = pages;
        User user = User.getCurUser(getContext());
        BmobQuery<Node> query = new BmobQuery<>();
        query.addWhereEqualTo("companyId",user.getId());
        query.setLimit(number);
        query.setSkip(number*pages);
        query.findObjects(getContext(), new FindListener<Node>() {
            @Override
            public void onSuccess(List<Node> list) {
                Log.w("Jumy","返回的数据大小："+list.size());
                if (list.size()>0){
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
