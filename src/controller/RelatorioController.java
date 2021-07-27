package controller;


import dao.PessoaDao;
import dao.VendaDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Pessoa;
import model.Venda;
import util.Util;

import java.util.ArrayList;

public class RelatorioController {

    @FXML private TextField txtData;
    @FXML private TextField txtSituacao;
    @FXML private ComboBox<Pessoa> cbCliente;
    @FXML private ComboBox<Pessoa> cbVendedor;

    @FXML
    public void initialize() {
        cbCliente.setItems(FXCollections.observableArrayList(PessoaDao.listaTodas(true)));
        cbVendedor.setItems(FXCollections.observableArrayList(PessoaDao.listaTodas( true)));
    }

    @FXML
    public void exportaCSV() {

    }

    @FXML
    public void exportaHTML() {
        ArrayList<Venda> vendas = VendaDao.vendasRelatorio(txtData.getText(), txtSituacao.getText(), cbCliente.getSelectionModel().getSelectedItem(), cbVendedor.getSelectionModel().getSelectedItem());
        Util.gerarRelatorioVenda(vendas);
    }

}
