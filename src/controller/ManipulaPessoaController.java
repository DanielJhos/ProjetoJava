package controller;

import dao.PessoaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import model.Pessoa;
import util.Mensagens;
import util.Util;

import java.util.ArrayList;

public class ManipulaPessoaController {

    @FXML TextField txtNome;
    @FXML TextField txtTelefone;
    @FXML TextField txtEmail;
    @FXML TextField txtCPF;
    @FXML TextField txtSenha;
    @FXML CheckBox checkAtivo;
    @FXML ComboBox<String> cbTipo;
    @FXML Button btnExcluir;
    @FXML HBox hbox;

    public Pessoa selecionado = null;
    public IndexPessoaController index = null;
    public ManipulaVendaController vendaController = null;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll(
                "Cliente",
                "Vendedor",
                "Usuário"
        );
        btnExcluir.setVisible(selecionado != null);
    }

    public void atualizaObjeto(){
        if (selecionado != null){
            txtNome.setText(selecionado.getNome());
            txtTelefone.setText(selecionado.getTelefone());
            txtEmail.setText(selecionado.getEmail());
            txtCPF.setText(selecionado.getCpf());
            checkAtivo.setSelected(selecionado.isAtivo());
            cbTipo.setValue(selecionado.getTipo());
            //TODO Verificar com o usuario logado;
            txtSenha.setDisable(true);
        }

        if (vendaController != null){
            cbTipo.setValue("Cliente");
            cbTipo.setDisable(true);
            checkAtivo.setSelected(true);
            hbox.setVisible(false);
        }

        btnExcluir.setVisible(selecionado != null);
    }

    @FXML
    public void storeOrUpdate(){

        if(selecionado == null) {
            Pessoa p = new Pessoa();
            p.setNome(txtNome.getText());
            p.setTelefone(txtTelefone.getText());
            p.setEmail(txtEmail.getText());
            p.setCpf(txtCPF.getText());
            p.setSenha(txtSenha.getText());
            p.setAtivo(checkAtivo.isSelected());
            p.setTipo(cbTipo.getValue());
            PessoaDao.store(p);
        }else {
            selecionado.setNome(txtNome.getText());
            selecionado.setTelefone(txtTelefone.getText());
            selecionado.setEmail(txtEmail.getText());
            selecionado.setCpf(txtCPF.getText());
            selecionado.setAtivo(checkAtivo.isSelected());
            selecionado.setTipo(cbTipo.getValue());
            PessoaDao.update(selecionado);
        }

        if (index != null)
            index.exibirPessoa();
        else if (vendaController != null)
            vendaController.exibirClientes();

        Util.closeModal(txtNome.getScene().getWindow());
        selecionado = null;
    }

    @FXML
    public void delete(){
        if(Mensagens.msgOkCancel("Exclusão", "Confirma Exclusão?")== ButtonType.OK) {
            if(selecionado != null) {
                PessoaDao.delete(selecionado);
            }

            index.exibirPessoa();
            Util.closeModal(txtNome.getScene().getWindow());
            selecionado = null;
        }
    }

}
