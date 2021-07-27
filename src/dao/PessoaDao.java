package dao;

import model.CategoriaProduto;
import model.Pessoa;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PessoaDao {

    public static ArrayList<Pessoa> listaTodas(){
        return listaTodas(false);
    }

    public static ArrayList<Pessoa> listaTodas(boolean ativo){
        Connection c = Conexao.conn();
        ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
        try {
            String sql = "select * from pessoa order by nome";
            if(ativo)
                sql = "select * from pessoa where ativo = 'S' order by nome";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setTipo(rs.getString("tipo"));
                p.setCpf(rs.getString("cpf"));
                p.setSenha(rs.getString("senha"));
                p.setAtivo(rs.getString("ativo").equals("S")?true:false);
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void store(Pessoa p) {
        Connection c = Conexao.conn();
        try {
            String sql = "INSERT INTO pessoa (nome, email, telefone, senha, tipo, ativo, cpf) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelefone());
            ps.setString(4, p.getSenha());
            ps.setString(5, p.getTipo());
            ps.setString(6, p.isAtivo()?"S":"N");
            ps.setString(7, p.getCpf());

            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Pessoa p) {
        Connection c = Conexao.conn();
        try {
            String sql = "update pessoa set nome = ?, email = ?, telefone = ?, senha = ?, tipo = ?, ativo = ?, cpf = ? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelefone());
            ps.setString(4, p.getSenha());
            ps.setString(5, p.getTipo());
            ps.setString(6, p.isAtivo()?"S":"N");
            ps.setString(7, p.getCpf());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Pessoa p){
        Connection c = Conexao.conn();
        try {
            String sql = "delete from pessoa where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pessoa> filter(String filtro){
        Connection c = Conexao.conn();
        ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
        try {
            String sql = "select * from pessoa where nome like '%"+filtro+"%' or email like '%"+filtro+"%' order by nome";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setTipo(rs.getString("tipo"));
                p.setCpf(rs.getString("cpf"));
                p.setSenha(rs.getString("senha"));
                p.setAtivo(rs.getString("ativo").equals("S")?true:false);
                lista.add(p);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static Pessoa consultaLogin(String email, String senha){
        Connection c = Conexao.conn();
        try {
            String sql = "select * from pessoa where email like ? and senha = ? and ativo = ? and (tipo = ? or tipo = ?) limit 1";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
            ps.setString(3, "S");
            ps.setString(4, "Usuário");
            ps.setString(5, "Vendedor");
            ResultSet rs = ps.executeQuery();

            if (!rs.isClosed()){
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setTipo(rs.getString("tipo"));
                p.setCpf(rs.getString("cpf"));
                p.setSenha(rs.getString("senha"));
                p.setAtivo(rs.getString("ativo").equals("S"));

                c.close();
                return p;
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pessoa find(int id) {
        return find(id, false);
    }

    public static Pessoa find(int id, boolean ativo) {
        Connection c = Conexao.conn();
        Pessoa p = new Pessoa();
        try {
            String sql = "select * from pessoa where id = ?";
            if (ativo)
                sql = "select * from pessoa where id = ? and ativo = 'S'";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setTipo(rs.getString("tipo"));
                p.setCpf(rs.getString("cpf"));
                p.setSenha(rs.getString("senha"));
                p.setAtivo(rs.getString("ativo").equals("S"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}