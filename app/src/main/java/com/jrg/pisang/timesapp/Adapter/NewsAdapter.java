package com.jrg.pisang.timesapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jrg.pisang.timesapp.News.HeadlineNewsFragment;
import com.jrg.pisang.timesapp.News.LatestNewsFragment;

public class NewsAdapter extends FragmentPagerAdapter {

    public NewsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HeadlineNewsFragment();
            case 1:
                return new LatestNewsFragment();
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
                return " HEADLINE ";
            case 1:
                return " LATEST NEWS ";
            default:
                return null;
        }
    }
}
