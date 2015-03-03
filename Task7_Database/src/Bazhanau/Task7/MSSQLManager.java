package Bazhanau.Task7;

import java.sql.DriverManager;

public class MSSQLManager {
    private final String url = "jdbc:sqlserver://";
    private final String serverName = "127.0.0.1\\BAZHANAU";
    private final String portNumber = "1433";
    private String databaseName = "JavaTask7";
    private final String userName = "program";
    private final String password = "qwerty";
    // Сообщает драйверу о необходимости использовать сервером побочного курсора,
    // что позволяет использовать несколько активных выражений
    // для подключения.
    private final String selectMethod = "cursor";
    private java.sql.Connection connection = null;

    // Конструктор
    public MSSQLManager() {
    }

    public MSSQLManager(String databaseName) {
        this.databaseName = databaseName;
    }

    private String getConnectionUrl() {
        return url + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";selectMethod=" + selectMethod + ";";
    }

    public java.sql.Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(getConnectionUrl(), userName, password);
            if (connection != null) System.out.println("Connection Successful!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }
        return connection;
    }

     /*
          Вывести свойства драйвера, сведения о базе данных 
     */

    public void displayDbProperties() {
        java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        try {
            connection = this.getConnection();
            if (connection != null) {
                dm = connection.getMetaData();
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: " + dm.getDriverName());
                System.out.println("\tDriver Version: " + dm.getDriverVersion());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());
                System.out.println("Avalilable Catalogs ");
                rs = dm.getCatalogs();
                while (rs.next()) {
                    System.out.println("\tcatalog: " + rs.getString(1));
                }
                rs.close();
                rs = null;
                closeConnection();
            } else System.out.println("Error: No active Connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dm = null;
    }

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
