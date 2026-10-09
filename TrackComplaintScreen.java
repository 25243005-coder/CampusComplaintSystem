package ui;

import dao.ComplaintDAO;
import database.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TrackComplaintScreen {

    public void show(Stage stage, User user) {

        Label title = new Label("Track Complaint");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        Label selectLabel = new Label("Select Complaint");

        ComboBox<String> complaintBox = new ComboBox<>();
        complaintBox.setPrefWidth(450);

        ComplaintDAO complaintDAO = new ComplaintDAO();

        List<String> complaints =
                complaintDAO.getMyComplaintTitles(user.getUserId());

        complaintBox.getItems().addAll(complaints);

        Label statusTitle = new Label("Complaint Status");
        statusTitle.setFont(Font.font("Arial", 20));
        statusTitle.setTextFill(Color.DARKBLUE);

        Label complaintDetails = new Label();
        complaintDetails.setFont(Font.font("Arial", 15));
        complaintDetails.setWrapText(true);

        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Arial", 18));
        statusLabel.setFont(
                Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 18)
        );

        complaintBox.setOnAction(e -> {

            String selected = complaintBox.getValue();


            if (selected == null) {
                return;
            }

            int complaintId =
                    Integer.parseInt(selected.split(" - ")[0]);

            String sql =
        "SELECT c.title, c.description, " +
        "c.priority, c.status, " +
        "cat.category_name, " +
        "(SELECT response FROM complaint_updates " +
        "WHERE complaint_id = c.complaint_id " +
        "ORDER BY updated_at DESC LIMIT 1) AS admin_response " +
        "FROM complaints c " +
        "JOIN categories cat " +
        "ON c.category_id = cat.category_id " +
        "WHERE c.complaint_id = ?";

            try (Connection connection =
                         DBConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, complaintId);

                ResultSet result = statement.executeQuery();

                if (result.next()) {

                    String titleText =
                            result.getString("title");

                    String description =
                            result.getString("description");

                    String category =
                            result.getString("category_name");

                    String priority =
                            result.getString("priority");

                    String status =
                            result.getString("status");
                    String adminResponse = result.getString("admin_response");

                    complaintDetails.setText(
        "Title: " + titleText +
        "\nCategory: " + category +
        "\nPriority: " + priority +
        "\n\nDescription:\n" + description +
        "\n\nAdmin Response:\n" +
        (adminResponse == null ? "No response yet." : adminResponse)
);

                    statusLabel.setText(
                            "Current Status: " + status
                    );

                    statusLabel.setTextFill(
                            getStatusColor(status)
                    );
                }

            } catch (Exception ex) {

                complaintDetails.setText(
                        "Unable to load complaint details."
                );

                statusLabel.setText("");

                ex.printStackTrace();
            }
        });

        Button backButton = new Button("BACK");

        backButton.setPrefWidth(150);

        backButton.setStyle(
                "-fx-background-color: #757575;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setOnAction(e -> {
            new StudentDashboard().show(stage, user);
        });

        VBox content = new VBox(
                15,
                title,
                selectLabel,
                complaintBox,
                statusTitle,
                statusLabel,
                complaintDetails,
                backButton
        );

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setMaxWidth(600);

        StackPane root = new StackPane(content);

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Track Complaint");
        stage.setScene(scene);
        stage.show();
    }

    private Color getStatusColor(String status) {

        switch (status) {

            case "SUBMITTED":
                return Color.ORANGE;

            case "ASSIGNED":
                return Color.BLUE;

            case "IN_PROGRESS":
                return Color.DARKORANGE;

            case "RESOLVED":
                return Color.GREEN;

            default:
                return Color.BLACK;
        }
    }
}