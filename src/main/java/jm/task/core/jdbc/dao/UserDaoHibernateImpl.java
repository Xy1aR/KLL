package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserDaoHibernateImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS Users (" +
                " id BIGINT NOT NULL AUTO_INCREMENT, " +
                " name VARCHAR(50) NOT NULL, " +
                " lastName VARCHAR(50) NOT NULL, " +
                " age TINYINT(3) NOT NULL, " +
                " PRIMARY KEY (id) )";

        try (Session session = Util.getSessionFactory().openSession()) {
            NativeQuery query = session.createSQLQuery(sqlQuery);
            query.executeUpdate();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS Users";

        try (Session session = Util.getSessionFactory().openSession()) {
            NativeQuery query = session.createSQLQuery(sqlQuery);
            query.executeUpdate();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            logger.log(Level.INFO, "User с именем {0} добавлен в базу данных", name);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.warning(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        String sqlQuery = "DELETE User WHERE id = :id";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(sqlQuery);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.warning(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            result = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String sqlQuery = "DELETE FROM User";

        try (Session session = Util.getSessionFactory().openSession()) {
            createUsersTable();
            transaction = session.beginTransaction();
            Query query = session.createQuery(sqlQuery);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.warning(e.getMessage());
        }
    }
}
