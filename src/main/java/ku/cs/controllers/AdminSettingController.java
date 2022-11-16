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
import ku.cs.services.Check;
import ku.cs.services.DataSource;
import ku.cs.services.UserListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class AdminSettingController {

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private ImageView adminImageView;
    @FXML private Button updateNameAndSurname;
    @FXML private Button updatePassword;
    @FXML private Button browseButton;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private Label warningNameMessageLabel;
    @FXML private Label warningPasswordMessageLabel;

    private User admin;

    @FXML
    public void initialize()
    {
        System.out.println("Setting Picture");
        admin = (User) FXRouter.getData();
        String path = "/" + admin.getImagePath();
        adminImageView.setImage(new Image("file:"+ admin.getImagePath(), true));
        nameTextField.setText(admin.getName());
        surnameTextField.setText(admin.getSurname());
        warningNameMessageLabel.setText("");
        warningPasswordMessageLabel.setText("");

    }

    @FXML public void handleUpdateNameAndSurname(ActionEvent event) {

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User changeAdmin = userList.SearchByUsername(admin.getUsername());

        changeAdmin.setName(nameTextField.getText().trim());
        changeAdmin.setSurname(surnameTextField.getText().trim());
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();

        if(!(name.isBlank() || surname.isBlank())) {
            changeAdmin.setName(name);
            changeAdmin.setSurname(surname);
            dataSource = new UserListFileDataSource();
            dataSource.writeData(userList);
            warningNameMessageLabel.setText("");
        }else
            warningNameMessageLabel.setText("โปรดใส่ข้อมูลให้ครบถ้วน");
    }

    @FXML public void handleUpdatePassword(ActionEvent event) {

        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User changeAdmin = userList.SearchByUsername(admin.getUsername());

        String password = currentPasswordField.getText();
        String passwordNew = newPasswordField.getText();
        String confirmPasswordNew = confirmNewPasswordField.getText();

        if(changeAdmin.checkPassword(password) && passwordNew.equals(confirmPasswordNew) && !(password.isBlank() || passwordNew.isBlank() || confirmPasswordNew.isBlank())) {
            changeAdmin.setPassword(passwordNew);
            dataSource = new UserListFileDataSource();
            dataSource.writeData(userList);
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmNewPasswordField.setText("");
            warningPasswordMessageLabel.setText("");
        }else if(password.isBlank() || confirmPasswordNew.isBlank()) {
            warningPasswordMessageLabel.setText("โปรดใส่ข้อมูลให้ครบถ้วน");
        }else if(!changeAdmin.checkPassword(password)) {
            warningPasswordMessageLabel.setText("โปรดใส่รหัสเดิมให้ถูกต้อง");
        }else if(!passwordNew.equals(confirmPasswordNew)){
            warningPasswordMessageLabel.setText("โปรดยืนยันรหัสให้เหมือนกัน");
        }
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
                File destDir = new File("images/admin");
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
                adminImageView.setImage(new Image(target.toUri().toString()));
                User newUser = userList.SearchByUsername(admin.getUsername());
                newUser.setImagePath("images" + "/" + "admin" + "/" + filename);
                dataSource.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDoneButton(ActionEvent event){
        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        User admin = userList.SearchByUsername(this.admin.getUsername());
        try {
            FXRouter.goTo("marketplace",admin);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}