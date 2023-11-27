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
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;
import static com.example.sistemaingressos.telas.AdminController.sessaoSelecionada;

public class AddEditSessaoController {
    @FXML
    ChoiceBox<String> selectFilme, selectHorario;
    @FXML
    TextField capacidadeInput, precoInput;
    @FXML Label errorMsg;

    public void initialize() {
        selectFilme.getItems().addAll(filmes.keySet());
        selectFilme.setOnAction(ActionEvent -> addHorarios());


        if (sessaoSelecionada != null) {
            selectFilme.setValue(String.valueOf(sessaoSelecionada.getNome()));
            selectHorario.setValue(sessaoSelecionada.getHorario() + " horas");
            capacidadeInput.setText(String.valueOf(sessaoSelecionada.getQuantMaxPessoas()));
            precoInput.setText(String.valueOf(sessaoSelecionada.getPreco()));
        }
    }
    @FXML
    public void salvarSessao(ActionEvent event) throws InterruptedException {
        try {
            if (selectFilme.getValue() == null) {
                errorMsg.setText("Selecione o filme!");
                return;
            }
            int horario = Integer.parseInt(selectHorario.getValue().split(" ",0)[0]);
            int capacidade = Integer.parseInt(capacidadeInput.getText());
            double preco = Double.parseDouble(precoInput.getText());
            FilmeModel filme = filmes.get(selectFilme.getValue());
            if (sessaoSelecionada == null) {
                SessaoModel sessao = new SessaoModel(horario, preco, capacidade, filme);
                SessaoModel.addSessao(sessao);
            } else {
                String newId = selectFilme.getValue() + "|" + horario;
                if (SessaoDAO.existeSessaoPorId(newId)) {
                    errorMsg.setText("Ja existe outra sessao desse filme nesse horario");
                } else {
                    sessaoSelecionada.setFilme(filmes.get(selectFilme.getValue()));
                    sessaoSelecionada.setHorario(horario);
                    sessaoSelecionada.setPreco(preco);
                    sessaoSelecionada.setQuantMaxPessoas(capacidade);
                    SessaoDAO.editarSessao(sessaoSelecionada);
                    sessaoSelecionada.setId(newId);
                    sessaoSelecionada = null;
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

        } catch (NumberFormatException ignored) {
            System.out.println("opa");
            errorMsg.setText("Preencha com numeros");
            //Thread.sleep(2000);
            //errorMsg.setText(" ");
        }
    }

    public void addHorarios() {
        String selecionado = selectFilme.getValue();
        selectHorario.getItems().clear();
        ArrayList<Integer> horariosUsados = SessaoDAO.buscarHorariosDisponiveis(selecionado);
        String[] horarios = new String[24];
        for (int i = 0; i < 24; i++) {
            horarios[i] = i + " horas";
        }
        selectHorario.getItems().addAll(horarios);
        for (int i: horariosUsados) {
            selectHorario.getItems().remove(i + " horas");
        }
    }
}
