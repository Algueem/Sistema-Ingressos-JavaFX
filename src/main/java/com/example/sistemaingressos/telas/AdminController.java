package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.FilmeDAO;
import com.example.sistemaingressos.database.IngressoDAO;
import com.example.sistemaingressos.database.SessaoDAO;
import com.example.sistemaingressos.database.VendaDAO;
import com.example.sistemaingressos.models.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;
import static com.example.sistemaingressos.models.SalaModel.salas;

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
    TableView<SalaModel> tabelaSalas;
    @FXML
    TableColumn<SalaModel, Integer> idTabelaSalas, qntTabelaSalas;
    @FXML
    TableColumn<SalaModel, String> editarTabelaSalas;

    @FXML
    TableView<FilmeVendido> tabelaTopVendas;
    @FXML
    TableColumn<FilmeVendido, String> nomeTabelaTopVendas, quantidadeTabelaTopVendas;

    @FXML
    TableView<FilmeVendido> tabelaVendas;
    @FXML
    TableColumn<FilmeVendido, String> filmeTabelaVendas, qntTabelaVendas;

    @FXML
    DatePicker filtroData;
    ObservableList<FilmeModel> filmesLista = FXCollections.observableArrayList();
    ObservableList<SessaoModel> sessoesLista = FXCollections.observableArrayList();
    ObservableList<SalaModel> salasLista = FXCollections.observableArrayList();
    public static SessaoModel sessaoSelecionada = null;
    public static FilmeModel filmeSelecionado = null;
    
    public void initialize() {
        filmesLista.setAll(filmes.values());
        sessoesLista.setAll(sessoes);
        salasLista.setAll(salas.values());

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

        // Salas
        tabelaSalas.setItems(salasLista);
        idTabelaSalas.setCellValueFactory(new PropertyValueFactory<>("id"));
        qntTabelaSalas.setCellValueFactory(new PropertyValueFactory<>("qntMaxPessoas"));
        editarTabelaSalas.setCellValueFactory(data -> data.getValue().get("editar"));
        editarTabelaSalas.setCellFactory(tc -> new TableCell<>(){
            private final Button btnEditar = new Button("Editar");
            private final TextField input = new TextField();

            {
                btnEditar.setOnAction(event -> {
                    SalaModel sala = getTableRow().getItem();
                    if (sala != null) {
                        input.setText(String.valueOf(sala.getQntMaxPessoas()));
                        setGraphic(input);
                    }
                });
                input.setOnAction(event -> {
                    SalaModel sala = getTableRow().getItem();
                    if (sala != null) {
                        try {
                            int novaCapacidade = Integer.parseInt(input.getText());
                            if (novaCapacidade > 70 || novaCapacidade < 1) {
                                exibirErro("Capacidade invalida!", "Digite um número entre 1 e 70");
                                return;
                            }
                            sala.setQntMaxPessoas(novaCapacidade);
                            setGraphic(btnEditar);
                        } catch (NumberFormatException ignored) {
                            exibirErro("Entrada inválida!", "Digite um número valido");
                        }
                    }
                });
                input.focusedProperty().addListener((obs, oldValue, newValue) -> {
                    if (!newValue) {
                        setGraphic(btnEditar);
                        salasLista.setAll(salas.values());
                    }
                });
                setGraphic(btnEditar);
            }
        });

        // Filmes mais vendidos
        tabelaTopVendas.setItems(FXCollections.observableArrayList(IngressoDAO.buscarFilmeMaisVendidos()));
        nomeTabelaTopVendas.setCellValueFactory(data -> data.getValue().get("filme"));
        quantidadeTabelaTopVendas.setCellValueFactory(data -> data.getValue().get("quantidade"));

        // Vendas no dia
        filtroData.setValue(LocalDate.now());
        filtrarData();
        
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
            filmesLista.remove(filmeSelecionado);
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
        sessaoSelecionada = tabelaSessoes.getSelectionModel().getSelectedItem();
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
            sessoesLista.remove(sessaoSelecionada);
            SessaoDAO.deletarSessao(sessaoSelecionada);
        } else {
            exibirErro("Erro", "Selecione a sessão a ser deletada");
        }
    }



    public void salvarSalas(ActionEvent event) {
        for (SalaModel sala: tabelaSalas.getItems()) {
            SalaModel.editarSala(sala);
        }
    }

    public void filtrarData() {
        LocalDate dia = filtroData.getValue();
        if (dia != null) {
            ArrayList<FilmeVendido> vendas = VendaDAO.buscarVendasNoDia(dia);
            tabelaVendas.setItems(FXCollections.observableArrayList(vendas));
            filmeTabelaVendas.setCellValueFactory(data -> data.getValue().get("filme"));
            qntTabelaVendas.setCellValueFactory(data -> data.getValue().get("quantidade"));
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
