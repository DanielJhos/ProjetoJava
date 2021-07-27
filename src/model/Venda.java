package model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venda {
    protected IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty dataCriacao = new SimpleStringProperty();
    protected Pessoa vededor = new Pessoa();
    protected Pessoa cliente = new Pessoa();
    protected StringProperty situacao = new SimpleStringProperty();
    protected ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDataCriacao() {
        return dataCriacao.get();
    }

    public StringProperty dataCriacaoProperty() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao.set(dataCriacao);
    }

    public Pessoa getVededor() {
        return vededor;
    }

    public void setVededor(Pessoa vededor) {
        this.vededor = vededor;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public String getSituacao() {
        return situacao.get();
    }

    public StringProperty situacaoProperty() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao.set(situacao);
    }

    public double getTotal(){
        double total = 0;
        for (ItemVenda i : this.getItens()) {
            total += (i.getQuantidade() * i.getValor());
        }
        return total;
    }

    public StringProperty totalProperty(){
        return new SimpleStringProperty("R$ "+getTotal());
    }

    public void AddItem(ItemVenda item){
        this.itens.add(item);
    }

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemVenda> itens) {
        this.itens = itens;
    }
}
