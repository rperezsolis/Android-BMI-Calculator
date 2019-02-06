package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ProgressSectionsPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> pager = new ArrayList();

    public ProgressSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return this.pager.size();
    }

    public Fragment getItem(int position) {
        return this.pager.get(position);
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void add(Fragment f) {
        this.pager.add(f);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        this.pager.remove(position);
        this.notifyDataSetChanged();
    }
}
