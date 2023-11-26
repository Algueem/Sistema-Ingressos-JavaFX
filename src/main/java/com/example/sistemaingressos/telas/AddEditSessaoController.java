package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.SessaoDAO;
import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;
import static com.example.sistemaingressos.telas.AdminController.sessaoSelecionada;

public class AddEditSessaoController {
    @FXML
    ChoiceBox<String> selectFilme;
    @FXML
    TextField horarioInput, capacidadeInput, precoInput;
    @FXML Label errorMsg;

    public void initialize() {
        selectFilme.getItems().addAll(filmes.keySet());
        if (sessaoSelecionada != null) {
            selectFilme.setValue(String.valueOf(sessaoSelecionada.getNome()));
            horarioInput.setText(String.valueOf(sessaoSelecionada.getHorario()));
            capacidadeInput.setText(String.valueOf(sessaoSelecionada.getQuantMaxPessoas()));
            precoInput.setText(String.valueOf(sessaoSelecionada.getPreco()));
        }
    }
    @FXML
    public void salvarSessao(ActionEvent event) throws InterruptedException {
        try {
            System.out.println("tst");
            System.out.println(horarioInput.getText());
            System.out.println(capacidadeInput.getText());
            System.out.println(precoInput.getText());
            if (selectFilme.getValue() == null) {
                errorMsg.setText("Selecione o filme!");
                return;
            }
            int horario = Integer.parseInt(horarioInput.getText());
            int capacidade = Integer.parseInt(capacidadeInput.getText());
            double preco = Double.parseDouble(precoInput.getText());
            FilmeModel filme = filmes.get(selectFilme.getValue());
            if (sessaoSelecionada == null) {
                SessaoModel sessao = new SessaoModel(horario, preco, capacidade,filme);
                SessaoModel.addSessao(sessao);
            }
            sessaoSelecionada = null;
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AdminTela.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (NumberFormatException ignored) {
            System.out.println("opa");
            errorMsg.setText("Preencha com numeros");
            //Thread.sleep(2000);
            //errorMsg.setText(" ");
        }
    }
}
