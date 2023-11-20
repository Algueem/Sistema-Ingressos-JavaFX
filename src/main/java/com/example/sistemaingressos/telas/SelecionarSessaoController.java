package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.SessaoModel;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import static com.example.sistemaingressos.telas.DadosClienteController.*;
import static com.example.sistemaingressos.models.SessaoModel.*;



public class SelecionarSessaoController {
    @FXML Label selectTeste;
    @FXML TableView tabelaSessoes;
    @FXML
    TableColumn nomeTabelaSessoes, classificacaoTabelaSessoes, horarioTabelaSessoes, precoTabelaSessoes;

    @FXML Label logadoNome;
    public void initialize(){
        SessaoModel.carregarSessoes();
        tabelaSessoes.setItems(sessoes);
        nomeTabelaSessoes.setCellValueFactory(new PropertyValueFactory<String, SessaoModel>("filme"));
        precoTabelaSessoes.setCellValueFactory(new PropertyValueFactory<Double, SessaoModel>("preco"));
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
        SessaoModel a = (SessaoModel) tabelaSessoes.getSelectionModel().getSelectedItem();
        if (a != null) {
            selectTeste.setText(a.getFilme());
        } else {
            selectTeste.setText("Selecione primeiro");
        }

    }
}
