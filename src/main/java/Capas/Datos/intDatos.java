package Capas.Datos;
import Capas.Estructura.Contactos;
import java.util.List;

public interface intDatos {
    boolean existe();
    void existeTabla();
    void agregarArchivo(String nombre,String apellido,String email,String telefono);
    void listarContactos();
    String buscar(String buscar);
    void editarContacto(int id, String nombre, String apellido, String email, String telefono);
    void eliminarContacto(int id);
}
