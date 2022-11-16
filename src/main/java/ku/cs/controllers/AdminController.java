package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.router.FXRouter;
import ku.cs.services.UserListFileDataSource;

import java.io.IOException;

public class AdminController {

    @FXML private ListView<User> usersListView;
    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private Label surnameLabel;
    @FXML private Label storeNameLabel;

    @FXML
    private ImageView userImageView;
    private UserListFileDataSource dataSource;
    private UserList userList;
    private User user;

    @FXML
    public void initialize() {
        dataSource = new UserListFileDataSource();
        userList = dataSource.readData();
        user = (User) FXRouter.getData();
        userImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        showListView();
        clearSelectedUser();
        handleSelectedListView();
    }

    private void showListView() {
        usersListView.getItems().addAll(userList.getAllUsers());
        usersListView.refresh();
    }

    private void handleSelectedListView() {
        usersListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable,
                                        User oldValue, User newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedUser(newValue);
                    }
                });
    }

    private void showSelectedUser(User user) {
        nameLabel.setText(user.getName());
        surnameLabel.setText(user.getSurname());
        usernameLabel.setText(user.getUsername());
        String storeName = user.getIdName();
        if(storeName.equals("null"))
            storeName = "";
        storeNameLabel.setText(storeName);
        String userImagePath = "/" + user.getImagePath();
        userImageView.setImage(new Image("file:"+user.getImagePath(), true));
    }

    private void clearSelectedUser() {
        usernameLabel.setText("");
        nameLabel.setText("");
        surnameLabel.setText("");
        storeNameLabel.setText("");
    }

    @FXML
    public void handleAdminSettingButton(ActionEvent actionEvent){
        try {
            User admin = (User) FXRouter.getData();
            FXRouter.goTo("adminsetting", admin);
        } catch (IOException e) {
            System.err.println("กลับหน้า adminsetting ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("กลับหน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}