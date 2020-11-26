package com.cz.launcher.overlay.sample.scroll;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new SimpleEmptyFragment());
        for(int i=0;i<2;i++){
            fragmentList.add(SimpleFragment.newInstance(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
