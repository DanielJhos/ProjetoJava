package model;

import javafx.beans.property.*;

public class Pessoa {
    private IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty nome = new SimpleStringProperty();
    protected StringProperty cpf = new SimpleStringProperty();
    protected StringProperty email = new SimpleStringProperty();
    protected StringProperty telefone = new SimpleStringProperty();
    protected StringProperty senha  = new SimpleStringProperty();
    protected StringProperty tipo = new SimpleStringProperty();
    protected BooleanProperty ativo = new SimpleBooleanProperty();

    @Override
    public String toString() {
        return getNome();
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

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCpf() {
        return cpf.get();
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public String getSenha() {
        return senha.get();
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
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
