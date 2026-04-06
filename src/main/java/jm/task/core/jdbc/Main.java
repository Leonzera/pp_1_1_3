package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 22);
        userService.saveUser("Олег", "Олегов", (byte) 21);
        userService.saveUser("Гело", "Вгело", (byte) 35);
        userService.saveUser("Нави", "Петров", (byte) 24);

        List<User> users = userService.getAllUsers();
        System.out.println("\n---- Список всех пользователей ----");
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}