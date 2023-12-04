package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.SalaDAO;

import java.util.HashMap;

public class SalaModel {
    private int id;
    private int qntMaxPessoas;

    public static HashMap<Integer, SalaModel> salas = new HashMap<>();

    public SalaModel(int id, int qntMaxPessoas) {
        this.id = id;
        this.qntMaxPessoas = qntMaxPessoas;
    }

    public static void carregarSalas() {
        SalaDAO.carregarSalas();
    }

    public static void addSala(SalaModel sala) {
        SalaDAO.adicionarSala(sala);
    }
    public static void editarSala(SalaModel sala) {
        SalaDAO.editarSala(sala);
    }
    public static void deletarSala(SalaModel sala) {
        SalaDAO.deletarSala(sala);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQntMaxPessoas() {
        return qntMaxPessoas;
    }

    public void setQntMaxPessoas(int qntMaxPessoas) {
        this.qntMaxPessoas = qntMaxPessoas;
    }
}
