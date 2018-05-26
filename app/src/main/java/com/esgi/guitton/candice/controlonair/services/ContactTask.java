package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.esgi.guitton.candice.controlonair.Constants;
import com.esgi.guitton.candice.controlonair.Utils;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactTask extends AsyncTask<Context, Object, ArrayList<Contact>>{




    public interface OnTaskCompleted {
        void onTaskComplete(ArrayList<Contact> contacts);
    }

    private OnTaskCompleted listener;

    public ContactTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Contact> doInBackground(Context... contexts) {
        ContentResolver cr = contexts[0].getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Contact contact = new Contact(id, name, number);
                        contacts.add(contact);
                        FirebaseDatabase database = Utils.getDatabase();
                        SharedPreferences sharedPreferences = contexts[0].getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
                        String userNode = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "plop");

                        DatabaseReference dataReference = database.getReference(Constants.USERS_NODE).child(userNode).child(Constants.CONTACTS_NODE);


                        dataReference.child(contact.getId()).setValue(contact);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }

        return contacts;
    }

    protected void onPostExecute(ArrayList<Contact> contacts) {
        // your stuff

        Collections.sort(contacts);

        listener.onTaskComplete(contacts);
    }
}