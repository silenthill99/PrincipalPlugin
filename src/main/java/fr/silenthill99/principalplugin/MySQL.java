package fr.silenthill99.principalplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("CallToPrintStackTrace")
public class MySQL
{
    private static Connection conn;

    private static MySQL instance;

    public static MySQL getInstance() {
        if (instance == null) {
            instance = new MySQL();
        }
        instance.connect("minecraft118.omgserv.com", 3306, "minecraft_235640", "minecraft_235640", "Mylene.10");
        return instance;
    }

    private void connect(String host, int port, String database, String user, String password)
    {
        if (!isConnected())
        {
            try
            {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port  + "/" + database, user, password);
                Main.getInstance().getLogger().info("Connection établie avec la BDD");
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
    public static boolean isConnected() {
        try {
            return (conn != null) && (!conn.isClosed()) && !conn.isValid(5);
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
