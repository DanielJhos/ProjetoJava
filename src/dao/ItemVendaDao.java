package dao;

import model.ItemVenda;
import model.Venda;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ItemVendaDao {
    public static void store(int id, ItemVenda iv) {
        Connection c = Conexao.conn();
        try {
            String sql = "INSERT INTO item_venda (venda_id, produto_id, quantidade, valor) VALUES (?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, iv.getProduto().getId());
            ps.setInt(3, iv.getQuantidade());
            ps.setDouble(4, iv.getValor());

            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ItemVenda> listaTodasByVenda(int id_venda){
        Connection c = Conexao.conn();
        ArrayList<ItemVenda> lista = new ArrayList<ItemVenda>();
        try {
            String sql = "select * from item_venda where venda_id = ? order by id";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_venda);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ItemVenda iv = new ItemVenda();
                iv.setId(rs.getInt("id"));
                iv.setProduto(ProdutoDao.find(rs.getInt("produto_id"), false));
                iv.setQuantidade(rs.getInt("quantidade"));
                iv.setValor(rs.getDouble("valor"));
                lista.add(iv);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
