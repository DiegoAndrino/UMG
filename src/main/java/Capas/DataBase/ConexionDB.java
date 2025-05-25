package Capas.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:sqlserver://PHA-RECEP-PC1\\MMSSQLSERVER:51901;database=Contactosdb;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "man2025";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            throw e;
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("¡Conexión exitosa a la base de datos Contactosdb!");
            System.out.println("Estado de la conexión: " + !conn.isClosed());
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
