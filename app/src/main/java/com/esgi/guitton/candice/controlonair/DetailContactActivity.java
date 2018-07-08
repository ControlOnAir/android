package com.esgi.guitton.candice.controlonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.models.Contact;

import static com.esgi.guitton.candice.controlonair.ContactActivity.CONST_CONTACT_KEY;

public class DetailContactActivity extends AppCompatActivity {

    private TextView contact_name;
    private TextView contact_number;
    private Toolbar toolbar;
    private Button modify;
    private Button delete;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        toolbar = findViewById(R.id.toolbar);

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        //title.setText(R.string.contact_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contact_name = findViewById(R.id.contact_name);
        contact_number = findViewById(R.id.contact_number);
        modify = findViewById(R.id.modify_contact);
        delete = findViewById(R.id.delete_contact);
        final Contact contact = (Contact) getIntent().getSerializableExtra(CONST_CONTACT_KEY);
        contact_name.setText(contact.getName());
        contact_number.setText(contact.getNumber());


        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
