package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.router.FXRouter;
import ku.cs.services.DataSource;
import ku.cs.services.UserListFileDataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class RegisterController {
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField confirmPasswordTextField;
    @FXML private Label warningMessageLabel;

    @FXML
    public void initialize() {
        warningMessageLabel.setText("");
    }

    public void handleRegisterButton(ActionEvent actionEvent){

        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();

        try {
            User userRegister = userList.SearchByUsername(username);
            if(password.equals(confirmPassword) && (userRegister == null) && !(name.isBlank() || surname.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank())) {
                LocalDateTime localDateTime = LocalDateTime.now();
                User user = new User(name, surname, username, password);
                userList.addUser(user);
                dataSource = new UserListFileDataSource();
                dataSource.writeData(userList);
                FXRouter.goTo("registerpicture",user);
            }else if(name.isBlank() || surname.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                warningMessageLabel.setText("โปรดใส่ข้อมูลให้ครบถ้วน");
            }else if(userRegister != null){
                warningMessageLabel.setText("มี username นี้อยู่แล้ว");
            }else if(!password.equals(confirmPassword)){
                warningMessageLabel.setText("โปรดยืนยันรหัสให้เหมือนกัน");
            }
        } catch (IOException e) {
            System.err.println("กลับหน้า member_card_detail ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleBackToLoginButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("กลับหน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
