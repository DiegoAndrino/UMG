package Capas.Estructura;

public class Contactos {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    //Contructores
    public Contactos(){};
    public Contactos(String nombreContacto, String apellidoContacto, String emailContacto, String telefonoContacto) {
        this.nombre = nombreContacto;
        this.apellido = apellidoContacto;
        this.email = emailContacto;
        this.telefono = telefonoContacto;
    }
    public Contactos(String linea) {
        System.out.println(linea);
    }


    //Getters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getTelefono() {return telefono;}
    //Setters
    public void setId(int id) {this.id = id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setEmail(String email) {this.email = email;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    @Override
    public String toString() {
        return "ID: "+ id +
                " | Nombre: "+nombre+
                " | Apellido: "+apellido+
                " | Email: "+email+
                " | Telefono: "+telefono;
    }
}
