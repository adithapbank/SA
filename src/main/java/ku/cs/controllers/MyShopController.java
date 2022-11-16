package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.models.User;
import ku.cs.router.FXRouter;

import java.io.IOException;

public class MyShopController {
    private User user;
    @FXML
    public void initialize()
    {
        System.out.println("Enter MyShop");
        user = (User) FXRouter.getData();
    }

    @FXML
    public void handleGoToAddItemButton()
    {
        try {
            FXRouter.goTo("additem",user);
        } catch (IOException e) {
            System.err.println("ไปหน้า additem ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleGoToShowAllYourItem()
    {
        try {
            FXRouter.goTo("myitem",user);
        } catch (IOException e) {
            System.err.println("ไปหน้า myitem ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleBackToMarketPlace(ActionEvent actionEvent){
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("กลับหน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
