package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.view_holder.ConversationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class ConversationActivity extends AppCompatActivity implements ConversationViewHolder.OnConversationClickListener {

    private EditText editTextSearch;
    private RecyclerView conversationsRecyclerView;
    private FloatingActionButton create_message_button;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference conversationsReference;
    private FirebaseRecyclerAdapter<Conversation, ConversationViewHolder> adapter;


    public final static String CONST_CONVERSATION_KEY = "conversation";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        create_message_button = findViewById(R.id.create_message_button);
        editTextSearch = findViewById(R.id.edit_text_search);

        conversationsRecyclerView = findViewById(R.id.list_view_conversation);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.conversation_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        create_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConversationActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }
        });

        conversationsReference = Utils.getDatabase().getReferenceFromUrl(Constants.CONVERSATIONS_FIREBASE_URL_REFERENCE);

        Query query = conversationsReference.orderByKey();

        FirebaseRecyclerOptions<Conversation> options = new FirebaseRecyclerOptions.Builder<Conversation>().setQuery(query, Conversation.class).build();

        setupAdapter(options);

        conversationsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        conversationsRecyclerView.setLayoutManager(layoutManager);
        conversationsRecyclerView.setAdapter(adapter);
    }

    private void setupAdapter(FirebaseRecyclerOptions<Conversation> options) {
        adapter = new FirebaseRecyclerAdapter<Conversation, ConversationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ConversationViewHolder holder, int position, @NonNull Conversation conversation) {
                holder.bind(conversation);
            }

            @NonNull
            @Override
            public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_list_item, parent, false);
                return new ConversationViewHolder(view, ConversationActivity.this);
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
    public void onConversationClicked(Conversation conversation) {
        Toast.makeText(this, "salut, t'as cliqué sur cette conversation  " + conversation.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ConversationActivity.this, MessageListActivity.class);
        intent.putExtra(CONST_CONVERSATION_KEY, conversation);
        startActivity(intent);
    }


}
