package com.esgi.guitton.candice.controlonair;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.adapter.ContactAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.services.ContactService;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.OnContactClickListener {

    ListView contacts;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ListView contactsListView = findViewById(R.id.list_view_contact);
        ArrayList<Contact> contactList = ContactService.getContactList(getApplicationContext());
        ContactAdapter adapter = new ContactAdapter(this,contactList, this);
        contactsListView.setAdapter(adapter);
    }


    @Override
    public void onContactClickListener(long id) {
        Toast.makeText(this, "salut, t'as cliqué sur ce contact  " + id, Toast.LENGTH_SHORT).show();

    }
}
