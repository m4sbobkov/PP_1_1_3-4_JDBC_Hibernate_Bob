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

        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `kata_db`.`users` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT NOT NULL,
                      PRIMARY KEY (`id`));""");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "INSERT INTO kata_db.users (name, lastName, age) VALUES (?,?,?)";

        try (Connection con = Util.getConnection(); PreparedStatement statement = con.prepareStatement(insert)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {

        String delete = "DELETE FROM users WHERE id = ?";

        try (Connection con = Util.getConnection(); PreparedStatement statement = con.prepareStatement(delete)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String getSQL = "SELECT * FROM kata_db.users";

        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {

            ResultSet resultSet = statement.executeQuery(getSQL);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;

    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {

            statement.executeUpdate("DELETE FROM users");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
