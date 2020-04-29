/*
* Representa la mesa de juego, donde estarán todas las cartas.
* Tendrá dos partes diferenciadas:
* - la parte interior, donde inicialmente estarán colocadas las cartas correctamente para jugar al solitario
* - los montones exteriores, donde estarán colocadas las cartas por palo ordenadas de menor a mayor
* Estructura: Se utilizarán TADs adecuados para su respresentación. En concreto:
* - Una matriz de Pilas para representar la parte o montón interior 
* - Un array de Pilas para representar los montones exteriores.
* Funcionalidad: colocar las cartas para iniciar el juego, quitar una carta de la parte interior, colocar una carta en el interior,
* colocar una carta en el montón exterior correspondiente, visualizar cartas en la mesa, etc

La Pila es una estructura de datos que existe en Java y que se corresponde con la clase Stack. Por lo tanto debereis hacer uso de dicha
clase para representar la mesa de juego, y en particular de los métodos que se indican a continuación (de ser necesarios):

        public boolean empty();
        // Produce: Si la pila está vacía devuelve true, sino false.
        public Carta peek();
        // Produce: Devuelve la Carta del tope de la pila, sin eliminarla de ella.
        public Carta pop();
        // Produce: Elimina la Carta del tope de la pila y la devuelve.
        public void push(Carta item);
        // Produce: Introduce la Carta en el tope de la pila.
 */
package solitario.Core;

import java.util.Stack;

/**
 *
 * @author AEDI
 */
public class Mesa {

    public static Stack<Carta>[] montonExterior; // 4 montones ( los 4 palos) 
    public static Stack<Carta>[][] montonInterior;  // Una matriz

    public Mesa() {
        //Inicializa el monton exterior
        montonExterior = new Stack[4];

        //En el tablero del jugador (Exterior) máximo 10 cartas por Stack/Palo 
        for (int i = 0; i < montonExterior.length; i++) { // No se ponen números directamente, se ponen de donde salen ( por si se cambia el código en un futuro o loquesea)
            montonExterior[i] = new Stack<>(); //Almacena los stacks en el array de montonExterior
        }

        //Inicializa el monton interior
        montonInterior = new Stack[4][4];
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col] = new Stack<>(); // Crea espacio en la mesa
            }
        }
    }

    //Coloca las cartas en el monton interior: 2 cartas por stack y los stacks
    //de las diagonales 3 cartas
    public void colocarCartas(Baraja b) throws Exception { // Puede lanzar excepción si no hay cartas ( ver clase baraja)
        // Método especificado por Rosalía 

        // 1º Paso : Repartir 16 primeras cartas boca abajo
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Colocar en posición carta
            }
        }
        //2º Paso : Repartir cartas en las 2 diagonales
        //Diagonal principal
        for (int posicion = 0; posicion < montonInterior.length; posicion++) {
            montonInterior[posicion][posicion].push(b.sacarCarta()); // La diagonal principal coincide en (0,0) (1,1) (2,2) (3,3)
            montonInterior[posicion][montonInterior.length - posicion - 1].push(b.sacarCarta()); // La diagonal secundaria coincide en (0,3) (1,2) (2,1) (3,0)
        }

        //3º Paso : Repartir 16 últimas cartas boca arriba
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Colocar en posición carta
            }
        }
    }

    //Muestra por pantalla las cartas que hay visibles en la mesa
    public void mostrarMesa() {
        // Creamos una representación para carta inexistente
        String cartaInexistente = "[--|-]";

        // Mostramos la última carta de los montones exteriores     
        System.out.println("Montón Exterior:\n");
        for (Stack<Carta> monton : montonExterior) {// Recorremos los palos
            if (monton.empty()) { // Si un montón está vacio , se muestra representación por defecto carta inexistente
                System.out.print(cartaInexistente);
            } else {
                System.out.print(monton.peek().toString()); // Mostramos la representación de la carta (clase carta método toString)
            }
            System.out.print(" "); // Separacion horizontal
        }

        // Mostramos la última carta de los montones exteriores     
        System.out.println("\n\nMontón Interior:\n");
        for (Stack[] posicion : montonInterior) {// Recorremos posiciones de montón Interior
            for (Stack<Carta> monton : posicion) { // Accedemos a las cartas de cada posición
                if (monton.empty()) {
                    System.out.print(cartaInexistente);
                } else {
                    System.out.print(monton.peek().toString()); // Mostramos la representación de la carta (clase carta método toString)
                }
                System.out.print(" "); // Separacion horizontal
            }
            System.out.println(); // Separación vertical
        }
    }

    //Comprueba si los montones exteriores han sido completados
    public boolean MontonExteriorCompleto() {
        boolean flag = true;
        //comprueba cada stack del monton
        for (Stack<Carta> stack : montonExterior) {
            if (stack.size() != 10) {
                flag = false; // Si hay algún montón que no tenga 10 cartas significa que los montones exteriores no están llenos
            }
        }
        return flag;
    }

//Metodo que verifica si existe algun movimiento posible
    //si existe devuelve true y si no existe devuelve false
    /* public boolean finDelJuego(){
        boolean hayMovimientos=false;
        //Carta en evaluacion
        for (int i = 0; i < 4 && !hayMovimientos; i++) {
            for (int j = 0; j < 4 && !hayMovimientos; j++) {
                //Cartas a evaluar
                for (int k = 0; k < 5 && !hayMovimientos; k++) {
                    for (int l = 0; l < 4 && !hayMovimientos; l++) {
                        
                        //Verificacion de Movimientos interiores
                        if(k!=4){
                            //Se ignora la misma posicion y si alguna de ambas esta vacia
                            if((i!=k || j!=l) && (!montonInterior[i][j].empty() && !montonInterior[k][l].empty())){
                                //Si son del mismo palo
                                if(montonInterior[i][j].peek().getPalo()==montonInterior[k][l].peek().getPalo()){
                                    //Si son contiguas
                                    if((montonInterior[i][j].peek().getNumero() == 7 && montonInterior[k][l].peek().getNumero() == 10)  
                                        || (montonInterior[k][l].peek().getNumero() - montonInterior[i][j].peek().getNumero() == 1)){
                                        hayMovimientos=true;
                                    //Si no son contiguas
                                    }else{
                                        //Se comprueba si se pueden mover varias cartas apiladas
                                        Stack<Carta> aux1 = new Stack<Carta>();
                                        Stack<Carta> aux2 = new Stack<Carta>();
                                        aux1.push(montonInterior[i][j].pop());
                                        while(!(montonInterior[i][j].empty()) &&((aux1.peek().getNumero() == 7 && montonInterior[i][j].peek().getNumero() == 10)  
                                            || (montonInterior[i][j].peek().getNumero() - aux1.peek().getNumero() == 1))
                                            && (montonInterior[i][j].peek().isBocaArriba()) && (montonInterior[i][j].peek().getPalo() == aux1.peek().getPalo())){
                                    
                                                aux1.push(montonInterior[i][j].pop());
                                        }
                                        if((aux1.peek().getNumero() == 7 && montonInterior[k][l].peek().getNumero() == 10)  
                                            || (montonInterior[k][l].peek().getNumero() - aux1.peek().getNumero() == 1)){
                                                hayMovimientos=true;
                                        }
                                        while(!aux1.empty()){
                                            montonInterior[i][j].push(aux1.pop());
                                        } 
                                    }
                                }   
                            }
                        }
                        
                        //Verificacion de Movimientos exteriores
                        else{
                            //Se ignora la misma posicion y si la del monton interior esta vacia
                            if(!montonInterior[i][j].empty()){
                                //Si el monton exterior esta vacio
                                if(montonExterior[l].empty()){
                                    //Si el primer elemento del monton interior es igual a uno
                                    if(montonInterior[i][j].peek().getNumero()==1)
                                        hayMovimientos=true;
                                //si el monton exterior no esta vacio
                                }else{
                                //Si son del mismo palo
                                    if(montonInterior[i][j].peek().getPalo()==montonExterior[l].peek().getPalo()){
                                        //Si son contiguas
                                        if((montonInterior[i][j].peek().getNumero() == 10 && montonExterior[l].peek().getNumero() == 7)  
                                            || (montonInterior[i][j].peek().getNumero() - montonExterior[l].peek().getNumero() == 1)){
                                            hayMovimientos=true;
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        return hayMovimientos;
    }*/
    /*public boolean movPosibles() {
        boolean movimientos = false;
        int a = 0;//i
        int b = 0;//j
        int c = 0; //k
        int d = 0;//l

        Carta x = montonInterior[a][b].peek();
        Carta comp = montonInterior[a][b].peek();

        for (a = 0; a < montonInterior.length && !movimientos; a++) { // Recorre fila
            for (b = 0; b < montonInterior[a].length && !movimientos; b++) { //Recorre columna de fila actual
                for (c = 0; c < 5 && !movimientos; c++) {
                    for (d = 0; d < 4 && !movimientos; d++) {
                        //Verificacion de Movimientos interiores
                        if (c != 4) {
                            //Se ignora la misma posicion y si alguna de ambas esta vacia
                            if ((a != c || b != d) && (!montonInterior[a][b].empty() && !montonInterior[c][d].empty())) {
                                //Si son del mismo palo
                                if (montonInterior[a][b].peek().getPalo() == montonInterior[c][d].peek().getPalo()) {
                                    //Si son contiguas
                                    if ((montonInterior[a][b].peek().getNumero() == 7 && montonInterior[c][d].peek().getNumero() == 10)
                                            || (montonInterior[c][d].peek().getNumero() - montonInterior[a][b].peek().getNumero() == 1)) {
                                        movimientos = true;
                                        //Si no son contiguas
                                    } else {
                                        //Se comprueba si se pueden mover varias cartas apiladas
                                        Stack<Carta> carta1 = new Stack<Carta>();
                                        Stack<Carta> carta2 = new Stack<Carta>();
                                        carta1.push(montonInterior[a][b].pop());
                                        while (!(montonInterior[a][b].empty()) && ((carta1.peek().getNumero() == 7 && montonInterior[a][b].peek().getNumero() == 10)
                                                || (montonInterior[a][b].peek().getNumero() - carta1.peek().getNumero() == 1))
                                                && (montonInterior[a][b].peek().getPalo() == carta1.peek().getPalo())) {

                                            carta1.push(montonInterior[a][b].pop());
                                        }
                                        if ((carta1.peek().getNumero() == 7 && montonInterior[c][d].peek().getNumero() == 10)
                                                || (montonInterior[c][d].peek().getNumero() - carta1.peek().getNumero() == 1)) {
                                            movimientos = true;
                                        }
                                        while (!carta1.empty()) {
                                            montonInterior[a][b].push(carta1.pop());
                                        }
                                    }

                                }
                            }
                        }
                        
                         
                        //Verificacion de Movimientos exteriores
                        else{
                            //Se ignora la misma posicion y si la del monton interior esta vacia
                            if(!montonInterior[a][b].empty()){
                                //Si el monton exterior esta vacio
                                if(montonExterior[d].empty()){
                                    //Si el primer elemento del monton interior es igual a uno
                                    if(montonInterior[a][b].peek().getNumero()==1)
                                        movimientos=true;
                                //si el monton exterior no esta vacio
                                }else{
                                //Si son del mismo palo
                                    if(montonInterior[a][b].peek().getPalo()==montonExterior[d].peek().getPalo()){
                                        //Si son contiguas
                                        if((montonInterior[a][b].peek().getNumero() == 10 && montonExterior[d].peek().getNumero() == 7)  
                                            || (montonInterior[a][b].peek().getNumero() - montonExterior[d].peek().getNumero() == 1)){
                                            movimientos=true;
                                        }
                    }

                }
            }
        }
        return movimientos;
    }*/
    
    
    

}
