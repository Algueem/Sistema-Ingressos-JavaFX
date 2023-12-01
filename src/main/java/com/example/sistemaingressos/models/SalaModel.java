package com.example.sistemaingressos.models;

public class SalaModel {
    private int id;
    private int qntMaxPessoas;

    public SalaModel(int id, int qntMaxPessoas) {
        this.id = id;
        this.qntMaxPessoas = qntMaxPessoas;
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
