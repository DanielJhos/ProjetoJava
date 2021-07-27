package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Venda;
import util.Util;

public class VendaSuccessController {

    @FXML private TextField txtRecebido;
    @FXML private Label lblTotal;
    @FXML private Label lblTroco;

    protected Venda venda = null;

    @FXML
    public void atualiza() {
        if(venda != null){
            lblTotal.setText("R$ "+venda.getTotal());
        }
    }

    @FXML
    public void imprimir() {

    }

    @FXML
    public void fechar() {
        Util.closeModal(txtRecebido.getScene().getWindow());
    }

    @FXML
    public void calcular() {
        lblTroco.setText("R$ "+(Double.parseDouble(txtRecebido.getText()) - venda.getTotal()));
    }

}
