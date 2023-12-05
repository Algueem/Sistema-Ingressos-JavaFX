package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.IngressoModel;
import com.example.sistemaingressos.models.VendaModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.sistemaingressos.telas.SelecionarSessaoController.sessaoSelecionada;
import static com.example.sistemaingressos.telas.SelectCadeiraController.cadeirasSelecionadas;
import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class ComprarIngressoController {

    @FXML
    private VBox dados;

    @FXML
    private VBox confortoList;

    @FXML
    private RadioButton combo1, combo2, combo3;

    Font textBig, textSmall;
    ToggleGroup group = new ToggleGroup();
    ArrayList<String> cadeirasConfortaveis = new ArrayList<>();
    int selectedCombo = 0;
    private double totalIngressos = 0;

    public void initialize() {
        textBig = ((Label) dados.getChildren().get(0)).getFont();
        textSmall = ((Label) dados.getChildren().get(1)).getFont();


        combo1.setToggleGroup(group);
        combo2.setToggleGroup(group);
        combo3.setToggleGroup(group);

        for (String cadeira: cadeirasSelecionadas) {
            RadioButton btn = new RadioButton("Cadeira " + cadeira);
            btn.setFont(textBig);

            btn.setId(cadeira);
            btn.setOnAction(event -> {
                if (btn.isSelected()) {
                    cadeirasConfortaveis.add(cadeira);
                } else {
                    cadeirasConfortaveis.remove(cadeira);
                }
                atualizarTextoCompra();
            });
            confortoList.getChildren().add(btn);
        }
        atualizarTextoCompra();
        combo1.setOnMouseClicked(mouseEvent -> select(combo1));
        combo2.setOnMouseClicked(mouseEvent -> select(combo2));
        combo3.setOnMouseClicked(mouseEvent -> select(combo3));

    }

    public void finalizarCompra() {
        boolean confirm = exibirConfirmar("Finalizar compra", "Deseja finalizar a compra?");
        System.out.println(totalIngressos);
        if (confirm) {
            VendaModel venda = new VendaModel(cadeirasSelecionadas.size(), LocalDate.now(), cliente.getNome(), totalIngressos);
            int vendaId = VendaModel.addVenda(venda);
            ArrayList<IngressoModel> ingressos = new ArrayList<>();
            for (String cadeira: cadeirasSelecionadas) {
                ingressos.add(new IngressoModel(sessaoSelecionada.getFilme().getNome(), Integer.parseInt(cadeira),
                        cadeirasConfortaveis.contains(cadeira), sessaoSelecionada.getSalaId(),sessaoSelecionada.getId(),
                        vendaId, sessaoSelecionada.getPreco()));
            }
            venda.setIngressos(ingressos);
            venda.salvarIngressos();
            exibirAviso("Compra realizada com sucesso", "Você comprou os ingressos com sucesso!");
            System.exit(1);
        }
    }

    public void atualizarTextoCompra() {
        dados.getChildren().clear();
        ArrayList<Label> labels = new ArrayList<>();

        Label sessaoTexto = new Label("Dados da Sessão");
        sessaoTexto.setFont(textBig);
        labels.add(sessaoTexto);
        Label infoTexto = new Label(String.format(
                "• Filme: %s\n• Sala: %d\n• Horário: %dh%dmin\n", sessaoSelecionada.getStr("filme"),
                sessaoSelecionada.getSalaId(), sessaoSelecionada.getHora(), sessaoSelecionada.getMinuto()
        ));
        infoTexto.setFont(textSmall);
        labels.add(infoTexto);
        totalIngressos = 0;
        for (String cadeirasSelecionada : cadeirasSelecionadas) {
            double preco = sessaoSelecionada.getPreco() / (cliente.isEstudante() ? 2 : 1);
            String precoText = String.format("%.2f R$%s", preco, cliente.isEstudante() ? " (Meia)" : "(Integra)");

            boolean isEspecial = cadeirasConfortaveis.contains(cadeirasSelecionada);
            String especialText = isEspecial ? "Confortável": "Normal";
            double especialPreco = isEspecial ? 10 : 0;
            String especialPrecoText = isEspecial ? "+ 10,00 R$(Especial)": "";

            String texto = String.format("• Cadeira %s (%s) - %s %s", cadeirasSelecionada,
                    especialText, precoText, especialPrecoText);

            Label cadeiraTextoNew = new Label(texto);
            cadeiraTextoNew.setFont(textSmall);
            labels.add(cadeiraTextoNew);

            totalIngressos += preco + especialPreco;
        }
        Label totalIngressosText = new Label(String.format("Ingressos: %.2f R$", totalIngressos));
        totalIngressosText.setFont(textBig);
        labels.add(2, totalIngressosText);

        String comboEscolhido = selectedCombo == 0 ? "Nenhum" : String.valueOf(selectedCombo);
        Label comboEscolhidoText = new Label(String.format("Combo escolhido: %s", comboEscolhido));
        comboEscolhidoText.setFont(textBig);
        labels.add(comboEscolhidoText);

        String comboDescontoText = switch (selectedCombo){
            case 1 -> "• 10% de desconto";
            case 2 -> "• 15% de desconto";
            case 3 -> "• 20% desconto";
            default ->  "• Nenhum desconto";
        };
        Label comboDesconto = new Label(comboDescontoText);
        comboDesconto.setFont(textSmall);
        labels.add(comboDesconto);

        double descontoValor = switch (selectedCombo){
            case 1 -> 0.9;
            case 2 -> 0.85;
            case 3 -> 0.8;
            default -> 1;
        };
        totalIngressos *= descontoValor;
        Label precoFinal = new Label(String.format("Valor final: %.2f R$", totalIngressos));
        precoFinal.setFont(textBig);
        labels.add(precoFinal);
        dados.getChildren().addAll(labels);
    }

    public void select(RadioButton btn) {
        int id = Character.getNumericValue(btn.getId().charAt(5));
        if (selectedCombo == id) {
            group.getSelectedToggle().setSelected(false);
            selectedCombo = 0;
        } else {
            selectedCombo = id;
        }
        atualizarTextoCompra();


    }
    private void exibirAviso(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);
        alerta.showAndWait();
    }
    private boolean exibirConfirmar(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);

        ButtonType botaoConfirmar = new ButtonType("Sim");
        ButtonType botaoCancelar = new ButtonType("Não");
        alerta.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);
        AtomicBoolean result = new AtomicBoolean(false);
        alerta.showAndWait().ifPresent(button -> {
            result.set(button == botaoConfirmar);
        });
        return result.get();
    }
}
