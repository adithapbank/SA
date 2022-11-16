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

public class LoginController {
    private User test;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label warningMessageLabel;

    @FXML
    public void initialize()
    {
        System.out.println("Enter Login");
        warningMessageLabel.setText("");
    }


    public void handleLoginButton(ActionEvent event){

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();


        try {
            User user = userList.SearchByUsername(username);
            if(user != null && user.checkPassword(password)) {
                dataSource = new UserListFileDataSource();
                dataSource.writeData(userList);
                FXRouter.goTo("list",user);
            }else{
                warningMessageLabel.setText("ใส่ข้อมูลไม่ถูกต้อง หรือยังไม่ได้ลงทะเบียน");
            }
        } catch (IOException e) {
            System.err.println("ไปที่หน้า list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    public void handleRegisterButton(ActionEvent event){
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
