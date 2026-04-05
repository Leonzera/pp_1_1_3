package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                " id BIGINT AUTO_INCREMENT PRIMARY KEY,"+
                "name VARCHAR(50) NOT NULL,"+
                "lastname VARCHAR(50) NOT NULL,"+
                "age TINYINT NOT NULL" +
                ")";
         try(Connection connection = Util.getConnection();
             Statement statement=connection.createStatement()){
             statement.executeUpdate(sql);
             System.out.println("Таблица успешно создана");
         }catch (SQLException e){
             System.out.println("Ошибка создания таблицы"+ e.getMessage());
         }

    }

    public void dropUsersTable() {
                String sql="DROP TABLE IF EXISTS users";
                try(Connection conection = Util.getConnection();
                    Statement statement = conection.createStatement()){
                    statement.executeUpdate(sql);
                    System.out.println("Таблица users успешно удалена");

                }catch(SQLException e){
                    System.out.println("Ошибка удаления таблицы"+e.getMessage());
                }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try(Connection connection = Util.getConnection();
            PreparedStatement pstatement = connection.prepareStatement(sql)){
        pstatement.setString(1, name );
        pstatement.setString(2, lastName );
        pstatement.setByte(3, age );
        pstatement.executeUpdate();
            System.out.println("vse ok");
        } catch (SQLException ex){
            System.out.println("Добавление не удалось ");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection conection = Util.getConnection();
            PreparedStatement pstatement = conection.prepareStatement(sql)){
            pstatement.setLong(1,id);
            int rowsAffected= pstatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " успешно удален");
            } else {
                System.out.println("Пользователь с id " + id + " не найден");
            }
        } catch (SQLException ex){
            System.out.println("Удаление не удалось  ");
        }

    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        String sql = " SELECT * FROM users ";
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }

        } catch (SQLException e) {
            System.out.println("неудача");
        }

        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Таблица users успешно очищена ");
        }catch (SQLException E){
            System.out.println("Очистить не удалось ");
        }


    }
}
