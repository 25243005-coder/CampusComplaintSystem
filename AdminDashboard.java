package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;

public class AdminDashboard {

    public void show(Stage stage, User user) {

        Label title = new Label("Admin Dashboard");
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
                "Manage and monitor student complaints"
        );
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setTextFill(Color.web("#78909C"));

        Button viewComplaints =
                new Button("VIEW COMPLAINTS");

        Button manageComplaints =
                new Button("MANAGE COMPLAINTS");

        Button logout =
                new Button("LOGOUT");

        stylePrimaryButton(viewComplaints);
        stylePrimaryButton(manageComplaints);
        styleLogoutButton(logout);

        // Existing functionality
        viewComplaints.setOnAction(e -> {
            new AdminComplaintsScreen().show(stage, user);
        });

        manageComplaints.setOnAction(e -> {
            new AdminComplaintsScreen().show(stage, user);
        });

        logout.setOnAction(e -> {
            new LoginScreen().show(stage);
        });

        VBox menu = new VBox(
                15,
                viewComplaints,
                manageComplaints,
                logout
        );

        menu.setAlignment(Pos.CENTER);
        menu.setMaxWidth(330);

        VBox content = new VBox(
                12,
                title,
                welcome,
                subtitle,
                new javafx.scene.layout.Region(),
                menu
        );

        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

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

        stage.setTitle("Admin Dashboard");
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