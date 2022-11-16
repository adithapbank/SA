package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.router.FXRouter;
import ku.cs.services.DataSource;
import ku.cs.services.UserListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserSettings {

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private ImageView userImageView;
    @FXML private Button updateNameAndSurname;
    @FXML private Button updatePassword;
    @FXML private Button browseButton;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private Label warningNameMessageLabel;
    @FXML private Label warningPasswordMessageLabel;

    private User user;


    @FXML
    public void initialize()
    {
        System.out.println("Setting Picture");
        user = (User) FXRouter.getData();
        if (user.getImagePath().equals("null")){
            userImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        }
        else {
            userImageView.setImage(new Image("file:" + user.getImagePath() , true ));
        }
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        warningNameMessageLabel.setText("");
        warningPasswordMessageLabel.setText("");

    }
    @FXML public void handleUpdateNameAndSurname(ActionEvent event) {

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User changeUser = userList.SearchByUsername(user.getUsername());

        changeUser.setName(nameTextField.getText().trim());
        changeUser.setSurname(surnameTextField.getText().trim());
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();

        if(!(name.isBlank() || surname.isBlank())) {
            changeUser.setName(name);
            changeUser.setSurname(surname);
            dataSource = new UserListFileDataSource();
            dataSource.writeData(userList);
            warningNameMessageLabel.setText("");
        }else
            warningNameMessageLabel.setText("โปรดใส่ข้อมูลให้ครบถ้วน");
    }

    @FXML public void handleUpdatePassword(ActionEvent event) {

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User changeUser = userList.SearchByUsername(user.getUsername());

        String password = currentPasswordField.getText();
        String passwordNew = newPasswordField.getText();
        String confirmPasswordNew = confirmNewPasswordField.getText();

        if(changeUser.checkPassword(password) && passwordNew.equals(confirmPasswordNew) && !(password.isBlank() || passwordNew.isBlank() || confirmPasswordNew.isBlank())) {
            changeUser.setPassword(passwordNew);
            dataSource = new UserListFileDataSource();
            dataSource.writeData(userList);
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmNewPasswordField.setText("");
            warningPasswordMessageLabel.setText("");
        }else if(password.isBlank() || confirmPasswordNew.isBlank()) {
            warningPasswordMessageLabel.setText("โปรดใส่ข้อมูลให้ครบถ้วน");
        }else if(!changeUser.checkPassword(password)) {
            warningPasswordMessageLabel.setText("โปรดใส่รหัสเดิมให้ถูกต้อง");
        }else if(!passwordNew.equals(confirmPasswordNew)){
            warningPasswordMessageLabel.setText("โปรดยืนยันรหัสให้เหมือนกัน");
        }
    }


    private void clearSelectedMemberCard() {
        nameTextField.clear();
        surnameTextField.clear();
        updateNameAndSurname.setDisable(false);
        updatePassword.setDisable(false);
        browseButton.setDisable(false);
    }

    @FXML public void handleUploadButton(ActionEvent event){
        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images/Users");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                userImageView.setImage(new Image(target.toUri().toString()));
                User newUser = userList.SearchByUsername(user.getUsername());
                newUser.setImagePath("images"+ "/"+ "Users" + "/" + filename);
                dataSource.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleLoginButton(ActionEvent event){
        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User newUser = userList.SearchByUsername(user.getUsername());
        try {

            if (newUser.getImagePath().equals("null") ){
                newUser.setImagePath("images/default.png");
                dataSource.writeData(userList);
            }
            FXRouter.goTo("marketplace",newUser);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}