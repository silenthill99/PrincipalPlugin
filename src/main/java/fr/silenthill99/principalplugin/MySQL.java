package fr.silenthill99.principalplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL
{
    private Connection conn;
    Main main = Main.getInstance();

    public void connect(String host, int port, String database, String user, String password)
    {
        if (!isConnected())
        {
            try
            {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port  + "/" + database, user, password);
                main.getLogger().info("Connection établie avec la BDD");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect()
    {
        if (isConnected())
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected() {
        try {
            if ((conn == null) || (conn.isClosed()) ||conn.isValid(5)){
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupérer la connection
     * @return connection
     */

    public Connection getConnection() {
        return conn;
    }
}
