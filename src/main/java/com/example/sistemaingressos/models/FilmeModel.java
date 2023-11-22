package com.example.sistemaingressos.models;

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
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaIngressos", "root", "Admin@123");
        String sql = "SELECT * FROM FILMES";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            FilmeModel f = new FilmeModel(result.getString("nome"), result.getString("genero"),
                    result.getInt("duracao"), result.getInt("faixa_etaria"));
            filmes.put(f.getNome(), f);
        }
    }

    public void adicionarFilme(FilmeModel filme) {

    }

    public void editarFilme(FilmeModel filme) {

    }

    public void deletarFilme() {

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
}
