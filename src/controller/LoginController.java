package controller;

import dao.PessoaDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Pessoa;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    public void login(){
        try {
            Pessoa pessoa = PessoaDao.consultaLogin(txtEmail.getText(), txtSenha.getText());

            if (pessoa != null){
                Stage stage = (Stage) txtSenha.getScene().getWindow();
                stage.close();
                lblMensagem.setText("");

                FXMLLoader loaderHome = new FXMLLoader(getClass().getResource("/view/home.fxml"));
                BorderPane root = (BorderPane)loaderHome.load();
                HomeController instancia = (HomeController)loaderHome.getController();
                instancia.lblUser.setText("Olá, " + pessoa.getNome());
                instancia.user = pessoa;

                Scene scene = new Scene(root);
                Main.primaryStage.setScene(scene);
                //Main.primaryStage.setMaximized(true);
                Main.primaryStage.setTitle("Control Sale");
                Main.primaryStage.show();
            }else{
                lblMensagem.setText("Usuário ou Senha inválido.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
