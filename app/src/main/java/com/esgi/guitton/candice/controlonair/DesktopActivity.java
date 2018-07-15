package com.esgi.guitton.candice.controlonair;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AlertDialogLayout;
import android.text.Layout;
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
        askData = findViewById(R.id.askData);toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        title.setText(R.string.desktop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String privateKey = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "privateKey");

        printPrivateKey.setText(privateKey);


        askData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setView(R.layout.dialog_get_data);
                builder.create();
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
