package com.esgi.guitton.candice.controlonair;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.models.Contact;

public class AddContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText contactName;
    private EditText contactNumber;
    private Button  addContact;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.add_contact_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        contactName = findViewById(R.id.contact_name);
        contactNumber = findViewById(R.id.contact_number);
        addContact = findViewById(R.id.add_contact_button);


        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                contact.setName(contactName.getText().toString());
                contact.setNumber(contactNumber.getText().toString());
                addContact(contact.getName(), contact.getNumber());


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addContact(String name, String phone) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.PhoneLookup.NUMBER, phone);
        values.put(ContactsContract.PhoneLookup.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
        values.put(ContactsContract.PhoneLookup.LABEL, name);
        values.put(ContactsContract.PhoneLookup.DISPLAY_NAME, name);
        Uri dataUri = getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, ContactsContract.PhoneLookup.IN_DEFAULT_DIRECTORY);
        values.clear();
    }
}
