package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;

public class StudentDashboard {

    public void show(Stage stage, User user) {

        // Header
        Label title = new Label("Student Dashboard");
        title.setFont(
                Font.font("Arial", FontWeight.BOLD, 28)
        );
        title.setTextFill(Color.web("#123B63"));

        Label welcome = new Label(
                "Welcome, " + user.getName() + "!"
        );
        welcome.setFont(Font.font("Arial", 17));
        welcome.setTextFill(Color.web("#546E7A"));

        Label subtitle = new Label(
                "Manage your campus complaints easily"
        );
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setTextFill(Color.web("#78909C"));

        // Buttons
        Button raiseComplaint =
                new Button("RAISE COMPLAINT");

        Button myComplaints =
                new Button("MY COMPLAINTS");

        Button trackComplaint =
                new Button("TRACK COMPLAINT");

        Button logout =
                new Button("LOGOUT");

        stylePrimaryButton(raiseComplaint);
        stylePrimaryButton(myComplaints);
        stylePrimaryButton(trackComplaint);
        styleLogoutButton(logout);

        // Existing functionality
        raiseComplaint.setOnAction(e -> {
            new RaiseComplaintScreen().show(stage, user);
        });

        myComplaints.setOnAction(e -> {
            new MyComplaintsScreen().show(stage, user);
        });

        trackComplaint.setOnAction(e -> {
            new TrackComplaintScreen().show(stage, user);
        });

        logout.setOnAction(e -> {
            new LoginScreen().show(stage);
        });

        // Button container
        VBox menu = new VBox(
                15,
                raiseComplaint,
                myComplaints,
                trackComplaint,
                logout
        );

        menu.setAlignment(Pos.CENTER);
        menu.setMaxWidth(330);

        // Main content
        VBox content = new VBox(
                12,
                title,
                welcome,
                subtitle,
                new Region(),
                menu
        );

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        // Card
        VBox card = new VBox(content);

        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(500);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 18;" +
                "-fx-border-color: #D6E2EF;" +
                "-fx-border-radius: 18;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(" +
                "gaussian, rgba(0,0,0,0.12), 18, 0, 0, 5);"
        );

        // Background
        StackPane root = new StackPane(card);

        root.setPadding(new Insets(40));

        root.setStyle(
                "-fx-background-color: linear-gradient(" +
                "to bottom right, #EAF4FF, #F7FAFD);"
        );

        Scene scene = new Scene(
                root,
                900,
                650
        );

        stage.setTitle("Student Dashboard");

        stage.setScene(scene);
        stage.show();
    }

    private void stylePrimaryButton(Button button) {

        button.setPrefWidth(330);
        button.setPrefHeight(48);

        button.setStyle(
                "-fx-background-color: #1976D2;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;"
        );
    }

    private void styleLogoutButton(Button button) {

        button.setPrefWidth(330);
        button.setPrefHeight(45);

        button.setStyle(
                "-fx-background-color: #E53935;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;"
        );
    }
}