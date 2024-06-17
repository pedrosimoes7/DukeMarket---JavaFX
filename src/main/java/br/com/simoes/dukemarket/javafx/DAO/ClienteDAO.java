/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.simoes.dukemarket.javafx.DAO;

import br.com.simoes.dukemarket.javafx.model.Cliente;
import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aluno
 */
public class ClienteDAO {
    
    private static final String SQL_INSERT = "INSERT INTO cliente(nome,"
            +"endereco , cidade , uf , cep) "
            +"VALUES ( ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_ALL = "SELECT * FROM cliente ";
    private static final String SQL_SELECT_ID = " SELECT * FROM cliente "
            +"WHERE Id = ? ";
    
    private static final String SQL_UPDATE = "UPDATE cliente SET nome = ?, "
            +"endereco = ?, cidade = ?, uf = ?, cep = ? "
            +"WHERE Id = ?";
    
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE Id = ?";
    
    
    /**
     * insere um usuario na base de dados Cliente
     * @param p
     */
    
    public void create (Cliente c){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1,c.getNome());
            stmt.setString(2, c.getEndereco());
            stmt.setString(3, c.getCidade());
            stmt.setString(4, c.getUf());
            stmt.setString(5, c.getCep());
            
            //executa a query 
            
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Inclusão: " + auxRetorno);
            
        } catch (SQLException sQLExcpetion){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sQLExcpetion);
        } finally {
            //encerra a conexao com o banco
            MySQLConnection.closeConnection(conn, stmt);
        }
        
    }
    
    public List<Cliente> getResults(){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;
        List<Cliente> listaClientes = null;
        
        try {
            //prepara a string de SELECT e executa a query 
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            
            //carrega os dados da ResuttSet rs, converte em produto e 
            //adiciona na lista de retorno.
            
            listaClientes = new ArrayList<>();
            
            while (rs.next()){
                c = new Cliente();
                c.setId(rs.getInt("Id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCep(rs.getString("cep"));
                listaClientes.add(c);
                
            }
        } catch (SQLException ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return listaClientes;
    }
    
    /**
     * retorna um produto da tabela produto 
     * @param Id Indetificação do Cliente
     * @return 
     */
    
    public Cliente getResultByID( int Id){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;
        
        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, Id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("Id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getNString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCep(rs.getString("cep"));
                
            }
        } catch (SQLException sQLExcpetion) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, 
                    sQLExcpetion);
            
        } finally {
            //encerra a conexao com o banco e o statement
            MySQLConnection.closeConnection(conn, stmt, rs);
        }
       return c;
    }
    /**
     * autualiza um registrio na tabela Cliente 
     * @param c Cliente a ser atualizado 
     */
    public void update(Cliente c){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEndereco());
            stmt.setString(3, c.getCidade());
            stmt.setString(4, c.getUf());
            stmt.setString(5, c.getCep());
            stmt.setInt(6, c.getId());
            
            //executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);
            
    } catch (SQLException sQLExcpetion) {
        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, 
                sQLExcpetion);
    } finally {
            // encerra a conexao com o bando e o statement
            MySQLConnection.closeConnection(conn, stmt);
        }
    }
    /**
     * excçui um cliente na base de dados de acodo com o Id fornecido 
     * @param Id Indentificaçao do cliente 
     */
    
    public void delete(int Id){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1 , Id);
            
            // executa a query 
            
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Delete: " + auxRetorno);
        } catch (SQLException sQLExcpetion){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sQLExcpetion);
        } finally {
            MySQLConnection.closeConnection(conn, stmt);
        }
    }
}
    