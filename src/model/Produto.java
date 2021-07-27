package model;

import javafx.beans.property.*;

public class Produto {
    private IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty descricao = new SimpleStringProperty();
    protected StringProperty codigoEan = new SimpleStringProperty();
    protected CategoriaProduto categoria = new CategoriaProduto();
    protected DoubleProperty valor = new SimpleDoubleProperty();
    protected BooleanProperty ativo = new SimpleBooleanProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public String getCodigoEan() {
        return codigoEan.get();
    }

    public StringProperty codigoEanProperty() {
        return codigoEan;
    }

    public void setCodigoEan(String codigoEan) {
        this.codigoEan.set(codigoEan);
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
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

    public boolean isAtivo() {
        return ativo.get();
    }

    public BooleanProperty ativoProperty() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo.set(ativo);
    }
}
