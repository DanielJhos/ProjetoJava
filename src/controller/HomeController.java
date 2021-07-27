package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Pessoa;

public class HomeController {

    @FXML TabPane tabPane;
    @FXML Label lblUser;
    Pessoa user = null;

    @FXML
    public void openVenda() {
        abreTab("Vendas", "/view/venda/index.fxml");
    }

    @FXML
    public void openRelatorio() { abreTab("Relátorios", "/view/relatorio/index.fxml"); }

    @FXML
    public void openPessoa() {
        abreTab("Pessoas", "/view/pessoa/index.fxml");
    }

    @FXML
    public void openProduto() {
        abreTab("Produtos", "/view/produto/index.fxml");
    }

    @FXML
    public void openCreateVenda(){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(getClass().getResource("/view/venda/create.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaVendaController instancia = (ManipulaVendaController) loaderCreateVenda.getController();
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

    private void abreTab(String titulo, String path) {
        try{
            Tab tab = tabAberta(titulo);
            if (tab==null) {
                tab = new Tab(titulo);
                tab.setClosable(true);
                tabPane.getTabs().add(tab);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();

                if (titulo.equals("Vendas")){
                    IndexVendaController instancia = (IndexVendaController) loader.getController();
                    instancia.user = user;
                }

                tab.setContent((Node) root);
            }
            selecionaTab(tab);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Tab tabAberta(String titulo) {
        for (Tab tb : tabPane.getTabs()) {
            if(!(tb.getText()==null) && tb.getText().equals(titulo))
                return tb;
        }
        return null;
    }


    private void selecionaTab(Tab tab) {
        tabPane.getSelectionModel().select(tab);
    }
}
