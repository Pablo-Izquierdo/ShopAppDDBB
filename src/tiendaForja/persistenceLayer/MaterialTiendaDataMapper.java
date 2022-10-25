package tiendaForja.persistenceLayer;

import tiendaForja.domain.Material;
import tiendaForja.domain.Tienda;
import tiendaForja.domain.vistas.MaterialTienda;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el MaterialTienda.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class MaterialTiendaDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado insertaMaterialTienda
	 * y anhade un nuevo material a la tienda en la base de datos.
	 * @param nomTienda
	 * @param nomMaterial
	 * @param unidades
	 * @return true si se ha anhadido con exito, false en caso contrario.
	 */
	public boolean insertMaterialTienda(String nomTienda, String nomMaterial, int unidades) {
		
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaMaterialTienda(?, ?, ?, ?)}");
			cstmt.setString("nombreTienda", nomTienda);
			cstmt.setString("nombreMaterial", nomMaterial);
			cstmt.setInt("unidades", unidades);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el material: " + nomMaterial + 
					" en la tienda: " + nomTienda);
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo para obtener la fila de materialTienda mediante los id de ambos.
	 * @param idtienda
	 * @param idmaterial
	 * @return La fila de la tabla materialTienda indicada.
	 */
	public MaterialTienda selectMaterialTiendaById(int idtienda, int idmaterial) {
		MaterialTienda resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT t.*, m.idmaterial, m.nombre AS nombreMat, mo.unidades AS unidadesMat " +
			"FROM tienda t INNER JOIN materialTienda mt ON mt.idtienda = t.idtienda " +
			"INNER JOIN material m ON m.idmaterial = mt.idmaterial " + 
			"WHERE mt.idtienda = " + idtienda + " AND mt.idmaterial = " + idmaterial;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarMaterialTienda(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el material con id: " +
					idmaterial + " para la tienda con id: " + idtienda + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener todos los materiales de la tienda indicada por su nombre.
	 * @param nombre
	 * @return una lista de MaterialesTienda de la tienda
	 */
	public List<MaterialTienda> selectAllMaterialesFromTienda(String nombre) {
		List<MaterialTienda> materialesTienda = new LinkedList<MaterialTienda>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT t.*, m.idmaterial, m.nombre AS nombreMat, mt.unidades AS unidadesMat " +
			"FROM material m INNER JOIN materialTienda mt ON mt.idmaterial = m.idmaterial " +
			"INNER JOIN tienda t ON t.idtienda = mt.idtienda " + 
			"WHERE t.nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialesTienda = resultSetToMaterialTienda(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales para la tienda");
			e.printStackTrace();
		}
		return materialesTienda;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de materialTienda.
	 * @param idmaterial
	 * @param idtienda
	 * @return la lista de materialTienda de la base de datos.
	 */
	public List<MaterialTienda> selectAllMaterialesTienda() {
		List<MaterialTienda> materialTienda = new LinkedList<MaterialTienda>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT t.*, m.idmaterial, m.nombre AS nombreMat, mt.unidades AS unidadesMat " +
					"FROM tienda t INNER JOIN materialTienda mt ON mt.idtienda = t.idtienda " +
					"INNER JOIN material m ON m.idmaterial = mt.idmaterial ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialTienda = resultSetToMaterialTienda(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales de las tiendas");
			e.printStackTrace();
		}
		return materialTienda;
	}
	
	/**
	 * Como hay un trigger que evita el borrado se estableceran las unidades
	 * del material para la tienda a 0.
	 * @param idmaterial
	 * @param idtienda
	 * @return true si se ha activado el trigger con exito, false en caso contrario.
	 */
	public boolean deleteMaterialTienda(int idmaterial, int idtienda) {
		String deleteTxt = "DELETE FROM materialTienda WHERE idmaterial = " + idmaterial
				+ " AND idtienda = " + idtienda;
		return (!SqlServerConnectionManager.executeSqlStatement(deleteTxt, "Excepcion al intentar borrar el material: " +
				idmaterial + " de la tienda: " + idtienda));
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado actualizaMaterialTienda
	 * y cambia el numero de unidades del material para la tienda en la base
	 * de datos.
	 * @param nomTienda
	 * @param nomMaterial
	 * @param nuevasUds
	 * @return true si se ha actualizado con exito, false en otro caso.
	 */
	public boolean updateUnidadesMaterialTienda(String nomTienda, String nomMaterial, int nuevasUds) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.actualizaMaterialTienda(?, ?, ?, ?)}");
			cstmt.setString("nombreTienda", nomTienda);
			cstmt.setString("nombreMaterial", nomMaterial);
			cstmt.setInt("unidades", nuevasUds);
			cstmt.registerOutParameter("errores", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo auxiliar para crear instancias del MaterialTienda a partir de un
	 * conjunto de MaterialesTienda recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de MaterialTienda creadas
	 */
	private List<MaterialTienda> resultSetToMaterialTienda(ResultSet resultados){
		List<MaterialTienda> materialesTienda = new LinkedList<MaterialTienda>();
		
		// Material
		int idmaterial; String nombreMat;
		
		// Tienda
		int idtienda; String nombre; BigDecimal dineroTienda;
		
		// MaterialTienda
		int unidadesMat;
		
		try {
			while (resultados.next()) {
				// Tienda
				idtienda = resultados.getInt("idtienda");
				nombre = resultados.getString("nombre");
				dineroTienda = resultados.getBigDecimal("dinero");
				
				// Material
				idmaterial = resultados.getInt("idmaterial");
				nombreMat = resultados.getString("nombreMat");
				
				// MaterialObjeto
				unidadesMat = resultados.getInt("unidadesMat");
				
				materialesTienda.add(new MaterialTienda(new Material(idmaterial, nombreMat), 
						new Tienda(idtienda, nombre, dineroTienda), unidadesMat));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materialesTienda;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo materialTienda
	 * a partir de datos de una unica materialTienda seleccionado 
	 * recibida como parametro.
	 * @param resultados
	 * @return Un MaterialTienda ya creado con sus atributos.
	 */
	private MaterialTienda procesarMaterialTienda(ResultSet resultados) {
		MaterialTienda resultado = null;
		try {
			// Tienda
			int idtienda = resultados.getInt("idtienda");
			String nombre = resultados.getString("nombre");
			BigDecimal dineroTienda = resultados.getBigDecimal("dinero");
			
			// Material
			int idmaterial = resultados.getInt("idmaterial");
			String nombreMat = resultados.getString("nombreMat");
			
			// MaterialTienda
			int unidadesMat = resultados.getInt("unidadesMat");
			
			resultado = new MaterialTienda(new Material(idmaterial, nombreMat), 
					new Tienda(idtienda, nombre, dineroTienda), unidadesMat);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
