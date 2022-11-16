package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

import ku.cs.controllers.AddNameController;
import ku.cs.router.FXRouter;

import java.sql.*;

/**
 * JavaFX App
 */
public class App extends Application {



    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "ProJava", 800, 600);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        FXRouter.setAnimationType("fade", 1200);
        String packageStr = "ku/cs/";
        FXRouter.when("login", packageStr+"login.fxml",800,600);
        FXRouter.when("list", packageStr+"list.fxml",1024,768);
        FXRouter.when("register", packageStr+"register.fxml",800,600);
        FXRouter.when("myshop", packageStr+"myshop.fxml",450,350);
        FXRouter.when("addname", packageStr+"addname.fxml",1024,768);
        FXRouter.when("admin", packageStr+"admin.fxml");
        FXRouter.when("registerpicture", packageStr+"registerpicture.fxml",450,350);
        FXRouter.when("additem",packageStr+"additem.fxml",800,600);
        FXRouter.when("additemimage",packageStr+"additemimage.fxml",450,350);
        FXRouter.when("addpenalties",packageStr+"addpenalties.fxml",1024,768);
        FXRouter.when("usersetting",packageStr+"usersetting.fxml",800 ,600);
        FXRouter.when("adminsetting",packageStr+"adminsetting.fxml",800,600);
        FXRouter.when("adminsetting",packageStr+"adminsetting.fxml",800 ,600);




    }
    public static void main(String[] args) {
        launch();

    }
    Connection con;
    PreparedStatement pst;
    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc://localhost/human", "root", "");
            System.out.println("Database Connected");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}