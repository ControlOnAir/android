package com.esgi.guitton.candice.controlonair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.custom_firebase.ContactFirebaseAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.view_holder.ContactViewHolder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class SendMessageActivity extends  AppCompatActivity implements ContactViewHolder.OnContactClickListener{

    private Toolbar toolbar;
    private EditText body_message;
    private EditText destinataire;
    private ImageButton send_message;
    private DatabaseReference contactReference;
    private String privateKey;
    private ContactFirebaseAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

         toolbar = findViewById(R.id.toolbar);
         body_message = findViewById(R.id.edit_text_message);
         destinataire = findViewById(R.id.destinataire);
         send_message = findViewById(R.id.button_send_message);
         toolbar = findViewById(R.id.toolbar);
         TextView title = toolbar.findViewById(R.id.title);
         title.setText(R.string.new_message_title);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowTitleEnabled(false);

         SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
         privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "");
         String contactUrl = getString(R.string.contact_firebase_url_reference, privateKey);
         contactReference = Utils.getDatabase().getReferenceFromUrl(contactUrl);

         Query query = contactReference.orderByChild("name");

         FirebaseRecyclerOptions<Contact> options = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, Contact.class).build();

         adapter = new ContactFirebaseAdapter(options, this);
         getSupportActionBar().setHomeButtonEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         /*search_destinataire.setOnQueryTextListener(new SearchView.OnQueryTextListener()
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
         });*/
        body_message.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(view.getId() == R.id.edit_text_message && !hasFocus) {

                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                }
            }
        });
        destinataire.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(view.getId() == R.id.destinataire && !hasFocus) {

                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                }
            }
        });
         send_message.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String msg = body_message.getText().toString();
                 String phoneNo = destinataire.getText().toString();
                 sendSMS(phoneNo,msg);
                 body_message.setText("");
                 destinataire.setText("");
                 onBackPressed();

             }
         });


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


    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onContactClicked(Contact contact)
    {
    }
}

