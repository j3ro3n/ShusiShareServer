package data;

import java.sql.*;
import java.util.ArrayList;

public class SQLStorage implements StorageInterface {
    //The URL to the MariaDB sushifileshare database with live connection to it. Sets and gets database URL
    private static String databaseUrl = "jdbc:mysql://192.168.178.20:3306/sushifileshare?useSSL=false&serverTimezone=UTC"; //SSL op false gezet kan op True als we SSL hebben
    private Connection connection;

    static String getDatabaseUrl() {
        return databaseUrl;
    }

    static void setDatabaseUrl(String databaseUrl) {
        SQLStorage.databaseUrl = databaseUrl;
    }

    private static Connection connect() throws Exception {
        return DriverManager.getConnection(
                databaseUrl,
                "sushiuser",
                "SushiUser!"
        );
    }

    //Check the sushifileshare database connection
    public static boolean canMakeConnection() {
        try {
            connect();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    //Make new connection to sushifileshare database
    Connection newConnection() throws Exception {
        return connect();
    }

    //Load file from sushifileshare database
    @Override
    public ArrayList<StorageInterface> loadData() throws Exception {
        this.connection = connect();
        Statement statement = connection.createStatement();
        //Query for sushifileshare database to pull data
        ResultSet fileNameResultSet = statement.executeQuery("SELECT * FROM `sushifileshare`");
        ArrayList<StorageInterface> fileName = new ArrayList<>();

        connection.close();

        return fileName;
    }
}

