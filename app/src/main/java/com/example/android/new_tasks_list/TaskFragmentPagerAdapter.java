package com.example.android.new_tasks_list;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TaskFragmentPagerAdapter extends FragmentPagerAdapter {
    private int numOfPages;
    public TaskFragmentPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        numOfPages = count;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new InProgressFragment();
        } else if (position == 1){
            return new CompletedFragment();
        } else {
            return new ArchivedFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfPages;
    }
}
