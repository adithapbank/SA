//package ku.cs.controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//import ku.cs.models.Item;
//import ku.cs.models.ItemList;
//import ku.cs.models.User;
//import ku.cs.router.FXRouter;
//import ku.cs.services.DataSource;
////import ku.cs.services.ItemListFileDataSource;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//
//public class AddItemImageController {
//    private Item item;
//
//
//    @FXML private ImageView itemImageView;
//
//
//    @FXML
//    public void initialize()
//    {
//        System.out.println("Enter AddItem");
//        item = (Item) FXRouter.getData();
//        itemImageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
//
//    }
//
//    @FXML public void handleUploadButton(ActionEvent event) {
//        DataSource<ItemList> dataSource = new ItemListFileDataSource();
//        ItemList itemList = dataSource.readData();
//
//        FileChooser chooser = new FileChooser();
//        // SET FILECHOOSER INITIAL DIRECTORY
//        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        // DEFINE ACCEPTABLE FILE EXTENSION
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
//        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
//        Node source = (Node) event.getSource();
//        File file = chooser.showOpenDialog(source.getScene().getWindow());
//        if (file != null){
//            try {
//                // CREATE FOLDER IF NOT EXIST
//                File destDir = new File("images/Items");
//                if (!destDir.exists()) destDir.mkdirs();
//                // RENAME FILE
//                String[] fileSplit = file.getName().split("\\.");
//                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
//                        + fileSplit[fileSplit.length - 1];
//                Path target = FileSystems.getDefault().getPath(
//                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
//                );
//                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
//                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
//                // SET NEW FILE PATH TO IMAGE
//                itemImageView.setImage(new Image(target.toUri().toString()));
//                item.setImagePath("images" + "/" + "Items" + "/" +filename);
//                itemList.addItem(item);
//                dataSource.writeData(itemList);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML public void handleConfirmButton(ActionEvent actionEvent){
//        DataSource<ItemList> dataSource = new ItemListFileDataSource();
//        ItemList itemList = dataSource.readData();
//        Item newItem = itemList.SearchByItemNameAndStoreName(item.getIdName());
////        item.getName()
//        if (newItem == null){
//            itemList.addItem(item);
//        }
//        dataSource.writeData(itemList);
//
//        try {
//            FXRouter.goTo("list");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า list ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//
//    @FXML public void handleBackToAddItem(ActionEvent actionEvent){
//        try {
//            FXRouter.goTo("addname");
//        } catch (IOException e) {
//            System.err.println("กลับหน้า addname");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//}
