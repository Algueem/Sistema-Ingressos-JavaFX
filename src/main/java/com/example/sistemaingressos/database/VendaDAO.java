package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.IngressoModel;
import com.example.sistemaingressos.models.SalaModel;
import com.example.sistemaingressos.models.VendaModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VendaDAO {
    public static ArrayList<VendaModel> buscarVendasNoDia(LocalDate data) {
        try {
            ArrayList<VendaModel> vendas = new ArrayList<>();
            String sql = "SELECT * FROM vendas WHERE data = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            ps.setDate(1, Date.valueOf(data)); // Convertendo LocalDate para java.sql.Date para usar no PreparedStatement

            while (result.next()) {
                int id = result.getInt("id");
                int qntIngressos = result.getInt("qnt_ingressos");

                // Convertendo java.sql.Date para LocalDateTime
                LocalDate date = result.getDate("data").toLocalDate();
                String nomeComprador = result.getString("nome_comprador");

                // Crie a instÃ¢ncia de VendaModel com os dados recuperados
                VendaModel venda = new VendaModel(id, qntIngressos, date, nomeComprador);
                vendas.add(venda);
            }

            ps.close();
            con.close();
            return vendas;
        } catch (SQLException ignored) {

        }
        return null;
    }

    public static void addVenda(VendaModel novaVenda) throws SQLException{
        try {
            String sql = "INSERT INTO vendas values (?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, novaVenda.getId());
            ps.setInt(2, novaVenda.getQntIngressos());
            ps.setDate(3, Date.valueOf(novaVenda.getData()));
            ps.setString(4, novaVenda.getNomeComprador());

            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ignored) {

        }

    }


}

