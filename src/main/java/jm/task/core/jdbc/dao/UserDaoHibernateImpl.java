package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.List;
import org.hibernate.Session;
import java.sql.Connection;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL" +
                ")";
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");

        } catch (SQLException e) {
            System.out.println("ошибка создания таблицы");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = " DROP TABLE IF EXISTS users";
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");

        } catch (SQLException e) {
            System.out.println("ошибка удаления таблицы");
            throw new RuntimeException(e);
        }



    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
            try (Session session = Util.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.persist(user);
                session.getTransaction().commit();
                System.out.println("user с именем" + name + " добавлен");
            } catch (Exception e) {
                System.out.println("ошибка добавления");;
            }
        }


    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("удалено");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ошибка при удалении");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка пользователей: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("таблица очищена");
            session.clear();

        } catch (Exception e) {
            System.out.println("Ошибка при очищении " + e.getMessage());
        }

    }
}
