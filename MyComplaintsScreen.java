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

public class MyComplaintsScreen {

    public void show(Stage stage, User user) {

        Label title = new Label("My Complaints");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        Label welcome = new Label(
                "Complaints submitted by " + user.getName()
        );
        welcome.setFont(Font.font("Arial", 15));

        ListView<String> complaintList = new ListView<>();
        complaintList.setPrefHeight(400);
        complaintList.setPrefWidth(750);

        ComplaintDAO complaintDAO = new ComplaintDAO();

        List<String> complaints =
                complaintDAO.getMyComplaints(user.getUserId());

        if (complaints.isEmpty()) {

            complaintList.getItems().add(
                    "No complaints found."
            );

        } else {

            complaintList.getItems().addAll(complaints);
        }

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
                20,
                title,
                welcome,
                complaintList,
                backButton
        );

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        StackPane root = new StackPane(content);

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("My Complaints");
        stage.setScene(scene);
        stage.show();
    }
}