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

    @FXML private TextField caseIdTextField;
    @FXML private TextField departTextField;
    @FXML private TextField caseTextField;
    @FXML private TextField idTextField;
    @FXML private Label warningMessageLabel;

    @FXML
    public void initialize() {
        Connect();

    }
    Connection con;
    PreparedStatement pst;
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "");
            System.out.println("Database Connected");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML
    public void handleAddButton(ActionEvent actionEvent) {
        String caseId = caseIdTextField.getText();
        String depart = departTextField.getText();
        String caseName = caseTextField.getText();
        String idName = idTextField.getText();

        try {
            if (!Check.isString(depart) || !Check.isString(caseName)) {
                warningMessageLabel.setText("กรุณาใส่ชื่อหรือคำอธิบายเป็นตัวอักษร");
            } else {
                if(!Check.isInteger(caseId)){
                    warningMessageLabel.setText("กรุณาใส่ตัวเลข");
                }else {
                    Integer case2 = Integer.parseInt(caseId);
                    pst = con.prepareStatement("insert into case1(Case_ID, Depart_Name, Case_Name, E_ID)value(?,?,?,?)");
                    pst.setString(1, caseId);
                    pst.setString(2, depart);
                    pst.setString(3, caseName);
                    pst.setString(4, idName);
                    pst.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Case");
                    alert.setHeaderText("Added");
                    alert.setContentText("");
                    alert.showAndWait();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}

