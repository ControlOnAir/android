package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.adapter.ContactAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.services.ContactTask;
import com.esgi.guitton.candice.controlonair.view_holder.ContactViewHolder;
import com.esgi.guitton.candice.controlonair.view_holder.ConversationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements ContactViewHolder.OnContactClickListener {

    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;
    private EditText editTextSearch;
    private RecyclerView contactRecyclerView;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference contactReference;
    private FirebaseRecyclerAdapter<Contact, ContactViewHolder> adapter;

    public final static String CONST_CONTACT_KEY = "contact";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        editTextSearch = findViewById(R.id.edit_text_search);

        contactRecyclerView = findViewById(R.id.list_view_contact);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.contact_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        contactReference = Utils.getDatabase().getReferenceFromUrl(Constants.CONTACTS_FIREBASE_URL_REFERENCE);

        Query query = contactReference.orderByKey();

        FirebaseRecyclerOptions<Contact> options = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, Contact.class).build();

        setupAdapter(options);

        contactRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        contactRecyclerView.setLayoutManager(layoutManager);
        contactRecyclerView.setAdapter(adapter);
    }

    private void setupAdapter(FirebaseRecyclerOptions<Contact> options) {
        adapter = new FirebaseRecyclerAdapter<Contact, ContactViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position, @NonNull Contact contact) {
                holder.bind(contact);
            }

            @NonNull
            @Override
            public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
                return new ContactViewHolder(view, ContactActivity.this);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
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

    @Override
    public void onContactClicked(Contact contact) {
        Toast.makeText(this, "salut, t'as cliqué sur ce contact  " + contact.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ContactActivity.this, DetailContactActivity.class);
        intent.putExtra(CONST_CONTACT_KEY, contact);
        startActivity(intent);
    }



}


