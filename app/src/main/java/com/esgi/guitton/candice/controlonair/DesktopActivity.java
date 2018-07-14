package com.esgi.guitton.candice.controlonair;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


public class DesktopActivity extends AppCompatActivity {

    private TextView printPrivateKey;
    private Button askData;
    private android.support.v7.widget.Toolbar toolbar;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
        printPrivateKey = findViewById(R.id.printKey);
        askData = findViewById(R.id.askData);
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(R.string.new_message_title);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "privateKey");

        printPrivateKey.setText(privateKey);

        askData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

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
}
