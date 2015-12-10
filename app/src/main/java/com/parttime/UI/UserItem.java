package com.parttime.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parttime.R;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by ziyang on 2015/6/17.
 * 0.0
 */
@EViewGroup(R.layout.ui_fragment_mine_item)
public class UserItem extends LinearLayout {
    @ViewById(R.id.mine_item_img)
    public ImageView mImg;
    @ViewById(R.id.mine_item_show)
    public TextView mShow;
    @ViewById(R.id.mine_item_rightImg)
    ImageView mRightImg;
    //设置右箭头显示隐藏，默认显示
    public void setNextVisible(Integer res) {
        this.mRightImg.setVisibility(res);
    }

    //设置item图片
    public void setIcon(Integer res) {
        this.mImg.setBackgroundResource(res);
    }

    //设置item显示文字
    public void setContent(String text) {
        this.mShow.setText(text);
    }

    private Context mContext;

    public UserItem(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public UserItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public UserItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化根VIEW
     */
    void initView() {
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.color.white);
    }
}
