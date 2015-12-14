package com.parttime.Fragment.Company;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.parttime.R;
import com.parttime.UI.ViewPagerIndicator;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
    @ViewById(R.id.company_home_indicator)
    ViewPagerIndicator mIndicator;
    @ViewById(R.id.company_home_viewpager)
    ViewPager mViewPager;

    private List<String> mTitles = Arrays.asList("正在招聘","历史招聘");
    private FragmentPagerAdapter adapter = null;
    private List<Fragment> mTabContents = new ArrayList<>();

    @AfterViews
    void start(){
        initContentView();
        mIndicator.setTabItemTitles(mTitles);
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager,0);
    }

    private void initContentView(){
        mTabContents.add(new HomeFragmentItem1_());
        mTabContents.add(new HomeFragmentItem2_());
        adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }
        };
    }
}
