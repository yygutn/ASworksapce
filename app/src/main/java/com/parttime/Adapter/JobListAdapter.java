package com.parttime.Adapter;

import android.view.View;
import android.view.ViewGroup;
import com.parttime.Adapter.Base.BaseRecyclerViewAdapter;
import com.parttime.Modules.Node;
import com.parttime.R;
import com.parttime.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jumy on 15/12/6 下午8:53.
 * deadline is the first productivity
 */
public class JobListAdapter extends BaseRecyclerViewAdapter<Node, JobListAdapter.ViewHolder> {
    private static final String TAG = JobListAdapter.class.getSimpleName();

    /**
     * @param list the datas to attach the adapter
     */
    public JobListAdapter(List<Node> list) {
        super(list);
    }

    @Override
    protected void bindDataToItemView(ViewHolder holder, Node item) {
        holder.setText(R.id.list_item_name, item.getJobname());
        holder.setText(R.id.list_item_pay, item.getPay());
        holder.setText(R.id.list_item_location, item.getWorkLocation());
        holder.setText(R.id.list_item_sex, item.getSexExpected());
        holder.setText(R.id.list_item_number, item.getNumHave() + "/" + item.getNumExpected());
        if (item.getGathering_time() > 10000000) {
            holder.setText(R.id.list_item_gathering_time, StringUtil.getSecondTotime(item.getGathering_time()));
        }
        holder.setText(R.id.list_item_work_time_start, item.getWork_time_start());
        holder.setText(R.id.list_item_work_time_end, item.getWork_time_end());
        holder.setText(R.id.list_item_workType, item.getWorkType());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_fragemnt_node));
    }


    public class ViewHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addList(List<Node> list){
        if (mList == null){
            mList = new ArrayList<>();
        }
        mList.addAll(0,list);
        notifyDataSetChanged();
    }
}
