package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage primaryStage;
    public static Boolean debug = false;

    @Override
    public void start(Stage ps) throws Exception{

        try {
            primaryStage = ps;
           // debug = true;

            //primaryStage.getIcons().add(new Image("/util/imagens/ilustracao.png"));

            if (debug){
                BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/home.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/util/tab.css");
                scene.getStylesheets().add("/util/table.css");

                //Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                primaryStage.setTitle("SISTEMA DE VENDA - CCP");
                primaryStage.setScene(scene);
                primaryStage.show();
            }else{
                Stage stageLogin = new Stage();
                FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/view/login/index.fxml"));
                Parent rootLogin = loaderLogin.load();

                stageLogin.setScene(new Scene(rootLogin));
                stageLogin.initModality(Modality.WINDOW_MODAL);
                stageLogin.setResizable(false);
                stageLogin.setTitle("Control Sale • Login");
                //stageLogin.initStyle(StageStyle.UNDECORATED);
                stageLogin.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
