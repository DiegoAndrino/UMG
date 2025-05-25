package Capas.Datos;
import Capas.DataBase.ConexionDB;
import java.sql.*;

public class impDatos implements intDatos {

    @Override
    public boolean existe() {
        try (Connection conn = ConexionDB.getConnection()) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public void existeTabla() {
        String sql = """
        IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Contactos')
        BEGIN
            CREATE TABLE Contactos (
                id INT IDENTITY(1,1) PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100),
                email VARCHAR(100),
                telefono VARCHAR(20) NOT NULL)
            PRINT 'Tabla Contactos creada exitosamente'
        END
        ELSE
        BEGIN
            PRINT 'La tabla Contactos ya existe'
        END
    """;

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Verificación de tabla completada");

        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void agregarArchivo(String nombre, String apellido, String email, String telefono) {
        String sql = "INSERT INTO Contactos (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, email);
            stmt.setString(4, telefono);

            stmt.executeUpdate();
            System.out.println("Contacto agregado exitosamente a la base de datos");

        } catch (SQLException e) {
            System.err.println("Error al agregar contacto: " + e.getMessage());
        }
    }

    @Override
    public void listarContactos() {
        String sql = "SELECT * FROM Contactos";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        "ID " + rs.getInt("id") +
                        "| Nombre: " + rs.getString("nombre") +
                        ", Apellido: " + rs.getString("apellido") +
                        ", Email: " + rs.getString("email") +
                        ", Telefono: " + rs.getString("telefono")
                );}
            System.out.println("----------------------------\n");
        } catch (SQLException e) {
            System.err.println("Error al listar contactos: " + e.getMessage());
        }
    }

    @Override
    public String buscar(String buscar) {
        String resultado = null;
        String sql = "SELECT * FROM Contactos WHERE nombre LIKE ? OR apellido LIKE ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + buscar + "%");
            stmt.setString(2, "%" + buscar + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    resultado = "ID " + rs.getInt("id") +
                            ": Nombre: " + rs.getString("nombre") +
                            ", Apellido: " + rs.getString("apellido") +
                            ", Email: " + rs.getString("email") +
                            ", Teléfono: " + rs.getString("telefono");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar contacto: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public void editarContacto(int id, String nombre, String apellido, String email, String telefono){
        String sql = "UPDATE Contactos SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, email);
            stmt.setString(4, telefono);
            stmt.setInt(5, id);

            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Contacto actualizado exitosamente");
            } else {
                System.out.println("No se encontró el contacto con ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar contacto: " + e.getMessage());
        }

    }

    @Override
    public void eliminarContacto(int id) {
        String sql = "DELETE FROM Contactos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Contacto eliminado exitosamente");
            } else {
                System.out.println("No se encontró el contacto con ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar contacto: " + e.getMessage());
        }
    }
}

