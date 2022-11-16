package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.router.FXRouter;
import ku.cs.services.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPenaltiesController {
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private boolean update;
    String emId;

    @FXML
    private TextField departmentTextField;
    @FXML
    private TextField errorLevelTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField salaryTextField;

    @FXML
    public void initialize() {
        Connect();
        clearSelectedItem();

    }

    @FXML
    public void handleFixDataItemButton(ActionEvent actionEvent) {
        Connect();
        String E_Name = nameTextField.getText();
        String Depart_Name = departmentTextField.getText();
        String P_ID = errorLevelTextField.getText();
        Double E_Salary = Double.parseDouble(salaryTextField.getText().trim());

        getQuery();
        insert();
//        try {
//                preparedStatement = connection.prepareStatement("UPDATE employee set E_Name=?,Depart_Name=?,E_Salary=?,P_ID=? where E_ID = ?");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameTextField.getText());
            preparedStatement.setString(2, departmentTextField.getText());
            preparedStatement.setString(3, salaryTextField.getText());
            preparedStatement.setString(4, errorLevelTextField.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AddPenaltiesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void getQuery() {

            query = "UPDATE `employee` SET "
                    + "`E_Name`=?,"
                    + "`Depart_Name`=?,"
                    + "`E_Salary`=?,"
                    + "`P_ID`= ? WHERE id = '"+emId+"'";


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

        errorLevelTextField.setText("");
    }

    @FXML
    public void handleBackToList(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("list");
        } catch (IOException e) {
            System.err.println("กลับหน้า list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    void setTextField(String E_ID, String E_Name, String Depart_Name, Double E_Salary, String P_ID) {

        emId = E_ID;
        nameTextField.setText(E_Name);
        departmentTextField.setText(Depart_Name);
        salaryTextField.setText(E_Salary.toString());
        errorLevelTextField.setText(P_ID);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }



}

