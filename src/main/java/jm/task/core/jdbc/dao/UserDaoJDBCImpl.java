package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS Users (" +
                " id BIGINT NOT NULL AUTO_INCREMENT, " +
                " name VARCHAR(50) NOT NULL, " +
                " lastName VARCHAR(50) NOT NULL, " +
                " age TINYINT(3) NOT NULL, " +
                " PRIMARY KEY (id) )";
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS Users";
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection con = Util.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            con.setAutoCommit(false);
            createUsersTable();
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            con.commit();
            logger.info(String.format("User с именем %s был добавлен в базу данных", name));
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM Users WHERE id = (?)";
        try (Connection con = Util.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            con.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String sqlString = "SELECT * FROM Users";
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlString)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                user.setId(id);
                result.add(user);
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
        return result;
    }

    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE TABLE Users";
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            con.setAutoCommit(false);
            createUsersTable();
            statement.executeUpdate(sqlQuery);
            con.commit();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }
}
