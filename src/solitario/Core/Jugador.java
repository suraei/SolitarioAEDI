/*
 * Representa al único jugador de la partida, identificado por el nombre 
 * Funcionalidad: le da la vuelta a una carta que está boca abajo, escoge una carta para moverla o al montón de descarte
 *                o la mueve encima de otra carta del interior
 */
package solitario.Core;

import solitario.IU.ES;

public class Jugador {

    private String nombre;

    public Jugador(String nombre) {

        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   /* private int[] seleccionarPosicion() {
        int[] posicion = {-1, -1};
        do {
            posicion[0] = ES.pideNumero("[*]Selecciona la fila deseada [0-3]: "); 
        } while (posicion[0] < 0 || posicion[0] > 3);
        do {
            posicion[1] = ES.pideNumero("[*]Selecciona la columna deseada [0-3]: ");
        } while (posicion[1] < 0 || posicion[1] > 3);
        return posicion;

    }*/
    
       public int[] seleccionarCarta() {
        System.out.println("[?] Qué carta quieres mover del montón interior?");
        return solitario.IU.Solitario.seleccionarPosicion();

    }

    public int[] seleccionarDestino() {
        System.out.println("[?] Indica la posición de destino de tu carta");
        return solitario.IU.Solitario.seleccionarPosicion();
    }
    
        public void moverCartaInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Comprobar si el montón al que se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaDest][colDest].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas a espacios vacíos");
        }

        //Una vez listas las comprobaciones previas vemos que cartas queremos coger
        Carta cartaOri = Mesa.montonInterior[filaOri][colOri].peek();

        //Vemos sobre que carta queremos ponerla
        Carta cartaDest = Mesa.montonInterior[filaDest][colDest].peek();

        //Comprobar que la carta que hemos cogido y la carta sobre la cual vamos a poner sean del mismo palo
        if (!cartaOri.getPalo().equals(cartaDest.getPalo())) {
            throw new Exception("Movimiento inválido : No se pueden juntar cartas de distintos palos");
        }

        //Comprobar que el número de la carta origen sea menor que la del destino
        //Encima del 12 no se puede poner nada
        if (cartaOri.getNumero() == 12
                || (cartaOri.getNumero() == 7 && cartaDest.getNumero() != 10)
                || (cartaOri.getNumero() != 7 && cartaOri.getNumero() != cartaDest.getNumero() - 1)) {
            throw new Exception("Movimiento inválido : La carta de destino no es una unidad mayor que la de origen");
        }

        // Una vez listas las comprobaciones podremos mover la carta
        Mesa.montonInterior[filaDest][colDest].push(Mesa.montonInterior[filaOri][colOri].pop()); // Movemos a la posicion de destino la carta situada en posicion origen

        /*if (!montonInterior[filaDest][colDest].empty()) {  
            //Mientras el nº de la carta origen sea MENOR que el nº de la carta que se oculta
            while (montonInterior[filaOri][colOri].peek().getNumero() < montonInterior[filaDest][colDest].peek().getNumero()) { ES UN BUCLE INFINITO QUE BLOQUEA EL PROGRAMA SI SE MUEVE CARTA ORIGEN>DESTINO

                //Si se oculta un 10 pero la carta de encima es distinta de 7...
                if (montonInterior[filaOri][colOri].peek().getNumero() != 7
                        && montonInterior[filaDest][colDest].peek().getNumero() == 10) {
                    System.out.println("Movimiento inválido!");
                } else {
                    //Se pone en el stack destino la carta que sacamos del stack origen
                    montonInterior[filaDest][colDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        } else {
            System.out.println("Movimiento inválido!");
        }
    
        ERRORES :
        -NO SE COMPRUEBA SI COGES DE UNA POSICIÓN VACÍA
        -NO SE COMPRUEBA SI LA CARTA ORIGEN Y DESTINO SON DEL MISMO PALO
        -SI LA CARTA ORIGEN ES MENOR EN MAS DE UNA UNIDAD QUE LA CARTA DESTINO SE EJECUTA UN BUCLE IFNINITO QUE MUEVE TODO EL STACK
        -SI LA CARTA ORIGEN ES MAYOR O IGUAL QUE LA CARTA DESTINO NO HACE NADA NI MUESTRA MENSAJE
        -NO SE COMPRUEBA SI LA CARTA ORIGEN ES UNA UNIDAD MENOR QUE LA CARTA DESTINO
         */
    }

    //Colocar carta en un monton exterior
    //Se saca una carta de un stack del monton interior y se mete en el stack del 
    //palo correpondiente en el monton exterior
    //La carta que se pone en el monton exterior tiene que ser mayor que la carta que queda oculta
    //Si la carta que se oculta es un 7, la carta que se coloca encima tiene que ser un 10
    //Solo se puede colocar un AS(1) como primera carta en un stack exterior vacío
    public void moverCartaExterior(int filaOri, int colOri) throws Exception {

        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Vemos que carta vamos a mover al montón Exterior
        Carta cartaOri = Mesa.montonInterior[filaOri][colOri].peek();

        //Miramos el palo de la carta que acabamos de coger y le asignamos el montón del mismo Palo
        int montonDest = cartaOri.getPalo().ordinal();

        //Comprobación de que la primera carta escogida para mover sea un AS
        if (Mesa.montonExterior[montonDest].empty()){
            if(cartaOri.getNumero() != 1){
                throw new Exception("Movimiento inválido : Si un montón de un palo está vacío la primera carta a poner debe ser un as");
            }
        }else{

        //Vemos que carta vamos a solapar
        Carta cartaDest = Mesa.montonExterior[montonDest].peek();

        //Comprobar cartaOri sea una unidad mayor sobre la carta a solapar.
        if ((cartaOri.getNumero() == 10 && cartaDest.getNumero() != 7)
                || (cartaOri.getNumero() != 10 && cartaOri.getNumero() - 1 != cartaDest.getNumero()) ){
            throw new Exception("Movimiento inválido :La carta de destino no es una unidad menor que la de origen");
        }
        }
        //Una vez listas las comprobaciones movemos la carta al montón exterior
        Mesa.montonExterior[montonDest].push(Mesa.montonInterior[filaOri][colOri].pop());

        /*//Si el monton exterior esta vacío
        if (montonExterior[montonDest].empty()) {
            //Solo puede ponerse un AS
            if (montonInterior[filaOri][colOri].peek().getNumero() == 1) {
                //Se hace el movimiento del AS
                montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
            } else { //Si no es un AS
                System.out.println("Movimiento inválido!");
            }
        } else { //Si hay elementos en el monton exterior
            //Mientras la carta que se coloca sea mayor que la carta que se oculta
            while (montonInterior[filaOri][colOri].peek().getNumero() > montonExterior[montonDest].peek().getNumero()) {
                //Si la carta que se coloca encima del 7 es distinta de 10
                if (montonExterior[montonDest].peek().getNumero() == 7 && montonInterior[filaOri][colOri].peek().getNumero() != 10) {
                    System.out.println("Movimiento inválido!");
                } else { //Cualquier otro caso es correcto y se realiza el movimiento
                    montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        }
        ERRORES :
        - NO SE COMPRUEBA SI EL MONTÓN INTERIOR ESTÁ VACÍO
        - NO SE COMPRUEBA QUE SEAN DEL MISMO PALO
        - BUCLE INFINITO SI LA CARTA ORIGEN ES MAYOR QUE LA DE DESTINO , QUE MUEVE TODO UN STACK HACIA FUERA O MUESTRA DE FORMA INDEFINIDA EL MENSAJE "MOVIMIENTO INVÁLIDO"
        - NO SE COMPRUEBA QUE LA CARTA ORIGEN SEA UNA UNIDAD MAYOR QUE LA CARTA DESTINO
        - SI LA CARTA ORIGEN ES MENOR O IGUAL A LA DE DESTINO, NO HACE NADA NI MUESTRA NINGÚN MENSAJE
         */
    }
    
}

   