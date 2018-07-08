package com.esgi.guitton.candice.controlonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SendMessageActivity extends  AppCompatActivity {

    private Toolbar toolbar;
    private EditText searchDestinataire;
    private EditText body_message;
    private ImageButton send_message;
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

         searchDestinataire = findViewById(R.id.search_destinataire);
         toolbar = findViewById(R.id.toolbar);
         body_message = findViewById(R.id.edit_text_message);
         send_message = findViewById(R.id.button_send_message);
         toolbar = findViewById(R.id.toolbar);
         TextView title = toolbar.findViewById(R.id.title);
         title.setText(R.string.new_message_title);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowTitleEnabled(false);


         getSupportActionBar().setHomeButtonEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         searchDestinataire.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {


             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         send_message.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                // body_message.getText()
             }
         });


    }
}
