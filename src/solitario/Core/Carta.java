/*
 * Representa una carta, formada por un número y su palo correspondiente
 */
package solitario.Core;

/**
 *
 * @author AEDI
 */
public class Carta {

    private final int numero; // Final para quitar warnings (No se modifican los valores en ningún momento)
    private final Palos palo;

    public Carta(int numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public int getNumero() {
        return this.numero; // por seguir el formato simplemente
    }

    public Palos getPalo() {
        return this.palo;
    }

    @Override //toString() es un método que se está reescribiendo 
    public String toString() {

        StringBuilder toret = new StringBuilder();

        toret.append("[").append(this.numero).append("|").append(this.palo).append("]"); // Quito salto línea para que no aparezcan todas en pila ( representación mesa mas fácil) , cambio el get. por this. (acceso directo al atributo, más eficiente)

        return toret.toString();
    }

}
