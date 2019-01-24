package com.example.rafael_perez.mi_peso_ideal.Results.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Presenter.DBQueryPresenter;
import com.example.rafael_perez.mi_peso_ideal.ProgressPresentationActivity;
import com.example.rafael_perez.mi_peso_ideal.R;
import com.example.rafael_perez.mi_peso_ideal.Results.InterfaceResults;
import com.example.rafael_perez.mi_peso_ideal.Results.Presenter.ResultsPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultsActivity extends AppCompatActivity implements InterfaceDBQuery.View, InterfaceResults.View {
    float data[] = new float[5];
    float calories[] = new float[3];
    String name;
    TextView txt_imc, txt_mg, txt_icc, txt_calories;
    float genre, IMC, MG, ICC, GET, GEB, ETA, AF;
    LinearLayout results_imc_mg;
    LinearLayout results_icc;
    String date;
    private InterfaceDBQuery.Presenter queryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        queryPresenter = new DBQueryPresenter(this);
        InterfaceResults.Presenter resultsPresenter = new ResultsPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_my_results);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_imc        = findViewById(R.id.result_imc);
        txt_mg         = findViewById(R.id.result_mg);
        txt_icc        = findViewById(R.id.result_icc);
        txt_calories   = findViewById(R.id.result_calories);
        results_imc_mg = findViewById(R.id.results_imc_mg);
        results_icc    = findViewById(R.id.results_icc);

        data = getIntent().getExtras().getFloatArray("results");
        calories = getIntent().getExtras().getFloatArray("calories");
        name = getIntent().getExtras().getString("name");

        genre = data[0];
        IMC  = data[1];
        MG   = data[2];
        ICC = data[3];
        GET  = data[4];
        GEB = calories[0];
        ETA = calories[1];
        AF  = calories[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date Date = new Date();
        date = String.valueOf(dateFormat.format(Date));

        Button btn_save_data = findViewById(R.id.guardar);
        btn_save_data.setOnClickListener(v -> queryPresenter.saveResults(this, name, date, IMC, MG, ICC));

        Button plot = findViewById(R.id.graficar);
        plot.setOnClickListener(v -> queryPresenter.checkForUserName(this, name));

        resultsPresenter.setLevel(genre, IMC, MG, ICC);

        txt_imc.setText(String.format("%.2f", IMC));
        txt_mg.setText(String.format("%.2f", MG));
        txt_icc.setText(String.format("%.2f", ICC));
        txt_calories.setText(String.format("%.0f", GET));
    }

    @Override
    public void setColor(String[] colors) {
        results_imc_mg.setBackgroundColor(Color.parseColor(colors[0]));
        results_icc.setBackgroundColor(Color.parseColor(colors[1]));
    }

    @Override
    public void goToMyProgress() {
        Intent intent = new Intent(ResultsActivity.this, ProgressPresentationActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
    }

    @Override
    public void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) { }

    @Override
    public void noUser() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), getString(R.string.no_user), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void saved() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), getString(R.string.data_saved), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void notSaved() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), getString(R.string.data_do_not_saved), Snackbar.LENGTH_SHORT);
        snackbar.show();
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

    public void explicar(View v){
        /*double [] ImcMg = {genre, IMC, MG, ICC, GET};
        Intent intent = new Intent(ResultsActivity.this, ActivityImcMg.class);
        intent.putExtra("ActivityImcMg",ImcMg);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);*/
    }

    public void explicarIcc(View v){
        /*Intent intent = new Intent(ResultsActivity.this, ActivityIcc.class);
        intent.putExtra("ICC", ICC);
        intent.putExtra("genre", genre);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);*/
    }

    public void explicarCalorias(View v){
        /*double [] calories = {GEB, ETA, AF, GET};
        Intent intent = new Intent(ResultsActivity.this, ActivityCalorías.class);
        intent.putExtra("calories",calories);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);*/
    }
}
