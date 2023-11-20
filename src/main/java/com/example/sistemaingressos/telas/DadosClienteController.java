package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.ClienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DadosClienteController {
    @FXML TextField nomeCliente;
    @FXML TextField idadeCliente;
    @FXML CheckBox estudante;
    public static ClienteModel cliente = null;

    public void initialize() {
        if (cliente != null) {
            nomeCliente.setText(cliente.getNome());
            idadeCliente.setText(String.valueOf(cliente.getIdade()));
            estudante.setSelected(cliente.isEstudante());
        }
    }
    @FXML
    protected void salvarDadosCliente(ActionEvent event) {
        if (cliente == null){
            cliente = new ClienteModel(nomeCliente.getText(), Integer.parseInt(idadeCliente.getText()), estudante.isSelected());
        } else {
            cliente.setNome(nomeCliente.getText());
            cliente.setIdade(Integer.parseInt(idadeCliente.getText()));
            cliente.setEstudante(estudante.isSelected());
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ListaSessoesTela.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
        scene.setRoot(root);

    }
}
