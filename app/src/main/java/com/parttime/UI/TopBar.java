package com.parttime.UI;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.Interface.TopBarStatusRight;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Jumy on 15/12/8 下午2:16.
 * deadline is the first productivity
 */
@EViewGroup(R.layout.ui_topbar)
public class TopBar extends LinearLayout{

    @ViewById(R.id.topBar_logo)
    SimpleDraweeView mLogo;
    @ViewById(R.id.topBar_nav)
    SimpleDraweeView mNavIcon;
    @ViewById(R.id.topBar_title)
    TextView mTitle;
    @ViewById(R.id.topBar_rightTV)
    TextView mRightTv;

    public void setTitle(String text){
        mTitle.setText(text);
        mTitle.setVisibility(VISIBLE);
    }
    public void setTitleColor(Integer color){
        mTitle.setTextColor(color);
    }

    public void setBackIconVisible(){
        mNavIcon.setVisibility(VISIBLE);
    }

    public void setLogo(Uri uri){
        mLogo.setImageURI(uri);
        mLogo.setVisibility(VISIBLE);
    }

    public void setmNavIcon(Uri uri){
        mNavIcon.setImageURI(uri);
        mNavIcon.setVisibility(VISIBLE);
    }

    public void setRightTitle(String text){
        mRightTv.setText(text);
        mRightTv.setVisibility(VISIBLE);
    }

    public void setRightInVisible(){
        mRightTv.setVisibility(GONE);
    }

    @Click({R.id.topBar_nav,R.id.topBar_rightTV})
    void backClicked(View v){
        switch (v.getId()){
            case R.id.topBar_nav:{
                topBarStatus.onTopBarBackClickDelegate();
                break;
            }
            case R.id.topBar_rightTV:{
                topBarStatusRight.onRightClickDelegate();
                break;
            }
            default:break;
        }
    }

    TopBarStatusRight topBarStatusRight;
    public void setTopBarStatusRight(TopBarStatusRight listener){
        topBarStatusRight = listener;
    }

    TopBarStatus topBarStatus;

    public void setTopBarStatusListener(TopBarStatus listener){
        topBarStatus = listener;
    }

    public void setRightTitleColor(Integer color){
        mRightTv.setTextColor(color);
    }

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
