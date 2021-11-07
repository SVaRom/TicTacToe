package com.varom.proyectou3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv1,tv2;
    Button[][] botones=new Button[3][3];
    Button btnReset;
    int pPoints=0,cPoints=0,empate=0;
    boolean pTurn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String  btnID="button_"+i+j;
                int idFin=getResources().getIdentifier(btnID,"id",getPackageName());
                botones[i][j]=findViewById(idFin);
                botones[i][j].setOnClickListener(this);
                botones[i][j].setText("");
            }
        }
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pPoints=0;
                cPoints=0;
                tv1.setText("Player: "+pPoints);
                tv2.setText("CPU: "+cPoints);
                limpiar();
            }
        });
    }

    public void limpiar(){
        empate=0;
        pTurn=true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                botones[i][j].setText("");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(((Button)view).getText().toString().equals("")){
            ((Button)view).setText("O");
            empate++;
        }
        if(verificar()&&pTurn){
            pPoints++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv1.setText("Player: "+pPoints);
                    limpiar();
                }
            },1000);
            tv1.setText("Player: Ganador");
        }else if(empate==9){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv1.setText("Player: "+pPoints);
                    tv2.setText("CPU: "+cPoints);
                }
            },1000);
            tv1.setText("Player: Empate!");
            tv2.setText("CPU: Empate!");
            limpiar();
        }else{
            pTurn=!pTurn;
            cpu();
        }
    }

    public boolean verificar(){
        return botones[0][0].getText() == botones[0][1].getText() && botones[0][0].getText() == botones[0][2].getText() && botones[0][0].getText() != ""
                || botones[1][0].getText() == botones[1][1].getText() && botones[1][0].getText() == botones[1][2].getText() && botones[1][0].getText() != ""
                || botones[2][0].getText() == botones[2][1].getText() && botones[2][0].getText() == botones[2][2].getText() && botones[2][0].getText() != ""
                || botones[0][0].getText() == botones[1][0].getText() && botones[0][0].getText() == botones[2][0].getText() && botones[0][0].getText() != ""
                || botones[0][1].getText() == botones[1][1].getText() && botones[0][1].getText() == botones[2][1].getText() && botones[0][1].getText() != ""
                || botones[0][2].getText() == botones[1][2].getText() && botones[0][2].getText() == botones[2][2].getText() && botones[0][2].getText() != ""
                || botones[0][0].getText() == botones[1][1].getText() && botones[0][0].getText() == botones[2][2].getText() && botones[0][0].getText() != ""
                || botones[0][2].getText() == botones[1][1].getText() && botones[0][2].getText() == botones[2][0].getText() && botones[0][2].getText() != "";
    }

    public void cpu(){
        Random random = new Random();
        int i,j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (botones[i][j].getText() != "");
        final int fI=i,fJ=j;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                botones[fI][fJ].setText("X");
                empate++;
                if(verificar()&&!pTurn){
                    cPoints++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv2.setText("CPU: "+cPoints);
                            limpiar();
                        }
                    },1000);
                    tv2.setText("CPU: Ganador");
                }else if (empate == 9) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText("Player: "+pPoints);
                            tv2.setText("CPU: "+cPoints);
                        }
                    },1000);
                    tv1.setText("Player: Empate!");
                    tv2.setText("CPU: Empate!");
                    limpiar();
                }else pTurn=!pTurn;
            }
        }, 500);
    }
}