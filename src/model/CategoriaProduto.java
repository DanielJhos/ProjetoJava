package model;

import javafx.beans.property.*;

public class CategoriaProduto {
    private IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty descricao = new SimpleStringProperty();
    protected BooleanProperty ativo = new SimpleBooleanProperty();

    @Override
    public String toString() {
        return getDescricao();
    }

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
