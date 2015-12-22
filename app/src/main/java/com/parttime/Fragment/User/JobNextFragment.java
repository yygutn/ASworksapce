package com.parttime.Fragment.User;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
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

import java.util.List;

/**
 * Created by Jumy on 15/12/8 下午2:55.
 * deadline is the first productivity
 */
@EFragment(R.layout.fragment_jobnext_list)
public class JobNextFragment extends Fragment{
    @ViewById(R.id.fragment_listView)
    RecyclerView mListView;
    @ViewById(R.id.fragment_refreshView)
    PullToRefreshView mRecView;

    JobListAdapter adapter = new JobListAdapter(null);
    private int page = 0;//当前请求的页数

    private int number = 10;//每次请求的数量

    @AfterViews
    void start(){
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

    void updateList(int pages){
        page = pages;
        BmobQuery<Node> query = new BmobQuery<>();
        query.addWhereGreaterThan("id",0);
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
