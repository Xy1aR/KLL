package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Nikita", "Krasnikov", (byte) 23);
        userService.saveUser("Ivan", "Ivanov", (byte) 12);
        userService.saveUser("Petr", "Petrov", (byte) 199);
        userService.saveUser("Aleksey", "Vorobyov", (byte) 9);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
