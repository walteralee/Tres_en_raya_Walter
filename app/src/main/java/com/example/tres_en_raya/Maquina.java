package com.example.tres_en_raya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Inteligencia de la máquina para el modo "contra la máquina".
 *
 * En el tablero la máquina es el 5 y el adversario el 1 (ver Reglas).
 * Las casillas se numeran de 0 a 8:
 *   [0][1][2]
 *   [3][4][5]
 *   [6][7][8]
 *
 * En cada turno se prueba, por este orden:
 *   1. ganar:        si hay una casilla que gana la partida, se juega
 *   2. defender:     si el adversario gana en la siguiente jugada, se le tapa
 *   3. estrategia:   aperturas y trampas preparadas (esquina, centro, triángulo, L...)
 *   4. bifurcación:  si el adversario puede hacer una doble amenaza, se le corta
 *   5. despiste:     se juega en una línea propia para obligar al adversario a tapar
 *   6. aleatorio:    cualquier casilla libre
 * Cuando hay varias opciones igual de buenas se elige una al azar.
 */
public class Maquina {

    private static final int VACIO = Reglas.VACIO;
    private static final int ADVERSARIO = Reglas.FICHA_1;
    private static final int MAQUINA = Reglas.FICHA_5;

    private final Random r;

    //Número de fichas que ha puesto la máquina en la partida actual
    private int contador = 0;

    //Trampas que la máquina ha empezado a preparar y tiene que terminar en su tercera jugada
    private boolean trampa_triangulo = false;
    private boolean trampa_L_larga = false;

    public Maquina() {
        this(new Random());
    }

    //Constructor para los tests, con una semilla fija se repiten siempre las mismas jugadas
    public Maquina(Random r) {
        this.r = r;
    }

    //Deja la máquina lista para una partida nueva
    public void reiniciar() {
        contador = 0;
        trampa_triangulo = false;
        trampa_L_larga = false;
    }

    public int getContador() {
        return contador;
    }

    //Elige la jugada de la máquina, la pone en el tablero y devuelve {fila, columna}.
    //Si no queda ninguna casilla libre devuelve {-1, -1}
    public int[] retorna_posicion(int[][] tablero) {
        //Cada paso se calcula una sola vez (algunos eligen al azar) y se descarta si la casilla no está libre
        int p = ganar(tablero);

        if (!es_libre(tablero, p)) {
            p = defender(tablero);
        }
        if (!es_libre(tablero, p)) {
            p = estrategia(tablero);
        }
        if (!es_libre(tablero, p)) {
            p = bloquear_bifurcacion(tablero);
        }
        if (!es_libre(tablero, p)) {
            p = despiste(tablero);
        }
        if (!es_libre(tablero, p)) {
            p = aleatorio(tablero);
        }

        if (p == -1) {
            return new int[]{-1, -1};
        }

        tablero[p / 3][p % 3] = MAQUINA;
        contador++;

        return new int[]{p / 3, p % 3};
    }

    //####################################################################################################
    //PASOS DE LA JUGADA

    //Casilla que completa una línea con dos fichas de la máquina (suma 10 = 5 + 5 + 0) o -1
    private int ganar(int[][] tablero) {
        return casilla_para_completar(tablero, 2 * MAQUINA);
    }

    //Casilla que tapa una línea con dos fichas del adversario (suma 2 = 1 + 1 + 0) o -1
    private int defender(int[][] tablero) {
        return casilla_para_completar(tablero, 2 * ADVERSARIO);
    }

    //Aperturas y trampas según la jugada en la que va la máquina. Devuelve -1 si no aplica ninguna
    private int estrategia(int[][] tablero) {
        int p = -1;

        if (contador == 0) {

            //Primera jugada: si el adversario ha jugado en esquina o medio, al centro.
            //Si no ha jugado nadie o ha jugado en el centro, a una esquina
            int inicial = identificar_vacio_esquina_centro_medio_inicial(tablero);

            if (inicial == 1 || inicial == 3) {
                p = 4;
            } else {
                p = esquina_aleatoria();
            }

        } else if (contador == 1) {

            if ((p = detecta_trampa_L_corta(tablero)) != -1) {
                return p;
            }
            if ((p = detecta_trampa_L_larga(tablero)) != -1) {
                return p;
            }
            if ((p = detecta_trampa_triangulo(tablero)) != -1) {
                return p;
            }
            if (aplicar_trampa_triangulo_primera(tablero)) {
                trampa_triangulo = true;
                return 4;
            }
            if ((p = aplicar_trampa_L_larga_medio_primera(tablero)) != -1) {
                trampa_L_larga = true;
                return p;
            }
            p = aplicar_trampa_L_larga_centro(tablero);

        } else if (contador == 2) {

            if (trampa_L_larga) {
                p = aplicar_trampa_L_larga_medio_segunda(tablero);
            } else if (trampa_triangulo) {
                p = aplicar_trampa_triangulo_segunda(tablero);
            }

        }

        return p;
    }

    //Si el adversario tiene una casilla con la que haría dos amenazas a la vez (bifurcación),
    //busca una jugada que lo evite: o se ocupa esa casilla, o se le obliga a tapar en otra parte
    //sin que al tapar consiga él la doble amenaza. Devuelve -1 si no hay bifurcación que cortar
    private int bloquear_bifurcacion(int[][] tablero) {

        if (casillas_bifurcacion(tablero, ADVERSARIO).isEmpty()) {
            return -1;
        }

        List<Integer> opciones = new ArrayList<>();

        for (int c = 0; c < 9; c++) {
            if (!es_libre(tablero, c)) {
                continue;
            }

            int[][] copia = copiar(tablero);
            copia[c / 3][c % 3] = MAQUINA;

            int tapar = casilla_para_completar(copia, 2 * MAQUINA);

            if (tapar != -1) {
                //La jugada obliga al adversario a tapar: vale si al tapar no hace dos amenazas
                copia[tapar / 3][tapar % 3] = ADVERSARIO;
                if (amenazas(copia, ADVERSARIO) < 2) {
                    opciones.add(c);
                }
            } else if (casillas_bifurcacion(copia, ADVERSARIO).isEmpty()) {
                //La jugada no obliga a nada, pero ya no le deja ninguna bifurcación
                opciones.add(c);
            }
        }

        return elegir(opciones);
    }

    //Casilla en una línea donde solo está la máquina (suma 5 = 5 + 0 + 0), para que el adversario
    //tenga que tapar o pierda. Devuelve -1 si no hay ninguna
    private int despiste(int[][] tablero) {
        return casilla_libre_en_linea(tablero, MAQUINA);
    }

    //Última casilla libre del tablero o -1 si está lleno
    private int aleatorio(int[][] tablero) {
        int p = -1;

        for (int c = 0; c < 9; c++) {
            if (es_libre(tablero, c)) {
                p = c;
            }
        }

        return p;
    }

    //####################################################################################################
    //PRIMERA JUGADA

    //Devuelve 0 (tablero vacío), 1 (el adversario está en una esquina), 2 (en el centro) o 3 (en un medio)
    private int identificar_vacio_esquina_centro_medio_inicial(int[][] tablero) {
        int pi = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (tablero[i][j] == ADVERSARIO) {

                    if ((i != 1) && (j != 1)) {
                        pi = 1;
                    } else if ((i == 1) && (j == 1)) {
                        pi = 2;
                    } else {
                        pi = 3;
                    }

                }
            }
        }

        return pi;
    }

    //####################################################################################################
    //SEGUNDA JUGADA

    //Detecta la trampa de L corta: el adversario empezó en un medio, la máquina en el centro
    //y el adversario juega en otro medio pegado. Se tapa la esquina entre los dos
    //[0][1][0]
    //[1][5][0]
    //[0][0][0]
    private int detecta_trampa_L_corta(int[][] tablero) {
        int ps = -1;

        if ((tablero[0][1] == ADVERSARIO) && (tablero[1][0] == ADVERSARIO)) {
            ps = 0;
        } else if ((tablero[0][1] == ADVERSARIO) && (tablero[1][2] == ADVERSARIO)) {
            ps = 2;
        } else if ((tablero[2][1] == ADVERSARIO) && (tablero[1][0] == ADVERSARIO)) {
            ps = 6;
        } else if ((tablero[2][1] == ADVERSARIO) && (tablero[1][2] == ADVERSARIO)) {
            ps = 8;
        }

        return ps;
    }

    //Detecta la trampa de L larga: el adversario empezó en una esquina, la máquina en el centro
    //y el adversario juega en la esquina opuesta. Hay que jugar en un medio, nunca en esquina
    //[1][0][0]
    //[0][5][0]
    //[0][0][1]
    private int detecta_trampa_L_larga(int[][] tablero) {
        int ps = -1;

        if (detecta_posicion_diagonal_1_5_1(tablero)) {
            ps = medio_aleatorio();
        }

        return ps;
    }

    //Detecta la trampa del triángulo: el adversario empezó en el centro, la máquina en una esquina
    //y el adversario juega en la esquina opuesta a la de la máquina. Se juega en otra esquina
    //[1][0][0]
    //[0][1][0]
    //[0][0][5]
    private int detecta_trampa_triangulo(int[][] tablero) {
        int ps = -1;

        if (detecta_posicion_diagonal_1_1_5(tablero)) {
            ps = esquina_libre_aleatoria(tablero);
        }

        return ps;
    }

    //Primera parte de la trampa del triángulo: la máquina empezó en una esquina
    //y el adversario respondió en un medio. La máquina juega en el centro
    //[5][0][0]
    //[1][5][0]
    //[0][0][0]
    private boolean aplicar_trampa_triangulo_primera(int[][] tablero) {
        boolean comprobar = false;

        if (contador_simbolos(tablero) == 2) {

            if (tablero[0][0] == MAQUINA || tablero[0][2] == MAQUINA || tablero[2][0] == MAQUINA || tablero[2][2] == MAQUINA) {

                if (tablero[0][1] == ADVERSARIO || tablero[1][0] == ADVERSARIO || tablero[1][2] == ADVERSARIO || tablero[2][1] == ADVERSARIO) {
                    comprobar = true;
                }

            }
        }

        return comprobar;
    }

    //Trampa de L larga cuando la máquina empezó en una esquina y el adversario respondió en otra esquina.
    //Se juega en una de las esquinas libres
    //[5][0][0]
    //[0][0][0]
    //[0][0][1]
    private int aplicar_trampa_L_larga_medio_primera(int[][] tablero) {
        int ps = -1;

        if (detectar_posicion_doble_esquina(tablero)) {
            ps = ultima_esquina_libre(tablero);
        }

        return ps;
    }

    //Trampa de L larga cuando la máquina empezó en una esquina y el adversario respondió en el centro.
    //Se juega en la esquina opuesta a la de la máquina
    //[5][0][0]
    //[0][1][0]
    //[0][0][5]
    private int aplicar_trampa_L_larga_centro(int[][] tablero) {
        int ps = -1;

        if (detectar_posicion_esquina_centro(tablero)) {

            if (tablero[0][0] == MAQUINA) {
                ps = 8;
            } else if (tablero[0][2] == MAQUINA) {
                ps = 6;
            } else if (tablero[2][0] == MAQUINA) {
                ps = 2;
            } else if (tablero[2][2] == MAQUINA) {
                ps = 0;
            }

        }

        return ps;
    }

    //####################################################################################################
    //TERCERA JUGADA

    //Segunda parte de la trampa del triángulo: se juega en la esquina que deja dos amenazas a la vez
    //[5][0][0]
    //[1][5][0]
    //[0][0][1]
    private int aplicar_trampa_triangulo_segunda(int[][] tablero) {
        int pt = -1;

        if (tablero[0][0] == MAQUINA) {

            if (tablero[0][1] == ADVERSARIO) {
                pt = 6;
            } else if (tablero[1][0] == ADVERSARIO) {
                pt = 2;
            }

        } else if (tablero[0][2] == MAQUINA) {

            if (tablero[0][1] == ADVERSARIO) {
                pt = 8;
            } else if (tablero[1][2] == ADVERSARIO) {
                pt = 0;
            }

        } else if (tablero[2][0] == MAQUINA) {

            if (tablero[1][0] == ADVERSARIO) {
                pt = 8;
            } else if (tablero[2][1] == ADVERSARIO) {
                pt = 0;
            }

        } else if (tablero[2][2] == MAQUINA) {

            if (tablero[1][2] == ADVERSARIO) {
                pt = 6;
            } else if (tablero[2][1] == ADVERSARIO) {
                pt = 2;
            }

        }

        return pt;
    }

    //Segunda parte de la trampa de L larga: se juega en la última esquina libre
    private int aplicar_trampa_L_larga_medio_segunda(int[][] tablero) {
        return ultima_esquina_libre(tablero);
    }

    //####################################################################################################
    //AYUDAS

    //Busca una línea cuya suma sea la indicada (dos fichas iguales y un hueco) y devuelve el hueco o -1
    private int casilla_para_completar(int[][] tablero, int suma) {
        List<Integer> opciones = new ArrayList<>();

        for (int[] linea : Reglas.LINEAS) {
            if (Reglas.sumaLinea(tablero, linea) == suma) {
                for (int c : linea) {
                    if (es_libre(tablero, c) && !opciones.contains(c)) {
                        opciones.add(c);
                    }
                }
            }
        }

        return elegir(opciones);
    }

    //Devuelve una casilla libre de una línea donde solo hay una ficha, la del jugador indicado, o -1
    private int casilla_libre_en_linea(int[][] tablero, int ficha) {
        List<Integer> opciones = new ArrayList<>();

        for (int[] linea : Reglas.LINEAS) {
            if (Reglas.sumaLinea(tablero, linea) == ficha) {
                for (int c : linea) {
                    if (es_libre(tablero, c) && !opciones.contains(c)) {
                        opciones.add(c);
                    }
                }
            }
        }

        return elegir(opciones);
    }

    //Número de líneas en las que el jugador tiene dos fichas y un hueco (gana en la siguiente jugada)
    private int amenazas(int[][] tablero, int ficha) {
        int total = 0;

        for (int[] linea : Reglas.LINEAS) {
            if (Reglas.sumaLinea(tablero, linea) == 2 * ficha) {
                total++;
            }
        }

        return total;
    }

    //Casillas libres en las que el jugador indicado conseguiría dos amenazas a la vez
    private List<Integer> casillas_bifurcacion(int[][] tablero, int ficha) {
        List<Integer> casillas = new ArrayList<>();

        for (int c = 0; c < 9; c++) {
            if (es_libre(tablero, c)) {
                int[][] copia = copiar(tablero);
                copia[c / 3][c % 3] = ficha;

                if (amenazas(copia, ficha) >= 2) {
                    casillas.add(c);
                }
            }
        }

        return casillas;
    }

    private boolean es_libre(int[][] tablero, int p) {
        return p >= 0 && p <= 8 && tablero[p / 3][p % 3] == VACIO;
    }

    //Devuelve una opción al azar de la lista o -1 si está vacía
    private int elegir(List<Integer> opciones) {
        if (opciones.isEmpty()) {
            return -1;
        }
        return opciones.get(r.nextInt(opciones.size()));
    }

    private int[][] copiar(int[][] tablero) {
        int[][] copia = new int[3][];
        for (int i = 0; i < 3; i++) {
            copia[i] = tablero[i].clone();
        }
        return copia;
    }

    //Una esquina al azar entre las 4: {0, 2, 6, 8}
    private int esquina_aleatoria() {
        int[] opciones = {0, 2, 6, 8};
        return opciones[r.nextInt(opciones.length)];
    }

    //Un medio al azar entre los 4: {1, 3, 5, 7}
    private int medio_aleatorio() {
        int[] opciones = {1, 3, 5, 7};
        return opciones[r.nextInt(opciones.length)];
    }

    //Una esquina libre al azar o -1 si están todas ocupadas
    private int esquina_libre_aleatoria(int[][] tablero) {
        List<Integer> opciones = new ArrayList<>();

        for (int c : new int[]{0, 2, 6, 8}) {
            if (es_libre(tablero, c)) {
                opciones.add(c);
            }
        }

        return elegir(opciones);
    }

    //La última esquina libre recorriendo el tablero en orden o -1
    private int ultima_esquina_libre(int[][] tablero) {
        int ps = -1;

        for (int c : new int[]{0, 2, 6, 8}) {
            if (es_libre(tablero, c)) {
                ps = c;
            }
        }

        return ps;
    }

    //Número de fichas que hay en el tablero
    private int contador_simbolos(int[][] tablero) {
        int total = 0;

        for (int[] fila : tablero) {
            for (int casilla : fila) {
                if (casilla != VACIO) {
                    total++;
                }
            }
        }

        return total;
    }

    //Hay dos fichas y las dos están en esquinas
    //[5][0][0]
    //[0][0][0]
    //[0][0][1]
    private boolean detectar_posicion_doble_esquina(int[][] tablero) {

        if (contador_simbolos(tablero) != 2) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] != VACIO && (i == 1 || j == 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    //Hay dos fichas: la de la máquina en una esquina y la del adversario en el centro
    //[5][0][0]
    //[0][1][0]
    //[0][0][0]
    private boolean detectar_posicion_esquina_centro(int[][] tablero) {

        if (contador_simbolos(tablero) != 2) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == MAQUINA && (i == 1 || j == 1)) {
                    return false;
                } else if (tablero[i][j] == ADVERSARIO && (i != 1 && j != 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    //La máquina en el centro y el adversario en dos esquinas opuestas
    //[1][0][0]
    //[0][5][0]
    //[0][0][1]
    private boolean detecta_posicion_diagonal_1_5_1(int[][] tablero) {

        if (tablero[1][1] != MAQUINA) {
            return false;
        }

        return (tablero[0][0] == ADVERSARIO && tablero[2][2] == ADVERSARIO)
                || (tablero[0][2] == ADVERSARIO && tablero[2][0] == ADVERSARIO);
    }

    //El adversario en el centro y en una esquina, y la máquina en la esquina opuesta
    //[1][0][0]
    //[0][1][0]
    //[0][0][5]
    private boolean detecta_posicion_diagonal_1_1_5(int[][] tablero) {

        if (tablero[1][1] != ADVERSARIO) {
            return false;
        }

        return (tablero[0][0] == ADVERSARIO && tablero[2][2] == MAQUINA)
                || (tablero[0][0] == MAQUINA && tablero[2][2] == ADVERSARIO)
                || (tablero[0][2] == MAQUINA && tablero[2][0] == ADVERSARIO)
                || (tablero[0][2] == ADVERSARIO && tablero[2][0] == MAQUINA);
    }
}
