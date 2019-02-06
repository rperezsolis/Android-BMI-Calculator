package com.example.rafael_perez.mi_peso_ideal.Helper;

import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatHelper {

    public static void formatDate(TextView textView, String dateString){
        DateFormat input_format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat output_format = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = input_format.parse(dateString);
            String output_string = output_format.format(date);
            textView.setText(output_string);
        } catch (ParseException e){
            e.printStackTrace();
        }
    }
}
