package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.FilmeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.*;

import java.sql.*;

public class FilmeModel {
    private String nome;
    private String genero;
    private int duracao; // duracao em minutos
    private int faixaEtaria;
    public static ObservableMap<String, FilmeModel> filmes = FXCollections.observableHashMap();

    public FilmeModel(String nome, String genero, int duracao, int faixaEtaria) {
        this.nome = nome;
        this.genero = genero;
        this.duracao = duracao;
        this.faixaEtaria = faixaEtaria;
    }

    public static void carregarFilmes() throws SQLException {
        FilmeDAO.carregarFilmes();
    }

    public void addFilme(FilmeModel filme){
        filmes.put(filme.getNome(), filme);
        FilmeDAO.adicionarFilme(filme);
    }

    public void editarFilme(FilmeModel filme){
        FilmeDAO.editarFilme(filme);
    }

    public void deletarFilme(FilmeModel filme){
        filmes.remove(filme.getNome());
        FilmeDAO.deletarFilme(filme);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getClassificacao() {
        if (faixaEtaria > 0){
            return String.format("%d+ anos", faixaEtaria);
        } else {
            return "Livre";
        }
    }
}
