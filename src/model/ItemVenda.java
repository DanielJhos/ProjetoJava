package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemVenda {
    private IntegerProperty id = new SimpleIntegerProperty();
    protected Venda venda = new Venda();
    protected Produto produto = new Produto();
    protected IntegerProperty quantidade = new SimpleIntegerProperty();
    protected DoubleProperty valor = new SimpleDoubleProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public double getValor() {
        return valor.get();
    }

    public DoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }
}
