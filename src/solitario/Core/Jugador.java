
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

    public int[] seleccionarCarta() {
        System.out.println("[?] Qué carta quieres mover del montón interior?");
        return solitario.IU.Solitario.seleccionarPosicion();

    }

    public int[] seleccionarDestino() {
        System.out.println("[?] Indica la posición de destino de tu carta");
        return solitario.IU.Solitario.seleccionarPosicion();
    }

    public void comprobarMovimientoInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
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
    }
    
    public void moverCartaInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        this.comprobarMovimientoInterior(filaOri, colOri, filaDest, colDest);
        // Una vez listas las comprobaciones podremos mover la carta
        Mesa.montonInterior[filaDest][colDest].push(Mesa.montonInterior[filaOri][colOri].pop()); // Movemos a la posicion de destino la carta situada en posicion origen
    }


    public int comprobarMovimientoExterior(int filaOri, int colOri) throws Exception {

        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Vemos que carta vamos a mover al montón Exterior
        Carta cartaOri = Mesa.montonInterior[filaOri][colOri].peek();

        //Miramos el palo de la carta que acabamos de coger y le asignamos el montón del mismo Palo
        int montonDest = cartaOri.getPalo().ordinal();

        //Comprobación de que la primera carta escogida para mover sea un AS
        if (Mesa.montonExterior[montonDest].empty()) {
            if (cartaOri.getNumero() != 1) {
                throw new Exception("Movimiento inválido : Si un montón de un palo está vacío la primera carta a poner debe ser un as");
            }
        } else {

            //Vemos que carta vamos a solapar
            Carta cartaDest = Mesa.montonExterior[montonDest].peek();

            //Comprobar cartaOri sea una unidad mayor sobre la carta a solapar.
            if ((cartaOri.getNumero() == 10 && cartaDest.getNumero() != 7)
                    || (cartaOri.getNumero() != 10 && cartaOri.getNumero() - 1 != cartaDest.getNumero())) {
                throw new Exception("Movimiento inválido :La carta de destino no es una unidad menor que la de origen");
            }
        }
        return montonDest;
    }
    
    public void moverCartaExterior(int filaOri, int colOri) throws Exception {
        int montonDest = this.comprobarMovimientoExterior(filaOri,colOri);
        //Una vez listas las comprobaciones movemos la carta al montón exterior
        Mesa.montonExterior[montonDest].push(Mesa.montonInterior[filaOri][colOri].pop());
    }

    
        
    public boolean movPosibles() {
        boolean quedanMov = false;
        
        int fila, columna;
        int filaC, columnaC;
        
        fila = 0;
        while(!quedanMov && fila < Mesa.montonInterior.length) {
            columna = 0;
            while(!quedanMov && columna < Mesa.montonInterior[fila].length) {
                
                // --- Comprobaciones del monton interior
                filaC = 0;
                while(!quedanMov && filaC < Mesa.montonInterior.length) {
                    columnaC = 0;
                    while(!quedanMov && columnaC < Mesa.montonInterior[fila].length) {
                        if(fila != filaC && columna != columnaC) {
                            try {
                                this.comprobarMovimientoInterior(fila, columna, filaC, columnaC);
                                quedanMov = true;
                            } catch(Exception err) {}
                        } 
                        columnaC++;
                    }
                    filaC++;
                }
                
                // --- Comprobaciones del montón exterior
                try {
                    this.comprobarMovimientoExterior(fila, columna);
                    quedanMov = true;
                } catch(Exception err) {}
                
                columna++;
            }
            fila++;
        }
                
        return quedanMov;
    }
}
