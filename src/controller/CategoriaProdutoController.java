package controller;

import dao.CategoriaProdutoDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.CategoriaProduto;

import java.util.ArrayList;

public class CategoriaProdutoController {

    @FXML TextField txtDescricao;
    @FXML CheckBox checkAtivo;

    @FXML TableView<CategoriaProduto> tbl;
    @FXML TableColumn<CategoriaProduto, String> colDescricao;
    @FXML TableColumn<CategoriaProduto, Boolean> colAtivo;
    @FXML TextField txtFiltro;

    public CategoriaProduto selecionado = null;
    public ManipulaProdutoController controller = null;

    @FXML
    public void initialize() {
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colAtivo.setCellValueFactory(cellData -> cellData.getValue().ativoProperty());
        exibirCategoriaProduto();
    }

    protected void exibirCategoriaProduto(){
        ArrayList<CategoriaProduto> lista = CategoriaProdutoDao.listaTodas();
        tbl.setItems(FXCollections.observableArrayList(lista));
    }

    public void filter() {
        String filtro = txtFiltro.getText().toUpperCase();
        ArrayList<CategoriaProduto> filtradas = CategoriaProdutoDao.filter(filtro);
        tbl.setItems(FXCollections.observableArrayList(filtradas));
    }

    public void storeOrUpdate() {
        if(selecionado == null) {
            CategoriaProduto p = new CategoriaProduto();
            p.setDescricao(txtDescricao.getText());
            p.setAtivo(checkAtivo.isSelected());
            CategoriaProdutoDao.store(p);
        }else {
            selecionado.setDescricao(txtDescricao.getText());
            selecionado.setAtivo(checkAtivo.isSelected());
            CategoriaProdutoDao.update(selecionado);
        }
        selecionado = null;
        exibirCategoriaProduto();
        if(controller != null)
            controller.cbCategoria.setItems(FXCollections.observableArrayList(CategoriaProdutoDao.listaTodas(true)));
    }

    @FXML
    public void clicaTabela() {
        selecionado = tbl.getSelectionModel().getSelectedItem();
        if(selecionado != null) {
            txtDescricao.setText(selecionado.getDescricao());
            checkAtivo.setSelected(selecionado.isAtivo());
        }
    }
}
