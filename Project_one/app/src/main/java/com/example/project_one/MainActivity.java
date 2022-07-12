package com.example.project_one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnMedClick(View view) {
        Intent  intent = new Intent(this,GameActivity.class);
        intent.putExtra("column", 6);
        intent.putExtra("row", 5);
        startActivity(intent);
    }

    public void OnEasyClick(View view) {
        Intent  intent = new Intent(this,GameActivity.class);
        intent.putExtra("column", 4);
        intent.putExtra("row", 4);
        startActivity(intent);
    }

    public void OnHardClick(View view) {
        Intent  intent = new Intent(this,GameActivity.class);
        intent.putExtra("column", 6);
        intent.putExtra("row", 8);
        startActivity(intent);
    }
}