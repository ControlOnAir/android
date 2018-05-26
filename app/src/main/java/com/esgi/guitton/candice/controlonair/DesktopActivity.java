package com.esgi.guitton.candice.controlonair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;

import com.esgi.guitton.candice.controlonair.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DesktopActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);




        ListenerOnConversation listener = new ListenerOnConversation();
        // Read from the database
      //  contactReference.addChildEventListener(listener);

    }
}
