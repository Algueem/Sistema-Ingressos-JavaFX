package com.example.sistemaingressos.telas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

import static com.example.sistemaingressos.telas.DadosClienteController.*;
import static com.example.sistemaingressos.models.FilmeModel.*;



public class SelecionarSessaoController {

    @FXML Label logadoNome;
    public void initialize(){
        logadoNome.setText(cliente.getNome());
        // mostrar filmes tabela

    }
    @FXML
    public void editarDadosCliente(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("DadosClienteTela.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    public void comprarIngressos(ActionEvent event) {

    }
}
