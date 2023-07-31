package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Nikita", "Krasnikov", (byte) 23);
        userService.saveUser("Ivan", "Ivanov", (byte) 12);
        userService.saveUser("Petr", "Petrov", (byte) 127);
        userService.saveUser("Aleksey", "Vorobyov", (byte) 9);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
