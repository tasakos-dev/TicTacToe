package com.tasakos.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartMenu extends AppCompatActivity {
    private Button p1,p2,about,loadBut;
    private MainActivity main;
    private Intent myIntent;
    private int player1_points, player2_points;
    private boolean cpu, player1;





    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        p1= findViewById(R.id.p1);
        p2= findViewById(R.id.p2);
        about = findViewById(R.id.aboutBut);
        loadBut = findViewById(R.id.LoadBut);
        myIntent = new Intent(this, MainActivity.class);
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myIntent.putExtra("Cpu", true);
                startActivity(myIntent);

            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myIntent.putExtra("Cpu", false);
                startActivity(myIntent);
            }
        });
        loadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myIntent.putExtra("saved", true);
                startActivity(myIntent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "tasakos", Toast.LENGTH_SHORT).show();
            }
        });
        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan( "casual"), 0,s.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(s);
    }
}
