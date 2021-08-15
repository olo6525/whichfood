package com.coin.whichfood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }



    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new SlideViewFlagment();
        Bundle args = new Bundle();
        switch (i){
            case 0:
                args.putInt("page",0);
                fragment.setArguments(args);
                return fragment;
            case 1:
                args.putInt("page",1);
                fragment.setArguments(args);
                return fragment;
            case 2:
                args.putInt("page",2);
                fragment.setArguments(args);
                return fragment;
            case 3:
                args.putInt("page",3);
                fragment.setArguments(args);
                return fragment;
            default:
                return fragment;
        }
        // Our object is just an integer :-P

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "가게 정보 " + (position + 1);
    }
}
