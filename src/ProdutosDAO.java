/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
    try {
        conn = new conectaDAO().connectDB(); // Conecta ao banco
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());
        
        prep.executeUpdate();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutos(){
    
    String sql = "SELECT * FROM produtos";

    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while(resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
    }

    return listagem;
}
    
    public void venderProduto(String nomeProduto){
        try{
            conn = new conectaDAO().connectDB();
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE nome = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, nomeProduto);
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso.");
            
           
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());  
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
        try{
            conn = new conectaDAO().connectDB();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            while(resultset.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                vendidos.add(produto);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage());
        }
        return vendidos;
    }
    
    
    
        
}

