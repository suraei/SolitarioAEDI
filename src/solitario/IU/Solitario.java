/**
 * Representa el juego del solitario, con sus reglas.
 * Se recomienda una implementación modular.
 */
package solitario.IU;

import solitario.Core.Baraja;
import solitario.Core.Jugador;
import solitario.Core.Mesa;

/**
 *
 * @author AEDI
 */
public class Solitario {

    private static Jugador jugador;
    private static Mesa mesa;


    private static void mostrarMenuInicio() {
        System.out.println("[*]Bienvenid@ al juego del Solitario[*]\n[1]Comenzar partida\n[0]Salir");
    }
    
    private static Jugador crearJugador(){
       String nombre;
       do{
       nombre = ES.pideCadena("[?] Introduce tu nombre para comenzar a jugar: ");
       }while(nombre.trim().equals("")); // Comprueba que el nombre no esté vacío
        System.out.print("[+]Registrando al jugador ");
        System.out.println(nombre);
       return new Jugador(nombre);
    }
    
    private static Mesa crearJuego() throws Exception{
        System.out.println("[*] Generando un nuevo juego ...");
        Baraja baraja = new Baraja();
        Mesa _mesa = new Mesa();
        _mesa.colocarCartas(baraja);
        return _mesa;
        
    }
    
    private static void mostrarInterfaz(){
        System.out.print("[+] Jugador : ");
        System.out.println(jugador.getNombre());
        System.out.println("[+] Mesa : \n");
        mesa.mostrarMesa();
        System.out.println("\n[%]Opciones: \n[1]Mover carta del montón interior al montón exterior\n[2]Mover carta del montón interior al montón interior\n[0]Salir");     
    }
    
    private static void salir(){
        System.out.println("[*] Gracias por jugar al Solitario, esperamos verte pronto [*]");
        System.exit(0);
    }

    public static void inicioPartida() {

        int opcion;
        //Mostrar menu
        do {
            mostrarMenuInicio();
            opcion = ES.pideNumero("[?]Selecciona una opción: ");
        } while (opcion != 1 && opcion != 0);
        
        if(opcion == 0){
            salir();
        }
        
        //Crear jugador
        jugador = crearJugador();
        
        //Crear partida
        try{
            mesa = crearJuego();
        }catch(Exception err){
            System.err.print("[!] No se ha podido crear el juego: ");
            System.err.println(err.getMessage());
            System.exit(1); // Sale con error
        }
        
        //Mostrar interfaz
        while(!mesa.MontonExteriorCompleto()){ // Si no ha finalizado el juego sigue mostrando interfaz
        do{
            mostrarInterfaz();
            opcion = ES.pideNumero("[?]Selecciona una opción: ");
        }while (opcion<0 || opcion>2);
        
        if(opcion == 0){
            salir();
        }
} }}
