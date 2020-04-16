package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TrisActivity extends AppCompatActivity {
    private static final String TAG = "TrisActivity";


    public int m[][]; // matrice interi
    public int cont = 9;
    private boolean g1; // true o false

    private TextView lblTit;
    // possibilità 1
    Button b00, b01, b02;
    Button b10, b11, b12;
    Button b20, b21, b22;
    // possibilità 2
    Button b[][];
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tris);

        // binding
        bindComponents();
        // leggere parametri da MainActivity
        Intent intent;
        intent = getIntent();
        // in stringa -> intent.getStringExtra("g1");
        lblTit.setText(intent.getStringExtra("g1") + " VS " + intent.getStringExtra("g2"));

        // init giocatore
        g1 = true;
        // init matrice
        m = new int[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                m[i][j] = 0;
            }
        }

        // possibilità 1
       /* b00.setOnClickListener(new myListener());
        b01.setOnClickListener(new myListener());
        b02.setOnClickListener(new myListener());
        b10.setOnClickListener(new myListener());
        b11.setOnClickListener(new myListener());
        b12.setOnClickListener(new myListener());
        b20.setOnClickListener(new myListener());
        b21.setOnClickListener(new myListener());
        b22.setOnClickListener(new myListener());
        */
        // possibilità 2
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                b[i][j].setTransitionName("btn_" + i + "_" + j);
                b[i][j].setOnClickListener(new myListener());
            }
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++){
                        m[i][j] = 0; // resetto matrice
                        //----------
                        b[i][j].setEnabled(true);
                        b[i][j].setBackgroundResource(R.color.colorAccent);
                    }
                }
                cont = 9;
                btnReset.setVisibility(View.GONE);
            }
        });
    }
    private void bindComponents(){
        lblTit = findViewById(R.id.lblTit);
        // possibilità 1
        /*
        b00 = findViewById(R.id.btn00);
        b01 = findViewById(R.id.btn01);
        b02 = findViewById(R.id.btn02);
        b10 = findViewById(R.id.btn10);
        b11 = findViewById(R.id.btn11);
        b12 = findViewById(R.id.btn12);
        b20 = findViewById(R.id.btn20);
        b21 = findViewById(R.id.btn21);
        b22 = findViewById(R.id.btn22);
        */
        // possibilità 2
        b = new Button[3][3];
        b[0][0] = findViewById(R.id.btn00); //b[0][0].setTransitionName("btn_0_0");
        b[0][1] = findViewById(R.id.btn01); //b[0][1].setTransitionName("btn_0_1");
        b[0][2] = findViewById(R.id.btn02); //b[0][2].setTransitionName("btn_0_2");
        b[1][0] = findViewById(R.id.btn10); //b[1][0].setTransitionName("btn_1_0");
        b[1][1] = findViewById(R.id.btn11); //b[1][1].setTransitionName("btn_1_1");
        b[1][2] = findViewById(R.id.btn12); //b[1][2].setTransitionName("btn_1_2");
        b[2][0] = findViewById(R.id.btn20); //b[2][0].setTransitionName("btn_2_0");
        b[2][1] = findViewById(R.id.btn21); //b[2][1].setTransitionName("btn_2_1");
        b[2][2] = findViewById(R.id.btn22); //b[2][2].setTransitionName("btn_2_2");

        btnReset = findViewById(R.id.btnReset);
    }
    void vince(String g){
        Toast.makeText(this, g, Toast.LENGTH_LONG).show();
        btnReset.setVisibility(View.VISIBLE);
    }
    void pareggia(){
        Toast.makeText(this, "PAREGGIO", Toast.LENGTH_LONG).show();
        btnReset.setVisibility(View.VISIBLE);
    }
    void bloccaPulsanti(){
       /* POSSIBILITA' 1
        b00.setEnabled(false);
        b01.setEnabled(false);
        b02.setEnabled(false);
        ....
        b22.setEnabled(false);
        */
       // POSSIBILITA' 2
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                b[i][j].setEnabled(false);
            }
        }
    }
    class myListener implements View.OnClickListener{
        private static final String TAG = "ClassListener";
        @Override
        public void onClick(View v) {
            int x, y;
            int x_1, y_1;
            Random rnd = new Random();

            boolean vittoria;
            // 1. rintracciare pulsante chiamante
            Button bL = (Button) v;
            Log.i(TAG, String.valueOf(bL.getTransitionName()));
            // 2. assegno a x y le coordinate lette dal Button
            x = Integer.parseInt(bL.getTransitionName().split("_")[1]);
            y = Integer.parseInt(bL.getTransitionName().split("_")[2]);

            m[x][y] = 1;
            g1 = false;
            bL.setBackgroundResource(R.color.colorBlu);
            bL.setEnabled(false); // disabilitiamo il click del pulsante
            cont--;
            if (!controllaVittoria(g1, x, y)){
                if(cont == 0){
                    pareggia();
                }else{
                    do{
                        x_1 = rnd.nextInt(3);
                        y_1 = rnd.nextInt(3);
                    }while(m[x_1][y_1] != 0);
                    m[x_1][y_1] = 2;
                    cont--;
                    g1 = true;
                    b[x_1][y_1].setBackgroundResource(R.color.colorArancio);
                    b[x_1][y_1].setEnabled(false);
                    if(!controllaVittoria(g1, x_1, y_1)){
                        if(cont == 0){
                            pareggia();
                        }
                    }
                }
            }
            /*
            if(g1){
            }else{
                m[x][y] = 2;
                g1 = true;
                bL.setBackgroundResource(R.color.colorArancio);
            }
            */
            // stampo matrice
            for(int i=0; i<3; i++){
                Log.d("", String.valueOf(m[i][0]) + " " + String.valueOf(m[i][1]) + " " + String.valueOf(m[i][2]));
            }
            // -------
        }
        private boolean controllaVittoria(boolean g1, int x, int y){
            boolean vittoria = false;
            // Controllo Vittoria

            // VERTICALE
            if(m[0][y] == m[x][y] && m[1][y] == m[x][y] && m[2][y] == m[x][y]){
                // vittoria verticale
                vittoria = true;
            }else{
                // ORIZZONTALE
                if(m[x][0] == m[x][y] && m[x][1] == m[x][y] && m[x][2] == m[x][y]){
                    // vittoria orizzontale
                    vittoria = true;
                }else{
                    // Diagonale principale
                    if(m[0][0] == m[x][y] && m[1][1] == m[x][y] && m[2][2] == m[x][y]){
                        // Vittoria diagonale p.
                        vittoria = true;
                    }else if(m[0][2] == m[x][y] && m[1][1] == m[x][y] && m[2][0] == m[x][y]){// Diagonale secondaria
                        // Vittoria diagonale s.
                        vittoria = true;
                    }
                }
            }
            if(vittoria){
                if(!g1){ // ho già invertito giocatore 1 con giocatore 2
                    Log.d(TAG, "VINCE GIOCATORE 1");
                    vince("VINCE GIOCATORE 1");
                }else{
                    Log.d(TAG, "VINCE GIOCATORE 2");
                    vince("VINCE GIOCATORE 2");
                }
                bloccaPulsanti();
            }
            return vittoria;
        }
    }
}