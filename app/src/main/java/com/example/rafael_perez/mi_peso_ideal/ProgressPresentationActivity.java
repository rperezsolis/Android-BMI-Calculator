package com.example.rafael_perez.mi_peso_ideal;

import android.database.Cursor;
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
import com.example.rafael_perez.mi_peso_ideal.Models.ResultModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_DATE;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_ICC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_IMC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_MG;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_USER_NAME;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.CONTENT_URI;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract._ID;

public class ProgressPresentationActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static final Object sDataLock = new Object();  //Object for intrinsic lock
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

        //Extraemos los registros de la base de datos y los guardamos en los arrays que utilizarán las gráficas
        synchronized (ProgressPresentationActivity.sDataLock){
            ArrayList<ResultModel> results_list= new ArrayList<>();
            String[] projection = {_ID, COLUMN_USER_NAME, COLUMN_DATE, COLUMN_IMC, COLUMN_MG, COLUMN_ICC};
            Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    ResultModel result_model = new ResultModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
                    results_list.add(result_model);
                } while (cursor.moveToNext());
                cursor.close();
            }
            for (int i = 0; i < results_list.size(); i++) {
                values_imc.add(results_list.get(i).getIMC());
                values_mg.add(results_list.get(i).getMG());
                values_icc.add(results_list.get(i).getICC());
                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = format.parse(results_list.get(i).getFecha());
                    values_dates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (values_imc!=null && values_imc.size()>0){
            imc_series = createLineGraphSeries(values_imc, values_dates);
            mg_series = createLineGraphSeries(values_mg, values_dates);
            icc_series = createLineGraphSeries(values_icc, values_dates);
        }
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

            //TODO: Aqui se debe agregar la gráfica al rootView.
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
