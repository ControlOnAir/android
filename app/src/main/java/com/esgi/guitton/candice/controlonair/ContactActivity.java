package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.custom_firebase.ContactFirebaseAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.view_holder.ContactViewHolder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ContactActivity extends AppCompatActivity implements ContactViewHolder.OnContactClickListener
{

    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;
    private SearchView searchView;
    private RecyclerView contactRecyclerView;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference contactReference;
    private ContactFirebaseAdapter adapter;
    private FloatingActionButton addContactButton;
    public final static String CONST_CONTACT_KEY = "contact";

    private String privateKey;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        contactRecyclerView = findViewById(R.id.list_view_contact);
        addContactButton = findViewById(R.id.add_contact_button);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.contact_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "");
        String contactUrl = getString(R.string.contact_firebase_url_reference, privateKey);
        contactReference = Utils.getDatabase().getReferenceFromUrl(contactUrl);

        Query query = contactReference.orderByChild("name");

        FirebaseRecyclerOptions<Contact> options = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, Contact.class).build();

        adapter = new ContactFirebaseAdapter(options, this);

        contactRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        contactRecyclerView.setLayoutManager(layoutManager);
        contactRecyclerView.setAdapter(adapter);


        addContactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });


        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                adapter.getFilter().filter(s);

                return false;
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (adapter != null)
        {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (adapter != null)
        {
            adapter.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContactClicked(Contact contact)
    {
        Intent intent = new Intent(ContactActivity.this, DetailContactActivity.class);
        intent.putExtra(CONST_CONTACT_KEY, contact);
        startActivity(intent);
    }


}


