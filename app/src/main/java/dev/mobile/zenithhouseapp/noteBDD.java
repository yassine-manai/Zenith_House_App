package dev.mobile.zenithhouseapp;


import static dev.mobile.zenithhouseapp.DataBaseHandler.DATABASE_NAME;
import static dev.mobile.zenithhouseapp.DataBaseHandler.DATABASE_VERSION;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_ID;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_NAME;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_TEXT;
import static dev.mobile.zenithhouseapp.DataBaseHandler.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class noteBDD implements Serializable
{
    private SQLiteDatabase mDb;
    private DataBaseHandler DBnotes;

    public noteBDD(Context context)
    {
        DBnotes = new DataBaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase openEcriture()
    {
        mDb = DBnotes.getWritableDatabase();
        return mDb;
    }

    public SQLiteDatabase openLecture()
    {
        mDb = DBnotes.getReadableDatabase();
        return mDb;
    }

    public long addnotes(note n)
    {
        mDb=this.openEcriture();
        ContentValues v = new ContentValues();

        v.put(KEY_NAME, n.getNom());
        v.put(KEY_TEXT, n.getText());

        long i = mDb.insert(TABLE_NAME, null, v);
        return i;
    }

    public note searchnotes(int id)
    {
        String Query = "SELECT * FROM " + TABLE_NAME+" where "+KEY_ID+"=?";
        mDb = this.openLecture();
        Cursor curseur = mDb.rawQuery(Query, new String[] {String.valueOf(id)});

        if (curseur.getCount()==0)
        {
            return null;
        }

        note nt= new note();

        if(curseur.moveToFirst())
        {
            nt.setNom(curseur.getString(1));
            nt.setText(curseur.getString(2));
        }

        curseur.close();
        mDb.close();
        return nt;
    }

    public ArrayList<note> getAllnotes()
    {
        mDb=openLecture();
        Cursor c = mDb.query(TABLE_NAME, new String[]{KEY_ID,KEY_NAME, KEY_TEXT},
                null, null, null,
                null, null);

        if (c.getCount() == 0)
        {
            return new ArrayList<>(0);
        }

        //creation de la liste contact
        ArrayList<note> retnote = new ArrayList<>(c.getCount());
        c.moveToFirst();

        do {
            note nt = new note();

             nt.setId(c.getInt(0));
            nt.setNom(c.getString(1));
            nt.setText(c.getString(2));

            retnote.add(nt);
        } while (c.moveToNext());

        c.close();
        mDb.close();
        return retnote;
    }

    public void deletenotes(note note)
    {
        mDb=this.openEcriture();
        mDb.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(note.getId())});
        mDb.close();
    }

    public int updatenotes(note n)
    {
        SQLiteDatabase DB =openEcriture();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, n.getNom());
        values.put(KEY_TEXT, n.getText());

        return DB.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(n.getId()) });

    }
}
