package tiendaForja.persistenceLayer;

import tiendaForja.domain.Material;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el material.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class MaterialDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado insertaMaterial
	 * y anhade un nuevo material a la base de datos.
	 * @param nombre
	 * @return True si se ha podido anhadir, false en otro caso.
	 */
	public boolean insertMaterial(String nombre) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaMaterial(?, ?)}");
			cstmt.setString("nombre", nombre);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el material: " + nombre);
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que selecciona un material de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idmaterial
	 * @return la instancia de la clase Material rellenada
	 */
	public Material selectMaterialById(int idmaterial) {
		Material resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM material WHERE idmaterial = " + idmaterial + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarMaterial(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener un material con id: " + idmaterial + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas los materiales de la base de datos.
	 * @return una lista de las instancias de la clase Compra
	 */
	public List<Material> selectAllMateriales() {
		List<Material> materiales = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM material";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			materiales = resultSetToMaterial(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener todas las compras");
			e.printStackTrace();
		}
		return materiales;
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado borraMaterial y
	 * lo elimina de la base de datos.
	 * @param nombre
	 * @return True si ha sido borrado con exito, false en otro caso.
	 */
	public boolean deleteMaterial(String nombre) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.borraMaterial(?, ?)}");
			cstmt.setString("nombre", nombre);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al borrar material: " + nombre);
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo auxiliar para crear instancias del material a partir de un
	 * conjunto de materiales recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de materiales creados
	 */
	private List<Material> resultSetToMaterial(ResultSet resultados){
		List<Material> materiales = new LinkedList<Material>();
		
		int idmaterial; String nombre;
		
		try {
			while(resultados.next()) {
				idmaterial = resultados.getInt("idmaterial");
				nombre = resultados.getString("nombre");
				materiales.add(new Material(idmaterial, nombre));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materiales;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo material
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Un material ya creada con sus atributos.
	 */
	private Material procesarMaterial(ResultSet resultados) {
		Material resultado = null;
		try {
			int idmaterial = resultados.getInt("idmaterial");
			String nombre = resultados.getString("nombre");
			resultado = new Material(idmaterial, nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
