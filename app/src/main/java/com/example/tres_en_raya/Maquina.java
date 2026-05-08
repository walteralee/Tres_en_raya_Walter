package com.example.tres_en_raya;

import java.util.ArrayList;
import java.util.Random;

//NOTAS:

/*
 * El programa debe de funcionar de manera que cuando hayan multiples alternativas, devolverá
 * una de forma aleatoria
 *
 * De momento solo empieza en la esquina, pero posteriormente le debes dar la oportunidad a la
 * maquina de que empieze en centro y en medio
 *
 * Crear metodo que detecta trampa L larga
 */
//CONTADOR:

/*
 * 0   1  2  3
 *[01] 23 45 [67]
 *0{empiezo yo(en un principio seria esquina pero luego lo haré para centro y medio) | el(centro) | el(esquina) | el(medio)} 0 o 1 simbolo
 *1{}
 */
public class Maquina {

    //Atributos
    private int contador = 0;
    private boolean trampa_triangulo = false;
    private boolean trampa_L_larga = false;

    //Constructor por defecto
    public Maquina() {

    }

    //Retorna contador
    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int[] retorna_posicion(int[][] tablero) {
        int posicion[] = {-1,-1};
        int p = 0;

        //Primero comprobamos si hay alguna posicion que gane directamente
        if ((p = ganar(tablero)) != -1) {
            posicion = this.pasar_unidimencional_bidimencional(p);
            tablero[posicion[0]][posicion[1]] = 5;
            contador++;
        } //Si hay alguna posicion que tengamos que defender la defendemos
        else if ((p = defender(tablero)) != -1) {
            posicion = this.pasar_unidimencional_bidimencional(p);
            tablero[posicion[0]][posicion[1]] = 5;
            contador++;
        }//Si se puede hacer alguna estrategia se hace
        else if ((p = estrategia(tablero)) != -1) {
            posicion = this.pasar_unidimencional_bidimencional(p);
            tablero[posicion[0]][posicion[1]] = 5;
            contador++;
        }//Devuelve una posicion donde si se despista el adversario ganamos
        else if ((p = despiste(tablero)) != -1) {
            posicion = this.pasar_unidimencional_bidimencional(p);
            tablero[posicion[0]][posicion[1]] = 5;
            contador++;
        }//En caso de no cumplirse ninguna de las anteriores se devuelve la primera posicion posible
        else if ((p = aleatorio(tablero)) != -1) {
            posicion = this.pasar_unidimencional_bidimencional(p);
            tablero[posicion[0]][posicion[1]] = 5;
            contador++;
        }

        return posicion;
    }






















    /*
    [0][0][0]
    [0][1][0]
    [5][0][5]
     */
    //##########################################################################################################################################################
    //En este metodo lo que hacemos es detectar si hay una posicion que al llenarla ganamos, en caso de no haberla devolvemos un -1
    private int ganar(int[][] posicion) {
        int p = -1;

        int x = 10;
        int y = 10;

        //Horizontal
        for (int i = 0; i < 3; i++) {
            if (posicion[i][0] + posicion[i][1] + posicion[i][2] == 10) {
                x = i;

                for (int j = 0; j < 3; j++) {

                    if (posicion[i][j] != 5) {
                        y = j;
                    }

                }

            }
        }

        //Vertical
        for (int j = 0; j < 3; j++) {
            if (posicion[0][j] + posicion[1][j] + posicion[2][j] == 10) {
                y = j;

                for (int i = 0; i < 3; i++) {

                    if (posicion[i][j] != 5) {
                        x = i;
                    }

                }

            }
        }

        //Diagonal //00 11 22 | 02 11 20
        if (posicion[0][0] + posicion[1][1] + posicion[2][2] == 10) {

            for (int i = 0; i < 3; i++) {
                if (posicion[i][i] != 5) {
                    x = i;
                    y = i;
                }
            }

        } else if (posicion[0][2] + posicion[1][1] + posicion[2][0] == 10) {

            for (int i = 0, j = 2; i < 3; i++, j--) {
                if (posicion[i][j] != 5) {
                    x = i;
                    y = j;
                }
            }

        }

        p = (x * 3) + y;

        if (p > 10) {
            p = -1;
        }

        return p;
    }

    //En este metodo lo que hacemos es detectar si hay una posicion a defender
    private int defender(int[][] posicion) {
        int p = -1;

        int x = 10;
        int y = 10;

        //Horizontal
        for (int i = 0; i < 3; i++) {
            if (posicion[i][0] + posicion[i][1] + posicion[i][2] == 2) {
                x = i;

                for (int j = 0; j < 3; j++) {

                    if (posicion[i][j] != 1) {
                        y = j;
                    }

                }

            }
        }

        //Vertical
        for (int j = 0; j < 3; j++) {
            if (posicion[0][j] + posicion[1][j] + posicion[2][j] == 2) {
                y = j;

                for (int i = 0; i < 3; i++) {

                    if (posicion[i][j] != 1) {
                        x = i;
                    }

                }

            }
        }

        //Diagonal //00 11 22 | 02 11 20
        if (posicion[0][0] + posicion[1][1] + posicion[2][2] == 2) {

            for (int i = 0; i < 3; i++) {
                if (posicion[i][i] != 1) {
                    x = i;
                    y = i;
                }
            }

        } else if (posicion[0][2] + posicion[1][1] + posicion[2][0] == 2) {

            for (int i = 0, j = 2; i < 3; i++, j--) {
                if (posicion[i][j] != 1) {
                    x = i;
                    y = j;
                }
            }

        }

        p = (x * 3) + y;

        if (p > 10) {
            p = -1;
        }

        return p;
    }

    //Metodo que en caso de no cumplirse las anteriores (ganar y defender) devuelve una posicion que funciona como una estrategia mas avanzada
    private int estrategia(int[][] posicion) {
        int p = -1;

        if (contador == 0) {

            if (this.identificar_vacio_esquina_centro_medio_inicial(posicion) == 0) {
                p = this.esquina_aleatoria();
            } else if (this.identificar_vacio_esquina_centro_medio_inicial(posicion) == 1) {
                p = 4;
            } else if (this.identificar_vacio_esquina_centro_medio_inicial(posicion) == 2) {
                p = this.esquina_aleatoria();
            } else if (this.identificar_vacio_esquina_centro_medio_inicial(posicion) == 3) {
                p = 4;
            }

        } else if (contador == 1) {

            if ((this.detecta_trampa_L_corta(posicion)) != -1) {
                p = this.detecta_trampa_L_corta(posicion);
            }else if(detecta_trampa_L_larga(posicion) != -1){
                p = detecta_trampa_L_larga(posicion);
            }else if(detecta_trampa_triangulo(posicion) != -1){
                p = detecta_trampa_triangulo(posicion);
            }else if (aplicar_trampa_triangulo_primera(posicion)) {
                p = 4;
                this.trampa_triangulo = true;
            }else if(this.aplicar_trampa_L_larga_medio_primera(posicion) != -1){
                p = this.aplicar_trampa_L_larga_medio_primera(posicion);
                this.trampa_L_larga = true;
            }else if(aplicar_trampa_L_larga_centro(posicion) != -1){
                p = aplicar_trampa_L_larga_centro(posicion);
            }

        } else if (contador == 2) {

            if(this.trampa_triangulo){
                p = this.aplicar_trampa_triangulo_segunda(posicion);
            }if(this.trampa_L_larga){
                p = alplicar_trampa_L_larga_medio_segunda(posicion);
            }

        }

        return p;
    }

    //Metodo que en caso de no cumplirse las anteriores (ganar,defender y estrategia) devuelve una posicion donde solo se gana si el adversario no proteje
    private int despiste(int[][] posicion) {

        int p = -1;

        int x = 10;
        int y = 10;

        //Horizontal
        for (int i = 0; i < 3; i++) {
            if (posicion[i][0] + posicion[i][1] + posicion[i][2] == 5) {
                x = i;

                for (int j = 0; j < 3; j++) {

                    if (posicion[i][j] != 5) {
                        y = j;
                    }

                }

            }
        }

        //Vertical
        for (int j = 0; j < 3; j++) {
            if (posicion[0][j] + posicion[1][j] + posicion[2][j] == 5) {
                y = j;

                for (int i = 0; i < 3; i++) {

                    if (posicion[i][j] != 5) {
                        x = i;
                    }

                }

            }
        }

        //Diagonal //00 11 22 | 02 11 20
        if (posicion[0][0] + posicion[1][1] + posicion[2][2] == 5) {

            for (int i = 0; i < 3; i++) {
                if (posicion[i][i] != 5) {
                    x = i;
                    y = i;
                }
            }

        } else if (posicion[0][2] + posicion[1][1] + posicion[2][0] == 5) {

            for (int i = 0, j = 2; i < 3; i++, j--) {
                if (posicion[i][j] != 5) {
                    x = i;
                    y = j;
                }
            }

        }

        p = (x * 3) + y;

        if (p > 10) {
            p = -1;
        }

        return p;

    }

    //Si no se cumple ninguna de las anteriores (ganar,defender, estrategia y despiste) devuelve una posicion aleatoria
    private int aleatorio(int[][] posicion) {
        int p = -1;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas

                if (posicion[i][j] == 0) {
                    p = (i * 3) + j;
                }

            }
        }

        return p;

    }

    //####################################################################################################################################################



































    //Para cuando la maquina empieze en el centro o empieze el adversario
    //####################################################################################################################################################
    //POSICION INICIAL
    /*
    [00][01][02]
    [10][11][12]
    [20][21][22]
     */
    //Metodo que devuelve 0(vacio) 1(esquina) 2(centro) 3(medio)
    private int identificar_vacio_esquina_centro_medio_inicial(int[][] posicion) {
        int pi = 0;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas

                if (posicion[i][j] == 1) {

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










    //####################################################################################################################################################
    //POSICION SECUNDARIA

    //Metodo para detectar la trampa de L corta. Este lo usaremos en caso de que el adversario empieze primero
    //en el medio, lo pongamos en el centro y nos aplique dicha trama
    //[0][1][0]
    //[1][5][0]
    //[0][0][0]
    private int detecta_trampa_L_corta(int[][] posicion) {
        int ps = -1;

        if ((posicion[0][1] == 1) && (posicion[1][0] == 1)) {
            ps = 0;
        } else if ((posicion[0][1] == 1) && (posicion[1][2] == 1)) {
            ps = 2;
        } else if ((posicion[2][1] == 1) && (posicion[1][0] == 1)) {
            ps = 6;
        } else if ((posicion[2][1] == 1) && (posicion[1][2] == 1)) {
            ps = 8;
        }

        return ps;
    }

    //Metodo para detectar la trampa de L larga. Este lo usaremos en caso de que el adversario empieze primero
    //en una esquina, sigamos poniendola en el centro y este nos devuelva un 1 en la esquina mas lejana a su 1
    //[1][0][0]
    //[0][5][0]
    //[0][0][1]
    private int detecta_trampa_L_larga(int[][] posicion){
        int ps = -1;

        if(this.detecta_posicion_diagonal_1_5_1(posicion)){
            ps = this.medio_aleatorio();
        }

        return ps;
    }

    //Metodo para detectar la trampa de triangulo. Este lo usaremos en caso de que el adversario empieze primero
    //en el centro, sigamos poniendola en una esquina y este nos devuelva un 1 en la esquina mas lejana a nuestro 5
    //[1][0][0]
    //[0][1][0]
    //[0][0][5]
    private int detecta_trampa_triangulo(int[][] posicion){
        int ps = -1;

        if(detecta_posicion_diagonal_1_1_5(posicion)){
            ps = this.esquina_aleatoria_entre_2_opciones(posicion);
        }

        return ps;
    }

    //Metodo para ver si se puede aplicar la trampa del triangulo primera parte. Aqui se cumple cuando
    //empezamos primero, poniendola en el centro y el adversario comete el error de ponerla en unos de los
    //medios.
    //[5][0][0]
    //[1][5][0]
    //[0][0][0]
    private boolean aplicar_trampa_triangulo_primera(int[][] posicion) {
        boolean comprobar = false;

        if (this.contador_simbolos(posicion) == 2) {

            if (posicion[0][0] == 5 | posicion[0][2] == 5 | posicion[2][0] == 5 | posicion[2][2] == 5) {

                if (posicion[0][1] == 1 | posicion[1][0] == 1 | posicion[1][2] == 1 | posicion[2][1] == 1) {
                    comprobar = true;
                }

            }

        }

        return comprobar;
    }

    //Metodo para aplicar la trampade de L larga aplicada a cuando la maquina juega inicialmente en una esquina y anfitrion responde en el medio.
    //Se devolvera una esquina aleatoria
    //[5][0][0]
    //[1][5][0]
    //[0][0][1]
    private int aplicar_trampa_L_larga_medio_primera(int[][] posicion){
        int ps = -1;
        int contador = -1;
        if(detectar_posicion_doble_esquina(posicion)){

            for (int i = 0; i < 3; i++) { // Recorrer filas
                for (int j = 0; j < 3; j++) { // Recorrer columnas

                    contador++;

                    if(posicion[i][j] == 0 && (i != 1 && j != 1)){

                        ps = contador;

                    }

                }
            }

        }

        return ps;
    }

    //Metodo para aplicar la trampade de L larga aplicada a cuando la maquina juega en una esquina incial y el anfitrion juega en el centro.
    //Se devolvera la posicion de esquina mas lejana
    //[5][0][0]
    //[0][1][0]
    //[0][0][5]
    private int aplicar_trampa_L_larga_centro(int[][] posicion){
        int ps = -1;

        if(detectar_posicion_esquina_centro(posicion)){

            if(posicion[0][0] == 5){
                ps = 8;
            }else if(posicion[0][2] == 5){
                ps = 6;
            }else if(posicion[2][0] == 5){
                ps = 2;
            }else if(posicion[2][2] == 5){
                ps = 0;
            }

        }

        return ps;
    }











    //####################################################################################################################################################
    //POSICION TERCIARIA
    //Metodo para aplicar la trampade triangulo segunda parte.
    //[5][0][0]
    //[1][5][0]
    //[0][0][1]
    private int aplicar_trampa_triangulo_segunda(int[][] posicion) {
        int pt = -1;

        if (posicion[0][0] == 5) {

            if (posicion[0][1] == 1) {
                pt = 6;
            } else if (posicion[1][0] == 1) {
                pt = 2;
            }

        } else if (posicion[0][2] == 5) {

            if (posicion[0][1] == 1) {
                pt = 8;
            } else if (posicion[1][2] == 1) {
                pt = 0;
            }

        } else if (posicion[2][0] == 5) {

            if (posicion[1][0] == 1) {
                pt = 8;
            } else if (posicion[2][1] == 1) {
                pt = 0;
            }

        } else if (posicion[2][2] == 5) {

            if (posicion[1][2] == 1) {
                pt = 6;
            } else if (posicion[2][1] == 1) {
                pt = 2;
            }

        }

        return pt;
    }
    private int alplicar_trampa_L_larga_medio_segunda(int[][] posicion){
        int ps = -1;

        int contador = -1;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas

                contador++;

                if(posicion[i][j] == 0 && (i != 1 && j != 1)){

                    ps = contador;

                }

            }
        }

        return ps;
    }

    //####################################################################################################################################################








































    //##########################################################################################################################################################
    //Aqui sencillamente le pasamos un String que contiene los posibles espacios a escojer
    //y devuelve uno aleatoriamente
    private int opcion_aleatoria(String opciones) {
        int p = 0;

        return p;
    }

    //Metodo que devuelve una esquina aleatoria entre las 4. {0,2,6,8}
    private int esquina_aleatoria() {
        int p = 0;

        int opciones[] = {0, 2, 6, 8};

        Random r = new Random();

        p = opciones[r.nextInt(4)];

        return p;
    }

    private int esquina_aleatoria_entre_2_opciones(int[][] posicion){
        int p = 0;

        ArrayList<Integer> opciones = new ArrayList<>();

        int contador = -1;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas
                contador++;

                if(posicion[i][j] == 0 && (i != 1 && j != 1)){
                    opciones.add(contador);
                }

            }
        }

        Random r = new Random();

        p = opciones.get(r.nextInt(2));

        return p;
    }

    //Metodo que devuelve un medio aleatorio entre los 4. {1,3,5,7}
    private int medio_aleatorio(){
        int p = 0;

        int opciones[] = {1, 3, 5, 7};

        Random r = new Random();

        p = opciones[r.nextInt(4)];

        return p;
    }

    private int medio_aleatorio_entre_2_opciones(){
        int p = 1;

        return p;
    }

    //Metodo al cual le pasamo una posicion de una dimencion y nos devuelve un array de 2 posiciones con los valores en matriz
    private int[] pasar_unidimencional_bidimencional(int p) {

        int posiciones[] = new int[2];

        int contador = 0;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas
                if (contador == p) {
                    posiciones[0] = i;
                    posiciones[1] = j;
                }

                contador++;
            }
        }

        return posiciones;

    }

    private int contador_simbolos(int[][] posicion) {
        int contador = 0;

        for (int i = 0; i < 3; i++) { // Recorrer filas
            for (int j = 0; j < 3; j++) { // Recorrer columnas

                if ((posicion[i][j] == 1) || (posicion[i][j] == 5)) {
                    contador++;
                }

            }
        }

        return contador;
    }
    /*
    [5][0][0]
    [0][0][0]
    [0][0][1]
     */
    private boolean detectar_posicion_doble_esquina(int[][] posicion){
        boolean comprobar = true;

        if(this.contador_simbolos(posicion) == 2){

            for (int i = 0; i < 3; i++) { // Recorrer filas
                for (int j = 0; j < 3; j++) { // Recorrer columnas

                    if ((posicion[i][j] == 5 || posicion[i][j] == 1) && (i == 1 || j == 1)){

                        comprobar = false;

                    }
                }
            }

        }else{
            comprobar = false;
        }

        return comprobar;
    }

    /*
    [5][0][0]
    [0][1][0]
    [0][0][0]
     */
    private boolean detectar_posicion_esquina_centro(int[][] posicion){
        boolean comprobar = true;

        if(this.contador_simbolos(posicion) == 2){

            for (int i = 0; i < 3; i++) { // Recorrer filas
                for (int j = 0; j < 3; j++) { // Recorrer columnas

                    if (posicion[i][j] == 5 && (i == 1 || j == 1)){

                        comprobar = false;

                    }else if(posicion[i][j] == 1 && (i != 1 && j != 1)){

                        comprobar = false;

                    }
                }
            }

        }else{
            comprobar = false;
        }

        return comprobar;
    }

    /*
    [1][0][0]
    [0][5][0]
    [0][0][1]
     */
    private boolean detecta_posicion_diagonal_1_5_1(int[][] posicion){
        boolean comprobar = false;

        if(posicion[1][1] == 5){

            if(posicion[0][0] == 1 && posicion[2][2] == 1){
                comprobar = true;
            }else if(posicion[0][2] == 1 && posicion[2][0] == 1){
                comprobar = true;
            }

        }

        return comprobar;
    }


    private boolean detecta_posicion_diagonal_1_1_5(int[][] posicion){
        boolean comprobar = false;

        if(posicion[1][1] == 1){

            if(posicion[0][0] == 1 && posicion[2][2] == 5){
                comprobar = true;
            }else if(posicion[0][0] == 5 && posicion[2][2] == 1){
                comprobar = true;
            }else if(posicion[0][2] == 5 && posicion[2][0] == 1){
                comprobar = true;
            }else if(posicion[0][2] == 1 && posicion[2][0] == 5){
                comprobar = true;
            }

        }

        return comprobar;
    }

    //####################################################################################################################################################


}
