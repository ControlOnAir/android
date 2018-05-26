package com.esgi.guitton.candice.controlonair;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.adapter.ConversationAdapter;
import com.esgi.guitton.candice.controlonair.adapter.MessageListAdapter;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.esgi.guitton.candice.controlonair.services.ConversationsTask;
import com.esgi.guitton.candice.controlonair.services.MessageService;
import com.esgi.guitton.candice.controlonair.services.MessagesTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity implements MessagesTask.OnMessagesTaskCompleted {

    private RecyclerView messageRecycler;
    private MessageListAdapter messageAdapter;
    private List<Message> messageList;
    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;
    private TextView text_view_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_message_list);
        retryButton = findViewById(R.id.retryButton);
        loader = findViewById(R.id.loader);
        emptyView = findViewById(R.id.emptyView);
        text_view_contact = findViewById(R.id.text_view_contact);

        messageRecycler = findViewById(R.id.recyclerview_message_list);
        messageAdapter = new MessageListAdapter(this, messageList);

        final Conversation conversation = (Conversation) getIntent().getSerializableExtra(ConversationActivity.CONST_CONVERSATION_KEY);

        loadMessages(conversation.getId());

        text_view_contact.setText(conversation.getContact().getName());
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMessages(conversation.getId());
            }
        });

    }

    private void loadMessages(Integer id) {

        messageRecycler.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        Pair<Integer, Context> pair = new Pair<>(id, getBaseContext());
        MessagesTask messagesTask = new MessagesTask(this);

        messagesTask.execute(pair);
    }


    @Override
    public void onTaskComplete(ArrayList<Message> messages) {
        this.messageList = messages;
        if (messages.isEmpty()) {

            messageRecycler.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {
            MessageListAdapter adapter = new MessageListAdapter(MessageListActivity.this, messages);
            messageRecycler.setAdapter(adapter);
            messageRecycler.setLayoutManager(new LinearLayoutManager(this));
            loader.setVisibility(View.GONE);
            messageRecycler.setVisibility(View.VISIBLE);

        }
    }
}