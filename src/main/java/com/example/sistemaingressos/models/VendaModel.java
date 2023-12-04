package com.example.sistemaingressos.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class VendaModel {
    private int id;
    private int qntIngressos;
    private LocalDate data;
    private String nomeComprador;
    private ArrayList<IngressoModel> ingressos;

    public VendaModel(int id, int qntIngressos, LocalDate data, String nomeComprador, ArrayList<IngressoModel> ingressos) {
        this.id = id;
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.nomeComprador = nomeComprador;
        this.ingressos = ingressos;
    }

    public VendaModel(int id, int qntIngressos, LocalDate data, String nomeComprador) {
        this.id = id;
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.nomeComprador = nomeComprador;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }
}
