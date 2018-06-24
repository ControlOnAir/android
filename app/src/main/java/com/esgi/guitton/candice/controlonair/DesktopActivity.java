package com.esgi.guitton.candice.controlonair;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DesktopActivity extends AppCompatActivity {

    private TextView printPrivateKey;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
        printPrivateKey = findViewById(R.id.printKey);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "privateKey");

        printPrivateKey.setText(privateKey);


    }
}
