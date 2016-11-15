package br.ufpi.tbd.initial;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.ResultSet;

import br.ufpi.tbd.config.Conexao;

public class Main {
	
	/**
	 * Cria a tabela infomapa caso não exista no banco
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void create_table_infomapa() throws SQLException, ClassNotFoundException{
		Conexao.conectar();//Conecta ao BD
        String query = "CREATE TABLE IF NOT EXISTS infomapa("
        		+ "id INT AUTO_INCREMENT PRIMARY KEY,"
        		+ "nome_ponto VARCHAR(20) NOT NULL,"
        		+ "latitude FLOAT NOT NULL,"
        		+ "longitude FLOAT NOT NULL,"
        		+ ")ENGINE=MyISAM";//Criando query
        //ps = Conexao.conexao.prepareStatement(query);//Prepara query
        //ps.executeQuery(query);//Executa query
	}
	
	/**
	 * Insere na tabela as informações pertencentes a cada ponto do mapa
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertInfoTableInfomapa() throws SQLException, ClassNotFoundException{
		PreparedStatement ps;
		Conexao.conectar();//Conecta ao BD
		ps = Conexao.conexao.prepareStatement("INSERT INTO infomapa VALUES (?, ?, ?, ?)");//Prepara a query de inserção
		ps.setInt(1, 0);//Seta o valor do ID(no caso é desnecessário. Feito só pra nada mesmo)
        ps.setString(2, "3-av. nossa senhora de fatima");////Seta nome da imagem na segunda coluna da tabela 'img'
        ps.setFloat(3, (float) -5.066794);
        ps.setFloat(4, (float) -42.793243);
        ps.execute();//Executa query para inserção no BD
	}
	
	/**
	 * Retorna uma lista de cada informações de cada ponto do mapa encontado no banco de dados
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<InfoMapa> getInfoMapa() throws ClassNotFoundException, SQLException{
		Conexao.conectar();//Conecta ao BD
		PreparedStatement ps;
		ArrayList<InfoMapa> lista = new ArrayList<InfoMapa>();
		InfoMapa im;
        
		String query = "SELECT * FROM infomapa";//Criando query
        ps = Conexao.conexao.prepareStatement(query);//Prepara query
        ResultSet rs = (ResultSet) ps.executeQuery(query);//Executa query e joga resultado na variavel 'rs'
        while(rs.next()){ //Vai para o primeiro(e único) registro
	        im = new InfoMapa();//Cria um objeto InfoMapa
	        im.setId(rs.getInt("id"));
	        im.setNomePonto(rs.getString("nome_ponto"));//Seta nome do ponto
	        im.setLatituide(rs.getFloat("latitude"));
	        im.setLongitude(rs.getFloat("longitude"));
	        lista.add(im);
        }        
        return lista;
	}
	
	/**
	 * NÃO ESTÁ FUNCIONANDO
	 * Deletar um item da tabela infomapa
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void deleteInfoMapa(int id) throws ClassNotFoundException, SQLException{
		Conexao.conectar();//Conecta ao BD
		PreparedStatement ps;
        
		String query = "DELETE FROM infomapa WHERE id = " + id;//Criando query
        ps = Conexao.conexao.prepareStatement(query);//Prepara query
        ResultSet rs = (ResultSet) ps.executeQuery(query);//Executa query e joga resultado na variavel 'rs'
        rs.next();
	}
	
	/**
	 * exibe na tela as informações presentes na tabela infomapa
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void exibirInfoMapa() throws ClassNotFoundException, SQLException{
		ArrayList<InfoMapa> lista = getInfoMapa();
		for (InfoMapa im : lista){
			System.out.println(im.getId());
			System.out.println(im.getNomePonto());
			System.out.println(im.getLatituide());
			System.out.println(im.getLongitude());
			System.out.println();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Conexao.conectar();
		
		// acessa os metadados do banco de dados
		DatabaseMetaData metadados = (DatabaseMetaData) Conexao.conexao.getMetaData();

		// verificar se a tabela existe
		ResultSet tabela = (ResultSet) metadados.getTables(null, null, "infomapa", null);

		// condição, caso a tabela exista
		if (tabela.next()) {
			System.out.println("Existe");
			insertInfoTableInfomapa();
			// getInfoMapa();
			exibirInfoMapa();
			//deleteInfoMapa(5);
		}
		else{
			System.out.println("Tabela \"infomapa\" não existe!!!");
					
		}

	}
	
	

}

class InfoMapa {
	public String nomePonto;
	public float latituide, longitude;
	public int id;
	
	public String getNomePonto() {
		return nomePonto;
	}
	
	public void setNomePonto(String nomePonto) {
		this.nomePonto = nomePonto;
	}
	
	public float getLatituide() {
		return latituide;
	}
	
	public void setLatituide(float latituide) {
		this.latituide = latituide;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}