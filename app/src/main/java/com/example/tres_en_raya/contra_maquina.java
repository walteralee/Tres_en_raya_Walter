package com.example.tres_en_raya;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/*
 * Pantalla de juego contra la máquina.
 * El usuario juega con la cruz (1 en el tablero) y la máquina con el círculo (5 en el tablero).
 * Quién empieza se decide al azar en cada partida.
 */
public class contra_maquina extends AppCompatActivity {

    private static final int USUARIO = Reglas.FICHA_1;
    private static final int MAQUINA = Reglas.FICHA_5;

    //Tiempo que se muestra el resultado antes de empezar otra partida
    private static final long ESPERA_NUEVA_PARTIDA_MS = 2000;

    private final Maquina maquina = new Maquina();
    private final Random r = new Random();

    //Botones del tablero en orden: la casilla i está en la fila i / 3 y la columna i % 3
    private final ImageButton[] botones = new ImageButton[9];

    private TextView anotaciones;

    private final int[][] tablero = new int[3][3];

    private CountDownTimer temporizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contra_maquina);

        botones[0] = findViewById(R.id.boton_20);
        botones[1] = findViewById(R.id.boton_21);
        botones[2] = findViewById(R.id.boton_22);
        botones[3] = findViewById(R.id.boton_23);
        botones[4] = findViewById(R.id.boton_24);
        botones[5] = findViewById(R.id.boton_25);
        botones[6] = findViewById(R.id.boton_26);
        botones[7] = findViewById(R.id.boton_27);
        botones[8] = findViewById(R.id.boton_28);

        anotaciones = findViewById(R.id.text_notas_maquina);

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

        maquina.reiniciar();
        Reglas.vaciar(tablero);

        anotaciones.setText("Tu turno");

        for (ImageButton b : botones) {
            b.setEnabled(true);
            b.setImageResource(R.drawable.vacio);
        }

        //La mitad de las veces empieza la máquina
        if (r.nextBoolean()) {
            turno_maquina();
        }
    }

    //Se llama desde el layout (android:onClick) al pulsar una casilla
    public void turno_usuario(View v) {

        int casilla = indice_de(v);

        if (casilla == -1 || tablero[casilla / 3][casilla % 3] != Reglas.VACIO) {
            return;
        }

        marcar(casilla, USUARIO, R.drawable.cruz);

        if (!partida_terminada()) {
            turno_maquina();
        }
    }

    private void turno_maquina() {

        //La máquina devuelve {fila, columna} y ya deja su ficha puesta en el tablero
        int[] posicion = maquina.retorna_posicion(tablero);

        if (posicion[0] == -1) {
            return;
        }

        marcar(posicion[0] * 3 + posicion[1], MAQUINA, R.drawable.circulo);

        if (!partida_terminada()) {
            anotaciones.setText("Tu turno");
        }
    }

    //Pone la ficha en el tablero y su imagen en el botón
    private void marcar(int casilla, int ficha, int imagen) {
        tablero[casilla / 3][casilla % 3] = ficha;
        botones[casilla].setImageResource(imagen);
        botones[casilla].setEnabled(false);
    }

    //Comprueba si hay ganador o empate. Si la partida ha terminado lo muestra y prepara otra
    private boolean partida_terminada() {

        int ganador = Reglas.ganador(tablero);

        if (ganador == USUARIO) {
            anotaciones.setText("¡Has ganado!");
        } else if (ganador == MAQUINA) {
            anotaciones.setText("Gana la máquina");
        } else if (Reglas.estaLleno(tablero)) {
            anotaciones.setText("¡Empate!");
        } else {
            return false;
        }

        parar_todo();
        return true;
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
