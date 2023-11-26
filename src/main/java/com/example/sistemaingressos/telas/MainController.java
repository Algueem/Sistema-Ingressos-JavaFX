package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    protected void logarCliente(ActionEvent event) throws SQLException {
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
    protected void logarAdmin(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminTela.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
}