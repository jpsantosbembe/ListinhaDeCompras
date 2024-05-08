package com.joaobembe.apps.listinhadecompras.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.joaobembe.apps.listinhadecompras.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}