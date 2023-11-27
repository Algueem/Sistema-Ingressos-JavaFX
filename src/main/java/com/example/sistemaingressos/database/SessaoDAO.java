package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;

import java.sql.*;
import java.util.ArrayList;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;

public class SessaoDAO {
    public static void carregarSessoes() {
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM sessoes";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                FilmeModel f = filmes.get(result.getString("filme"));
                SessaoModel sessao = new SessaoModel(result.getInt("horario"), result.getDouble("preco"),
                        result.getInt("qnt_max_pessoas"), f);
                sessoes.add(sessao);
            }
        } catch (SQLException ignored) {

        }
    }

    public static void adicionarSessao(SessaoModel sessao){
        //nome, genero, duracao, faxa_estaria
        try {
            String sql = "INSERT INTO sessoes values (?, ?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessao.getId());
            ps.setString(2, sessao.getNome());
            ps.setInt(3, sessao.getHorario());
            ps.setDouble(4, sessao.getPreco());
            ps.setInt(5, sessao.getQuantMaxPessoas());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

    public static void editarSessao(SessaoModel novaSessao){
        try {
            System.out.println("update " + novaSessao.getId());
            String sql = "UPDATE sessoes SET filme = ?, horario = ?, preco = ?, qnt_max_pessoas = ? WHERE id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novaSessao.getNome());
            ps.setInt(2, novaSessao.getHorario());
            ps.setDouble(3, novaSessao.getPreco());
            ps.setInt(4, novaSessao.getQuantMaxPessoas());
            ps.setString(5, novaSessao.getNome() + "|" + novaSessao.getHorario());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarSessao(SessaoModel deletarSessao){
        try {
            String sql = "DELETE FROM sessoes WHERE id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, deletarSessao.getId());

            ps.execute();
            ps.close();
        } catch (SQLException ignored) {

        }

    }

    public static boolean existeSessaoPorId(String id) {
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM sessoes WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            return result.next();
        } catch (SQLException ignored) {
            return false;
        }
    }

    public static ArrayList<Integer> buscarHorariosDisponiveis(String filme) {
        ArrayList<Integer> horarios = new ArrayList<>();
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM sessoes WHERE filme = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, filme);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                horarios.add(result.getInt("horario"));
            }
            return horarios;
        } catch (SQLException ignored) {
            return horarios;
        }
    }

}
