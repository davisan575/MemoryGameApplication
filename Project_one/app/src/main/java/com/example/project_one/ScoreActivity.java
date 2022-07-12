package com.example.project_one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        int score = getIntent().getIntExtra("score", -1);
        String textStr = "Final score: " + Integer.toString(score);
        TextView scrText = findViewById(R.id.prevScore);
        scrText.setText("Final score: " + Integer.toString(score));
    }

    public void ContinueClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
