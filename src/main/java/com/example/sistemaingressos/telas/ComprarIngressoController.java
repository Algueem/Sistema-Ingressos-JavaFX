package com.example.sistemaingressos.telas;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static com.example.sistemaingressos.telas.SelecionarSessaoController.sessaoSelecionada;
import static com.example.sistemaingressos.telas.SelectCadeiraController.cadeirasSelecionadas;
import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class ComprarIngressoController {

    @FXML
    private VBox dados;

    @FXML
    private ComboBox<String> selectConforto;

    public void initialize() {
        Label textBig = (Label) dados.getChildren().get(0);
        Label textSmall = (Label) dados.getChildren().get(1);
        dados.getChildren().clear();

        double totalIngressos = 0;

        ArrayList<Label> labels = new ArrayList<>();

        for (int i = 0; i < cadeirasSelecionadas.size(); i++) {
            double preco = sessaoSelecionada.getPreco() / (cliente.isEstudante() ? 2: 1);
            String precoText = String.format("%.2f R$%s", preco, cliente.isEstudante() ? " (Meia)": "(Integra)");
            totalIngressos += preco;
            //String tipo =
            //totalIngressos += especial
            String texto = String.format("Cadeira %s (%s) - %s %s", cadeirasSelecionadas.get(i),
                    "Tipo", precoText, "+ ?? R$(Especial)");

            Label cadeiraTextoNew = new Label(texto);
            cadeiraTextoNew.setFont(textSmall.getFont());
            labels.add(cadeiraTextoNew);
        }
        Label totalText = new Label(String.format("Ingressos: %.2f R$", totalIngressos));
        totalText.setFont(textBig.getFont());
        labels.add(0, totalText);
        dados.getChildren().addAll(labels);
    }

}
