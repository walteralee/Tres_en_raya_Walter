package com.example.tres_en_raya;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class contra_amigo extends AppCompatActivity {

    private ImageButton b0;
    private ImageButton b1;
    private ImageButton b2;
    private ImageButton b3;
    private ImageButton b4;
    private ImageButton b5;
    private ImageButton b6;
    private ImageButton b7;
    private ImageButton b8;

    private TextView anotaciones;

    Random r = new Random();

    int contador;

    int[][] tablero = new int[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra_amigo);

        b0 = findViewById(R.id.boton_0);
        b1 = findViewById(R.id.boton_1);
        b2 = findViewById(R.id.boton_2);
        b3 = findViewById(R.id.boton_3);
        b4 = findViewById(R.id.boton_4);
        b5 = findViewById(R.id.boton_5);
        b6 = findViewById(R.id.boton_6);
        b7 = findViewById(R.id.boton_7);
        b8 = findViewById(R.id.boton_8);

        anotaciones = findViewById(R.id.text_notas);

        nueva_partida();

    }

    private void nueva_partida(){

        b0.setEnabled(true);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
        b8.setEnabled(true);

        b0.setImageResource(R.drawable.vacio);
        b1.setImageResource(R.drawable.vacio);
        b2.setImageResource(R.drawable.vacio);
        b3.setImageResource(R.drawable.vacio);
        b4.setImageResource(R.drawable.vacio);
        b5.setImageResource(R.drawable.vacio);
        b6.setImageResource(R.drawable.vacio);
        b7.setImageResource(R.drawable.vacio);
        b8.setImageResource(R.drawable.vacio);

        contador = r.nextInt(2);

        if(contador%2 == 0){
            anotaciones.setText("Juegan O");
        }else{
            anotaciones.setText("Juegan X");
        }

        vaciar_tablero();

    }

    public void insertar_simbolo(View v){

        ImageButton b = (ImageButton) v;

        annadir_a_matriz(b);

        if(contador%2 == 0){
           b.setImageResource(R.drawable.circulo);
            anotaciones.setText("Juegan X");
        }else{
           b.setImageResource(R.drawable.cruz);
            anotaciones.setText("Juegan O");
        }

        b.setEnabled(false);

        contador++;

        if(comprobar_ganador()){
            parar_todo();
        } else if (comprobar_empate()) {
            anotaciones.setText("EMPATE!!");
            parar_todo();
        }

    }

    private void annadir_a_matriz(ImageButton b){

        int tipo = 0;

        if(contador%2 == 0){
            tipo = 1;
        }else{
            tipo = 5;
        }

        if(b.getId() == R.id.boton_0){

            this.tablero[0][0] = tipo;

        }else if(b.getId() == R.id.boton_1){

            this.tablero[0][1] = tipo;

        }else if(b.getId() == R.id.boton_2){

            this.tablero[0][2] = tipo;

        }else if(b.getId() == R.id.boton_3){

            this.tablero[1][0] = tipo;

        }else if(b.getId() == R.id.boton_4){

            this.tablero[1][1] = tipo;

        }else if(b.getId() == R.id.boton_5){

            this.tablero[1][2] = tipo;

        }else if(b.getId() == R.id.boton_6){

            this.tablero[2][0] = tipo;

        }else if(b.getId() == R.id.boton_7){

            this.tablero[2][1] = tipo;

        }else if(b.getId() == R.id.boton_8){

            this.tablero[2][2] = tipo;

        }

    }

    private boolean comprobar_ganador(){

        boolean ganador = false;

        //Horizontal
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] + tablero[i][1] + tablero[i][2] == 15) {

                ganador = true;
                anotaciones.setText("Ganan X!");

            }else if(tablero[i][0] + tablero[i][1] + tablero[i][2] == 3){

                ganador = true;
                anotaciones.setText("Ganan O!");

            }
        }

        //Vertical
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] + tablero[1][j] + tablero[2][j] == 15) {

                ganador = true;
                anotaciones.setText("Ganan X!");

            }else if(tablero[0][j] + tablero[1][j] + tablero[2][j] == 3){

                ganador = true;
                anotaciones.setText("Ganan O!");

            }
        }

        //Diagonal //00 11 22 | 02 11 20
        if ((tablero[0][0] + tablero[1][1] + tablero[2][2] == 15) || (tablero[0][2] + tablero[1][1] + tablero[2][0] == 15)) {

            ganador = true;
            anotaciones.setText("Ganan X!");

        } else if ((tablero[0][0] + tablero[1][1] + tablero[2][2] == 3) || (tablero[0][2] + tablero[1][1] + tablero[2][0] == 3)) {

            ganador = true;
            anotaciones.setText("Ganan O!");

        }

        return ganador;

    }

    private boolean comprobar_empate(){

        boolean empate = true;

        for (int i = 0; i < tablero.length; i++) { // Recorrer filas
            for (int j = 0; j < tablero[i].length; j++) { // Recorrer columnas
                if(tablero[i][j] == 0){
                    empate = false;
                }
            }

            System.out.println();
        }

        return empate;

    }

    private void parar_todo(){
        b0.setEnabled(false);
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
        b7.setEnabled(false);
        b8.setEnabled(false);

        CountDownTimer countDownTimer = new CountDownTimer(2000, 2000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                nueva_partida();
            }
        };
        countDownTimer.start();

    }

    private void vaciar_tablero(){

        for (int i = 0; i < tablero.length; i++) { // Recorrer filas
            for (int j = 0; j < tablero[i].length; j++) { // Recorrer columnas
                tablero[i][j] = 0;
            }

            System.out.println();
        }

    }

}