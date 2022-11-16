package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.models.*;
import ku.cs.router.FXRouter;
import ku.cs.services.DataSource;
import ku.cs.services.ItemListFileDataSource;
import ku.cs.services.StringConfiguration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListController {
    @FXML private TableView<Item> itemTableView;
    @FXML private Button adminButton;
    @FXML private Label userNameLabel;
    @FXML private ImageView itemImageView;
    @FXML private ImageView userImageView;
    @FXML private Label nameSurNameLabel;
    @FXML private Button fixGoToAddPenalties;
    @FXML private Label idNameLabel;
    @FXML private Label nameLabel;
    @FXML private Label descriptionTextField;
    @FXML private Label errorLevelLabel;
    @FXML private TableColumn<Item, String> idCol;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> levelCol;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private User user;

    private DataSource<ItemList> dataSource;
    private ItemList itemList;
    private ObservableList<Item> itemObservableList;

    private Item selectedItem;

    @FXML
    public void initialize()
    {
        System.out.println("Enter List");
        user = (User) FXRouter.getData();
        itemImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        userImageView.setImage(new Image("file:"+user.getImagePath() ,true));
        userNameLabel.setText(user.getUsername());
        nameSurNameLabel.setText(user.getName()+ " " +user.getSurname());

        if(user.getType().equals("user")){
            adminButton.setDisable(true);
            fixGoToAddPenalties.setDisable(true);}
        clearSelectedItem();
        loadData();
        showItemData();
//        showSelectedItem();

//        dataSource = new ItemListFileDataSource();
//        itemList = dataSource.readData();
//        showItemData();

//        itemTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            showSelectedItem(newValue);
//        });
    }

    @FXML
    private void showItemData() {
        ObservableList<Item> ItemList = FXCollections.observableArrayList();
        try {
            query = "SELECT * FROM `employee` ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ItemList.add(new Item(
                        resultSet.getString("E_ID"),
                        resultSet.getString("E_Name"),
                        resultSet.getString("P_ID")));
                itemTableView.setItems(ItemList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData() {
        Connect();
        idCol.setCellValueFactory(new PropertyValueFactory<>("idName"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("errorLevel"));

    }
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "");
            System.out.println("Database Connected");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    private void clearSelectedItem() {
        selectedItem = null;

        idNameLabel.setText("");
        nameLabel.setText("");
        errorLevelLabel.setText("");
        descriptionTextField.setText("");

        itemImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        idNameLabel.setDisable(true);
    }


    private void showSelectedItem(Item item) {
        selectedItem = item;
        idNameLabel.setText(item.getIdName());
        nameLabel.setText(item.getName());
        errorLevelLabel.setText(item.getErrorLevel());
        descriptionTextField.setText(item.getDescription());
        idNameLabel.setDisable(false);


    }
    @FXML
    public void handleGoToAddPenalties(ActionEvent actionEvent){
//        try {
//            FXRouter.goTo("addpenalties",user);
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า addpenalties ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ku/cs/addpenalties.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }  catch (IOException ex) {
            System.err.println("ไปหน้า addpenalties ไม่ได้");
        }
    }


    @FXML
    public void handleSettingButton(ActionEvent actionEvent){
        if (user.getType().equals("user")){
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/ku/cs/usersetting.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }  catch (IOException ex) {
                System.err.println("ไปหน้า setting ไม่ได้");
            }} else if (user.getType().equals("admin"))
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/ku/cs/adminsetting.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }  catch (IOException ex) {
                System.err.println("ไปหน้า setting ไม่ได้");
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

    @FXML
    public void handleGoToAddName(ActionEvent actionEvent){
        try {
            FXRouter.goTo("addname");
//            Parent parent = FXMLLoader.load(getClass().getResource("/ku/cs/addname.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
        }  catch (IOException ex) {
            System.err.println("ไปหน้า addname ไม่ได้");
        }
    }

}
