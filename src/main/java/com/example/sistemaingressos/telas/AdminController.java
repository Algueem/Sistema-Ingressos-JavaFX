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
import java.util.List;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;

public class AdminController {
    @FXML 
    TableView tabelaSessoes;
    @FXML
    TableColumn nomeTabelaSessoes, horarioTabelaSessoes;
    
    @FXML
    TableView tabelaFilmes;
    @FXML
    TableColumn nomeTabelaFilmes, classificacaoTabelaFilmes, generoTabelaFilmes;
    
    ObservableList<FilmeModel> filmesLista = FXCollections.observableArrayList();
    ObservableList<FilmeModel> sessoesLista = FXCollections.observableArrayList();
    public static SessaoModel sessaoSelecionada = null;
    public static FilmeModel filmeSelecionado = null;
    
    public void initialize() {
        loadTabelas();
        tabelaSessoes.setItems(sessoes); // sessoesLista
        nomeTabelaSessoes.setCellValueFactory(new PropertyValueFactory<String, SessaoModel>("nome"));
        horarioTabelaSessoes.setCellValueFactory(new PropertyValueFactory<String, SessaoModel>("horario"));
        
        tabelaFilmes.setItems(filmesLista);
        nomeTabelaFilmes.setCellValueFactory(new PropertyValueFactory<String, FilmeModel>("nome"));
        classificacaoTabelaFilmes.setCellValueFactory(new PropertyValueFactory<String, FilmeModel>("classificacao"));
        generoTabelaFilmes.setCellValueFactory(new PropertyValueFactory<String, FilmeModel>("genero"));
        
    }
    public void adicionarFilme(ActionEvent event) {
        
    }

    public void editarFilme(ActionEvent event) {
        FilmeModel filme = filmes.get("miranha");
        filme.setDuracao(100);
        filme.setGenero("sei la2");
        loadTabelas();
        FilmeDAO.editarFilme(filme);
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
    
    public void loadTabelas() {
        filmesLista.clear();
        filmesLista.addAll(filmes.values());
        //sessoesLista.clear();
        //sessoesLista.addAll(sessoes);
    }
}
