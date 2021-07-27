package controller;

import dao.CategoriaProdutoDao;
import dao.ProdutoDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CategoriaProduto;
import model.Produto;
import util.Mensagens;
import util.Util;

public class ManipulaProdutoController {

    @FXML TextField txtDescricao;
    @FXML TextField txtCodigoEan;
    @FXML TextField txtValor;
    @FXML CheckBox checkAtivo;
    @FXML ComboBox<CategoriaProduto> cbCategoria;
    @FXML Button btnExcluir;

    public Produto selecionado = null;
    public IndexProdutoController index = null;

    @FXML
    public void initialize() {
        cbCategoria.setItems(FXCollections.observableArrayList(CategoriaProdutoDao.listaTodas(true)));
        btnExcluir.setVisible(selecionado != null);
    }

    public void atualizaObjeto(){
        if (selecionado != null){
            txtDescricao.setText(selecionado.getDescricao());
            txtValor.setText(selecionado.getValor()+"");
            txtCodigoEan.setText(selecionado.getCodigoEan());
            cbCategoria.setValue(selecionado.getCategoria());
            checkAtivo.setSelected(selecionado.isAtivo());
        }
        btnExcluir.setVisible(selecionado != null);
    }

    @FXML
    public void categoria(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/categoriaProduto/index.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            CategoriaProdutoController instancia = (CategoriaProdutoController)loaderCreateVenda.getController();
            instancia.controller = this;

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Categoria Produto");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void storeOrUpdate(){

        if(selecionado == null) {
            Produto p = new Produto();
            p.setDescricao(txtDescricao.getText());
            p.setCodigoEan(txtCodigoEan.getText());
            p.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
            p.setValor(Double.parseDouble(txtValor.getText()));
            p.setAtivo(checkAtivo.isSelected());
            ProdutoDao.store(p);
        }else {
            selecionado.setDescricao(txtDescricao.getText());
            selecionado.setCodigoEan(txtCodigoEan.getText());
            selecionado.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
            selecionado.setValor(Double.parseDouble(txtValor.getText()));
            selecionado.setAtivo(checkAtivo.isSelected());
            ProdutoDao.update(selecionado);
        }

        index.exibirProduto();
        Util.closeModal(txtDescricao.getScene().getWindow());
        selecionado = null;
    }

    @FXML
    public void delete(){
        if(Mensagens.msgOkCancel("Exclusão", "Confirma Exclusão?")== ButtonType.OK) {
            if(selecionado != null) {
                ProdutoDao.delete(selecionado);
            }

            index.exibirProduto();
            selecionado = null;
            Util.closeModal(txtDescricao.getScene().getWindow());
        }
    }

}
