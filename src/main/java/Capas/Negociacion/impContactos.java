package Capas.Negociacion;
import Capas.Estructura.Contactos;
import Capas.Datos.impDatos;
import Capas.Datos.intDatos;
import Capas.DataBase.ConexionDB;
import java.util.*;
import java.sql.*;



public class impContactos implements intContactos{
    private final intDatos datos = new impDatos();
    Scanner sc = new Scanner(System.in);

    @Override
    public void existeConexion() {
        if (this.datos.existe()) {
            this.datos.existeTabla();
            System.out.println("Conexión a la base de datos establecida...\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Error al conectar con la base de datos\n");
            System.exit(1);
        }
    }

    @Override
    public void registrarContacto(){
        System.out.print("\n----- Registrar Contacto -----");
        System.out.print("\nIngresar Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("\nIngresar Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("\nIngresar Email: ");
        String email = sc.nextLine();
        System.out.print("\nIngresar Telefono: ");
        String telefono = sc.nextLine();
        System.out.print("\n------------------------------\n\n");

        Contactos contacto = new Contactos(nombre, apellido, email, telefono);

        if(datos.existe()){
            datos.agregarArchivo(nombre, apellido, email, telefono);
        }else{
            System.out.println("Error al conectarse con la base de datos");
        }

    }
    @Override
    public void listaContactos() {
        datos.listarContactos();
    }

    @Override
    public void buscarContacto() {
        System.out.println("\n----- Buscar Contacto -----");
        System.out.print("Ingrese el nombre o apellido a buscar: ");
        String buscar = sc.nextLine();
        String resultado = this.datos.buscar(buscar);

        if (resultado != null) {
            System.out.println("\nContacto encontrado:\n" + resultado);
        } else {
            System.out.println("No se encontró ningún contacto con ese criterio");
        }
        System.out.println("\n--------------------------\n");
    }


    @Override
    public void editarContacto(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n----- Editar Contacto -----");
        System.out.print("Ingrese el nombre o apellido del contacto a editar: ");
        String buscar = sc.nextLine();
        String resultado = this.datos.buscar(buscar);
        if (resultado == null) {
            System.out.println("No se encontró ningún contacto con ese criterio");
            return;
        }

        System.out.println("\nContacto encontrado a editar:\n" + resultado);

        System.out.print("\nIngrese el ID del contacto a editar: ");
        int id;
        try { id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
            return;
        }

        System.out.println("----- Actualizando contacto -----");
        System.out.print("\nNuevo Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("\nNuevo Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("\nNuevo Email: ");
        String email = sc.nextLine();
        System.out.print("\nNuevo Telefono: ");
        String telefono = sc.nextLine();
        System.out.print("\n------------------------------\n\n");

        if(nombre.isEmpty() && apellido.isEmpty() && email.isEmpty() && telefono.isEmpty()) {
            System.out.println("No hay datos a actualizar");
            return;
        }
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contactos WHERE id = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (nombre.isEmpty()) nombre = rs.getString("nombre");
                    if (apellido.isEmpty()) apellido = rs.getString("apellido");
                    if (email.isEmpty()) email = rs.getString("email");
                    if (telefono.isEmpty()) telefono = rs.getString("telefono");
                    datos.editarContacto(id, nombre, apellido, email, telefono);
                } else {
                    System.out.println("No se encontró el contacto con ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener datos del contacto: " + e.getMessage());
        }
        System.out.println("\n--------------------------\n");
    }

    @Override
    public void eliminarContacto() {
        System.out.println("\n----- Eliminar Contacto -----");
        System.out.print("Ingrese el nombre o apellido del contacto a eliminar: ");
        String buscar = sc.nextLine();

        String resultado = this.datos.buscar(buscar);
        int id;
        if (resultado == null) {
            System.out.println("No se encontró ningún contacto con ese criterio");
            return;
        }
        System.out.println("\nContacto encontrado:\n" + resultado);
        System.out.print("\nIngrese el ID del contacto a eliminar: ");
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
            return;
        }
        System.out.print("\n¿Está seguro que desea eliminar este contacto? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada");
            return;
        }

        datos.eliminarContacto(id);
        System.out.println("\n--------------------------\n");
    }

}
