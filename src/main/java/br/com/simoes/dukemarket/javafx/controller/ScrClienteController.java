/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.simoes.dukemarket.javafx.controller;

import br.com.simoes.dukemarket.javafx.DAO.ClienteDAO;
import br.com.simoes.dukemarket.javafx.model.Cliente;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class ScrClienteController implements Initializable {
    
    @FXML
    TableView <Cliente> tblCliente;
    
    @FXML
    TableColumn <Cliente, Integer> tcoId;
    
    @FXML
    TableColumn <Cliente, String> tcoNome;
    
    @FXML
    TableColumn <Cliente, String> tcoCidade;
    
    @FXML
    TableColumn <Cliente, String> tcoCep;
    
    @FXML
    TableColumn <Cliente, String> tcoUf;
    
    @FXML
    TextField txtNome;
    
    @FXML
    TextField txtEndereco;
    
    @FXML
    TextField txtCidade;
    
    @FXML
    TextField txtUf;
    
    @FXML
    TextField txtCep;
    
    @FXML
    TextField txtId;
    
    ClienteDAO cDAO;
    Cliente cClicked; //vai armazenar na tabela o cliente.
    private boolean flagNovo = true;
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //vincula as colunas da Table View a classe do cliente 
        bindColumns();
        
        carregaDados();
        // TODO
    }    
    
    /**
     * conecta as TableView com os atributos da classe Cliente
     * @return 
     */
    public void bindColumns(){
        //vincula a coluna tcoId com o atributo Id da classe Cliente 
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        //adiciona uma configuração extra de alinhamento a direita 
        tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcoNome.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tcoCidade.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tcoCep.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoUf.setCellValueFactory(new PropertyValueFactory("uf"));
        tcoUf.setStyle("-fx-alignment: CENTER_RIGHT;");
        
    }
    
    public void carregaDados(){
        
        this.cDAO = new ClienteDAO();
        
        //carrega view tblCliente com todos os resultados de Cliente
        this.tblCliente.getItems().setAll(cDAO.getResults());
    }
    /**
     * action do botao novo
     * limpa os campos e posiciona um focus em txtId
     * 
     * @return 
     */
    @FXML
    public void btnNovoClick(){
        this.flagNovo = true;
        
        txtId.setText("");
        txtId.setEditable(false);
        
        txtNome.setText("");
        txtNome.requestFocus();
        
        txtEndereco.setText("");
        txtCidade.setText("");
        txtCep.setText("");
        txtUf.setText("");
        
        
    }
    
    @FXML
    public void tblClienteOnMouseClicked(){
        //getSelectedItem devolve um objeto
        //pClicked representa o Cliente clickado 
        
        this.flagNovo = false;
        
        this.cClicked = tblCliente.getSelectionModel().getSelectedItem();
        
        if (cClicked != null){
            txtId.setText(String.valueOf(cClicked.getId()));
            txtId.setEditable(false);
            
            txtNome.setText(cClicked.getNome());
            txtEndereco.setText(cClicked.getEndereco());
            txtCidade.setText(cClicked.getCidade());
            
            txtCep.setText(String.valueOf(cClicked.getCep()));
            txtUf.setText(String.valueOf(cClicked.getUf()));
            
            this.flagNovo = false;
            
        }
    }
    
    /**
     * gerencia o click do botao de salvar, adiciona um novo registro quando
     * flagNovo == true ou atualiza quando flagNovo
     * 
     */
    
    @FXML
    public void btnSalvarClick(){
        
        Cliente c = new Cliente();
        
        c.setNome(txtNome.getText());
        c.setEndereco(txtEndereco.getText());
        c.setCidade(txtCidade.getText());
        c.setCep(txtCep.getText());
        c.setUf(txtUf.getText());
        
        if (this.flagNovo){
            //novo cliente
            
            this.cDAO.create(c);
            
        } else {
            // uma atualizaçao
            c.setId(Integer.parseInt(txtId.getText()));
            this.cDAO.update(c);
        }
        
        this.carregaDados();
    }
    
    public void btnExcluirClick(){
        
        if (this.cClicked != null){
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma Exclusão do Cliente?");
            alert.setHeaderText(cClicked.getNome());
            alert.setContentText("Tem certeza que deseja excluir?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK){
                this.cDAO.delete(this.cClicked.getId());
                
                this.tblCliente.getItems().remove(this.cClicked);
            }
            this.tblCliente.getSelectionModel().clearSelection();
            cClicked = null;
        } else {
            Alert alert  = new Alert( AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de excluir!");
            
            alert.showAndWait();
        }
    }
}