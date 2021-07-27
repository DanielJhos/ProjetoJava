package controller;

import dao.PessoaDao;
import dao.ProdutoDao;
import dao.VendaDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ItemVenda;
import model.Pessoa;
import model.Produto;
import model.Venda;
import util.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManipulaVendaController {

    protected Pessoa pessoa = null;
    protected Pessoa vendedor = null;
    public IndexVendaController controller = null;
    protected Venda venda = new Venda();

    @FXML TabPane tabVenda;
    @FXML private TableView<Pessoa> tblCliente;
    @FXML private TableColumn<Pessoa, String> colCliente;
    @FXML private TextField txtFiltroCliente;
    @FXML private TableView<Produto> tblProdutos;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TextField txtFiltroProduto;
    @FXML private Label lblCliente;
    @FXML private Label lblTotal;
    @FXML private TableView<ItemVenda> tblItens;
    @FXML private TableColumn<ItemVenda, String> colProduto;
    @FXML private TableColumn<ItemVenda, Number> colQuant;
    @FXML private TableColumn<ItemVenda, Number> colValor;

    @FXML
    public void initialize() {
        colCliente.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        exibirClientes();

        colNome.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        exibirProdutos();

        colProduto.setCellValueFactory(cellData -> cellData.getValue().getProduto().descricaoProperty());
        colQuant.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty());
        colValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty());
        exibirItens();
    }

    protected void exibirClientes(){
        ArrayList<Pessoa> listaClientes = PessoaDao.listaTodas(true);
        tblCliente.setItems(FXCollections.observableArrayList(listaClientes));
    }

    protected void exibirProdutos(){
        ArrayList<Produto> listaProdutos = ProdutoDao.listaTodas(true);
        tblProdutos.setItems(FXCollections.observableArrayList(listaProdutos));
    }

    protected void exibirItens(){
        tblItens.setItems(FXCollections.observableArrayList(venda.getItens()));
        lblTotal.setText("Total: R$ "+venda.getTotal());
    }

    @FXML
    void createCliente() {
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreate = new FXMLLoader(Util.class.getResource("/view/pessoa/create.fxml"));
            Parent rootLogin = loaderCreate.load();

            ManipulaPessoaController instancia = (ManipulaPessoaController)loaderCreate.getController();
            instancia.vendaController = this;
            instancia.atualizaObjeto();

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
    public void filterProduto() {
        String filtro = txtFiltroProduto.getText().toUpperCase();
        ArrayList<Produto> filtradasProdutos = ProdutoDao.filter(filtro);
        tblProdutos.setItems(FXCollections.observableArrayList(filtradasProdutos));
    }

    @FXML
    public void filterPessoa() {
        String filtro = txtFiltroCliente.getText().toUpperCase();
        ArrayList<Pessoa> filtradas = PessoaDao.filter(filtro);
        tblCliente.setItems(FXCollections.observableArrayList(filtradas));
    }

    @FXML
    public Tab selecionarPessoa() {
        pessoa = tblCliente.getSelectionModel().getSelectedItem();
        venda.setCliente(pessoa);
        lblCliente.setText(pessoa.getNome());
        for (Tab tab : tabVenda.getTabs()) {
            if(!(tab.getText()==null) && tab.getText().equals("2 • Itens da Venda"))
                tabVenda.getSelectionModel().select(tab);
        }
        return null;
    }

    @FXML
    void selecionarProduto() {
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/venda/item.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            ManipulaItemVendaController instancia = (ManipulaItemVendaController) loaderCreateVenda.getController();
            instancia.controller = this;
            instancia.produto = tblProdutos.getSelectionModel().getSelectedItem();
            instancia.atualizaObjeto();

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Novo Item Venda");
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void finalizar() {
        if (vendedor != null){
            venda.setVededor(vendedor);
        }
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        venda.setDataCriacao(formattedDate);
        venda.setSituacao("Aprovada");
        VendaDao.store(venda);
        if (controller != null){
            controller.exibirVendas();
        }

        pessoa = null;
        vendedor = null;
        controller = null;
        Util.closeModal(txtFiltroCliente.getScene().getWindow());
        success(venda);
    }

    public void success(Venda venda){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource("/view/venda/success.fxml"));
            Parent rootLogin = loaderCreateVenda.load();

            VendaSuccessController instancia = (VendaSuccessController) loaderCreateVenda.getController();
            instancia.venda = venda;
            instancia.atualiza();

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle("Nova Venda");
            stageCreateVenda.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
