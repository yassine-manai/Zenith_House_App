package dev.mobile.zenithhouseapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION=1;

    public static final String DATABASE_NAME="DBnotes",TABLE_NAME="contact",KEY_ID="id",KEY_NAME="nom",KEY_TEXT="text";

    public DataBaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table "
                + TABLE_NAME + " (" + KEY_ID
                + " integer primary key, " + KEY_NAME
                + " text not null, " + KEY_TEXT + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("drop table " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }
}


