package com.example.sistemaingressos.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SessaoModel {
    public static ObservableList<SessaoModel> sessoes = FXCollections.observableArrayList();
    private String id; // "Homem aranha|14; "Homem aranha|16;
    private String horario; // "14";
    private double preco;
    private int quantMaxPessoas;
    private String filme;

    public SessaoModel(String horario, double preco, int quantMaxPessoas, String filme) {
        this.id = filme + "|" + horario;
        this.horario = horario;
        this.preco = preco;
        this.quantMaxPessoas = quantMaxPessoas;
        this.filme = filme;
    }

    public static void carregarSessoes() {
        sessoes.add(new SessaoModel("14", 20, 10, "Miranha"));
        sessoes.add(new SessaoModel("16", 20, 10, "Bathomem"));
    }

    public static void addSessao(SessaoModel sessao) {

    }

    public static void editarSessao(SessaoModel sessao) {

    }

    public static void deletarSessao(SessaoModel sessao) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantMaxPessoas() {
        return quantMaxPessoas;
    }

    public void setQuantMaxPessoas(int quantMaxPessoas) {
        this.quantMaxPessoas = quantMaxPessoas;
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }
}
