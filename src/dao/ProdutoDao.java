package dao;

import model.Pessoa;
import model.Produto;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutoDao {

    public static ArrayList<Produto> listaTodas(){
        return listaTodas(false);
    }

    public static ArrayList<Produto> listaTodas(boolean ativo){
        Connection c = Conexao.conn();
        ArrayList<Produto> lista = new ArrayList<Produto>();
        try {
            String sql = "select * from produto order by descricao";
            if(ativo)
                sql = "select * from produto where ativo = 'S' order by descricao";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCodigoEan(rs.getString("codigo_ean"));
                p.setCategoria(CategoriaProdutoDao.find(rs.getInt("categoria_id"), false));
                p.setValor(rs.getDouble("valor"));
                p.setAtivo(rs.getString("ativo").equals("S"));
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void store(Produto p) {
        Connection c = Conexao.conn();
        try {
            String sql = "INSERT INTO produto (descricao, codigo_ean, categoria_id, valor, ativo) VALUES (?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, p.getDescricao());
            ps.setString(2, p.getCodigoEan());
            ps.setInt(3, p.getCategoria().getId());
            ps.setDouble(4, p.getValor());
            ps.setString(5, p.isAtivo()?"S":"N");

            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Produto p) {
        Connection c = Conexao.conn();
        try {
            String sql = "update produto set descricao = ?, codigo_ean = ?, categoria_id = ?, valor = ?, ativo = ? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, p.getDescricao());
            ps.setString(2, p.getCodigoEan());
            ps.setInt(3, p.getCategoria().getId());
            ps.setDouble(4, p.getValor());
            ps.setString(5, p.isAtivo()?"S":"N");
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Produto p){
        Connection c = Conexao.conn();
        try {
            String sql = "delete from produto where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produto> filter(String filtro){
        Connection c = Conexao.conn();
        ArrayList<Produto> lista = new ArrayList<Produto>();
        try {
            String sql = "select * from produto where descricao like '%"+filtro+"%' order by descricao";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCodigoEan(rs.getString("codigo_ean"));
                p.setCategoria(CategoriaProdutoDao.find(rs.getInt("categoria_id"), false));
                p.setValor(rs.getDouble("valor"));
                p.setAtivo(rs.getString("ativo").equals("S"));
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static Produto find(int id, boolean ativo) {
        Connection c = Conexao.conn();
        Produto p = new Produto();
        try {
            String sql = "select * from produto where id = ?";
            if (ativo)
                sql = "select * from produto where id = ? and ativo = 'S'";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCodigoEan(rs.getString("codigo_ean"));
                p.setCategoria(CategoriaProdutoDao.find(rs.getInt("categoria_id"), false));
                p.setValor(rs.getDouble("valor"));
                p.setAtivo(rs.getString("ativo").equals("S"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}