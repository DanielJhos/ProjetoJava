package util;

import controller.HomeController;
import controller.ManipulaPessoaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ItemVenda;
import model.Venda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;

public class Util {

    public static void createModal(String title, String view){
        try {
            Stage stageCreateVenda = new Stage();
            FXMLLoader loaderCreateVenda = new FXMLLoader(Util.class.getResource(view));
            Parent rootLogin = loaderCreateVenda.load();

            stageCreateVenda.setScene(new Scene(rootLogin));
            stageCreateVenda.initModality(Modality.WINDOW_MODAL);
            stageCreateVenda.setResizable(false);
            stageCreateVenda.setTitle(title);
            stageCreateVenda.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeModal(Window window){
        Stage stage = (Stage) window;
        stage.close();
    }

    public static String gerarRelatorioVenda(ArrayList<Venda> vendas){
        String html = "<!DOCTYPE html><html><head><title>Relátorio de Vendas</title><link rel=\"stylesheet\" type=\"text/css\" href=\"relatorio.css\"></head>"
        + "<body><div class=\"container\"><div class=\"card\"><h1>Relátorio de Vendas</h1></div>";


        for (Venda v : vendas){
            html += "<div class=\"card\">" +
                    "<h3>Venda #"+v.getId()+" - "+v.getCliente().getNome()+"</h4>" +
                    "<h5>Vendedor: "+v.getVededor().getNome()+"</h4>" +
                    "<h5>Data da Venda: "+v.getDataCriacao()+"</h4>" +
                    "<h5 class=\" "+v.getSituacao()+" \">Situação: "+v.getSituacao()+"</h4>" +
                    "<br>" +
                    "<table class=\"table\">" +
                    "<thead>" +
                    "<tr>" +
                    "<th scope=\"col\">#</th>" +
                    "<th scope=\"col\">Produto</th>" +
                    "<th scope=\"col\">Quantidade</th>" +
                    "<th scope=\"col\">Valor Unitário</th>" +
                    "</tr>" +
                    "</thead>"+
                    "<tbody>";

            int count = 1;
            for (ItemVenda iv : v.getItens()){
                html += "<tr>" +
                        "<th scope=\"row\">"+count+"</th>" +
                        "<td>"+iv.getProduto().getDescricao()+"</td>" +
                        "<td>"+iv.getQuantidade()+"</td>" +
                        "<td>" +
                        "<span class=\"label label-inline label-light-primary font-weight-bold\">" +
                        "R$ "+iv.getValor() +
                        "</span>" +
                        "</td>" +
                        "</tr>";
                count++;
            }

            html += "</tbody>"+
                    "<tfoot>" +
                    "<tr>" +
                    "<th colspan=\"4\">Total R$ "+v.getTotal()+"</th>" +
                    "</tr>" +
                    "</tfoot>" +
                    "</table></div>";

        }

        html += "</div></body></html>";

        try {
            FileWriter fw = new FileWriter(new File("relatorio.html"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(html);
            bw.close();
            fw.close();

            //ABRE O RELATÓRIO
            File caminho = new File("relatorio.html");
            URI url = caminho.toURI();
            //Caminho no MAC
            System.out.println("open "+url);
            Runtime.getRuntime().exec("open "+url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }


}
