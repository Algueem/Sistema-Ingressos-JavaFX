package com.example.sistemaingressos.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static com.example.sistemaingressos.models.FilmeModel.filmes;

public class SessaoModel {
    public static ObservableList<SessaoModel> sessoes = FXCollections.observableArrayList();
    private String id; // "Homem aranha|14; "Homem aranha|16;
    private String horario; // "14";
    private double preco;
    private int quantMaxPessoas;
    private FilmeModel filme;

    public SessaoModel(String horario, double preco, int quantMaxPessoas, FilmeModel filme) {
        this.id = filme + "|" + horario;
        this.horario = horario;
        this.preco = preco;
        this.quantMaxPessoas = quantMaxPessoas;
        this.filme = filme;
    }

    public static void carregarSessoes() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaIngressos", "root", "Admin@123");

        String sql = "SELECT * FROM SESSOES";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            FilmeModel f = filmes.get(result.getString("filme"));
            SessaoModel sessao = new SessaoModel(result.getString("horario"), result.getDouble("preco"),
                    result.getInt("qnt_max_pessoas"), f);
            sessoes.add(sessao);
        }
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

    public FilmeModel getFilme() {
        return filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public String getFilmeNome() {
        return this.filme.getNome();
    }

    public int getFaixaEtaria() {
        return this.filme.getFaixaEtaria();
    }
}
