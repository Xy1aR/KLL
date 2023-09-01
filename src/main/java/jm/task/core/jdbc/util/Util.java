package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL_JDBC = "jdbc:mysql://localhost:3306/task_1.1.4";
    private static final String URL_HIBER = "jdbc:mysql://localhost:3306/task_1.1.5";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_JDBC, USERNAME, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration()
                        .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                        .setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", URL_HIBER)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)
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
