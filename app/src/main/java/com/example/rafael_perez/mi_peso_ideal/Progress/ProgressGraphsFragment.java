package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafael_perez.mi_peso_ideal.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

public class ProgressGraphsFragment extends Fragment{
    static LineGraphSeries<DataPoint> imc_series;
    static LineGraphSeries<DataPoint> mg_series;
    static LineGraphSeries<DataPoint> icc_series;

    public static ProgressGraphsFragment newInstance() {
        ProgressGraphsFragment fragment = new ProgressGraphsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_graphs, container, false);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_3));
        return view;
    }

    public void setProgressData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        imc_series = createLineGraphSeries(context, values_imc, values_dates);
        mg_series = createLineGraphSeries(context, values_mg, values_dates);
        icc_series = createLineGraphSeries(context, values_icc, values_dates);
    }

    private LineGraphSeries<DataPoint> createLineGraphSeries(Context context, ArrayList<Double> values, ArrayList<Date> values_dates){
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for (int i=0; i<values.size(); i++){
            dataPoints.add(new DataPoint(values_dates.get(i), values.get(i)));
        }
        DataPoint[] dataPointArray = dataPoints.toArray(new DataPoint[dataPoints.size()]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
        series.setColor(context.getResources().getColor(R.color.colorAccent));
        return series;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ProgressGraphsFragment.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
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
            View rootView = inflater.inflate(R.layout.fragment_results_graphs, container, false);

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
            graph.getGridLabelRenderer().setHumanRounding(true);

            return rootView;
        }
    }
}
