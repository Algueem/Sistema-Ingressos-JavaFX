package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.Conexao;
import com.example.sistemaingressos.database.SessaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static com.example.sistemaingressos.models.FilmeModel.filmes;

public class SessaoModel {
    public static ObservableList<SessaoModel> sessoes = FXCollections.observableArrayList();
    private String id; // "Homem aranha|14; "Homem aranha|16;
    private int horario; // ;
    private double preco;
    private int quantMaxPessoas;
    private FilmeModel filme;

    public SessaoModel(int horario, double preco, int quantMaxPessoas, FilmeModel filme) {
        this.id = filme.getNome() + "|" + horario;
        this.horario = horario;
        this.preco = preco;
        this.quantMaxPessoas = quantMaxPessoas;
        this.filme = filme;
    }

    public static void carregarSessoes() {
        SessaoDAO.carregarSessoes();
    }

    public static void addSessao(SessaoModel sessao) {
        SessaoDAO.adicionarSessao(sessao);
        sessoes.add(sessao);
    }

    public static void editarSessao(SessaoModel sessao) {
        SessaoDAO.editarSessao(sessao);
    }

    public static void deletarSessao(SessaoModel sessao) {
        SessaoDAO.deletarSessao(sessao);
        sessoes.remove(sessao);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
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

    public FilmeModel getFilme() {
        return filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public String getNome() {
        return this.filme.getNome();
    }

    public String getGenero() {
        return this.filme.getGenero();
    }
    public String getFaixaEtaria() {
        int faixaEtaria = this.filme.getFaixaEtaria();
        if (faixaEtaria > 0){
            return String.format("%d+ anos", this.filme.getFaixaEtaria());
        } else {
            return "Livre";
        }
    }
    public String getSessao() {
        return String.format("%d horas", this.horario);
    }
    public String getCusto(){
        return String.format("%.2f R$", this.preco);
    }
}
