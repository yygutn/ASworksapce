package com.parttime.Adapter.Base;

import android.view.View;

/**
 * Created by Jumy on 15/12/9 下午4:05.
 * deadline is the first productivity
 */
public interface BaseRecViewClickStatus<T> {
    void onItemClickDelegate(View view, T item);

//    void onItemLongClickDelegate(View view, T item);

//    void onItemSelectedDelegate(View view, T item);
}
