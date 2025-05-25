package Capas.Presentacion;
import Capas.Negociacion.*;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        intContactos contactos = new impContactos();
        contactos.existeConexion();

        int opcion;
        while(true){
            System.out.print("===== Gestor de Contactos =====\n");
            System.out.print("1. Registrar Contacto\n");
            System.out.print("2. Lista de Contactos\n");
            System.out.print("3. Buscar Contacto\n");
            System.out.print("4. Editar Contacto\n");
            System.out.print("5. Eliminar Contacto\n");
            System.out.print("0. Salir\n");
            opcion = sc.nextInt();

            switch(opcion){
                case 1:contactos.registrarContacto();
                    break;
                case 2:contactos.listaContactos();
                    break;
                case 3:contactos.buscarContacto();
                    break;
                case 4:contactos.editarContacto();
                    break;
                case 5:contactos.eliminarContacto();
                    break;
                case 0: System.exit(0);
                    System.out.println("Saliendo del programa...");
                    break;
                default: System.out.println("Opcion Invalida");
                    break;
            }
        }
    }
}