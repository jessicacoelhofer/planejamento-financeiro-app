package com.example.aplicativo;

public class Transacao {
    private String descricao;
    private String valor;
    private String data;
    private String tipo;

    // Construtor
    public Transacao(String descricao, String valor, String data, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    // Getters e setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método toString para facilitar a exibição ou depuração
    @Override
    public String toString() {
        return "Descrição: " + descricao + "\nValor: " + valor + "\nData: " + data + "\nTipo: " + tipo + "\n";
    }
}
