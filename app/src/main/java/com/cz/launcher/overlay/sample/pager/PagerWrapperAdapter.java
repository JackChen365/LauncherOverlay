package com.cz.launcher.overlay.sample.pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerWrapperAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    public PagerWrapperAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList,int emptyIndex) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        if(null!=fragmentList){
            this.fragmentList.addAll(fragmentList);
        }
        this.fragmentList.add(emptyIndex,new EmptyFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
