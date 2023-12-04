package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.FilmeDAO;
import com.example.sistemaingressos.database.IngressoDAO;
import com.example.sistemaingressos.database.SessaoDAO;
import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.FilmeVendido;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
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

    @FXML
    TableView<FilmeVendido> topVendasTabela;
    @FXML
    TableColumn<FilmeVendido, String> nomeTopVendasTabela, quantidadeTopVendasTabela;

    @FXML
    DatePicker filtroData;
    ObservableList<FilmeModel> filmesLista = FXCollections.observableArrayList();
    ObservableList<SessaoModel> sessoesLista = FXCollections.observableArrayList();
    public static SessaoModel sessaoSelecionada = null;
    public static FilmeModel filmeSelecionado = null;
    
    public void initialize() {
        filmesLista.setAll(filmes.values());
        sessoesLista.setAll(sessoes);

        // Sessoes
        tabelaSessoes.setItems(sessoes); // sessoesLista
        nomeTabelaSessoes.setCellValueFactory(data -> data.getValue().get("filme"));
        horarioTabelaSessoes.setCellValueFactory(data -> data.getValue().get("horario"));

        // Filmes
        tabelaFilmes.setItems(filmesLista);
        nomeTabelaFilmes.setCellValueFactory(data -> data.getValue().get("nome"));
        classificacaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("classificacao"));
        generoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("genero"));
        duracaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("duracao"));

        // Filmes mais vendidos
        topVendasTabela.setItems(FXCollections.observableArrayList(IngressoDAO.buscarFilmeMaisVendidos()));
        nomeTopVendasTabela.setCellValueFactory(data -> data.getValue().get("filme"));
        quantidadeTopVendasTabela.setCellValueFactory(data -> data.getValue().get("quantidade"));

        // Vendas no dia
        filtroData.setValue(LocalDate.now());

        
    }
    public void adicionarFilme(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AddEditFilme.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarFilme(ActionEvent event) {
        filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado != null) {
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AddEditFilme.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exibirErro("Erro", "Selecione o filme a ser editado");
        }
    }

    public void removerFilme(ActionEvent event) {
        filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado != null) {
            filmes.remove(filmeSelecionado.getNome());
            FilmeDAO.deletarFilme(filmeSelecionado);
        } else {
            exibirErro("Erro", "Selecione o filme a ser deletado");
        }
    }

    public void adicionarSessao(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AddEditSessao.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarSessao(ActionEvent event) {
        sessaoSelecionada = (SessaoModel) tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelecionada != null) {
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AddEditSessao.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exibirErro("Erro", "Selecione a sessão a ser editada");
        }
    }
    public void deletarSessao(ActionEvent event) {
        sessaoSelecionada = tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelecionada != null) {
            sessoes.remove(sessaoSelecionada);
            SessaoDAO.deletarSessao(sessaoSelecionada);
        } else {
            exibirErro("Erro", "Selecione a sessão a ser deletada");
        }
    }

    public void filtrarData(ActionEvent event) {
        System.out.println("oi");
        LocalDate data = filtroData.getValue();
        if (data != null) {

        }
    }

    private void exibirErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);
        alerta.showAndWait();
    }
}
