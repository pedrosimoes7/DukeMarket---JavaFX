
package br.com.simoes.dukemarket.javafx.DAO;

//import br.com.simoes.dukemarket.bean.Produto;
import br.com.simoes.dukemarket.javafx.model.Produto;
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
public class ProdutosDAO {
    
    private static final String SQL_INSERT = "INSERT INTO produto(codBarras, "
            +"descricao , Qtd, valorCompra, valorVenda) "
            +"VALUES ( ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_ALL = "SELECT * FROM produto ";
    private static final String SQL_SELECT_ID = "SELECT * FROM produto "
            +"WHERE Id = ?";
    
    
    private static final String SQL_UPDATE = "UPDATE produto SET codBarras = ?, "
            +"descricao = ?, Qtd = ?, valorCompra = ?, valorVenda = ? "
            +"WHERE Id = ?";
    
    private static final String SQL_DELETE = "DELETE FROM produto WHERE Id = ?";
    
    
    
    /**
     *  insere um usuario na base de dados Produto
     * @param p
     */
    
    public void create (Produto p){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, p.getCodBarras());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getQtd());
            stmt.setDouble(4, p.getValorCompra());
            stmt.setDouble(5, p.getValorVenda());
            
            
            // execeuta a query
            
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.INFO, null,
                    "Inclusao: " + auxRetorno);
        } catch (SQLException sQLException){
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // encerra a conexao com o banco e o statement 
            MySQLConnection.closeConnection(conn, stmt);
        }
        
    }
    
    
    /**
     *  retorna todos os registros da tabela produto
     * @return
     */
    
    public List<Produto> getResults(){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = null;
        List<Produto> listaProdutos = null;
        
        try {
            // prepara a string de SELECT e executa a query 
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            
            // carrega os dados da resultSet rs, converte em produto e
            // adciona na lista de retorno.
            
            listaProdutos = new ArrayList<>();
            
            while (rs.next()){
                p = new Produto();
                p.setId(rs.getInt("Id"));
                p.setCodBarras(rs.getString("codBarras"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(rs.getDouble("Qtd"));
                p.setValorCompra(rs.getDouble("valorCompra"));
                p.setValorVenda(rs.getDouble("valorVenda"));
                p.setDataCadastro(rs.getString("DataCadastro"));
                listaProdutos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    
        }
        return listaProdutos;
        
    }
    
    /**
     *  retorna um produto da tabela produto
     * @param Id Indentificaçao do produto 
     * @return 
     */
    
    public Produto getResultById( int Id){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = null;
        
        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, Id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("Id"));
                p.setCodBarras(rs.getString("codBarras"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(rs.getDouble("Qtd"));
                p.setValorCompra(rs.getDouble("valorCompra"));
                p.setValorVenda(rs.getDouble("valorVenda"));
                p.setDataCadastro(rs.getString("DataCadastro"));
                
            }
        } catch (SQLException sQlException) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null,
                    sQlException);
        } finally {
            // encerrra a conexao com o banco e o statement
            MySQLConnection.closeConnection(conn, stmt, rs);
        }
        return p;
    }
    
    /**
     * atualiza um registro na tabela produto 
     * @param p Produto a ser atualizado 
     */
    
    public void update(Produto p){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, p.getCodBarras());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getQtd());
            stmt.setDouble(4, p.getValorCompra());
            stmt.setDouble(5, p.getValorVenda());
            stmt.setInt(6, p.getId());
            
            // executa a query 
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);
            
        } catch (SQLException sQLException){
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, 
                    sQLException);
        } finally {
            // encerrra a conexao com o banco e o statement
            MySQLConnection.closeConnection(conn, stmt);
        }
    }
    
    /**
     * exclui um produto da base de acordo com o Id fornecido
     * @param Id Indentificaçao do produto 
     */
    
    public void delete( int Id){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1 , Id);
            
            //executa a query 
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.INFO, null, 
                    "Delete: " + auxRetorno);
        } catch (SQLException sQLException){ 
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
    }   finally {
            MySQLConnection.closeConnection(conn, stmt);
        }

}
}   
