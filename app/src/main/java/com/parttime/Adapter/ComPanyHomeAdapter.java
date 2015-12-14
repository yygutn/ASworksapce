package com.parttime.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jumy on 15/12/10 下午9:56.
 * deadline is the first productivity
 */
public class ComPanyHomeAdapter extends PagerAdapter {

    private Context context;
    private List<View> mListView;

    public ComPanyHomeAdapter(Context context, List<View> mListView) {
        this.context = context;
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        if (mListView == null)
            return 0;
        return mListView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListView.get(position),0);
        return mListView.get(position);
    }
}
