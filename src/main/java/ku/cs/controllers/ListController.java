package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.models.*;
import ku.cs.router.FXRouter;
import ku.cs.services.Check;
import ku.cs.services.DataSource;
//import ku.cs.services.ItemListFileDataSource;
import ku.cs.services.StringConfiguration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListController {
    @FXML private TableView<Item> itemTableView;
    @FXML private Button adminButton;
    @FXML private Label userNameLabel;
    @FXML private ImageView userImageView;
    @FXML private Label nameSurNameLabel;
    @FXML private TextField levelText;
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField depText;
    @FXML private TextField salaText;
    @FXML private Button addLevelBtn;
    @FXML private Button deleteCase;
    @FXML private Button deleteBtn;
    @FXML private Button caseData;
    @FXML private Label warningMessageLabel5;
    @FXML private TableColumn<Item, String> idCol;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> levelCol;
    @FXML private TableColumn<Item, String> penalCol;
    @FXML private TableColumn<Item, String> departCol;
    @FXML private TableColumn<Item, String> saCol;
    @FXML private TableColumn<Item, String> caseCol;
    @FXML private TableColumn<Item, String> caseIdCol;
    @FXML private Button goToPenalties;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private User user;

    private Item selectedItem;
    @FXML
    public void initialize()
    {
        System.out.println("Enter List");
        user = (User) FXRouter.getData();
        userNameLabel.setText(user.getUsername());
        nameSurNameLabel.setText(user.getName()+ " " +user.getSurname());

        if(user.getType().equals("user")){
            adminButton.setDisable(true);
            adminButton.setVisible(false);
            addLevelBtn.setVisible(false);
            addLevelBtn.setDisable(true);
            salaText.setDisable(true);
            idText.setDisable(true);
            depText.setDisable(true);
            nameText.setDisable(true);
            levelText.setDisable(true);
            goToPenalties.setDisable(true);
            goToPenalties.setVisible(false);
            deleteBtn.setDisable(true);
            deleteBtn.setVisible(false);
            deleteCase.setDisable(true);
            deleteCase.setVisible(false);
            caseData.setDisable(true);
            caseData.setVisible(false);
            }
        loadData();
        showItemData();


    }
    @FXML void showCaseData() {
        ObservableList<Item> ItemList = FXCollections.observableArrayList();
        try {
            query = "SELECT * FROM employee INNER JOIN penalty ON employee.P_ID = penalty.P_ID INNER JOIN case1 ON employee.E_ID = case1.E_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ItemList.add(new Item(
                        resultSet.getString("E_ID"),
                        resultSet.getString("E_Name"),
                        resultSet.getString("Depart_Name"),
                        resultSet.getDouble("E_Salary"),
                        resultSet.getInt("P_ID"),
                        resultSet.getInt("Case_ID"),
                        resultSet.getString("P_Name"),
                        resultSet.getString("Case_Name")
                        ));


                itemTableView.setItems(ItemList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showItemData() {
        ObservableList<Item> ItemList = FXCollections.observableArrayList();
        try {
            query = "SELECT * FROM employee INNER JOIN penalty ON employee.P_ID = penalty.P_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ItemList.add(new Item(
                        resultSet.getString("E_ID"),
                        resultSet.getString("E_Name"),
                        resultSet.getString("Depart_Name"),
                        resultSet.getDouble("E_Salary"),
                        resultSet.getInt("P_ID"),
                        resultSet.getString("P_Name")));

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
        departCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        saCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("errorLevel"));
        penalCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        caseCol.setCellValueFactory(new PropertyValueFactory<>("caseName"));
        caseIdCol.setCellValueFactory(new PropertyValueFactory<>("caseId"));


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
    public void handleGoToPenalties(ActionEvent actionEvent){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/ku/cs/addpenalties.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    public void handleSelect(MouseEvent mouseEvent) {
        Item item = itemTableView.getSelectionModel().getSelectedItem();
        idText.setText(""+item.getIdName());
        nameText.setText(""+item.getName());
        depText.setText(""+item.getDepartment());
        salaText.setText(""+item.getSalary());
        levelText.setText(""+item.getErrorLevel());
    }
    @FXML
    public void deleteCase(ActionEvent actionEvent) {
        try {
            Item item = itemTableView.getSelectionModel().getSelectedItem();
            preparedStatement = connection.prepareStatement("delete from case1 where Case_ID = "+item.getCaseId());
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Case Deleted");
            alert.setContentText("");
            alert.showAndWait();
            showCaseData();
        } catch (SQLException e) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    @FXML
    public void deleteBtn(ActionEvent actionEvent) {
        try {
            Item item = itemTableView.getSelectionModel().getSelectedItem();
            preparedStatement = connection.prepareStatement("delete from employee where E_ID = "+item.getIdName());
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Deleted");
            alert.setContentText("");
            alert.showAndWait();
            showItemData();
        } catch (SQLException e) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void addLevelBtn(ActionEvent actionEvent) {
        String id = idText.getText();
        String name = nameText.getText();
        String dep = depText.getText();
        String sala = salaText.getText();
        String lev = levelText.getText();
        if(!Check.isInteger(lev)){
            warningMessageLabel5.setText("กรุณาใส่ตัวเลข");
        } else {
            Integer lev2 = Integer.parseInt(lev);
            if(lev2 < 0 || lev2 > 6){
                warningMessageLabel5.setText("กรุณาใส่ตัวเลข 0-6");
            }else{
                try{
                    preparedStatement = connection.prepareStatement("UPDATE employee SET E_Name = ?, Depart_Name = ?, E_Salary = ? ,P_ID = ? where E_ID = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2,dep);
                    preparedStatement.setString(3,sala);
                    preparedStatement.setString(4,lev);
                    preparedStatement.setString(5,id);
                    preparedStatement.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Level Add");
                    alert.setHeaderText("Level Updated");
                    alert.setContentText("");
                    alert.showAndWait();
                    showItemData();
                    warningMessageLabel5.setText("");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
