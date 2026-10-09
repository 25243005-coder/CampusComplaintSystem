package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ComplaintDAO {

    public boolean addComplaint(
            int userId,
            int categoryId,
            String title,
            String description,
            String priority) {

        String sql = "INSERT INTO complaints " +
                "(user_id, category_id, title, description, priority) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, categoryId);
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setString(5, priority);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public java.util.List<String> getMyComplaints(int userId) {

    java.util.List<String> complaints =
            new java.util.ArrayList<>();

    String sql =
            "SELECT c.complaint_id, c.title, " +
            "cat.category_name, c.priority, c.status " +
            "FROM complaints c " +
            "JOIN categories cat " +
            "ON c.category_id = cat.category_id " +
            "WHERE c.user_id = ? " +
            "ORDER BY c.created_at DESC";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        ResultSet result = statement.executeQuery();

        while (result.next()) {

            String complaint =
                    "ID: " + result.getInt("complaint_id") +
                    " | " + result.getString("title") +
                    " | Category: " +
                    result.getString("category_name") +
                    " | Priority: " +
                    result.getString("priority") +
                    " | Status: " +
                    result.getString("status");

            complaints.add(complaint);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return complaints;
}
public java.util.List<String> getMyComplaintTitles(int userId) {

    java.util.List<String> complaints =
            new java.util.ArrayList<>();

    String sql =
            "SELECT complaint_id, title " +
            "FROM complaints " +
            "WHERE user_id = ? " +
            "ORDER BY created_at DESC";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        ResultSet result = statement.executeQuery();

        while (result.next()) {

            String complaint =
                    result.getInt("complaint_id")
                    + " - "
                    + result.getString("title");

            complaints.add(complaint);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return complaints;
}
public java.util.List<String> getAllComplaints() {

    java.util.List<String> complaints =
            new java.util.ArrayList<>();

    String sql =
            "SELECT c.complaint_id, c.title, " +
            "cat.category_name, c.priority, c.status " +
            "FROM complaints c " +
            "JOIN categories cat " +
            "ON c.category_id = cat.category_id " +
            "ORDER BY c.created_at DESC";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        while (result.next()) {

            String complaint =
                    "ID: " + result.getInt("complaint_id") +
                    " | " + result.getString("title") +
                    " | Category: " +
                    result.getString("category_name") +
                    " | Priority: " +
                    result.getString("priority") +
                    " | Status: " +
                    result.getString("status");

            complaints.add(complaint);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return complaints;
}
public boolean updateComplaintStatus(
        int complaintId,
        int adminId,
        String status,
        String response) {

    String updateComplaint =
            "UPDATE complaints " +
            "SET status = ? " +
            "WHERE complaint_id = ?";

    String insertUpdate =
            "INSERT INTO complaint_updates " +
            "(complaint_id, updated_by, status, response) " +
            "VALUES (?, ?, ?, ?)";

    try (Connection connection = DBConnection.getConnection()) {

        connection.setAutoCommit(false);

        try (PreparedStatement statement =
                     connection.prepareStatement(updateComplaint)) {

            statement.setString(1, status);
            statement.setInt(2, complaintId);
            statement.executeUpdate();
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(insertUpdate)) {

            statement.setInt(1, complaintId);
            statement.setInt(2, adminId);
            statement.setString(3, status);
            statement.setString(4, response);
            statement.executeUpdate();
        }

        connection.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
