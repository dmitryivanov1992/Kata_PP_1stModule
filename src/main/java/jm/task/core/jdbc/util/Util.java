package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mydb";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static Connection connection;

    public static Connection getConnectionJDBC() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(boolean createOrDropTable) {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.hbm2ddl.auto", createOrDropTable ? "create" : "drop")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

}
