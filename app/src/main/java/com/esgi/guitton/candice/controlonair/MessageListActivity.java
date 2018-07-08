package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.esgi.guitton.candice.controlonair.view_holder.MessageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MessageListActivity extends AppCompatActivity implements MessageViewHolder.OnMessageClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager;
    private DatabaseReference messagesReference;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter;

    public final static String CONST_MESSAGE_KEY = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_message_list);

        recyclerView = findViewById(R.id.recyclerView);

        if (getIntent() == null || getIntent().getSerializableExtra(ConversationActivity.CONST_CONVERSATION_KEY) == null) {
            return;
        }

        Conversation conversation = (Conversation) getIntent().getSerializableExtra(ConversationActivity.CONST_CONVERSATION_KEY);


        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(conversation.getContact().getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "");

        String formattedAdress = conversation.getContact().getNumber().replace("+", "a");

        String messageUrl = getString(R.string.message_firebase_url_reference, privateKey, formattedAdress);

        messagesReference = Utils.getDatabase().getReferenceFromUrl(messageUrl);

        Query query = messagesReference.orderByKey();

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>().setQuery(query, Message.class).build();

        setupAdapter(options);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupAdapter(FirebaseRecyclerOptions<Message> options) {
        adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message message) {
                holder.bind(message);
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
                return new MessageViewHolder(view, MessageListActivity.this);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                scrollToBottom();
            }
        };

    }

    private void scrollToBottom()
    {
        if (recyclerView != null && adapter != null && adapter.getItemCount() > 1)
        {
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
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
    public void onMessageClicked(Message message) {
        Toast.makeText(this, "salut, t'as cliqué sur ce message  " + message.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MessageListActivity.this, MessageListActivity.class);
        intent.putExtra(CONST_MESSAGE_KEY, message);
        startActivity(intent);
    }
}





