package com.tasakos.tictactoe;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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

import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
    private HashSet<Integer> ids = new HashSet<Integer>();
    private int  player1_points=0, player2_points=0, round, id;
    private TextView text_p1, text_p2;
    private Button resbut;
    private boolean player1=true, Cpu, saved=false;
    private Intent myIntent, menuIntent;


    public static final String SHARED_PREFS="shared_prefs", ROUND="round", PLAYER1P="player1p",
            PLAYER2P="player2p",PLAYER1="player1",CPU="cpu";


    @Override
    public void onClick(View view) {
        String X = getResources().getString(R.string.x);
        String O = getResources().getString(R.string.o);
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (Cpu) {
            ((Button) view).setText(X);
            ids.add(view.getId());
            round++;
            if (ids.size() != 9) {
                cpu();
            }
        }
        else{
            if (player1){
                ((Button) view).setText(X);
                player1=!player1;
            }
            else{
                ((Button) view).setText(O);
                player1=!player1;
            }
        }
            if (checkForWin(X)){
                Player1Wins();
            }
            else if (checkForWin(O)){
                Player2Wins();
            }
            else if (round==9){
                draw();
            }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveitem:
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putInt(ROUND,round);
                editor.putInt(MainActivity.PLAYER1P,player1_points);
                editor.putInt(MainActivity.PLAYER2P,player2_points);
                editor.putBoolean(MainActivity.PLAYER1,player1);
                editor.putBoolean(MainActivity.CPU,Cpu);
                editor.apply();
                Toast.makeText(this, "Data saved!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "tasakos",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myIntent = getIntent();
        menuIntent = new Intent(this, StartMenu.class);
        Cpu= (boolean) myIntent.getBooleanExtra("Cpu",false);
        saved=(boolean) myIntent.getBooleanExtra("saved",false);

        for (int i=0; i<3;i++){
            for (int j=0; j<3;j++){
                String butID="butt"+ String.valueOf(i+1) + String.valueOf(j+1);
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                buttons.put(resID, (Button) findViewById(resID));
                buttons.get(resID).setOnClickListener(this);
            }
        }
        System.out.println(Cpu);
        text_p1 = (TextView) findViewById(R.id.text_view_p1);
        text_p2 = (TextView) findViewById(R.id.text_view_p2);
        resbut = (Button) findViewById(R.id.resbut);
        resbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        if (saved){
            loadData();
        }
        saved = false;
        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan( "casual"), 0,s.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(s);

    }

    private void cpu(){
        Random rand = new Random();
        while (true){
            String butID="butt"+ String.valueOf(rand.nextInt(3)+1) + String.valueOf(rand.nextInt(3)+1);
            int resID = getResources().getIdentifier(butID, "id", getPackageName());
            if (!ids.contains(resID)){
                buttons.get(resID).setText("O");
                ids.add(resID);
                break;
            }
        }
        round++;
    }



    private boolean checkForWin(String s) {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String butID="butt"+ String.valueOf(i+1) + String.valueOf(j+1);
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                field[i][j] = buttons.get(resID).getText().toString();
            }
        }


        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(s)) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(s)) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(s)) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && field[0][2].equals(s)) {
            return true;
        }

        return false;
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String butID="butt"+ String.valueOf(i+1) + String.valueOf(j+1);
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                buttons.get(resID).setText("");
            }
        }
        round = 0;
        ids.clear();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void Player1Wins(){
        player1_points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void Player2Wins(){
        player2_points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText(){
        text_p1.setText(getResources().getString(R.string.Player1)+" "+player1_points);
        text_p2.setText(getResources().getString(R.string.Player2)+" "+player2_points);
    }

    private void resetGame(){
        player1_points=0;
        player2_points=0;
        resetBoard();
        updatePointsText();
    }



    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt(ROUND,round);
        editor.putInt(PLAYER1P,player1_points);
        editor.putInt(PLAYER2P,player2_points);
        editor.putBoolean(PLAYER1,player1);
        editor.putBoolean(CPU,Cpu);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        player1=sharedPreferences.getBoolean(PLAYER1,true);
        Cpu=sharedPreferences.getBoolean(CPU,false);
        player1_points=sharedPreferences.getInt(PLAYER1P,0);
        player2_points=sharedPreferences.getInt(PLAYER2P,0);
        updatePointsText();
        Toast.makeText(this, "Data Loads succesfull", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }
}


