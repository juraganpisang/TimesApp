package com.jrg.pisang.timesapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jrg.pisang.timesapp.Explore.FokusExploreFragment;
import com.jrg.pisang.timesapp.Explore.KanalExploreFragment;


public class ExploreAdapter extends FragmentPagerAdapter {

    public ExploreAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KanalExploreFragment();
            case 1:
                return new FokusExploreFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return " KANAL ";
            case 1:
                return " FOKUS ";
            default:
                return null;
        }
    }
}
