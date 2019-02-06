package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafael_perez.mi_peso_ideal.R;

import java.util.ArrayList;
import java.util.Date;

public class ProgressListFragment extends Fragment implements ProgressListTabFragment.ListDataCallback {
    private ProgressListPagerAdapter progressListPagerAdapter;
    private  ArrayList<Double> values_imc, values_mg, values_icc;
    private ArrayList<Date> values_dates;
    private Context context;
    private ProgressListTabFragment progressListTabFragmentImc;
    private ProgressListTabFragment progressListTabFragmentMg;
    private ProgressListTabFragment progressListTabFragmentIcc;
    public static final int IMC = 0;
    public static final int MG = 1;
    public static final int ICC = 2;

    public static ProgressListFragment newInstance() {
        ProgressListFragment fragment = new ProgressListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_list, container, false);
        progressListPagerAdapter = new ProgressListPagerAdapter(getChildFragmentManager());
        progressListPagerAdapter.add(ProgressListTabFragment.newInstance());
        progressListPagerAdapter.add(ProgressListTabFragment.newInstance());
        progressListPagerAdapter.add(ProgressListTabFragment.newInstance());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(progressListPagerAdapter);
        int sections = 3;
        viewPager.setOffscreenPageLimit(sections);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getString(R.string.tab_text_1)));
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getString(R.string.tab_text_2)));
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getString(R.string.tab_text_3)));
        progressListTabFragmentImc = (ProgressListTabFragment) progressListPagerAdapter.getItem(IMC);
        progressListTabFragmentImc.setListDataCallback(this);
        progressListTabFragmentImc.setResultType(IMC);
        progressListTabFragmentMg = (ProgressListTabFragment) progressListPagerAdapter.getItem(MG);
        progressListTabFragmentMg.setListDataCallback(this);
        progressListTabFragmentMg.setResultType(MG);
        progressListTabFragmentIcc = (ProgressListTabFragment) progressListPagerAdapter.getItem(ICC);
        progressListTabFragmentIcc.setListDataCallback(this);
        progressListTabFragmentIcc.setResultType(ICC);
        return view;
    }

    public void setProgressData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        this.values_imc = values_imc;
        this.values_mg = values_mg;
        this.values_icc = values_icc;
        this.values_dates = values_dates;
        this.context = context;
    }

    @Override
    public void setListData(int resultType) {
        switch (resultType){
            case IMC:
                progressListTabFragmentImc.setProgressData(context, values_imc, values_dates);
                break;
            case MG:
                progressListTabFragmentMg.setProgressData(context, values_mg, values_dates);
                break;
            case ICC:
                progressListTabFragmentIcc.setProgressData(context, values_icc, values_dates);
                break;
        }
    }
}
