package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ku.cs.models.Item;
import ku.cs.models.ItemList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.router.FXRouter;
import ku.cs.services.DataSource;
import ku.cs.services.ItemListFileDataSource;
import ku.cs.services.StringConfiguration;
import ku.cs.services.UserListFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class AddPenaltiesController {

    private User user;
    private Item item;

    @FXML private TableView<Item> itemTableView;
    @FXML private Label idNameLabel;
    @FXML private Label nameLabel;
    @FXML private Label departmentLabel;
    @FXML private Label salaryLabel;
    @FXML private TextField descriptionTextField;
    @FXML private TextField errorLevelTextField;
    @FXML private ImageView itemImageView;
    @FXML private Button fixItemDataButton;


    private DataSource<ItemList> dataSource;
    private ItemList itemList;
    private ObservableList<Item> itemObservableList;
    private Item selectedItem;


    @FXML
    public void initialize() {
        System.out.println("Enter MyShop");
        user = (User) FXRouter.getData();
        DataSource<UserList> dataSourceUser = new UserListFileDataSource();
        UserList userList = dataSourceUser.readData();


        dataSource = new ItemListFileDataSource();
        itemList = dataSource.readData();

        clearSelectedItem();
        showItemData();

        itemTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSelectedItem(newValue);
        });

    }

    private void clearSelectedItem() {
        selectedItem = null;
        idNameLabel.setText("");
        nameLabel.setText("");
        departmentLabel.setText("");
        salaryLabel.setText("");
        errorLevelTextField.setText("");
        descriptionTextField.setText("");
        itemImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        fixItemDataButton.setDisable(true);
    }

    private void showSelectedItem(Item item) {
        DataSource<UserList> dataSourceUser = new UserListFileDataSource();
        UserList userList = dataSourceUser.readData();
        User newUser = userList.SearchByUsername(user.getUsername());

        selectedItem = item;
        idNameLabel.setText(item.getIdName());
        nameLabel.setText(item.getName());
        departmentLabel.setText(item.getDepartment());
        salaryLabel.setText(String.valueOf(item.getSalary()));
        errorLevelTextField.setText(item.getErrorLevel());
        descriptionTextField.setText(item.getDescription());

        if (item.getImagePath().equals("null")) {
            itemImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        } else {
            itemImageView.setImage(new Image("file:" + item.getImagePath(), true));
        }
        fixItemDataButton.setDisable(false);
    }

    private void showItemData() {
        itemObservableList = FXCollections.observableArrayList(itemList.getAllItems());
        itemTableView.setItems(itemObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:รหัสพนักงาน", "field:idName"));
        configs.add(new StringConfiguration("title:รายชื่อ", "field:name"));
        configs.add(new StringConfiguration("title:แผนก", "field:department"));
        configs.add(new StringConfiguration("title:เงินเดือน", "field:salary"));
        configs.add(new StringConfiguration("title:ระดับความผิด", "field:errorLevel"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            itemTableView.getColumns().add(col);
        }
    }


    @FXML
    public void handleFixDataItemButton(ActionEvent actionEvent) {
        try {
            Item s = itemList.SearchByItemNameAndStoreName(item.getDescription());
            s.setDescription(descriptionTextField.getText().trim());
            dataSource.writeData(itemList);

            FXRouter.goTo("list");
        } catch (IOException e) {
            System.err.println("ไปหน้า list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
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