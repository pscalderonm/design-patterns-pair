package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    private Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/";

    public String database;
    public String username;
    public String password;

    private DatabaseConnection(String database, String username, String password){
        try{
            this.database = database;
            this.username = username;
            this.password = password;

            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url+this.database,this.username,this.password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver de conexion no encontrado "+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la conexion debase de datos: "+ e.getMessage());
        }
    }

    public static DatabaseConnection getInstance(String database, String username, String password) throws SQLException {
        if(instance == null){
            instance = new DatabaseConnection(database, username, password);
        }else if (instance.getConnection().isClosed()){
            instance = new DatabaseConnection(database, username, password);
        }
        return instance;
    }

    @Override
    public String toString() {
        return "DatabaseConnection{" +
                "database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Connection getConnection() {
        return connection;
    }

}
