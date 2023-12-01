package com.example.sistemaingressos.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class VendaModel {
    private int id;
    private int qntIngressos;
    private LocalDateTime data;
    private String nomeComprador;
    private ArrayList<IngressoModel> ingressos;

    public VendaModel(int id, int qntIngressos, LocalDateTime data, String nomeComprador, ArrayList<IngressoModel> ingressos) {
        this.id = id;
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.nomeComprador = nomeComprador;
        this.ingressos = ingressos;
    }

    public void salvarIngressos() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQntIngressos() {
        return qntIngressos;
    }

    public void setQntIngressos(int qntIngressos) {
        this.qntIngressos = qntIngressos;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }
}
