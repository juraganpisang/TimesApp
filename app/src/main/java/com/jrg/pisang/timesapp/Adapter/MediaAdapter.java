package com.jrg.pisang.timesapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jrg.pisang.timesapp.Media.FotoMediaFragment;
import com.jrg.pisang.timesapp.Media.VideoMediaFragment;

public class MediaAdapter extends FragmentPagerAdapter {

    public MediaAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FotoMediaFragment();
            case 1:
                return new VideoMediaFragment();
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
                return " FOTO ";
            case 1:
                return " VIDEO ";
            default:
                return null;
        }
    }
}