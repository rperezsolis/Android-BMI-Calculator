package com.example.rafael_perez.mi_peso_ideal;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private final String SHARED_PREF_FILE = "CalcPref";
    private final String KEY_NOMBRE = "user";

    private Context uContext;

    public Preferences(Context context){
        uContext = context;
    }

    private SharedPreferences getSettings(){
        return uContext.getSharedPreferences(SHARED_PREF_FILE, 0);
    }

    public String getUserName(){
        return getSettings().getString(KEY_NOMBRE, null);
    }

    public void setUserName(String user){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_NOMBRE, user);
        editor.apply();
    }
}
