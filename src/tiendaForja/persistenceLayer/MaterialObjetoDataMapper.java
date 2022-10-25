package tiendaForja.persistenceLayer;

import tiendaForja.domain.Material;
import tiendaForja.domain.Objeto;
import tiendaForja.domain.vistas.MaterialObjeto;

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
 * relacionadas con el MaterialObjeto.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class MaterialObjetoDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado insertaMaterialObjeto
	 * y permite anhadir una entrada a la tabla materialObjeto en la base
	 * de datos
	 * @param nombreMaterial
	 * @param nombreObjeto
	 * @param unidades
	 * @return true si se ha podido insertar con exito, false en otro caso.
	 */
	public boolean insertMaterialObjeto(String nombreMaterial, String nombreObjeto, int unidades) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaMaterialObjeto(?, ?, ?, ?)}");
			cstmt.setString("nombreMaterial", nombreMaterial);
			cstmt.setString("nombreObjeto", nombreObjeto);
			cstmt.setInt("unidades", unidades);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar material al objeto");
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo para obtener la fila de materialObjeto mediante los id de ambos.
	 * @param idobjeto
	 * @param idmaterial
	 * @return La fila de la tabla materialObjeto indicada
	 */
	public MaterialObjeto selectMaterialObjetoById(int idobjeto, int idmaterial) {
		MaterialObjeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT o.*, m.idmaterial, m.nombre AS nombreMat, mo.unidades AS unidadesMat " +
			"FROM objeto o INNER JOIN materialObjeto mo ON mo.idobjeto = o.idobjeto " +
			"INNER JOIN material m on m.idmaterial = mo.idmaterial " + 
			"WHERE mo.idobjeto = " + idobjeto + " AND mo.idmaterial = " + idmaterial;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarMaterialObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el material con id: " +
					idmaterial + " para el objeto con id: " + idobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener todos los materiales del objeto indicado por su nombre.
	 * @param nombre
	 * @return una lista de materialesObjeto del objeto
	 */
	public List<MaterialObjeto> selectAllMaterialesFromObjeto(String nombre) {
		List<MaterialObjeto> materialesObjeto = new LinkedList<MaterialObjeto>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT o.*, m.idmaterial, m.nombre AS nombreMat, mo.unidades AS unidadesMat " +
			"FROM material m INNER JOIN materialObjeto mo on mo.idmaterial = m.idmaterial " +
			"INNER JOIN objeto o ON o.idobjeto = mo.idobjeto " + 
			"WHERE o.nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialesObjeto = resultSetToMaterialObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales para el objeto");
			e.printStackTrace();
		}
		return materialesObjeto;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de materialObjeto.
	 * @param idmaterial
	 * @param idobjeto
	 * @return la lista de materialObjeto de la base de datos.
	 */
	public List<MaterialObjeto> selectAllMaterialesObjeto() {
		List<MaterialObjeto> materialObjeto = new LinkedList<MaterialObjeto>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT o.*, m.idmaterial, m.nombre AS nombreMat, mo.unidades AS unidadesMat " +
					"FROM objeto o INNER JOIN materialObjeto mo ON mo.idobjeto = o.idobjeto " +
					"INNER JOIN material m on m.idmaterial = mo.idmaterial ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialObjeto = resultSetToMaterialObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales de los objetos");
			e.printStackTrace();
		}
		return materialObjeto;
	}
	
	/**
	 * Metodo auxiliar para crear instancias del MaterialObjeto a partir de un
	 * conjunto de MaterialesObjeto recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de MaterialObjeto creadas
	 */
	private List<MaterialObjeto> resultSetToMaterialObjeto(ResultSet resultados){
		List<MaterialObjeto> materialesObjeto = new LinkedList<MaterialObjeto>();
		
		// Material
		int idmaterial; String nombreMat;
		
		// Objeto
		int idobjeto; String nombre; int nivel; BigDecimal precio; int diasCreacion;
		int idtipoobjeto; int idarma; int idefecto;
		
		// MaterialObjeto
		int unidadesMat;
		
		try {
			while (resultados.next()) {
				// Objeto
				idobjeto = resultados.getInt("idobjeto");
				nombre = resultados.getString("nombre");
				nivel = resultados.getInt("nivel");
				precio = resultados.getBigDecimal("precio");
				diasCreacion = resultados.getInt("diasCreacion");
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				idarma = resultados.getInt("idarma");
				idefecto = resultados.getInt("idefecto");
				
				// Material
				idmaterial = resultados.getInt("idmaterial");
				nombreMat = resultados.getString("nombreMat");
				
				// MaterialObjeto
				unidadesMat = resultados.getInt("unidadesMat");
				
				materialesObjeto.add(new MaterialObjeto(new Material(idmaterial, nombreMat), 
						new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
						unidadesMat));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materialesObjeto;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo materialObjeto
	 * a partir de datos de una unica materialObjeto seleccionado 
	 * recibida como parametro.
	 * @param resultados
	 * @return Un MaterialObjeto ya creado con sus atributos.
	 */
	private MaterialObjeto procesarMaterialObjeto(ResultSet resultados) {
		MaterialObjeto resultado = null;
		try {
			// Objeto
			int idobjeto = resultados.getInt("idobjeto");
			String nombre = resultados.getString("nombre");
			int nivel = resultados.getInt("nivel");
			BigDecimal precio = resultados.getBigDecimal("precio");
			int diasCreacion = resultados.getInt("diasCreacion");
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			int idarma = resultados.getInt("idarma");
			int idefecto = resultados.getInt("idefecto");
			
			// Material
			int idmaterial = resultados.getInt("idmaterial");
			String nombreMat = resultados.getString("nombreMat");
			
			// MaterialObjeto
			int unidadesMat = resultados.getInt("unidadesMat");
			
			resultado = new MaterialObjeto(new Material(idmaterial, nombreMat), 
					new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
					unidadesMat);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
