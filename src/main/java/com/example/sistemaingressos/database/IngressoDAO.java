package com.example.sistemaingressos.database;

import java.sql.*;

import com.example.sistemaingressos.models.FilmeVendido;
import com.example.sistemaingressos.models.IngressoModel;
import java.util.ArrayList;

public class IngressoDAO {

    public static ArrayList<IngressoModel> buscarCadeirasOcupadas(int sessaoId) {
        try {
            String sql = "SELECT * FROM ingressos WHERE sessao_id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            ps.setInt(1, sessaoId);

            ArrayList<IngressoModel> ingressos = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt("id");
                String filme = result.getString("filme");
                int sessao_id = result.getInt("sessao_id");
                int sala_id = result.getInt("sala_id");
                int cadeira_id = result.getInt("cadeira_id");
                boolean cadeira_especial = result.getBoolean("cadeira_especial");
                int venda_id = result.getInt("venda_id");
                double preco_final = result.getDouble("preco_final");

                IngressoModel ingresso = new IngressoModel(id, filme, cadeira_id, cadeira_especial, sala_id, sessao_id, venda_id, preco_final);
                ingressos.add(ingresso);
            }
            ps.close();
            con.close();
            return ingressos;
        } catch (SQLException ignored) {

        }
        return null;
    }

    public static ArrayList<FilmeVendido> buscarFilmeMaisVendidos() {
        ArrayList<FilmeVendido> vendas = new ArrayList<>();
        try {
            String sql = "SELECT filme, COUNT(*) AS quantidade \n" +
                    "FROM ingressos\n" +
                    "GROUP BY filme\n" +
                    "ORDER BY quantidade DESC;";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();



            while (result.next()) {
                FilmeVendido filme = new FilmeVendido(result.getString("filme"), result.getInt("quantidade"));
                vendas.add(filme);
            }
            ps.close();
            con.close();
            return vendas;
        } catch (SQLException ignored) {
            return vendas;
        }
    }

    public static void addIngresso(IngressoModel novoIngresso) {
        try {
            String sql = "Insert into ingressos values(?,?,?,?,?,?,?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, novoIngresso.getFilme());
            ps.setInt(2, novoIngresso.getSessaoId());
            ps.setInt(3, novoIngresso.getSalaId());
            ps.setInt(4, novoIngresso.getCadeiraId());
            ps.setBoolean(5, novoIngresso.getCadeiraEspecial());
            ps.setInt(6, novoIngresso.getVendaId());
            ps.setDouble(7, novoIngresso.getPrecoFinal());


            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ignored) {

        }
    }

}
