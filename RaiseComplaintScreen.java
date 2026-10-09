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

public class RaiseComplaintScreen {

    public void show(Stage stage, User user) {

        Label title = new Label("Raise a New Complaint");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        Label studentLabel = new Label(
                "Student: " + user.getName()
        );

        TextField complaintTitle = new TextField();
        complaintTitle.setPromptText("Enter complaint title");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(
                "Academic",
                "Hostel",
                "Transport",
                "Library",
                "Infrastructure",
                "IT / Computer",
                "Canteen",
                "Electrical",
                "Other"
        );
        categoryBox.setPromptText("Select complaint category");
        categoryBox.setPrefWidth(400);

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(
                "LOW",
                "MEDIUM",
                "HIGH"
        );
        priorityBox.setValue("MEDIUM");
        priorityBox.setPrefWidth(400);

        TextArea description = new TextArea();
        description.setPromptText("Describe your complaint...");
        description.setPrefRowCount(6);
        description.setWrapText(true);

        Button submitButton = new Button("SUBMIT COMPLAINT");
        Button backButton = new Button("BACK");

        submitButton.setStyle(
                "-fx-background-color: #1E88E5;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setStyle(
                "-fx-background-color: #757575;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        Label message = new Label();

        submitButton.setOnAction(e -> {

            String titleText = complaintTitle.getText().trim();
            String category = categoryBox.getValue();
            String priority = priorityBox.getValue();
            String descriptionText = description.getText().trim();

            if (titleText.isEmpty()
                    || category == null
                    || descriptionText.isEmpty()) {

                message.setTextFill(Color.RED);
                message.setText(
                        "Please fill all required fields."
                );
                return;
            }

            try {

                int categoryId = getCategoryId(category);

                if (categoryId == -1) {
                    message.setTextFill(Color.RED);
                    message.setText(
                            "Selected category not found."
                    );
                    return;
                }

                ComplaintDAO complaintDAO = new ComplaintDAO();

                boolean success = complaintDAO.addComplaint(
                        user.getUserId(),
                        categoryId,
                        titleText,
                        descriptionText,
                        priority
                );

                if (success) {

                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION
                    );

                    alert.setTitle("Complaint Submitted");
                    alert.setHeaderText(
                            "Complaint Submitted Successfully"
                    );
                    alert.setContentText(
                            "Your complaint has been recorded."
                    );

                    alert.showAndWait();

                    new StudentDashboard().show(stage, user);

                } else {

                    message.setTextFill(Color.RED);
                    message.setText(
                            "Failed to submit complaint."
                    );
                }

            } catch (Exception ex) {

                message.setTextFill(Color.RED);
                message.setText(
                        "Database error occurred."
                );

                ex.printStackTrace();
            }
        });

        backButton.setOnAction(e -> {
            new StudentDashboard().show(stage, user);
        });

        VBox form = new VBox(
                12,
                studentLabel,
                new Label("Complaint Title"),
                complaintTitle,
                new Label("Category"),
                categoryBox,
                new Label("Priority"),
                priorityBox,
                new Label("Description"),
                description,
                submitButton,
                backButton,
                message
        );

        form.setPadding(new Insets(30));
        form.setMaxWidth(500);

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);

        BorderPane root = new BorderPane();

        root.setTop(title);
        root.setCenter(scrollPane);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(25));

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Raise Complaint");
        stage.setScene(scene);
        stage.show();
    }

    private int getCategoryId(String categoryName)
            throws Exception {

        String sql =
                "SELECT category_id FROM categories " +
                "WHERE category_name = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("category_id");
            }
        }

        return -1;
    }
}