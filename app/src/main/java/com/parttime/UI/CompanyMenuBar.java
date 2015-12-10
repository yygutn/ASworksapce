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
 * Created by Jumy on 15/12/7 下午5:02.
 * deadline is the first productivity
 */
@EViewGroup(R.layout.ui_compamy_menu_bar)
public class CompanyMenuBar extends LinearLayout{

    @ViewById(R.id.company_home)
    TextView mHome;
    @ViewById(R.id.company_mine)
    TextView mMine;

    @ViewById(R.id.menuBar_left)
    ImageView mLeftImg;
    @ViewById(R.id.menuBar_right)
    ImageView mRightImg;

    @ColorRes(R.color.menuColor)
    int menuColor;
    @ColorRes(R.color.smoke)
    int smoke;
    @ColorRes(R.color.white)
    int white;

    @Click({R.id.menuBar_right,R.id.menuBar_left,R.id.company_mine,R.id.company_home})
    void click(View view){
        switch (view.getId()){
            case R.id.company_home:
            case R.id.menuBar_left:{
                reset();
                mLeftImg.setBackgroundResource(R.mipmap.main_index_home_press);
                mHome.setTextColor(menuColor);
                switchIndex(0);
                break;
            }
            case R.id.company_mine:
            case R.id.menuBar_right:{
                reset();
                mRightImg.setBackgroundResource(R.mipmap.main_index_my_pressed);
                mMine.setTextColor(menuColor);
                switchIndex(1);
                break;
            }
            default:break;
        }
    }

    /**
     *  重置
     */
    private void reset(){
        mLeftImg.setBackgroundResource (R.mipmap.main_index_home_normal);
        mRightImg.setBackgroundResource(R.mipmap.main_index_my_normal);

        mHome.setTextColor (smoke);
        mMine.setTextColor(smoke);
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

    public CompanyMenuBar(Context context) {
        super(context);
    }

    public CompanyMenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompanyMenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
