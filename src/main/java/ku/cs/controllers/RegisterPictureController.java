package ku.cs.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class RegisterPictureController {

    @FXML private Button browseButton;
    @FXML private ImageView userImageView;


    private User user;


    @FXML
    public void initialize()
    {
        System.out.println("Setting Picture");
        user = (User) FXRouter.getData();
        userImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));

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
                newUser.setImagePath("images" + "/" + "Users" + "/" + filename);
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
