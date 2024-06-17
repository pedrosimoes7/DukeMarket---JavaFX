module br.com.simoes.dukemarket.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    opens br.com.simoes.dukemarket.javafx to javafx.fxml;
    opens br.com.simoes.dukemarket.javafx.controller to javafx.fxml;
            
    exports br.com.simoes.dukemarket.javafx;
    exports br.com.simoes.dukemarket.javafx.controller;
    exports br.com.simoes.dukemarket.javafx.DAO;
    exports br.com.simoes.dukemarket.javafx.model;
    exports connection;
    
    requires mysql.connector.java;
    
}
