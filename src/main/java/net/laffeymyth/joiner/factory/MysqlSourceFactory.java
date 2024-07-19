package net.laffeymyth.joiner.factory;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.sql.SQLException;

public class MysqlSourceFactory {
    public JdbcPooledConnectionSource create() {
        try {
            String host = "localhost";
            String port = "3306";
            String database = "joiner_youtube";
            JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC");
            connectionSource.setUsername("root");
            connectionSource.setPassword("smartCookieLikeMe2008");
            connectionSource.setMaxConnectionsFree(2);
            return connectionSource;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
