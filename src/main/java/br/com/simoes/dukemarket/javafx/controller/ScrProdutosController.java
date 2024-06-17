/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.simoes.dukemarket.javafx.controller;

import br.com.simoes.dukemarket.javafx.DAO.ProdutosDAO;
import br.com.simoes.dukemarket.javafx.model.Produto;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

        



/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class ScrProdutosController implements Initializable {
    
    @FXML
    TableView <Produto> tblProduto;
    
    @FXML
    TableColumn <Produto, Integer> tcoId;
    
    @FXML
    TableColumn <Produto, String> tcoCodBarras;
    
    @FXML
    TableColumn <Produto, String> tcoDescricao;
    
    @FXML
    TableColumn <Produto, Double> tcoQtd;
    
    @FXML
    TableColumn <Produto, Double> tcoValorCompra;
    
    @FXML
    TableColumn <Produto, Double> tcoValorVenda;
    
    @FXML
    TableColumn <Produto, Calendar> tcoDataCadastro;
    
    @FXML
    TextField  txtId;
    
    @FXML
    TextField txtCodBarras;
    
    @FXML
    TextField txtDescricao;
    
    @FXML
    TextField txtQtd;
    
    @FXML
    TextField txtValorCompra;
    
    @FXML
    TextField txtValorVenda;
    
    @FXML
    DatePicker dtpDataCadastro;
    
    ProdutosDAO pDAO;
    Produto pClicked;    //armazena o produto clicado na tabela 
    
    private boolean flagNovo;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //vincula as colunas da TableView a classe Produto
        bindColumns();
        
        //busca os dados 
        carregaDados();
        // TODO
    }    
    
    /**
     * Conecta as colunas da TableView com os atributos da classe Produto
     * @return void
     */
    
    public void bindColumns(){
        
        //vincula a coluna tcoId com o atributo id da classe Produto.
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        // adiciona uma configuraçao extra de alinhamento à direita.
        tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoCodBarras.setCellValueFactory(new PropertyValueFactory<>("CodBarras"));
        tcoCodBarras.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        tcoQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tcoQtd.setStyle("-fx-aligment: CENTER_RIGHT;");
        
        tcoValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
        tcoValorCompra.setStyle("-fx-aligment: CENTER_RIGHT;");
        
        tcoValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        tcoValorVenda.setStyle("-fx-aligment: CENTER_RIGHT;");
        
        tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tcoDataCadastro.setStyle("-fx-aligment: CENTER_RIGHT;");
    }
    
    public void carregaDados(){
        
        this.pDAO = new ProdutosDAO();
        
        // carrega view tblProduto com todos os resultados de produto
        this.tblProduto.getItems().setAll(pDAO.getResults());
    }
    
    /**
     * Action do botao btnNovo
     * Limpa os campos e posiciona o focus em txtId.
     * 
     * @return void 
     */
    
    @FXML
    public void btnNovoClick(){
        this.flagNovo = true;
        
        txtId.setText("");
        txtId.setEditable(false);
        
        txtCodBarras.setText("");
        txtCodBarras.requestFocus();
        
        txtDescricao.setText("");
        txtQtd.setText("");
        txtValorCompra.setText("");
        txtValorVenda.setText("");
        dtpDataCadastro.setValue(null);
        
    }
    
    @FXML
    public void tblProdutoOnMouseClicked(){
        
        //getSelectedItem devolve um objeto produto
        //pClicked representa o Produto clickado 
        this.flagNovo = false;
        
        this.pClicked = tblProduto.getSelectionModel().getSelectedItem();
        
        if (pClicked != null){
            txtId.setText(String.valueOf(pClicked.getId()));
            txtId.setEditable(false);
            
            txtCodBarras.setText(pClicked.getCodBarras());
            txtDescricao.setText(pClicked.getDescricao());
            
            txtQtd.setText(String.valueOf(pClicked.getQtd()));
            txtValorCompra.setText(String.valueOf(pClicked.getValorCompra()));
            txtValorVenda.setText(String.valueOf(pClicked.getValorVenda()));
            
            //tratamento da data do cadastro:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            formatter = formatter.withLocale(Locale.US);
            LocalDate date = LocalDate.parse(pClicked.getDataCadastro(), formatter);
            
            dtpDataCadastro.setValue(date);
            dtpDataCadastro.setEditable(false);
            
            this.flagNovo = false;
            
        }
        
    }
    /**
     * gerencia o click do botao salvar. adiciona um novo registro quando
     * flagNovo == true ou atualiza quando flagNovo == false;
     */
    @FXML
    public void btnSalvarClick(){
        
        Produto p = new Produto();
        
        p.setCodBarras(txtCodBarras.getText());
        p.setDescricao(txtDescricao.getText());
        p.setQtd(Double.parseDouble(txtQtd.getText()));
        p.setValorCompra(Double.parseDouble(txtValorCompra.getText()));
        p.setValorVenda(Double.parseDouble(txtValorVenda.getText()));
        
        
        if(this.flagNovo){
            //novo produto
            
            this.pDAO.create(p);
            
        } else {
            // uma atualizaçao
            p.setId(Integer.parseInt(txtId.getText()));
            this.pDAO.update(p);
            
        }
        
        this.carregaDados();
    }
    
    @FXML 
    public void btnExcluir(){
        
        if (this.pClicked != null){
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma Exclusão de Produto?");
            alert.setHeaderText(pClicked.getDescricao());
            alert.setContentText("Tem certeza que deseja excluir ? ");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.pDAO.delete(this.pClicked.getId());
                
                this.tblProduto.getItems().remove(this.pClicked);
        }
        this.tblProduto.getSelectionModel().clearSelection();
        pClicked = null;
        
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de excluir!");
            
            alert.showAndWait();        
        }
    }
}
