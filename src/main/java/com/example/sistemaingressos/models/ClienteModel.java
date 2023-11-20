package com.example.sistemaingressos.models;

public class ClienteModel {
    private String nome;
    private int idade;
    private boolean estudante;

    public ClienteModel(String nome, int idade, boolean estudante) {
        this.nome = nome;
        this.idade = idade;
        this.estudante = estudante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isEstudante() {
        return estudante;
    }

    public void setEstudante(boolean estudante) {
        this.estudante = estudante;
    }
}
