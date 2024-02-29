package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("James", "Brown", (byte) 48);
        userService.saveUser("Lindsey", "Mitchell", (byte) 36);
        userService.saveUser("Pablo", "Fachitas", (byte) 21);
        userService.saveUser("Dmitry", "Ivanov", (byte) 31);
        List<User> list = userService.getAllUsers();
        System.out.println(list);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
