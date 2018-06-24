package com.esgi.guitton.candice.controlonair;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.esgi.guitton.candice.controlonair.services.ContactTask;
import com.esgi.guitton.candice.controlonair.services.ConversationsTask;
import com.esgi.guitton.candice.controlonair.services.MessagesTask;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements ContactTask.OnTaskCompleted, ConversationsTask.OnConversationsTaskCompleted {
    private Button loadButton;
    private ProgressBar loader;
    private ConstraintLayout resultLayout;
    private CardView conversationsCard;
    private CardView contactCard;
    private Toolbar toolbar;

    private String privateKey;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadButton = findViewById(R.id.loadButton);
        loader = findViewById(R.id.loader);
        resultLayout = findViewById(R.id.resultLayout);
        conversationsCard = findViewById(R.id.conversations_card);
        contactCard = findViewById(R.id.contact_card);

        toolbar = findViewById(R.id.toolbar);

        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.home_page);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        conversationsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConversationActivity.class));
            }
        });

        contactCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadButton.setVisibility(View.GONE);
                loader.setVisibility(View.VISIBLE);

                privateKey = generatePrivateKey();

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
                sharedPreferences.edit().putString(Constants.GENERATED_PRIVATE_KEY, privateKey).apply();

                MainActivityPermissionsDispatcher.loadEveythingWithPermissionCheck(MainActivity.this);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @NeedsPermission({Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    })
    public void loadEveything() {
        ContactTask contactTask = new ContactTask(MainActivity.this);
        contactTask.execute(MainActivity.this);
    }

    @OnShowRationale({Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    })
    void showRationaleForLoadEverything(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permissions_rational_message)
                .setPositiveButton(R.string.ok, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.cancel, (dialog, button) -> request.cancel())
                .show();
    }

    private String generatePrivateKey() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        return android_id;
    }

    @Override
    public void onContactTaskComplete(ArrayList<Contact> contacts) {

        ConversationsTask conversationsTask = new ConversationsTask(MainActivity.this);
        conversationsTask.execute(MainActivity.this);

    }

    @Override
    public void onConversationsTaskComplete(final ArrayList<Conversation> conversations) {

        for (int i = 0; i < conversations.size(); i++) {

            final int index = i;
            Conversation conversation = conversations.get(index);

            MessagesTask messagesTask = new MessagesTask(new MessagesTask.OnMessagesTaskCompleted() {
                @Override
                public void onMessagesTaskComplete(ArrayList<Message> messages) {
                    if (index == conversations.size() - 1) {
                        onLoadCompleted();
                    }
                }
            });

            Pair<Integer, Context> pair = new Pair<>(conversation.getId(), MainActivity.this.getBaseContext());
            messagesTask.execute(pair);

        }


    }

    private void onLoadCompleted() {
        loader.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);

        Toast.makeText(MainActivity.this, "Votre clé privée : " + privateKey, Toast.LENGTH_LONG).show();
    }
}
