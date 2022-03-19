import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnector {
    private Connection connection;
    private Statement statement;
    //private DbConnector dbc = new DbConnector();

    private static String servername = "localhost";
    private static String username = "root";
    private static String dbname = "thermostatdb";
    private static Integer portnumber = 3306;
    private static String password = "";
    public  static Connection getConnection() {
        Connection con = null;

        MysqlDataSource datasource = new MysqlDataSource();

        datasource.setServerName(servername);
        datasource.setUser(username);
        datasource.setPassword(password);
        datasource.setDatabaseName(dbname);
        datasource.setPortNumber(portnumber);

        try {
            con = datasource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(" Get Connection -> " + DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    private void close() {
        if (statement != null) {
            rmConnection(null);
        }
        if (connection != null) {
            rmConnection(null);
        }
    }

    /**
     * Closes result set
     *
     * @param lResultSet ResultSet
     */

    private void rmConnection(ResultSet lResultSet) {
        try {
            try {
                if (lResultSet != null || !connection.isClosed()) {
                    connection.close();

                    if (lResultSet != null && !lResultSet.isClosed()) {
                        lResultSet.close();
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes select statement
     *
     * @param query String containing query
     * @return resultset
     */
    private ResultSet select(String query) {
        ResultSet resultSet = null;

        try {
            getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return resultSet;
    }

    /**
     * Update or insert
     *
     * @param query String containing query
     * @return resultset
     */

    private int executeUpsert(String query) {
        try {
            getConnection();
            statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            connection.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return 0;
    }

    /**
     * Executes insert statement
     *
     * @param query String containing query
     * @return method call
     */

    private int insert(String query) {
        return executeUpsert(query);
    }

    /**
     * Executes update statement
     *
     * @param query String containing query
     * @return method call
     */

    private int update(String query) {
        return executeUpsert(query);
    }

    /**
     * Get first row from dataset/resultset
     *
     * @param rowSet resultset
     * @return null||error/stacktrace
     */

    public ResultSet getData(String strSQL) {
        ResultSet result = null;
        try {
            Statement stmt = getConnection().createStatement();
            result = stmt.executeQuery(strSQL);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public int executeDML(String strSQL) {
        int result = 0;
        try {
            Statement stmt = getConnection().createStatement();
            result = stmt.executeUpdate(strSQL);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    private ResultSet goToFirstRow(ResultSet rowSet) {
        try {
            if (rowSet.next()) {
                rowSet.first();
                return rowSet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
