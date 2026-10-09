package ui;

import dao.ComplaintDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

public class AdminUpdateComplaintScreen {

    public void show(Stage stage, User user, int complaintId) {

        Label title = new Label("Update Complaint");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        Label complaintLabel =
                new Label("Complaint ID: " + complaintId);

        Label statusText =
                new Label("Select New Status");

        ComboBox<String> statusBox =
                new ComboBox<>();

        statusBox.getItems().addAll(
                "SUBMITTED",
                "ASSIGNED",
                "IN_PROGRESS",
                "RESOLVED"
        );

        statusBox.setValue("IN_PROGRESS");
        statusBox.setPrefWidth(400);

        Label responseText =
                new Label("Admin Response");

        TextArea responseArea =
                new TextArea();

        responseArea.setPromptText(
                "Enter response for the student..."
        );

        responseArea.setPrefRowCount(6);
        responseArea.setWrapText(true);

        Button updateButton =
                new Button("UPDATE COMPLAINT");

        Button backButton =
                new Button("BACK");

        updateButton.setStyle(
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

        updateButton.setOnAction(e -> {

            String status = statusBox.getValue();
            String response =
                    responseArea.getText().trim();

            if (status == null || response.isEmpty()) {

                message.setTextFill(Color.RED);
                message.setText(
                        "Please select status and enter response."
                );

                return;
            }

            ComplaintDAO complaintDAO =
                    new ComplaintDAO();

            boolean success =
                    complaintDAO.updateComplaintStatus(
                            complaintId,
                            user.getUserId(),
                            status,
                            response
                    );

            if (success) {

                message.setTextFill(Color.GREEN);
                message.setText(
                        "Complaint updated successfully!"
                );

                statusBox.setDisable(true);
                responseArea.setDisable(true);
                updateButton.setDisable(true);

            } else {

                message.setTextFill(Color.RED);
                message.setText(
                        "Failed to update complaint."
                );
            }
        });

        backButton.setOnAction(e -> {
            new AdminComplaintsScreen().show(stage, user);
        });

        VBox form = new VBox(
                12,
                title,
                complaintLabel,
                statusText,
                statusBox,
                responseText,
                responseArea,
                updateButton,
                backButton,
                message
        );

        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));
        form.setMaxWidth(500);

        StackPane root =
                new StackPane(form);

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene =
                new Scene(root, 900, 650);

        stage.setTitle("Update Complaint");
        stage.setScene(scene);
        stage.show();
    }
}