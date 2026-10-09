package dao;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User login(String email, String password) {

        String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                User user = new User();

                user.setUserId(result.getInt("user_id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password_hash"));
                user.setRole(result.getString("role"));
                user.setDepartment(result.getString("department"));
                user.setPhone(result.getString("phone"));

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean registerStudent(
        String name,
        String email,
        String password,
        String department,
        String phone) {

    String sql =
            "INSERT INTO users " +
            "(name, email, password_hash, role, department, phone) " +
            "VALUES (?, ?, ?, 'STUDENT', ?, ?)";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, email);
        statement.setString(3, password);
        statement.setString(4, department);
        statement.setString(5, phone);

        statement.executeUpdate();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
