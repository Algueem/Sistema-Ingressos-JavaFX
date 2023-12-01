package com.example.sistemaingressos.telas;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.telas.AdminController.filmeSelecionado;

import com.example.sistemaingressos.models.FilmeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddEditFilmeController {
    @FXML
    private ChoiceBox<String> classificacaoSelect;
    @FXML
    private TextField nomeFilme, generoFilme, duracaoFilme;
    @FXML
    private Label errorMsg;

    public void initialize() {
        classificacaoSelect.getItems().addAll("Livre", "10+ anos", "12+ anos", "14+ anos", "16+ anos", "18+ anos");

        if (filmeSelecionado != null) {
            String classificacao = filmeSelecionado.getFaixaEtaria() == 0 ? "Livre": filmeSelecionado.getFaixaEtaria() + "+ anos";
            nomeFilme.setText(filmeSelecionado.getNome());
            nomeFilme.setEditable(false);
            generoFilme.setText(filmeSelecionado.getGenero());
            duracaoFilme.setText(String.valueOf(filmeSelecionado.getDuracao()));
            classificacaoSelect.setValue(classificacao);
        }
    }

    @FXML
    void salvarFilme(ActionEvent event) {
        try {
            String nome = nomeFilme.getText();
            String genero = generoFilme.getText();
            if (nome.isEmpty() || genero.isEmpty()) {
                errorMsg.setText("Nome e/ou genero faltando");
                return;
            }
            int duracao = Integer.parseInt(duracaoFilme.getText());
            if (classificacaoSelect.getValue() == null) {
                errorMsg.setText("Selecione a classificação");
                return;
            }
            int classificacao;
            switch (classificacaoSelect.getValue()) {
                case "Livre":
                    classificacao = 0;
                    break;
                default:
                    classificacao = Integer.parseInt(classificacaoSelect.getValue().substring(0, 2));
            }
            if (filmeSelecionado == null) {
                if (!filmes.containsKey(nome)) {
                    FilmeModel filme = new FilmeModel(nome, genero, duracao, classificacao);
                    FilmeModel.addFilme(filme);
                } else {
                    errorMsg.setText("Já existe um filme com esse nome");
                }
            } else {
                filmeSelecionado.setGenero(genero);
                filmeSelecionado.setDuracao(duracao);
                filmeSelecionado.setFaixaEtaria(classificacao);
                FilmeModel.editarFilme(filmeSelecionado);
                filmeSelecionado = null;
            }

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AdminTela.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (NumberFormatException ignored) {
            errorMsg.setText("Duraçao inválida");
        }
    }
}