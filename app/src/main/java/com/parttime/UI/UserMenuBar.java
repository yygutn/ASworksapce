package com.parttime.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.R;
import com.parttime.UI.Interface.MenuBarStatus;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * 个人用户的MenuBar
 */
@EViewGroup(R.layout.ui_user_menu_bar)
public class UserMenuBar extends LinearLayout {

    @ViewById(R.id.menuBar_left)
    ImageView mleftImg;//今日图片
    @ViewById(R.id.menuBar_mid)
    ImageView mMidImg;//预约图片
    @ViewById(R.id.menuBar_right)
    ImageView mRightImg;//个人中心图片

    @ViewById(R.id.now)
    TextView mNowTv;//今日
    @ViewById(R.id.next)
    TextView mNextTv;//预约
    @ViewById(R.id.mine)
    TextView mMineTv;//个人中心

    @ColorRes(R.color.menuColor)
    int menuColor;
    @ColorRes(R.color.smoke)
    int smoke;

    @Click({R.id.menuBar_left,R.id.menuBar_mid,R.id.menuBar_right,R.id.now,R.id.next,R.id.mine})
    void click(View view){
        switch (view.getId()){
            case R.id.now:
            case R.id.menuBar_left:{
                reset();
                mleftImg.setBackgroundResource(R.mipmap.main_index_tuan_pressed);
                mNowTv.setTextColor(menuColor);
                switchIndex(0);
                break;
            }
            case R.id.next:
            case R.id.menuBar_mid:{
                reset();
                mMidImg.setBackgroundResource(R.mipmap.main_index_search_pressed);
                mNextTv.setTextColor(menuColor);
                switchIndex(1);
                break;
            }
            case R.id.mine:
            case R.id.menuBar_right:{
                reset();
                mRightImg.setBackgroundResource(R.mipmap.main_index_my_pressed);
                mMineTv.setTextColor(menuColor);
                switchIndex(2);
                break;
            }
            default:break;
        }
    }

    /**
     *  重置
     */
    private void reset(){
        mleftImg.setBackgroundResource (R.mipmap.main_index_tuan_normal);
        mMidImg.setBackgroundResource  (R.mipmap.main_index_search_normal);
        mRightImg.setBackgroundResource(R.mipmap.main_index_my_normal);

        mNowTv.setTextColor (smoke);
        mNextTv.setTextColor(smoke);
        mMineTv.setTextColor(smoke);
    }

    /**
     * 切换菜单栏 下标
     * @param cur
     */
    private void switchIndex(int cur){
        if (BaseActivity.mIndex == cur){
            return;
        }
        BaseActivity.preIndex = BaseActivity.mIndex;
        BaseActivity.mIndex = cur;
        Log.w("Jumy","preindex:"+BaseActivity.preIndex + ",nowIndex:"+ BaseActivity.mIndex);
        menuClickedListener.onMenuBarSwitchedDelegate(cur);
    }

    private MenuBarStatus menuClickedListener;

    public void setMenuStatusListener(MenuBarStatus listener){
        menuClickedListener = listener;
    }


    public UserMenuBar(Context context) {
        super(context);
    }

    public UserMenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserMenuBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
