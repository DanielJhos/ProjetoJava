package dao;

import model.ItemVenda;
import model.Pessoa;
import model.Produto;
import model.Venda;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class VendaDao {
    public static ArrayList<Venda> listaTodas(){
        return listaTodas(false);
    }

    public static ArrayList<Venda> listaTodas(boolean ativo){
        Connection c = Conexao.conn();
        ArrayList<Venda> lista = new ArrayList<Venda>();
        try {
            String sql = "select * from venda order by id desc";
            if(ativo)
                sql = "select * from venda order by id desc";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setCliente(PessoaDao.find(rs.getInt("cliente_id")));
                v.setVededor(PessoaDao.find(rs.getInt("vendedor_id")));
                v.setDataCriacao(rs.getString("data_criacao"));
                v.setSituacao(rs.getString("situacao"));
                v.setItens(ItemVendaDao.listaTodasByVenda(rs.getInt("id")));
                lista.add(v);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ArrayList<Venda> vendasRelatorio(String dataVenda, String situacao, Pessoa cliente, Pessoa vendedor){
        Connection c = Conexao.conn();
        ArrayList<Venda> lista = new ArrayList<Venda>();
        try {
            String sql = "select * from venda where id != 0 ";

            if (dataVenda != null && !dataVenda.equals(""))
                sql += "and data_criacao like '"+dataVenda+"%' ";
            if (situacao != null && !situacao.equals(""))
                sql += "and situacao = '"+situacao+"' ";
            if (cliente != null)
                sql += "and cliente_id = '"+cliente.getId()+"' ";
            if (vendedor != null)
                sql += "and vendedor_id = '"+vendedor.getId()+"' ";

            sql += "order by id desc";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setCliente(PessoaDao.find(rs.getInt("cliente_id")));
                v.setVededor(PessoaDao.find(rs.getInt("vendedor_id")));
                v.setDataCriacao(rs.getString("data_criacao"));
                v.setSituacao(rs.getString("situacao"));
                v.setItens(ItemVendaDao.listaTodasByVenda(rs.getInt("id")));
                lista.add(v);
            }

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void store(Venda v) {
        Connection c = Conexao.conn();
        try {
            String sql = "INSERT INTO venda (data_criacao, vendedor_id, cliente_id, situacao) VALUES (?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, v.getDataCriacao());
            ps.setInt(2, v.getVededor().getId());
            ps.setInt(3, v.getCliente().getId());
            ps.setString(4, v.getSituacao());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            Integer id = rs.getInt(1);

            c.close();

            for (ItemVenda i : v.getItens()) {
                ItemVendaDao.store(id, i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Venda> filter(String filtro){
        Connection c = Conexao.conn();
        ArrayList<Venda> lista = new ArrayList<Venda>();
        try {
            String sql = "SELECT v.* FROM venda v JOIN pessoa p on p.id = v.cliente_id WHERE p.nome like '%"+filtro+"%' order by id desc";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setCliente(PessoaDao.find(rs.getInt("cliente_id")));
                v.setVededor(PessoaDao.find(rs.getInt("vendedor_id")));
                v.setDataCriacao(rs.getString("data_criacao"));
                v.setSituacao(rs.getString("situacao"));
                v.setItens(ItemVendaDao.listaTodasByVenda(rs.getInt("id")));
                lista.add(v);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void updateSituacao(Venda v) {
        Connection c = Conexao.conn();
        try {
            String sql = "update venda set situacao = ? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, v.getSituacao());
            ps.setInt(2, v.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
