package controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ItemVenda;
import model.Produto;
import util.Util;

public class ManipulaItemVendaController {

    @FXML private Label lblProduto;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtValor;

    protected Produto produto = null;
    public ManipulaVendaController controller = null;

    public void atualizaObjeto(){
        if (produto != null){
            lblProduto.setText(produto.getDescricao());
            txtValor.setText(produto.getValor()+"");
        }
    }

    @FXML
    public void cancel() {
        produto = null;
        Util.closeModal(txtQuantidade.getScene().getWindow());
    }

    @FXML
    public void mais() {
        txtQuantidade.setText((Integer.parseInt(txtQuantidade.getText())+1)+"");
    }

    @FXML
    public void menos() {
        int quant = Integer.parseInt(txtQuantidade.getText());
        txtQuantidade.setText((quant <= 1 ? quant : quant-1)+"");
    }

    @FXML
    public void save() {
        ItemVenda i = new ItemVenda();
        i.setProduto(produto);
        i.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
        i.setValor(Double.parseDouble(txtValor.getText()));

        if (controller != null){
            controller.venda.AddItem(i);
            controller.exibirItens();
        }

        produto = null;
        Util.closeModal(txtQuantidade.getScene().getWindow());

    }

}
