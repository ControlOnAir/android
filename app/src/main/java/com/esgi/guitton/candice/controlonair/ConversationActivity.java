package com.esgi.guitton.candice.controlonair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.esgi.guitton.candice.controlonair.adapter.ConversationAdapter;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.services.ConversationsTask;
import java.util.ArrayList;


public class ConversationActivity extends AppCompatActivity
        implements ConversationAdapter.OnConversationClickListener, ConversationsTask.OnConversationsTaskCompleted {

    private EditText editTextSearch;
    private ListView conversationsListView;
    private ArrayList<Conversation> conversations;
    private ProgressBar loader;
    private LinearLayout emptyView;
    private Button retryButton;
    android.support.design.widget.FloatingActionButton create_message_button;
    public final static String CONST_CONVERSATION_KEY = "conversation";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        create_message_button = findViewById(R.id.create_message_button);
        editTextSearch = findViewById(R.id.edit_text_search);

        conversationsListView = findViewById(R.id.list_view_conversation);
        emptyView = findViewById(R.id.emptyView);
        retryButton = findViewById(R.id.retryButton);
        loader = findViewById(R.id.loader);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadConversations();
            }
        });

        conversations = new ArrayList<>();
        loadConversations();


        create_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConversationActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }
        });

    }


    private void loadConversations() {

        conversationsListView.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        ConversationsTask conversationsTask = new ConversationsTask(this);

        conversationsTask.execute(this);
    }

    @Override
    public void onConversationClicked(Conversation conversation) {
        Toast.makeText(this, "salut love, t'as cliqué sur cette conversation  " + conversation.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ConversationActivity.this, MessageListActivity.class);
        intent.putExtra(CONST_CONVERSATION_KEY, conversation);
        startActivity(intent);
    }

    @Override
    public void onTaskComplete(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
        if (conversations.isEmpty()) {

            conversationsListView.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {
            ConversationAdapter adapter = new ConversationAdapter(ConversationActivity.this, conversations, ConversationActivity.this);
            conversationsListView.setAdapter(adapter);
            loader.setVisibility(View.GONE);
            conversationsListView.setVisibility(View.VISIBLE);
        }
    }


}
