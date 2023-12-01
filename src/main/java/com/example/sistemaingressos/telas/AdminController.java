package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.FilmeDAO;
import com.example.sistemaingressos.database.SessaoDAO;
import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;

public class AdminController {
    @FXML 
    TableView<SessaoModel> tabelaSessoes;
    @FXML
    TableColumn<SessaoModel, String> nomeTabelaSessoes, horarioTabelaSessoes;
    
    @FXML
    TableView<FilmeModel> tabelaFilmes;
    @FXML
    TableColumn<FilmeModel, String> nomeTabelaFilmes, classificacaoTabelaFilmes, generoTabelaFilmes, duracaoTabelaFilmes;
    
    ObservableList<FilmeModel> filmesLista = FXCollections.observableArrayList();
    ObservableList<SessaoModel> sessoesLista = FXCollections.observableArrayList();
    public static SessaoModel sessaoSelecionada = null;
    public static FilmeModel filmeSelecionado = null;
    
    public void initialize() {
        filmesLista.setAll(filmes.values());
        sessoesLista.setAll(sessoes);
        tabelaSessoes.setItems(sessoes); // sessoesLista
        nomeTabelaSessoes.setCellValueFactory(data -> data.getValue().get("filme"));
        horarioTabelaSessoes.setCellValueFactory(data -> data.getValue().get("horario"));
        
        tabelaFilmes.setItems(filmesLista);
        nomeTabelaFilmes.setCellValueFactory(data -> data.getValue().get("nome"));
        classificacaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("classificacao"));
        generoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("genero"));
        duracaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("duracao"));
        
    }
    public void adicionarFilme(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddEditFilme.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void editarFilme(ActionEvent event) {
        FilmeModel filme = (FilmeModel) tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filme != null) {
            filmeSelecionado = filme;
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AddEditFilme.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(root);
        }
    }

    public void removerFilme(ActionEvent event) {
        FilmeModel filme = (FilmeModel) tabelaFilmes.getSelectionModel().getSelectedItem(); 
        if (filme != null) {
            filmes.remove(filme.getNome());
            FilmeDAO.deletarFilme(filme);
        }
    }

    public void adicionarSessao(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddEditSessao.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void editarSessao(ActionEvent event) {
        SessaoModel sessao = (SessaoModel) tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessao != null) {
            sessaoSelecionada = sessao;
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AddEditSessao.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(root);
        }
    }
    public void deletarSessao(ActionEvent event) {
        SessaoModel sessao = (SessaoModel) tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessao != null) {
            sessoes.remove(sessao);
            SessaoDAO.deletarSessao(sessao);
        }
    }
}
