package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Presenter.DBQueryPresenter;
import com.example.rafael_perez.mi_peso_ideal.R;

import java.util.ArrayList;
import java.util.Date;

public class ProgressActivity extends AppCompatActivity implements InterfaceDBQuery.InterfaceGetAllDataQuery.View{
    private ProgressSectionsPagerAdapter mSectionsPagerAdapter;
    private String name;
    private int GRAPH = 0, LIST = 1;
    private ArrayList<Double> values_imc = new ArrayList<>();
    private ArrayList<Double> values_mg = new ArrayList<>();
    private ArrayList<Double> values_icc = new ArrayList<>();
    private ArrayList<Date> values_dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        InterfaceDBQuery.InterfaceGetAllDataQuery.Presenter get_data_presenter = new DBQueryPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_progress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSectionsPagerAdapter = new ProgressSectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.add(ProgressGraphsFragment.newInstance());
        mSectionsPagerAdapter.add(ProgressListFragment.newInstance());
        ViewPager mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        int sections = 2;
        mViewPager.setOffscreenPageLimit(sections);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_graphs)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_list)));

        name = getIntent().getExtras().getString("name");

        get_data_presenter.getData(this, name, values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        ProgressGraphsFragment progressGraphsFragment = (ProgressGraphsFragment) (mSectionsPagerAdapter.getItem(GRAPH));
        progressGraphsFragment.setProgressData(ProgressActivity.this, values_imc, values_mg, values_icc, values_dates);
        ProgressListFragment progressListFragment = (ProgressListFragment)  (mSectionsPagerAdapter.getItem(LIST));
        progressListFragment.setProgressData(ProgressActivity.this, values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
