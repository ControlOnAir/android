package com.esgi.guitton.candice.controlonair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.adapter.ContactAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.services.ContactTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity
        implements ContactAdapter.OnContactClickListener, ContactTask.OnTaskCompleted {

    private ListView contactsListView;
    private ArrayList<Contact> contactList;
    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactsListView = findViewById(R.id.list_view_contact);
        emptyView = findViewById(R.id.emptyView);
        retryButton = findViewById(R.id.retryButton);
        loader = findViewById(R.id.loader);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContact();
            }
        });

        contactList = new ArrayList<>();
        loadContact();
    }


    private void loadContact() {

        contactsListView.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        ContactTask contactTask = new ContactTask(this);

        contactTask.execute(this);
    }


    @Override
    public void onContactClickListener(Contact contact) {
        Toast.makeText(this, "salut, t'as cliqué sur ce contact  " + contact.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskComplete(ArrayList<Contact> contacts) {

        contactList = contacts;
        if (contactList.isEmpty()) {

            contactsListView.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);


        } else {
            ContactAdapter adapter = new ContactAdapter(ContactActivity.this, contactList, ContactActivity.this);
            contactsListView.setAdapter(adapter);
            loader.setVisibility(View.GONE);
            contactsListView.setVisibility(View.VISIBLE);

        }
    }
}
