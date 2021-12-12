package database;

import java.sql.*;

public class Database {

    private static Database instance;

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    private Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.initConnection();
    }

    public static Database getInstance(String url, String user, String password){
        if (Database.instance == null) {
            Database.instance = new Database(url, user, password);
        }

        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void showMetaData() {
        try {
            DatabaseMetaData databaseMetaData = this.connection.getMetaData();

            ResultSet tables = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while(tables.next()) {
                System.out.println("TABLE_NAME : " + tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initConnection() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connection established !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
