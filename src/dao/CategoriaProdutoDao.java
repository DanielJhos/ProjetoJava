package dao;

import model.CategoriaProduto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoriaProdutoDao {

    public static ArrayList<CategoriaProduto> listaTodas() {
        return listaTodas(false);
    }

    public static ArrayList<CategoriaProduto> listaTodas(boolean ativo) {
        Connection c = Conexao.conn();
        ArrayList<CategoriaProduto> lista = new ArrayList<CategoriaProduto>();
        try {
            String sql = "select * from categoria_produto order by descricao";
            if (ativo)
                sql = "select * from categoria_produto where ativo = 'S' order by descricao";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaProduto p = new CategoriaProduto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setAtivo(rs.getString("ativo").equals("S"));
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void store(CategoriaProduto cp) {
        Connection c = Conexao.conn();
        try {
            String sql = "INSERT INTO categoria_produto (descricao, ativo) VALUES (?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, cp.getDescricao());
            ps.setString(2, cp.isAtivo() ? "S" : "N");

            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(CategoriaProduto cp) {
        Connection c = Conexao.conn();
        try {
            String sql = "update categoria_produto set descricao = ?, ativo = ? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, cp.getDescricao());
            ps.setString(2, cp.isAtivo() ? "S" : "N");
            ps.setInt(3, cp.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(CategoriaProduto cp) {
        Connection c = Conexao.conn();
        try {
            String sql = "delete from categoria_produto where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cp.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CategoriaProduto> filter(String filtro) {
        Connection c = Conexao.conn();
        ArrayList<CategoriaProduto> lista = new ArrayList<CategoriaProduto>();
        try {
            String sql = "select * from categoria_produto where descricao like '%" + filtro + "%' order by descricao";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaProduto p = new CategoriaProduto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setAtivo(rs.getString("ativo").equals("S"));
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static CategoriaProduto find(int id, boolean ativo) {
        Connection c = Conexao.conn();
        CategoriaProduto p = new CategoriaProduto();
        try {
            String sql = "select * from categoria_produto where id = ?";
            if (ativo)
                sql = "select * from categoria_produto where id = ? and ativo = 'S'";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setAtivo(rs.getString("ativo").equals("S") ? true : false);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}