package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import dao.UserDAO;
import model.User;

public class LoginScreen {

    public void show(Stage stage) {

        // Title
        Label title = new Label("Campus Complaint System");
        title.setFont(
                Font.font("Arial", FontWeight.BOLD, 28)
        );
        title.setTextFill(Color.web("#123B63"));

        Label subtitle = new Label(
                "Login to manage and track campus complaints"
        );
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setTextFill(Color.web("#607D8B"));

        // Email
        Label emailLabel = new Label("Email");
        emailLabel.setFont(
                Font.font("Arial", FontWeight.BOLD, 14)
        );

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefHeight(42);
        emailField.setMaxWidth(380);

        // Password
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(
                Font.font("Arial", FontWeight.BOLD, 14)
        );

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(42);
        passwordField.setMaxWidth(380);

        // Login Button
        Button loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(380);
        loginButton.setPrefHeight(45);

        loginButton.setStyle(
                "-fx-background-color: #1976D2;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;"
        );

        // Register Button
        Button registerButton =
                new Button("NEW STUDENT? REGISTER");

        registerButton.setPrefWidth(380);
        registerButton.setPrefHeight(42);

        registerButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: #1976D2;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #1976D2;" +
                "-fx-border-width: 1.5px;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;"
        );

        // Message
        Label message = new Label();
        message.setFont(Font.font("Arial", 13));
        message.setWrapText(true);

        // Login functionality
        loginButton.setOnAction(e -> {

            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {

                message.setTextFill(Color.RED);
                message.setText(
                        "Please enter email and password."
                );

                return;
            }

            UserDAO userDAO = new UserDAO();

            User user = userDAO.login(
                    email,
                    password
            );

            if (user != null) {

                message.setText("");

                if (user.getRole().equals("STUDENT")) {

                    new StudentDashboard().show(
                            stage,
                            user
                    );

                } else if (user.getRole().equals("ADMIN")) {

                    new AdminDashboard().show(
                            stage,
                            user
                    );
                }

            } else {

                message.setTextFill(Color.RED);
                message.setText(
                        "Invalid email or password."
                );
            }
        });

        // Registration functionality
        registerButton.setOnAction(e -> {
            new RegistrationScreen().show(stage);
        });

        // Form
        VBox form = new VBox(
                10,
                title,
                subtitle,
                new Region(),
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                loginButton,
                registerButton,
                message
        );

        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(35));
        form.setMaxWidth(450);

        // Card
        VBox card = new VBox(form);

        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(480);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #D6E2EF;" +
                "-fx-border-radius: 15;" +
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

        stage.setTitle(
                "Campus Complaint System - Login"
        );

        stage.setScene(scene);
        stage.show();
    }
}