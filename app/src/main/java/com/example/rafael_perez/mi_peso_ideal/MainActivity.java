package com.example.rafael_perez.mi_peso_ideal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.CONTENT_URI;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract._ID;

public class MainActivity extends AppCompatActivity {
    Spinner spinner_physical_activity;
    String[] items;
    EditText et_name, et_age, et_weight, et_height, et_neck, et_waist, et_hip;
    private Preferences preferences;
    String name, age, weight, height, neck, waist, hip, physical_activity;
    ImageView iv_man;
    ImageView iv_woman;
    int genre = 0;
    int genre_state;
    float GEB, ETA, AF, GET, IMC, MG, ICC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_calculator);

        spinner_physical_activity = findViewById(R.id.spinner);
        items = getResources().getStringArray(R.array.physical_activity_options);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_physical_activity.setAdapter(adapter);
        spinner_physical_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public  void onNothingSelected(AdapterView<?> parent){
            }
        });

        et_name   = findViewById(R.id.name);
        et_age    = findViewById(R.id.age);
        et_weight = findViewById(R.id.weight);
        et_height = findViewById(R.id.height);
        et_neck   = findViewById(R.id.neck);
        et_waist  = findViewById(R.id.waist);
        et_hip    = findViewById(R.id.hip);

        iv_man = findViewById(R.id.siluetahombre);
        iv_woman = findViewById(R.id.siluetamujer);

        preferences = new Preferences(this);
        if(preferences.getUserName() != null) et_name.setText(preferences.getUserName());

        Button btn_calculate = findViewById(R.id.calculate);
        btn_calculate.setOnClickListener(v -> calculate());

        Button btn_go_my_results = findViewById(R.id.go_my_results);
        btn_go_my_results.setOnClickListener(v -> goMyResults());
    }

    @Override
    public void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        genre_state = genre;
        guardaEstado.putInt("genre_state", genre_state);
    }


    @Override
    public void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        genre_state = recuperaEstado.getInt("genre_state");
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

    private void goMyResults(){
        String[] projection = {_ID};
        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        if (cursor!=null && cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), getString(R.string.no_user), Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, ProgressPresentationActivity.class);
            intent.putExtra("name", et_name.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
        }
    }

    public void calculate(){
        //validar();
        et_name.setError(null);  //se define que es un error que estos campos esten vacíos
        et_age.setError(null);
        et_weight.setError(null);
        et_height.setError(null);
        et_neck.setError(null);
        et_waist.setError(null);
        et_hip.setError(null);

        name = et_name.getText().toString();  //se guardan los valores introducidos por el usuario en su respectiva variable
        age = et_age.getText().toString();      //y así el programa sabe que ya no estan vacíos
        weight = et_weight.getText().toString();
        height = et_height.getText().toString();
        neck = et_neck.getText().toString();
        waist = et_waist.getText().toString();
        hip = et_hip.getText().toString();
        physical_activity = spinner_physical_activity.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)) {
            et_name.setError(getString(R.string.error_empty));
            et_name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(age)){
            et_age.setError(getString(R.string.error_empty));
            et_age.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(weight)){
            et_weight.setError(getString(R.string.error_empty));
            et_weight.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(height)){
            et_height.setError(getString(R.string.error_empty));
            et_height.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(neck)){
            et_neck.setError(getString(R.string.error_empty));
            et_neck.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(waist)){
            et_waist.setError(getString(R.string.error_empty));
            et_waist.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(hip)){
            et_hip.setError(getString(R.string.error_empty));
            et_hip.requestFocus();
            return;
        }

        //convertimos a flotantes los data de tipo string que introdujo el usuario
        float age_f     = Float.parseFloat(age);
        float weight_f     = Float.parseFloat(weight);
        float height_f = Float.parseFloat(height);
        float neck_f   = Float.parseFloat(neck);
        float waist_f  = Float.parseFloat(waist);
        float hip_f   = Float.parseFloat(hip);

        if (age_f<1 || age_f>99){
            et_age.setError(getString(R.string.error_edad));
            et_age.requestFocus();
            return;
        }

        if (weight_f<10){
            et_weight.setError(getString(R.string.error_peso));
            et_weight.requestFocus();
            return;
        }

        if (height_f<1 || height_f>2.5){
            et_height.setError(getString(R.string.error_estatura));
            et_height.requestFocus();
            return;
        }

        if (neck_f<20){
            et_neck.setError(getString(R.string.error_cuello));
            et_neck.requestFocus();
            return;
        }

        if (waist_f<40){
            et_waist.setError(getString(R.string.error_cintura));
            et_waist.requestFocus();
            return;
        }

        if(hip_f<40){
            et_hip.setError(getString(R.string.error_cadera));
            et_hip.requestFocus();
            return;
        }

        if(genre ==0){
            Toast.makeText(getApplicationContext(),"Seleccione un sexo", Toast.LENGTH_SHORT).show();
            return;
        }

        if(genre ==1){
            GEB = (float) (66.5 + 13.75*weight_f + 5.08*height_f*100 - 4.68*age_f);
            ETA = (float) (0.10*GEB);
            if(physical_activity.equals("Nula")){
                AF = (float) (0.1*GEB);
            }
            if(physical_activity.equals("Leve")){
                AF = (float) (0.2*GEB);
            }
            if(physical_activity.equals("Moderada")){
                AF = (float) (0.3*GEB);
            }
            if(physical_activity.equals("Intensa")){
                AF = (float) (0.4*GEB);
            }
            MG = (float) (495/(1.0324-0.19077*(Math.log10(waist_f-neck_f)) + 0.15456*(Math.log10(height_f*100))) - 450);
        }
        if(genre ==2){
            GEB = (float) (665.1 + 9.56*weight_f + 1.85*height_f*100 - 4.68*age_f);
            ETA = (float) (0.10*GEB);
            if(physical_activity.equals("Nula")){
                AF = (float) (0.1*GEB);
            }
            if(physical_activity.equals("Leve")){
                AF = (float) (0.2*GEB);
            }
            if(physical_activity.equals("Moderada")){
                AF = (float) (0.3*GEB);
            }
            if(physical_activity.equals("Intensa")){
                AF = (float) (0.4*GEB);
            }
            MG = (float) (495/(1.29579-0.35004*(Math.log10(waist_f + hip_f - neck_f)) + 0.22100*(Math.log10(height_f*100))) - 450);
        }

        GET = GEB + ETA + AF;

        IMC = weight_f/(height_f*height_f);

        ICC = waist_f/hip_f;

        float [] datos = {genre, IMC, MG, ICC, GET};
        float [] calorias = {GEB, ETA, AF};

        preferences.setUserName(et_name.getText().toString());

        Intent intent = new Intent(MainActivity.this, ResultsActivity
                .class);
        intent.putExtra("results",datos);
        intent.putExtra("calories",calorias);
        intent.putExtra("name", name);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_enter, R.anim.trans_exit);
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
