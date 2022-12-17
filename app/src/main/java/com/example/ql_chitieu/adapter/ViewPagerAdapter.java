package com.example.ql_chitieu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ql_chitieu.fragment.FragmentHistory;
import com.example.ql_chitieu.fragment.FragmentHome;
import com.example.ql_chitieu.fragment.FragmentProfile;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numberPage;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numberPage=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentHome fragmentHome=new FragmentHome();
                return fragmentHome;
            case 1:
                FragmentHistory fragmentHistory=new FragmentHistory();
                return fragmentHistory;
            case 2:
                FragmentProfile fragmentProfile=new FragmentProfile();
                return fragmentProfile;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
