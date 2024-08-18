import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.util.List;

public class AppView {
    AppController controller;
    AppModel model;
    Stage primaryStage;

    AppView(AppController controller, AppModel model, Stage primaryStage){
        this.controller = controller;
        this.model = model;
        this.primaryStage = primaryStage;


    }
    Scene welcomePage(){
        Label welcomeLabel = new Label("Welcome to Management Housing Project");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Enter your username");
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Enter your password");

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(event -> createSignUpWindow());
        Button submitBtn = new Button("Login");
        submitBtn.setOnAction(event -> {
            String username = usernameTextField.getText().trim();
            String password = passwordTextField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()){
                if (controller.loginRequest(username,password).equals("Admin")){
                    primaryStage.setScene(homePageAdmin());
                } else if (controller.loginRequest(username,password).equals("User")){
                    primaryStage.setScene(homePageUser());
                } else if (controller.loginRequest(username,password).equals("Invalid")){
                    createNotificationWindow("The username or password is incorrect!!!");
                }
            } else {
                createNotificationWindow("the username or password can not be empty!!!");
            }
        });

        HBox welcomeRow = new HBox(welcomeLabel);
        welcomeRow.setAlignment(Pos.TOP_CENTER);
        HBox usernameLoginRow = new HBox(6, usernameLabel, usernameTextField);
        usernameLoginRow.setAlignment(Pos.CENTER);
        HBox passwordLoginRow = new HBox(6, passwordLabel, passwordTextField);
        passwordLoginRow.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(10,submitBtn,signUpBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox userLoginRow = new VBox(5, usernameLoginRow,passwordLoginRow);
        userLoginRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(50,welcomeRow,userLoginRow,buttonsRow);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,600);
    }
    public void createNotificationWindow(String announcement){
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label annoucementLabel = new Label(announcement);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> stage.close());

        VBox root = new VBox(20, annoucementLabel, closeBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        stage.show();

    }

    Scene homePageUser() {
        Label userProfile = new Label();
        userProfile.textProperty().bind(controller.userProfile());
        Label homePageLabel = new Label("HOME PAGE");

        Button accountBtn = new Button("Account");
        accountBtn.setOnAction(e -> primaryStage.setScene(accountUserPage()));
        Button mailBoxBtn = new Button("MailBox" + "(" + controller.numberOfMails().get() + ")");
        controller.numberOfMails().addListener((obs,oldValue,newValue) -> mailBoxBtn.setText("MailBox" + "(" + controller.numberOfMails().get() + ")"));
        Button marketBtn = new Button("Market");
        Button managementBtn = new Button("Management");
        Button logOutBtn = new Button("Log out");

        VBox buttonsCol = new VBox(5, accountBtn,mailBoxBtn,managementBtn,marketBtn);
        VBox userCol = new VBox(5,userProfile,logOutBtn);
        HBox mainRow = new HBox(20, userCol,buttonsCol);
        mainRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(5, homePageLabel, mainRow);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,600);
    }
    Scene accountUserPage() {
        Label accountPageLabel = new Label("ACCOUNT PAGE");
        Label name = new Label();
        name.textProperty().bind(controller.nameCurrentUser());
        Label nameLabel = new Label("Name: ");
        Label address = new Label();
        address.textProperty().bind(controller.addressCurrentUser().asString());
        Label addressslabel = new Label("Address: ");
        Label phoneNumber = new Label();
        phoneNumber.textProperty().bind(controller.phoneNumberCurrentUser());
        Label phoneNumberLabel = new Label("Phone Number: ");
        Label username = new Label();
        username.textProperty().bind(controller.usernameCurrentUser());
        Label usernameLabel = new Label("Username: ");
        Label password = new Label();
        password.setText("********");
        Label passwordLabel = new Label("Password: ");

        Button visiblePassBtn = new Button("Show");
        visiblePassBtn.setOnAction(event -> {
            if (visiblePassBtn.getText().equals("Show")){
                visiblePassBtn.setText("Hide");
                password.setText(controller.passwordCurrentUser().get());
            } else {
                visiblePassBtn.setText("Show");
                password.setText("********");
            }
        });
        Button editNameBtn = new Button("Edit");
        editNameBtn.setOnAction(event -> {
            String value = createEditWindow("What is the name that you want to change?",true);
            if (!value.equals("null")){
                controller.setNameCurrentUser(value);
            }
        });
        Button editAddressBtn = new Button("Edit");
        Button editPhoneNumberBtn = new Button("Edit");
        Button editUsernameBtn = new Button("Edit");
        Button editPasswordBtn = new Button("Edit");
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        HBox nameRow = new HBox(20, nameLabel,name,editNameBtn);
        nameRow.setAlignment(Pos.CENTER);
        HBox addressRow = new HBox(20, addressslabel,address,editAddressBtn);
        addressRow.setAlignment(Pos.CENTER);
        HBox phoneNumberRow = new HBox(20, phoneNumberLabel,phoneNumber, editPhoneNumberBtn);
        phoneNumberRow.setAlignment(Pos.CENTER);
        HBox usernameRow = new HBox(20, usernameLabel,username, editUsernameBtn);
        usernameRow.setAlignment(Pos.CENTER);
        HBox passwordBtns = new HBox(10,visiblePassBtn,editPasswordBtn);
        passwordBtns.setAlignment(Pos.CENTER);
        HBox passwordRow = new HBox(20, passwordLabel,password, passwordBtns);
        passwordRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10,accountPageLabel,nameRow,addressRow,phoneNumberRow,usernameRow,passwordRow,backBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root,600,600);
    }
    public String createEditWindow(String prompt, boolean confirm){
        Stage stage = new Stage();
        stage.setTitle("Edit Information");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label(prompt);
        Label confirmLabel = new Label("Confirm Information");

        TextField textField = new TextField();
        textField.setPromptText("Enter your information");
        TextField confirmTextField = new TextField();
        confirmTextField.setPromptText("Re-Enter your information");

        Button submitBtn = new Button("Submit");
        Button cancelBtn = new Button("Cancel");

        final String[] result = new String[1];
        cancelBtn.setOnAction(event -> {
            result[0] = "null";
            stage.close();
        });

        HBox buttonsRow = new HBox(20, submitBtn,cancelBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.show();
        if (confirm){
            root.getChildren().addAll(title,textField,confirmLabel,confirmTextField,buttonsRow);
            submitBtn.setOnAction(event -> {
                String valueInput = textField.getText().trim();
                String valueConfirm = confirmTextField.getText().trim();
                if (valueInput.equals(valueConfirm)){
                    result[0] = valueInput;
                } else {
                    createNotificationWindow("Value does not match");
                }
            });
        } else {
            root.getChildren().addAll(title,textField,buttonsRow);
            submitBtn.setOnAction(event -> {
                String valueInput = textField.getText().trim();
                result[0] = valueInput;
            });
        }
        return result[0];
    }

    void createSignUpWindow(){
        Stage stage = new Stage();
        stage.setTitle("Sign Up");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address (Areas in Sydney)");
        Label phoneNumberLabel = new Label("Phone Number");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm password");
        Label roleLabel = new Label("Your are:");

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Type in your name (Ex: John, Emily,..)");
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Please type in with lowercase (Ex: central, st peters,...");
        TextField phoneNumberTextField = new TextField();
        phoneNumberTextField.setPromptText("Please type in a string of numbers (Ex: 04xxxxxxxx)");
        configTextFieldForInts(phoneNumberTextField);
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Ex: jt253, john123,...");
        TextField passwordtextField = new TextField();
        TextField confirmPasswordTextField = new TextField();

        ToggleGroup roletoggleGroup = new ToggleGroup();
        RadioButton agentToggleGroupBtn = new RadioButton("Agent");
        agentToggleGroupBtn.setToggleGroup(roletoggleGroup);
        RadioButton clientToggleGroupBtn = new RadioButton("Client");
        clientToggleGroupBtn.setToggleGroup(roletoggleGroup);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String name = nameTextField.getText().trim();
            String address = addressTextField.getText().trim();
            String phoneNumber = phoneNumberTextField.getText().trim();
            String username = usernameTextField.getText().trim();
            String password = passwordtextField.getText().trim();
            String confirmPassword = confirmPasswordTextField.getText().trim();
            String role;
            if (agentToggleGroupBtn.isSelected()){
                role = "Agent";
            } else if (clientToggleGroupBtn.isSelected()) {
                role = "Client";
            } else {
                role = "null";
                createNotificationWindow("Please choose your role");
            }
            if (!role.equals("null")) {
                List<String> errors = controller.signUpRequest(name,address,phoneNumber,username,password,confirmPassword,role);
                if (errors.isEmpty()){
                    createNotificationWindow("Your Account has been created!\nThanks for choosing to use our services");
                } else {
                    String errorString = "";
                    for (String error : errors){
                        errorString += "\n" + error;
                    }
                    createNotificationWindow(errorString);
                }
            }

        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> stage.close());

        VBox setName = new VBox(5, nameLabel, nameTextField);
        setName.setAlignment(Pos.CENTER_LEFT);
        VBox setAddress = new VBox(5, addressLabel,addressTextField);
        setAddress.setAlignment(Pos.CENTER_LEFT);
        VBox setPhoneNumber = new VBox(5,phoneNumberLabel,phoneNumberTextField);
        setPhoneNumber.setAlignment(Pos.CENTER_LEFT);
        VBox setUsername = new VBox(5, usernameLabel, usernameTextField);
        setUsername.setAlignment(Pos.CENTER_LEFT);
        VBox setPassword = new VBox(5, passwordLabel, passwordtextField);
        setPassword.setAlignment(Pos.CENTER_LEFT);
        VBox setConfirmPassword = new VBox(5, confirmPasswordLabel, confirmPasswordTextField);
        setConfirmPassword.setAlignment(Pos.CENTER_LEFT);
        HBox roleBtns = new HBox(40, agentToggleGroupBtn,clientToggleGroupBtn);
        roleBtns.setAlignment(Pos.CENTER);
        VBox setRole = new VBox(5,roleLabel,roleBtns);
        setRole.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(10, submitBtn,cancelBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(25, setName, setAddress, setPhoneNumber, setUsername, setPassword, setConfirmPassword, setRole, buttonsRow);

        Scene scene = new Scene(root,500,600);

        stage.setScene(scene);
        stage.show();
    }
    Scene homePageAdmin() {
        Label homePageLabel = new Label("ADMIN MODE");

        Button accountBtn = new Button("Account");
        Button managementBtn = new Button("Management");
        Button logOutBtn = new Button("Log out");

        VBox buttonsCol = new VBox(5, accountBtn,managementBtn,logOutBtn);
        buttonsCol.setAlignment(Pos.CENTER);
        VBox root = new VBox(5, homePageLabel, buttonsCol);
        root.setAlignment(Pos.CENTER);
        return new Scene(root);
    }
    protected void configTextFieldForInts(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            }
            return null;
        }));
    }
}
