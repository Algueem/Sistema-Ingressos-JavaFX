package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.SessaoModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.sistemaingressos.telas.DadosClienteController.*;
import static com.example.sistemaingressos.models.SessaoModel.*;



public class SelecionarSessaoController {
    @FXML Label selectTeste; // tirar

    @FXML
    ChoiceBox<String> selectFiltro;

    @FXML TextField barraPesquisa;

    @FXML TableView<SessaoModel> tabelaSessoes;
    @FXML
    TableColumn<SessaoModel, String> nomeTabelaSessoes, classificacaoTabelaSessoes, generoTabelaSessoes, horarioTabelaSessoes, precoTabelaSessoes;

    @FXML Label logadoNome;

    ObservableList<SessaoModel> sessoesLista = FXCollections.observableArrayList();
    public void initialize() {
        String[] selectOpcoes = {"Nome", "Classificação", "Gênero"};
        selectFiltro.getItems().addAll(selectOpcoes);
        selectFiltro.setValue("Nome");
        selectFiltro.setOnAction(ActionEvent-> {
            buscar();
        });

        sessoesLista.addAll(sessoes);
        tabelaSessoes.setItems(sessoesLista);
        nomeTabelaSessoes.setCellValueFactory(data -> data.getValue().get("nome"));
        generoTabelaSessoes.setCellValueFactory(data -> data.getValue().get("genero"));
        classificacaoTabelaSessoes.setCellValueFactory(data -> data.getValue().get("classificacao"));
        horarioTabelaSessoes.setCellValueFactory(data -> data.getValue().get("horario"));
        precoTabelaSessoes.setCellValueFactory(data -> data.getValue().get("preco"));
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
            if (cliente.getIdade() < a.getFilme().getFaixaEtaria()) {
                selectTeste.setText("Não podoe");
            } else {
                selectTeste.setText(a.getFilme().getNome());
            }
        } else {
            selectTeste.setText("Selecione primeiro");
        }

    }

    @FXML
    public void buscar() {
        sessoesLista.clear();
        switch (selectFiltro.getValue()) {
            case "Nome":
                for (SessaoModel s: sessoes) {
                    if (s.getStr("filme").contains(barraPesquisa.getText())) {
                        sessoesLista.add(s);
                    }
                }
                break;
            case "Classificação":
                for (SessaoModel s: sessoes) {
                    String idadeText = barraPesquisa.getText();
                    try {
                        int idade = Integer.parseInt(idadeText);
                        if (s.getFilme().getFaixaEtaria() <= idade) {
                            sessoesLista.add(s);
                        }
                    } catch (NumberFormatException ignored) {

                    }
                }
                break;
            case "Gênero":
                for (SessaoModel s: sessoes) {
                    if (s.getStr("genero").contains(barraPesquisa.getText())) {
                        sessoesLista.add(s);
                    }
                }
                break;
        }
    }


}
