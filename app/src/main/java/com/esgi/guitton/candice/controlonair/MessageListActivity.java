package com.esgi.guitton.candice.controlonair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.adapter.MessageListAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.esgi.guitton.candice.controlonair.services.MessagesTask;
import com.esgi.guitton.candice.controlonair.view_holder.ContactViewHolder;
import com.esgi.guitton.candice.controlonair.view_holder.ConversationViewHolder;
import com.esgi.guitton.candice.controlonair.view_holder.MessageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity implements MessageViewHolder.OnMessageClickListener {

    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;
    private TextView text_view_contact;


    private RecyclerView MessageRecyclerView;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference messageReference;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter;

    public final static String CONST_MESSAGE_KEY = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_message_list);
        retryButton = findViewById(R.id.retryButton);
        loader = findViewById(R.id.loader);
        emptyView = findViewById(R.id.emptyView);
        text_view_contact = findViewById(R.id.text_view_contact);


        MessageRecyclerView = findViewById(R.id.recyclerview_message_list);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.message_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        messageReference = Utils.getDatabase().getReferenceFromUrl(Constants.MESSAGES_FIREBASE_URL_REFERENCE);

        Query query = messageReference.orderByKey();

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>().setQuery(query, Message.class).build();

        setupAdapter(options);

        MessageRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        MessageRecyclerView.setLayoutManager(layoutManager);
        MessageRecyclerView.setAdapter(adapter);

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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_list, parent, false);
                return new MessageViewHolder(view, MessageListActivity.this);
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
    public void onMessageClicked(Message message) {
        Toast.makeText(this, "salut, t'as cliqué sur ce message  " + message.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MessageListActivity.this, MessageListActivity.class);
        intent.putExtra(CONST_MESSAGE_KEY, message);
        startActivity(intent);
    }
}





