package com.example.tres_en_raya;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//Tests de las reglas: quién gana y cuándo hay empate
public class ReglasTest {

    @Test
    public void tablero_vacio_no_tiene_ganador() {
        assertEquals(Reglas.VACIO, Reglas.ganador(new int[3][3]));
    }

    @Test
    public void detecta_ganador_en_horizontal() {
        int[][] t = {
                {1, 1, 1},
                {5, 5, 0},
                {0, 0, 0}};
        assertEquals(Reglas.FICHA_1, Reglas.ganador(t));
    }

    @Test
    public void detecta_ganador_en_vertical() {
        int[][] t = {
                {5, 1, 0},
                {5, 1, 0},
                {5, 0, 1}};
        assertEquals(Reglas.FICHA_5, Reglas.ganador(t));
    }

    @Test
    public void detecta_ganador_en_las_dos_diagonales() {
        int[][] diagonal = {
                {1, 5, 0},
                {5, 1, 0},
                {0, 0, 1}};
        int[][] diagonalInversa = {
                {1, 1, 5},
                {0, 5, 0},
                {5, 0, 1}};
        assertEquals(Reglas.FICHA_1, Reglas.ganador(diagonal));
        assertEquals(Reglas.FICHA_5, Reglas.ganador(diagonalInversa));
    }

    @Test
    public void dos_en_raya_no_es_ganar() {
        int[][] t = {
                {1, 1, 0},
                {5, 5, 0},
                {0, 0, 0}};
        assertEquals(Reglas.VACIO, Reglas.ganador(t));
    }

    @Test
    public void tablero_lleno_sin_ganador_es_empate() {
        int[][] t = {
                {1, 5, 1},
                {1, 5, 5},
                {5, 1, 1}};
        assertEquals(Reglas.VACIO, Reglas.ganador(t));
        assertTrue(Reglas.estaLleno(t));
    }

    @Test
    public void tablero_con_huecos_no_esta_lleno() {
        int[][] t = {
                {1, 5, 1},
                {1, 0, 5},
                {5, 1, 1}};
        assertFalse(Reglas.estaLleno(t));
    }

    @Test
    public void vaciar_deja_el_tablero_a_cero() {
        int[][] t = {
                {1, 5, 1},
                {1, 5, 5},
                {5, 1, 1}};
        Reglas.vaciar(t);
        assertEquals(Reglas.VACIO, Reglas.ganador(t));
        assertFalse(Reglas.estaLleno(t));
    }
}
