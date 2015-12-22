package com.parttime.Activity.User;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.parttime.Adapter.Base.BaseRecViewClickStatus;
import com.parttime.Adapter.JobListAdapter;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

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

    JobListAdapter adapter = new JobListAdapter(null);

    private Context context = this;

    private int page = 0;//当前请求的页数

    private int number = 10;//每次请求的数量
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
                        updateList(page);
                    }
                },1500);
            }
        });
        mRecView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        updateList(0);
        mRecView.setAdapter(adapter);
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

    void updateList(int pages){
        page = pages;
        BmobQuery<Node> query = new BmobQuery<>();
        query.addWhereGreaterThan("id",0);
        query.setLimit(number);
        query.setSkip(number*pages);
        query.findObjects(this, new FindListener<Node>() {
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
