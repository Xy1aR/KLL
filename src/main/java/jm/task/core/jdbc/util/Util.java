package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/task_1.1.4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration()
                        .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                        .setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/task_1.1.5")
                        .setProperty("hibernate.connection.username", "root")
                        .setProperty("hibernate.connection.password", "root")
                        .setProperty("show_sql", "true")
                        .setProperty("current_session_context_class", "thread")
                        .setProperty("hibernate.hbm2ddl_auto", "");

                sessionFactory = cfg.buildSessionFactory();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }
}
