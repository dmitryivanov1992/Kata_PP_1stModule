package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnectionJDBC();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE `mydb`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE `mydb`.`users`");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO users(name,lastName,age) VALUES(?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection
                .prepareStatement("DELETE FROM users WHERE id=?")) {
            statement.setLong(1, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection
                .createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            connection.commit();
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection
                .createStatement()) {
            statement.executeUpdate("DELETE FROM users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
        }
    }
}
