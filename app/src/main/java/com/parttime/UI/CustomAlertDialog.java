package com.parttime.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.*;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lrf on 2015/5/29 15:46.
 * deadline is the first productivity
 */
public abstract class CustomAlertDialog extends AlertDialog implements View.OnClickListener {
    private Context context;
    public Window rootView;
    public Integer Res;
    public int len_view,len_id;
    private SparseArray<TextView> viewList;
    public ArrayList<Integer> idList = new ArrayList<>();
    String mContenText;

    public CustomAlertDialog(Context context1, Integer res, ArrayList<Integer> list2, String contentText) {
        super(context1);
        context = context1;
        this.idList = list2;
        this.Res = res;
        this.context=context1;
        this.len_id=list2.size();
        mContenText = contentText;
    }

    public CustomAlertDialog(Context context1, Integer res, ArrayList<Integer> list2) {
        super(context1);
        context = context1;
        this.idList = list2;
        this.Res = res;
        this.context=context1;
        this.len_id=list2.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载自定义布局
        setContentView(Res);
        rootView = this.getWindow();
        //设置默认大小
        WindowManager.LayoutParams params = rootView.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        rootView.setAttributes(params);
        rootView.setGravity(Gravity.BOTTOM);
        //按钮
        viewList = new SparseArray<>();
        for(int i=0;i<len_id;i++){
            View view = rootView.findViewById(idList.get(i));
            view.setTag(i);
            view.setOnClickListener(this);
            if (i == 0 && mContenText != null){
                TextView v = (TextView) rootView.findViewById(idList.get(i));
                v.setText(mContenText);
                viewList.put(idList.get(i),v);
            }
        }
    }
    public void setType(int resource){
        rootView.setType(resource);
    }

    /**
     * 修改 框体大小
     *
     * @param width
     * @param height
     */
    public void setDialogSize(int width, int height,int location) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width;//ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height;//ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        this.getWindow().setGravity(location);
    }

    public <T extends View> T getView(int id) {
        TextView view = viewList.get(id);
        if (view == null) {
            view = (TextView) rootView.findViewById(id);
            viewList.put(id, view);
        }
        return (T) view;
    }

    /**
     * Dialog 显示区域字样
     * @param text
     * @param id
     */
    public void setText(String text, Integer id){
        TextView view = getView(id);
        view.setText(text);
    }

    /**
     * 自定义接口
     */
    public interface OnClickCallBack{
        void onClick(int n);
    }
    /**
     * 初始化接口变量
     */
    OnClickCallBack callback = null;

    /**
     * 自定义控件的自定义事件
     * @param iback
     */
    public void setCallBack(OnClickCallBack iback){
        callback=iback;
    }



    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        onclickCallback(tag);
//        callback.onClick(tag);
    }

    protected abstract void onclickCallback(int num);
}
