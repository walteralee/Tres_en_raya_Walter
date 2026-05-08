package com.example.tres_en_raya;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class contra_maquina extends AppCompatActivity {

    private Maquina maquina = new Maquina();

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
        setContentView(R.layout.activity_contra_maquina);

        b0 = findViewById(R.id.boton_20);
        b1 = findViewById(R.id.boton_21);
        b2 = findViewById(R.id.boton_22);
        b3 = findViewById(R.id.boton_23);
        b4 = findViewById(R.id.boton_24);
        b5 = findViewById(R.id.boton_25);
        b6 = findViewById(R.id.boton_26);
        b7 = findViewById(R.id.boton_27);
        b8 = findViewById(R.id.boton_28);

        anotaciones = findViewById(R.id.text_notas_maquina);

        nueva_partida();

    }

    private void nueva_partida(){

        maquina.setContador(0);

        anotaciones.setText("Tu turno");

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

        vaciar_tablero();

        inicio();


    }

    private void inicio(){

        if(contador%2 == 0){
            turno_maquina();
        }

    }

    public void turno_usuario(View v){

        ImageButton b = (ImageButton) v;

        b.setImageResource(R.drawable.cruz);

        annadir_a_matriz_usuario(b);

        b.setEnabled(false);

        boolean ganar = comprobar_ganador();
        boolean empate = comprobar_empate();

        if(!empate){
            turno_maquina();
        }


    }

    private void turno_maquina(){

        ImageButton b = annadir_a_matriz_pc(maquina.retorna_posicion(tablero));

        b.setImageResource(R.drawable.circulo);

        b.setEnabled(false);

        comprobar_ganador();
        comprobar_empate();


    }

    private ImageButton annadir_a_matriz_pc(int[] posicion){

        ImageButton boton = b0;

        if(posicion[0] == 0 &&  posicion[1] == 0){

            this.tablero[0][posicion[0]] = 5;
            boton = b0;

        }else if(posicion[0] == 0 &&  posicion[1] == 1){

            this.tablero[0][1] = 5;
            boton = b1;

        }else if(posicion[0] == 0 &&  posicion[1] == 2){

            this.tablero[0][2] = 5;
            boton = b2;

        }else if(posicion[0] == 1 &&  posicion[1] == 0){

            this.tablero[1][0] = 5;
            boton = b3;

        }else if(posicion[0] == 1 &&  posicion[1] == 1){

            this.tablero[1][1] = 5;
            boton = b4;

        }else if(posicion[0] == 1 &&  posicion[1] == 2){

            this.tablero[1][2] = 5;
            boton = b5;

        }else if(posicion[0] == 2 &&  posicion[1] == 0){

            this.tablero[2][0] = 5;
            boton = b6;

        }else if(posicion[0] == 2 &&  posicion[1] == 1){

            this.tablero[2][1] = 5;
            boton = b7;

        }else if(posicion[0] == 2 &&  posicion[1] == 2){

            this.tablero[2][2] = 5;
            boton = b8;

        }

        return boton;

    }

    private void annadir_a_matriz_usuario(ImageButton b){

        if(b.getId() == R.id.boton_20){

            this.tablero[0][0] = 1;

        }else if(b.getId() == R.id.boton_21){

            this.tablero[0][1] = 1;

        }else if(b.getId() == R.id.boton_22){

            this.tablero[0][2] = 1;

        }else if(b.getId() == R.id.boton_23){

            this.tablero[1][0] = 1;

        }else if(b.getId() == R.id.boton_24){

            this.tablero[1][1] = 1;

        }else if(b.getId() == R.id.boton_25){

            this.tablero[1][2] = 1;

        }else if(b.getId() == R.id.boton_26){

            this.tablero[2][0] = 1;

        }else if(b.getId() == R.id.boton_27){

            this.tablero[2][1] = 1;

        }else if(b.getId() == R.id.boton_28){

            this.tablero[2][2] = 1;

        }

    }

    private boolean comprobar_ganador(){

        boolean ganador = false;

        //Horizontal
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] + tablero[i][1] + tablero[i][2] == 15) {

                ganador = true;
                anotaciones.setText("Ganana PC");

            }else if(tablero[i][0] + tablero[i][1] + tablero[i][2] == 3){

                ganador = true;
                anotaciones.setText("Gananaste!!");

            }
        }

        //Vertical
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] + tablero[1][j] + tablero[2][j] == 15) {

                ganador = true;
                anotaciones.setText("Ganana PC");

            }else if(tablero[0][j] + tablero[1][j] + tablero[2][j] == 3){

                ganador = true;
                anotaciones.setText("Gananaste!!");

            }
        }

        //Diagonal //00 11 22 | 02 11 20
        if ((tablero[0][0] + tablero[1][1] + tablero[2][2] == 15) || (tablero[0][2] + tablero[1][1] + tablero[2][0] == 15)) {

            ganador = true;
            anotaciones.setText("Ganana PC");

        } else if ((tablero[0][0] + tablero[1][1] + tablero[2][2] == 3) || (tablero[0][2] + tablero[1][1] + tablero[2][0] == 3)) {

            ganador = true;
            anotaciones.setText("Gananaste!!");

        }

        if(ganador){
            anotaciones.setText("Gana PC");
            parar_todo();
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

        if(empate){
            anotaciones.setText("EMPATE!!");
            parar_todo();
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