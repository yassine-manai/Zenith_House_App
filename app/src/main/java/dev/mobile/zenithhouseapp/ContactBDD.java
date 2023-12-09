package dev.mobile.zenithhouseapp;


import static dev.mobile.zenithhouseapp.DataBaseHandler.DATABASE_NAME;
import static dev.mobile.zenithhouseapp.DataBaseHandler.DATABASE_VERSION;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_ID;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_NAME;
import static dev.mobile.zenithhouseapp.DataBaseHandler.KEY_NUMBER;
import static dev.mobile.zenithhouseapp.DataBaseHandler.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactBDD implements Serializable
{
    private SQLiteDatabase mDb;
    private DataBaseHandler DBcontacts;

    public ContactBDD(Context context)
    {
        DBcontacts = new DataBaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase openEcriture()
    {
        mDb = DBcontacts.getWritableDatabase();
        return mDb;
    }

    public SQLiteDatabase openLecture()
    {
        mDb = DBcontacts.getReadableDatabase();
        return mDb;
    }

    public long addContact(Contact c)
    {
        mDb=this.openEcriture();
        ContentValues v = new ContentValues();

        v.put(KEY_NAME, c.getNom());
        v.put(KEY_NUMBER, c.getNumber());

        long i = mDb.insert(TABLE_NAME, null, v);
        return i;
    }

    public Contact searchContact(int id)
    {
        String Query = "SELECT * FROM " + TABLE_NAME+" where "+KEY_ID+"=?";
        mDb = this.openLecture();
        Cursor curseur = mDb.rawQuery(Query, new String[] {String.valueOf(id)});

        if (curseur.getCount()==0)
        {
            return null;
        }

        Contact oc= new Contact();

        if(curseur.moveToFirst())
        {
            oc.setNom(curseur.getString(1));
            oc.setNumber(curseur.getString(2));
        }

        curseur.close();
        mDb.close();
        return oc;
    }

    public ArrayList<Contact> getAllContacts()
    {
        mDb=openLecture();
        Cursor c = mDb.query(TABLE_NAME, new String[]{KEY_ID,KEY_NAME, KEY_NUMBER },
                null, null, null,
                null, null);

        if (c.getCount() == 0)
        {
            return new ArrayList<>(0);
        }

        //creation de la liste contact
        ArrayList<Contact> retContact = new ArrayList<>(c.getCount());
        c.moveToFirst();

        do {
            Contact contact = new Contact();

            contact.setId(c.getInt(0));
            contact.setNom(c.getString(1));
            contact.setNumber(c.getString(2));

            retContact.add(contact);
        } while (c.moveToNext());

        c.close();
        mDb.close();
        return retContact;
    }

    public void deleteContact(Contact contact)
    {
        mDb=this.openEcriture();
        mDb.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(contact.getId())});
        mDb.close();
    }

    public int updateContact(Contact c)
    {
        SQLiteDatabase DB =openEcriture();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, c.getNom());
        values.put(KEY_NUMBER, c.getNumber());

        return DB.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(c.getId()) });

    }
}
