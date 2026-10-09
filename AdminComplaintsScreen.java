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

import java.util.List;

public class AdminComplaintsScreen {

    public void show(Stage stage, User user) {

        Label title = new Label("All Complaints");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        Label info = new Label(
                "Complaints submitted by students"
        );
        info.setFont(Font.font("Arial", 15));

        ListView<String> complaintList = new ListView<>();
        Button updateButton = new Button("UPDATE SELECTED COMPLAINT");

updateButton.setPrefWidth(250);

updateButton.setStyle(
        "-fx-background-color: #1E88E5;" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;"
);

updateButton.setOnAction(e -> {

    String selected = complaintList.getSelectionModel().getSelectedItem();

    if (selected == null) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Complaint Selected");
        alert.setHeaderText("Please select a complaint");
        alert.setContentText(
                "Select a complaint from the list first."
        );
        alert.showAndWait();

        return;
    }

    String idPart = selected.substring(
            selected.indexOf(":") + 1,
            selected.indexOf("|")
    ).trim();

    int complaintId = Integer.parseInt(idPart);

    new AdminUpdateComplaintScreen()
            .show(stage, user, complaintId);
});

        ComplaintDAO complaintDAO = new ComplaintDAO();

        List<String> complaints =
                complaintDAO.getAllComplaints();

        if (complaints.isEmpty()) {

            complaintList.getItems().add(
                    "No complaints found."
            );

        } else {

            complaintList.getItems().addAll(complaints);
        }

        complaintList.setPrefHeight(400);
        complaintList.setPrefWidth(800);

        Button backButton = new Button("BACK");

        backButton.setPrefWidth(150);

        backButton.setStyle(
                "-fx-background-color: #757575;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setOnAction(e -> {
            new AdminDashboard().show(stage, user);
        });

        VBox content = new VBox(
        20,
        title,
        info,
        complaintList,
        updateButton,
        backButton
);

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        StackPane root = new StackPane(content);

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("All Complaints");
        stage.setScene(scene);
        stage.show();
    }
}
