package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe MySQLConnection
 * Prepara conexão com o banco de dados.
 * @author Aluno
 */
public class MySQLConnection {

    // definicao de strings de conexao com o banco 
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://172.16.0.30:3306/pedro_dukemarket";

    private static final String USER = "pedro";
    private static final String PASS = "12345";

    /**
     * Abre conexão com o Banco.
     * @return Connection
     */
    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException(" Erro de conexao com o BD. Verifique!", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(" Falha na carga do Driver", ex);
        }

    }
    /**
     * Fecha a conexao com o DB
     * *@param conn Connection a ser fechada
     */
        
    public static void closeConnection( Connection conn){
        try {
            if (conn != null){
                conn.close();
            }
        }   catch (SQLException ex){
            Logger.getLogger(
                MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
/**
 * Fecha a conexao com o DB
 * @param conn conexao
 * @param stmt Statement 
 */
    
    public static void closeConnection(Connection conn, PreparedStatement stmt){
        closeConnection(conn);
        
        try {
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException ex){
            Logger.getLogger(
            MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs){
        closeConnection(conn, stmt);
        
        try {
            if ( rs != null){
                rs.close();
            }
        } catch (SQLException ex){
            Logger.getLogger(
                    MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
