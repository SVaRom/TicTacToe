package com.varom.proyectou3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[][] gameBoard=new Button[3][3];
    int roundC=0, playerPoints=0,cpuPoints=0;
    TextView tv1,tv2;
    boolean playerTurn=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                gameBoard[i][j] = findViewById(resID);
                gameBoard[i][j].setOnClickListener(this);
                gameBoard[i][j].setText("");
            }
        }
        Button btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                playerPoints = 0;
                cpuPoints = 0;
                tv1.setText("Player: "+playerPoints);
                tv2.setText("CPU: "+cpuPoints);
                reset();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (((Button) view).getText().toString().equals("")){
            if (playerTurn)((Button) view).setText("X");
            roundC++;
            if(verificarWin()){
                if(playerTurn){
                    playerPoints++;
                    Toast.makeText(this, "Player ganó!", Toast.LENGTH_SHORT).show();
                    tv1.setText("Player: "+playerPoints);
                    reset();
                }
            }else if (roundC == 9) {
                Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
                reset();
            }else{
                playerTurn=!playerTurn;
                cpuTurn();
            }
        }
    }
    public boolean verificarWin(){
        return gameBoard[0][0].getText() == gameBoard[0][1].getText() && gameBoard[0][0].getText() == gameBoard[0][2].getText() && gameBoard[0][0].getText() != ""
                || gameBoard[1][0].getText() == gameBoard[1][1].getText() && gameBoard[1][0].getText() == gameBoard[1][2].getText() && gameBoard[1][0].getText() != ""
                || gameBoard[2][0].getText() == gameBoard[2][1].getText() && gameBoard[2][0].getText() == gameBoard[2][2].getText() && gameBoard[2][0].getText() != ""
                || gameBoard[0][0].getText() == gameBoard[1][0].getText() && gameBoard[0][0].getText() == gameBoard[2][0].getText() && gameBoard[0][0].getText() != ""
                || gameBoard[0][1].getText() == gameBoard[1][1].getText() && gameBoard[0][1].getText() == gameBoard[2][1].getText() && gameBoard[0][1].getText() != ""
                || gameBoard[0][2].getText() == gameBoard[1][2].getText() && gameBoard[0][2].getText() == gameBoard[2][2].getText() && gameBoard[0][2].getText() != ""
                || gameBoard[0][0].getText() == gameBoard[1][1].getText() && gameBoard[0][0].getText() == gameBoard[2][2].getText() && gameBoard[0][0].getText() != ""
                || gameBoard[0][2].getText() == gameBoard[1][1].getText() && gameBoard[0][2].getText() == gameBoard[2][0].getText() && gameBoard[0][2].getText() != "";
    }
    public void cpuTurn(){
        Random random = new Random();
        int i,j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (gameBoard[i][j].getText() != "");
        final int fI=i,fJ=j;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gameBoard[fI][fJ].setText("O");
                roundC++;
                if(verificarWin()){
                    if(!playerTurn){
                        cpuPoints++;
                        Toast.makeText(getApplicationContext(), "CPU ganó!", Toast.LENGTH_SHORT).show();
                        tv2.setText("CPU: "+cpuPoints);
                        reset();
                    }
                }else if (roundC == 9) {
                    Toast.makeText(getApplicationContext(), "Empate!", Toast.LENGTH_SHORT).show();
                    reset();
                }else playerTurn=!playerTurn;
            }
        }, 500);
    }
    public void reset(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gameBoard[i][j].setText("");
                    }
                }
                roundC = 0;
                playerTurn = true;
            }
        }, 1000);
    }
}