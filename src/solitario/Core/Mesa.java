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
    

}
