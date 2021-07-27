package controller;

import dao.ProdutoDao;
import dao.VendaDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Pessoa;
import model.Produto;
import model.Venda;
import util.Mensagens;
import util.Util;

import java.util.ArrayList;

public class IndexVendaController {

    @FXML TableView<Venda> tbl;
    @FXML TableColumn<Venda, String> colCliente;
    @FXML TableColumn<Venda, String> colValor;
    @FXML TableColumn<Venda, String> colData;
    @FXML TableColumn<Venda, String> colSituacao;
    @FXML TextField txtFiltro;

    protected Pessoa user = null;

    @FXML
    public void initialize() {
        colCliente.setCellValueFactory(cellData -> cellData.getValue().getCliente().nomeProperty());
        colValor.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        colData.setCellValueFactory(cellData -> cellData.getValue().dataCriacaoProperty());
        colSituacao.setCellValueFactory(cellData -> cellData.getValue().situacaoProperty());
        exibirVendas();
    }

    protected void exibirVendas(){
        ArrayList<Venda> lista = VendaDao.listaTodas();
        tbl.setItems(FXCollections.observableArrayList(lista));
    }

    public void create(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/venda/create.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaVendaController instancia = (ManipulaVendaController) loaderCreateVenda.getController();
            instancia.controller = this;
            instancia.vendedor = user;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Venda");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void filter(){
        String filtro = txtFiltro.getText().toUpperCase();
        ArrayList<Venda> filtradas = VendaDao.filter(filtro);
        tbl.setItems(FXCollections.observableArrayList(filtradas));
    }
    public void edit(){
        if(Mensagens.msgOkCancel("Cancelar", "Confirma cancelamento da venda?")== ButtonType.OK) {
            Venda v = tbl.getSelectionModel().getSelectedItem();
            v.setSituacao("Cancelada");
            VendaDao.updateSituacao(v);
            exibirVendas();
        }
    }
}