package com.example.tres_en_raya;

/*
 * Reglas del tres en raya, sin nada de Android para poder probarlas con tests.
 *
 * El tablero es una matriz 3x3 de enteros:
 *   0 -> casilla vacía
 *   1 -> ficha de un jugador
 *   5 -> ficha del otro jugador
 *
 * Se usan 1 y 5 porque así la suma de una línea dice qué hay en ella:
 *   3 = tres fichas de 1, 15 = tres fichas de 5, 2 = dos de 1 y un hueco, 10 = dos de 5 y un hueco...
 */
public final class Reglas {

    public static final int VACIO = 0;
    public static final int FICHA_1 = 1;
    public static final int FICHA_5 = 5;

    //Las 8 líneas ganadoras, con las casillas numeradas de 0 a 8:
    //[0][1][2]
    //[3][4][5]
    //[6][7][8]
    public static final int[][] LINEAS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //Horizontales
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //Verticales
            {0, 4, 8}, {2, 4, 6}             //Diagonales
    };

    private Reglas() {
    }

    //Devuelve la ficha que ha hecho tres en raya (1 o 5) o 0 si todavía no ha ganado nadie
    public static int ganador(int[][] tablero) {
        for (int[] linea : LINEAS) {
            int suma = sumaLinea(tablero, linea);

            if (suma == 3 * FICHA_1) {
                return FICHA_1;
            } else if (suma == 3 * FICHA_5) {
                return FICHA_5;
            }
        }

        return VACIO;
    }

    //Devuelve true si no queda ninguna casilla vacía
    public static boolean estaLleno(int[][] tablero) {
        for (int[] fila : tablero) {
            for (int casilla : fila) {
                if (casilla == VACIO) {
                    return false;
                }
            }
        }

        return true;
    }

    //Suma de las tres casillas de una línea
    public static int sumaLinea(int[][] tablero, int[] linea) {
        int suma = 0;

        for (int casilla : linea) {
            suma += tablero[casilla / 3][casilla % 3];
        }

        return suma;
    }

    //Vacía el tablero para empezar una partida nueva
    public static void vaciar(int[][] tablero) {
        for (int[] fila : tablero) {
            java.util.Arrays.fill(fila, VACIO);
        }
    }
}
