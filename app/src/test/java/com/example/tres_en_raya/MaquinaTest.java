package com.example.tres_en_raya;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.Random;

//Tests de la máquina. Ella juega con 5 y el adversario con 1
public class MaquinaTest {

    private static final int MAQUINA = Reglas.FICHA_5;
    private static final int ADVERSARIO = Reglas.FICHA_1;

    @Test
    public void gana_si_puede() {
        int[][] t = {
                {5, 5, 0},
                {1, 1, 0},
                {1, 0, 0}};
        int[] jugada = new Maquina(new Random(1)).retorna_posicion(t);

        assertArrayEquals(new int[]{0, 2}, jugada);
        assertEquals(MAQUINA, Reglas.ganador(t));
    }

    @Test
    public void prefiere_ganar_antes_que_defender() {
        int[][] t = {
                {1, 1, 0},
                {5, 5, 0},
                {1, 0, 0}};
        int[] jugada = new Maquina(new Random(1)).retorna_posicion(t);

        assertArrayEquals(new int[]{1, 2}, jugada);
    }

    @Test
    public void defiende_si_el_adversario_va_a_ganar() {
        int[][] t = {
                {1, 0, 0},
                {0, 1, 0},
                {5, 0, 0}};
        int[] jugada = new Maquina(new Random(1)).retorna_posicion(t);

        assertArrayEquals(new int[]{2, 2}, jugada);
    }

    @Test
    public void con_el_tablero_lleno_no_juega() {
        int[][] t = {
                {1, 5, 1},
                {1, 5, 5},
                {5, 1, 1}};
        int[] jugada = new Maquina(new Random(1)).retorna_posicion(t);

        assertArrayEquals(new int[]{-1, -1}, jugada);
    }

    @Test
    public void reiniciar_pone_el_contador_a_cero() {
        Maquina m = new Maquina(new Random(1));
        m.retorna_posicion(new int[3][3]);
        assertEquals(1, m.getContador());

        m.reiniciar();
        assertEquals(0, m.getContador());
    }

    //Juega todas las partidas posibles del adversario (empezando él y empezando la máquina)
    //con varias semillas, y comprueba que la máquina nunca pisa una casilla ocupada ni pierde
    @Test
    public void nunca_pierde_ni_juega_en_casilla_ocupada() {
        for (int semilla = 0; semilla < 30; semilla++) {
            explorar(semilla, "", true);
            explorar(semilla, "", false);
        }
    }

    //Repite desde cero la secuencia de jugadas del adversario para que la máquina tenga su estado real
    private void explorar(int semilla, String jugadasAdversario, boolean empiezaMaquina) {
        int[][] t = new int[3][3];
        Maquina m = new Maquina(new Random(semilla));

        if (empiezaMaquina) {
            jugar_maquina(m, t, jugadasAdversario);
        }

        for (char c : jugadasAdversario.toCharArray()) {
            int casilla = c - '0';
            t[casilla / 3][casilla % 3] = ADVERSARIO;

            assertNotEquals("La máquina pierde con: " + jugadasAdversario, ADVERSARIO, Reglas.ganador(t));
            if (Reglas.ganador(t) != Reglas.VACIO || Reglas.estaLleno(t)) {
                return;
            }

            jugar_maquina(m, t, jugadasAdversario);
            if (Reglas.ganador(t) != Reglas.VACIO || Reglas.estaLleno(t)) {
                return;
            }
        }

        //La partida sigue: se prueba cada casilla libre como siguiente jugada del adversario
        for (int casilla = 0; casilla < 9; casilla++) {
            if (t[casilla / 3][casilla % 3] == Reglas.VACIO) {
                explorar(semilla, jugadasAdversario + casilla, empiezaMaquina);
            }
        }
    }

    private void jugar_maquina(Maquina m, int[][] t, String jugadasAdversario) {
        int[][] antes = new int[3][];
        for (int i = 0; i < 3; i++) {
            antes[i] = t[i].clone();
        }

        int[] jugada = m.retorna_posicion(t);

        if (jugada[0] == -1) {
            fail("La máquina no juega teniendo casillas libres: " + jugadasAdversario);
        }
        assertEquals("La máquina juega en una casilla ocupada: " + jugadasAdversario,
                Reglas.VACIO, antes[jugada[0]][jugada[1]]);
    }
}
