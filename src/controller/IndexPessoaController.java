package controller;

import dao.PessoaDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Pessoa;
import util.Util;
import java.util.ArrayList;

public class IndexPessoaController {

    @FXML TableView<Pessoa> tbl;
    @FXML TableColumn<Pessoa, String> colNome;
    @FXML TableColumn<Pessoa, String> colEmail;
    @FXML TableColumn<Pessoa, String> colTipo;
    @FXML TableColumn<Pessoa, Boolean> colAtivo;
    @FXML TextField txtFiltro;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        colAtivo.setCellValueFactory(cellData -> cellData.getValue().ativoProperty());
        exibirPessoa();
    }

    protected void exibirPessoa(){
        ArrayList<Pessoa> lista = PessoaDao.listaTodas();
        tbl.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void create(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreate = new FXMLLoader(Util.class.getResource("/view/pessoa/create.fxml"));
            Parent rootLogin = loaderCreate.load();

            ManipulaPessoaController instancia = (ManipulaPessoaController)loaderCreate.getController();
            instancia.index = this;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Pessoa");
            stageCreateVenda.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void edit(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/pessoa/create.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaPessoaController instancia = (ManipulaPessoaController)loaderCreateVenda.getController();
            instancia.selecionado = tbl.getSelectionModel().getSelectedItem();
            instancia.atualizaObjeto();
            instancia.index = this;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Pessoa");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void filter() {
        String filtro = txtFiltro.getText().toUpperCase();
        ArrayList<Pessoa> filtradas = PessoaDao.filter(filtro);
        tbl.setItems(FXCollections.observableArrayList(filtradas));
    }

}
