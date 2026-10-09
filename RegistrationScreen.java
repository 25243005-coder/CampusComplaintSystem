package ui;

import dao.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistrationScreen {

    public void show(Stage stage) {

        Label title = new Label("Student Registration");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.DARKBLUE);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Enter department");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");

        Button registerButton = new Button("REGISTER");
        Button backButton = new Button("BACK TO LOGIN");

        registerButton.setPrefWidth(200);
        registerButton.setStyle(
                "-fx-background-color: #1E88E5;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        backButton.setPrefWidth(200);
        backButton.setStyle(
                "-fx-background-color: #757575;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        Label message = new Label();

        registerButton.setOnAction(e -> {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            String department = departmentField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                department.isEmpty() ||
                phone.isEmpty()) {

                message.setTextFill(Color.RED);
                message.setText("Please fill all fields.");
                return;
            }

            UserDAO userDAO = new UserDAO();

            boolean success = userDAO.registerStudent(
                    name,
                    email,
                    password,
                    department,
                    phone
            );

            if (success) {

                message.setTextFill(Color.GREEN);
                message.setText(
                        "Registration successful! You can login now."
                );

                nameField.clear();
                emailField.clear();
                passwordField.clear();
                departmentField.clear();
                phoneField.clear();

            } else {

                message.setTextFill(Color.RED);
                message.setText(
                        "Registration failed. Email may already exist."
                );
            }
        });

        backButton.setOnAction(e -> {
            new LoginScreen().show(stage);
        });

        VBox form = new VBox(
                12,
                title,
                new Label("Name"),
                nameField,
                new Label("Email"),
                emailField,
                new Label("Password"),
                passwordField,
                new Label("Department"),
                departmentField,
                new Label("Phone"),
                phoneField,
                registerButton,
                backButton,
                message
        );

        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));
        form.setMaxWidth(450);

        StackPane root = new StackPane(form);

        root.setStyle(
                "-fx-background-color: #F4F8FC;"
        );

        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Student Registration");
        stage.setScene(scene);
        stage.show();
    }
}
