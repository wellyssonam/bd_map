package br.ufpi.tbd.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public static Connection conexao;
    
    public static void conectar() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Conexao.conexao = DriverManager.getConnection("jdbc:mysql://localhost/map","root","root");
        System.out.println("Conectado! ");

        
    }
    
}
