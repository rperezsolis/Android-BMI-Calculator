package com.example.rafael_perez.mi_peso_ideal.Data;

import android.net.Uri;

public final class ProgressDBContract {
    public static final String CONTENT_AUTHORITY = "com.example.rafael_perez.mi_peso_ideal";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PROGRESS = "PROGRESS";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PROGRESS);

    public static final String TABLE_NAME        = "PROGRESS";
    public static final String _ID               = "ID";
    public static final String COLUMN_USER_NAME  = "NAME";
    public static final String COLUMN_DATE       = "DATE";
    public static final String COLUMN_IMC        = "IMC";
    public static final String COLUMN_MG         = "MG";
    public static final String COLUMN_ICC        = "ICC";
}
