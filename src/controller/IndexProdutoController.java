package controller;

import dao.ProdutoDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CategoriaProduto;
import model.Produto;
import util.Util;

import java.util.ArrayList;

public class IndexProdutoController {

    @FXML TableView<Produto> tbl;
    @FXML TableColumn<Produto, String> colDescricao;
    @FXML TableColumn<Produto, Number> colValor;
    @FXML TableColumn<Produto, String> colCategoria;
    @FXML TableColumn<Produto, Boolean> colAtivo;
    @FXML TextField txtFiltro;

    @FXML
    public void initialize() {
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty());
        colCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().descricaoProperty());
        colAtivo.setCellValueFactory(cellData -> cellData.getValue().ativoProperty());
        exibirProduto();
    }

    protected void exibirProduto(){
        ArrayList<Produto> lista = ProdutoDao.listaTodas();
        tbl.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    public void create(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/produto/create.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaProdutoController instancia = (ManipulaProdutoController)loaderCreateVenda.getController();
            instancia.index = this;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Produto");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void edit(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/produto/create.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaProdutoController instancia = (ManipulaProdutoController)loaderCreateVenda.getController();
            instancia.selecionado = tbl.getSelectionModel().getSelectedItem();
            instancia.atualizaObjeto();
            instancia.index = this;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Produto");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void filter() {
        String filtro = txtFiltro.getText().toUpperCase();
        ArrayList<Produto> filtradas = ProdutoDao.filter(filtro);
        tbl.setItems(FXCollections.observableArrayList(filtradas));
    }

}
