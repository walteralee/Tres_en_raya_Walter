package com.example.tres_en_raya;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/*
 * Pantalla de juego para dos personas en el mismo móvil.
 * O es el círculo (1 en el tablero) y X la cruz (5 en el tablero).
 * Quién empieza se decide al azar en cada partida.
 */
public class contra_amigo extends AppCompatActivity {

    private static final int O = Reglas.FICHA_1;
    private static final int X = Reglas.FICHA_5;

    //Tiempo que se muestra el resultado antes de empezar otra partida
    private static final long ESPERA_NUEVA_PARTIDA_MS = 2000;

    private final Random r = new Random();

    //Botones del tablero en orden: la casilla i está en la fila i / 3 y la columna i % 3
    private final ImageButton[] botones = new ImageButton[9];

    private TextView anotaciones;

    private final int[][] tablero = new int[3][3];

    //Ficha del jugador al que le toca (O o X)
    private int turno;

    private CountDownTimer temporizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra_amigo);

        botones[0] = findViewById(R.id.boton_0);
        botones[1] = findViewById(R.id.boton_1);
        botones[2] = findViewById(R.id.boton_2);
        botones[3] = findViewById(R.id.boton_3);
        botones[4] = findViewById(R.id.boton_4);
        botones[5] = findViewById(R.id.boton_5);
        botones[6] = findViewById(R.id.boton_6);
        botones[7] = findViewById(R.id.boton_7);
        botones[8] = findViewById(R.id.boton_8);

        anotaciones = findViewById(R.id.text_notas);

        nueva_partida();
    }

    @Override
    protected void onDestroy() {
        //Si se sale de la pantalla no hace falta preparar la siguiente partida
        if (temporizador != null) {
            temporizador.cancel();
        }
        super.onDestroy();
    }

    private void nueva_partida() {

        Reglas.vaciar(tablero);

        for (ImageButton b : botones) {
            b.setEnabled(true);
            b.setImageResource(R.drawable.vacio);
        }

        turno = r.nextBoolean() ? O : X;
        mostrar_turno();
    }

    //Se llama desde el layout (android:onClick) al pulsar una casilla
    public void insertar_simbolo(View v) {

        int casilla = indice_de(v);

        if (casilla == -1 || tablero[casilla / 3][casilla % 3] != Reglas.VACIO) {
            return;
        }

        tablero[casilla / 3][casilla % 3] = turno;
        botones[casilla].setImageResource(turno == O ? R.drawable.circulo : R.drawable.cruz);
        botones[casilla].setEnabled(false);

        int ganador = Reglas.ganador(tablero);

        if (ganador != Reglas.VACIO) {
            anotaciones.setText(ganador == O ? "¡Ganan O!" : "¡Ganan X!");
            parar_todo();
        } else if (Reglas.estaLleno(tablero)) {
            anotaciones.setText("¡Empate!");
            parar_todo();
        } else {
            turno = (turno == O) ? X : O;
            mostrar_turno();
        }
    }

    private void mostrar_turno() {
        anotaciones.setText(turno == O ? "Juegan O" : "Juegan X");
    }

    //Devuelve el número de casilla (0-8) del botón pulsado
    private int indice_de(View v) {
        for (int i = 0; i < botones.length; i++) {
            if (botones[i] == v) {
                return i;
            }
        }
        return -1;
    }

    //Bloquea el tablero y empieza una partida nueva pasados unos segundos
    private void parar_todo() {

        for (ImageButton b : botones) {
            b.setEnabled(false);
        }

        temporizador = new CountDownTimer(ESPERA_NUEVA_PARTIDA_MS, ESPERA_NUEVA_PARTIDA_MS) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                nueva_partida();
            }
        };
        temporizador.start();
    }
}
