package ku.cs.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.models.Item;
import ku.cs.models.User;
import ku.cs.router.FXRouter;
import ku.cs.services.Check;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddNameController {
    private User user;
    private Item item;
    @FXML
    private TextField idNameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField departmentTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private Label warningMessageLabel3;

    @FXML
    public void initialize() {
        System.out.println("Enter AddName");
        user = (User) FXRouter.getData();
        warningMessageLabel3.setText("");
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
    public void handleAddItemButton(ActionEvent actionEvent) {

        String itemName = idNameTextField.getText();
        String describe = nameTextField.getText();
        String department = departmentTextField.getText();

        try {
            if (!Check.isString(department) || !Check.isString(describe)) {
                warningMessageLabel3.setText("กรุณาใส่ชื่อหรือคำอธิบายเป็นตัวอักษร");
            } else {
                double salary = Double.parseDouble(salaryTextField.getText().trim());

                if (salary > 0) {
                    pst = con.prepareStatement("insert into employee(E_ID,E_Name,Depart_Name,E_Salary,P_ID)value(?,?,?,?,?)");
                    pst.setString(1, itemName);
                    pst.setString(2,describe);
                    pst.setString(3,department);
                    pst.setDouble(4,salary);
                    pst.setString(5,"0");

                    pst.executeUpdate();
                    FXRouter.goTo("list");
                } else {
                    warningMessageLabel3.setText("กรุณากรอกข้อมูลให้ครบ");
                }

            }
        } catch (IOException e) {
            System.err.println("ไปที่หน้า additemimage ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        } catch (NumberFormatException e) {
            warningMessageLabel3.setText("กรุณากรอกข้อมูลให้ครบ");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void handleBackToList() {

        try {
            FXRouter.goTo("list");
        } catch (IOException e) {
            System.err.println("กลับหน้า list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }
}
