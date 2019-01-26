package com.example.rafael_perez.mi_peso_ideal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Presenter.DBQueryPresenter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

public class ProgressPresentationActivity extends AppCompatActivity implements InterfaceDBQuery.InterfaceGetAllDataQuery.View {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String name;
    ArrayList<Double> values_imc = new ArrayList<>();
    ArrayList<Double> values_mg = new ArrayList<>();
    ArrayList<Double> values_icc = new ArrayList<>();
    ArrayList<Date> values_dates = new ArrayList<>();
    static LineGraphSeries<DataPoint> imc_series;
    static LineGraphSeries<DataPoint> mg_series;
    static LineGraphSeries<DataPoint> icc_series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_presentation);
        InterfaceDBQuery.InterfaceGetAllDataQuery.Presenter get_data_presenter = new DBQueryPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_charts);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        name = getIntent().getExtras().getString("name");

        get_data_presenter.getData(this, values_imc, values_mg, values_icc, values_dates);
    }

    private LineGraphSeries<DataPoint> createLineGraphSeries(ArrayList<Double> values, ArrayList<Date> values_dates){
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for (int i=0; i<values.size(); i++){
            dataPoints.add(new DataPoint(values_dates.get(i), values.get(i)));
        }
        DataPoint[] dataPointArray = dataPoints.toArray(new DataPoint[dataPoints.size()]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
        series.setColor(getResources().getColor(R.color.colorAccent));
        return series;
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

    @Override
    public void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        imc_series = createLineGraphSeries(values_imc, values_dates);
        mg_series = createLineGraphSeries(values_mg, values_dates);
        icc_series = createLineGraphSeries(values_icc, values_dates);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() { }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_results_presentation, container, false);

            GraphView graph = rootView.findViewById(R.id.graph);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    graph.addSeries(imc_series);
                    break;
                case 2:
                    graph.addSeries(mg_series);
                    break;
                case 3:
                    graph.addSeries(icc_series);
                    break;
            }
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
            graph.getGridLabelRenderer().setHumanRounding(false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
