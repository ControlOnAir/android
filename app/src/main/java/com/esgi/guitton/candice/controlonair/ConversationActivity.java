package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.custom_firebase.ConversationFirebaseAdapter;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.view_holder.ConversationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class ConversationActivity extends AppCompatActivity implements ConversationViewHolder.OnConversationClickListener, SearchView.OnQueryTextListener
{

    private SearchView searchView;
    private RecyclerView conversationsRecyclerView;
    private FloatingActionButton create_message_button;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference conversationsReference;
    private ConversationFirebaseAdapter adapter;


    public final static String CONST_CONVERSATION_KEY = "conversation";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        create_message_button = findViewById(R.id.create_message_button);
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(this);

        conversationsRecyclerView = findViewById(R.id.list_view_conversation);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.conversation_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        create_message_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ConversationActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "");
        String conversationUrl = getString(R.string.conversation_firebase_url_reference, privateKey);

        conversationsReference = Utils.getDatabase().getReferenceFromUrl(conversationUrl);

        Query query = conversationsReference.orderByKey();

        FirebaseRecyclerOptions<Conversation> options = new FirebaseRecyclerOptions.Builder<Conversation>().setQuery(query, Conversation.class).build();

        adapter = new ConversationFirebaseAdapter(options, this);

        conversationsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        conversationsRecyclerView.setLayoutManager(layoutManager);
        conversationsRecyclerView.setAdapter(adapter);
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
    public void onConversationClicked(Conversation conversation)
    {
        Intent intent = new Intent(ConversationActivity.this, MessageListActivity.class);
        intent.putExtra(CONST_CONVERSATION_KEY, conversation);
        startActivity(intent);
    }


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
}
