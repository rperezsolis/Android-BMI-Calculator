package com.example.rafael_perez.mi_peso_ideal;

import android.support.v7.app.AppCompatActivity;

public class ChartsActivity extends AppCompatActivity {


    /*public static final Object sDataLock = new Object();  //Object for intrinsic lock
    LineChart lineChart;

    Button mostrarIMC;
    Button mostrarMG;
    Button mostrarICC;

    TextView seleccione;

    int graficaActual;
    int genre_state;
    String name;

    ArrayList<Entry> valoresYimcs = new ArrayList<>();
    ArrayList<Entry> valoresYmgs = new ArrayList<>();
    ArrayList<Entry> valoresYiccs = new ArrayList<>();

    ArrayList<String> valoresX = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        name = getIntent().getExtras().getString("name");

        final DataBase BaseDatosResultados = new DataBase(this, DataBaseContract.DATABASE_NAME, null, DataBaseContract.DATABASE_VERSION);

        //instanciamos la grafica
        lineChart = (LineChart) findViewById(R.id.grafica);

        //instanciamos los botones
        mostrarIMC = (Button) findViewById(R.id.boton_graficaIMC);
        mostrarMG  = (Button) findViewById(R.id.boton_graficaMG);
        mostrarICC = (Button) findViewById(R.id.boton_graficaICC);

        seleccione = (TextView) findViewById(R.id.seleccione);

        //Extraemos los registros de la base de data y los guardamos en los arrays que utilizaran las gráficas
        synchronized (ChartsActivity.sDataLock){
            String[] fechas = new String[BaseDatosResultados.leerRegistros(name).size()];
            float[] imcs    = new float[BaseDatosResultados.leerRegistros(name).size()];
            float[] mgs     = new float[BaseDatosResultados.leerRegistros(name).size()];
            float[] iccs    = new float[BaseDatosResultados.leerRegistros(name).size()];
            for (int i = 0; i < BaseDatosResultados.leerRegistros(name).size(); i++) {
                fechas[i] = BaseDatosResultados.leerRegistros(name).get(i).getFecha();
                imcs[i]   = BaseDatosResultados.leerRegistros(name).get(i).getIMC();
                mgs[i]    = BaseDatosResultados.leerRegistros(name).get(i).getMG();
                iccs[i]   = BaseDatosResultados.leerRegistros(name).get(i).getICC();
                valoresYimcs.add(new Entry(imcs[i], i));
                valoresYmgs.add(new Entry(mgs[i], i));
                valoresYiccs.add(new Entry(iccs[i], i));
                valoresX.add(fechas[i]);
            }
        }

        if (graficaActual == 0){
            lineChart.setVisibility(View.GONE);
            seleccione.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        genre_state = graficaActual;
        guardaEstado.putInt("Valor", genre_state);
    }


    @Override
    public void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        genre_state = recuperaEstado.getInt("Valor");
        graficaActual = genre_state;
        if (graficaActual != 0){
            comparar();
        }
    }

    public void asignar1(View v){
        graficaActual = 1;
        comparar();
    }

    public void asignar2 (View v){
        graficaActual = 2;
        comparar();
    }

    public void asignar3 (View v){
        graficaActual = 3;
        comparar();
    }

    public void comparar(){
        switch (graficaActual){
            case 1:
                mostrarIMC();
                break;
            case 2:
                mostrarMG();
                break;
            case 3:
                mostrarICC();
                break;
        }
    }

    //Configuramos la grafica IMC
    public void mostrarIMC(){
        lineChart.setVisibility(View.VISIBLE);
        seleccione.setVisibility(View.GONE);
        lineChart.setDescription("Indice de Masa Corporal");
        lineChart.setDescriptionTextSize(20f);
        LineDataSet lineDataSet = new LineDataSet(valoresYimcs, "Indice de Masa Corporal");
        lineDataSet.setDrawFilled(true);
        LineData lineData = new LineData(valoresX, lineDataSet);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);
        lineChart.animateY(1000);
        lineChart.animateX(1000);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    //Configuramos la grafica MG
    public void mostrarMG(){
        lineChart.setVisibility(View.VISIBLE);
        seleccione.setVisibility(View.GONE);
        lineChart.setDescription("Porcentaje de masa grasa");
        lineChart.setDescriptionTextSize(20f);
        LineDataSet lineDataSet = new LineDataSet(valoresYmgs, "Porcentaje de masa grasa");
        lineDataSet.setDrawFilled(true);
        LineData lineData = new LineData(valoresX, lineDataSet);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);
        lineChart.animateY(1000);
        lineChart.animateX(1000);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    //Configuramos la grafica ICC
    public void mostrarICC(){
        lineChart.setVisibility(View.VISIBLE);
        seleccione.setVisibility(View.GONE);
        lineChart.setDescription("Indice Cintura/Cadera");
        lineChart.setDescriptionTextSize(20f);
        LineDataSet lineDataSet = new LineDataSet(valoresYiccs, "Indice Cintura/Cadera");
        lineDataSet.setDrawFilled(true);
        LineData lineData = new LineData(valoresX, lineDataSet);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);
        lineChart.animateY(1000);
        lineChart.animateX(1000);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    @Override
    public void onBackPressed()
    {
        // Añade más funciones si fuese necesario
        super.onBackPressed();  // Invoca al método para retornar a la activity anterior
        overridePendingTransition(R.anim.trans_return, R.anim.trans_reenter);  //ejecuta las animaciones
    }*/
}