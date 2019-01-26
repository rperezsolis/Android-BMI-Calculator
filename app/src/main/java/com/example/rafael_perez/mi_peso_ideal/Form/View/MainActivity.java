package com.example.rafael_perez.mi_peso_ideal.Form.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Presenter.DBQueryPresenter;
import com.example.rafael_perez.mi_peso_ideal.Form.InterfaceForm;
import com.example.rafael_perez.mi_peso_ideal.Form.Presenter.MainPresenter;
import com.example.rafael_perez.mi_peso_ideal.Preferences;
import com.example.rafael_perez.mi_peso_ideal.ProgressPresentationActivity;
import com.example.rafael_perez.mi_peso_ideal.R;
import com.example.rafael_perez.mi_peso_ideal.Results.View.ResultsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        InterfaceForm.View,
        InterfaceDBQuery.InterfaceGoToMyProgress.View {
    private Spinner spinner_physical_activity;
    private EditText et_name, et_age, et_weight, et_height, et_neck, et_waist, et_hip;
    private ImageView iv_man;
    private ImageView iv_woman;
    private Preferences preferences;
    private int genre = 0;
    private int genre_state;
    float[] data;
    float[] calories;
    private InterfaceForm.Presenter formPresenter;
    private InterfaceDBQuery.InterfaceGoToMyProgress.Presenter queryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formPresenter = new MainPresenter(this);
        queryPresenter = new DBQueryPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_calculator);

        spinner_physical_activity = findViewById(R.id.spinner);
        String[] items = getResources().getStringArray(R.array.physical_activity_options);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_physical_activity.setAdapter(adapter);
        et_name   = findViewById(R.id.name);
        et_age    = findViewById(R.id.age);
        et_weight = findViewById(R.id.weight);
        et_height = findViewById(R.id.height);
        et_neck   = findViewById(R.id.neck);
        et_waist  = findViewById(R.id.waist);
        et_hip    = findViewById(R.id.hip);
        iv_man    = findViewById(R.id.siluetahombre);
        iv_woman  = findViewById(R.id.siluetamujer);

        preferences = new Preferences(this);
        if(preferences.getUserName() != null) et_name.setText(preferences.getUserName());

        Button btn_calculate = findViewById(R.id.calculate);
        btn_calculate.setOnClickListener(v -> calculate());

        Button btn_go_my_results = findViewById(R.id.go_my_results);
        btn_go_my_results.setOnClickListener(v -> seeMyProgress());
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        genre_state = genre;
        saveState.putInt("genre_state", genre_state);
    }

    @Override
    public void onRestoreInstanceState(Bundle restoreState) {
        super.onRestoreInstanceState(restoreState);
        genre_state = restoreState.getInt("genre_state");
        genre = genre_state;
        switch (genre){
            case 0:
                iv_man.setImageResource(R.drawable.silueta_normal_hombre_marca_agua);
                iv_woman.setImageResource(R.drawable.silueta_normal_mujer_marca_agua);
                break;
            case 1:
                iv_man.setImageResource(R.drawable.silueta_normal_hombre);
                iv_woman.setImageResource(R.drawable.silueta_normal_mujer_marca_agua);
                break;
            case 2:
                iv_woman.setImageResource(R.drawable.silueta_normal_mujer);
                iv_man.setImageResource(R.drawable.silueta_normal_hombre_marca_agua);
                break;
        }
    }

    public void calculate(){
        String name   = et_name.getText().toString();
        String age    = et_age.getText().toString();
        String weight = et_weight.getText().toString();
        String height = et_height.getText().toString();
        String neck   = et_neck.getText().toString();
        String waist  = et_waist.getText().toString();
        String hip    = et_hip.getText().toString();
        String physical_activity = spinner_physical_activity.getSelectedItem().toString();

        if (isFieldEmpty(name, et_name) || isFieldEmpty(age, et_age) || isFieldEmpty(weight, et_weight)
        || isFieldEmpty(height, et_height) || isFieldEmpty(neck, et_neck) || isFieldEmpty(waist, et_waist)
                || isFieldEmpty(hip, et_hip)) return;

        float age_f    = Float.parseFloat(age);
        float weight_f = Float.parseFloat(weight);
        float height_f = Float.parseFloat(height);
        float neck_f   = Float.parseFloat(neck);
        float waist_f  = Float.parseFloat(waist);
        float hip_f    = Float.parseFloat(hip);

        if (badData(age_f, weight_f, height_f, neck_f, waist_f, hip_f)) return;

        formPresenter.calculate(age_f, weight_f, height_f, neck_f, waist_f, hip_f, genre, physical_activity);

        preferences.setUserName(et_name.getText().toString());  //assign to preferences the last inserted name

        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("results",data);
        intent.putExtra("calories",calories);
        intent.putExtra("name", name);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
    }

    private boolean isFieldEmpty(String input, EditText edtText){
        if(TextUtils.isEmpty(input)) {
            edtText.setError(getString(R.string.error_empty));
            edtText.requestFocus();
            return true;
        }else return false;
    }

    private boolean badData(float age_f, float weight_f, float height_f, float neck_f, float waist_f, float hip_f){
        if (age_f<15 || age_f>99){
            et_age.setError(getString(R.string.error_edad));
            et_age.requestFocus();
            return true;
        } else if (weight_f<10){
            et_weight.setError(getString(R.string.error_peso));
            et_weight.requestFocus();
            return true;
        } else if (height_f<1 || height_f>2.5){
            et_height.setError(getString(R.string.error_estatura));
            et_height.requestFocus();
            return true;
        } else if (neck_f<20){
            et_neck.setError(getString(R.string.error_cuello));
            et_neck.requestFocus();
            return true;
        } else if (waist_f<40){
            et_waist.setError(getString(R.string.error_cintura));
            et_waist.requestFocus();
            return true;
        } else if(hip_f<40){
            et_hip.setError(getString(R.string.error_cadera));
            et_hip.requestFocus();
            return true;
        } else if(genre ==0){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.container),"Seleccione un sexo", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return true;
        } else return false;
    }

    @Override
    public void results(ArrayList<float[]> results) {
        data = results.get(0);
        calories = results.get(1);
    }

    private void seeMyProgress(){
        queryPresenter.checkForUserName(this, et_name.getText().toString());
    }

    @Override
    public void goToMyProgress() {
        Intent intent = new Intent(MainActivity.this, ProgressPresentationActivity.class);
        intent.putExtra("name", et_name.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
    }

    @Override
    public void noUser() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container),getString(R.string.no_user), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void maleGenre(View h){
        genre = 1;
        iv_man.setImageResource(R.drawable.silueta_normal_hombre);
        iv_woman.setImageResource(R.drawable.silueta_normal_mujer_marca_agua);
    }

    public void femaleGenre(View m){
        genre = 2;
        iv_woman.setImageResource(R.drawable.silueta_normal_mujer);
        iv_man.setImageResource(R.drawable.silueta_normal_hombre_marca_agua);
    }

    public void medir(View medir){
        /*Intent intentMedir = new Intent(MainActivity.this, ActivitycomoMedir.class);
        startActivity(intentMedir);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);*/
    }

    public void cleanForm(View limpiar){
        et_name.setText(null);
        et_age.setText(null);
        et_weight.setText(null);
        et_height.setText(null);
        et_neck.setText(null);
        et_waist.setText(null);
        et_hip.setText(null);
        et_name.setError(null);
        et_age.setError(null);
        et_weight.setError(null);
        et_height.setError(null);
        et_neck.setError(null);
        et_waist.setError(null);
        et_hip.setError(null);
        et_name.clearFocus();
        et_age.clearFocus();
        et_weight.clearFocus();
        et_height.clearFocus();
        et_neck.clearFocus();
        et_waist.clearFocus();
        et_hip.clearFocus();
        iv_man.setImageResource(R.drawable.silueta_normal_hombre_marca_agua);
        iv_woman.setImageResource(R.drawable.silueta_normal_mujer_marca_agua);
        genre = 0;
    }
}
