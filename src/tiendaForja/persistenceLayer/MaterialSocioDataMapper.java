package tiendaForja.persistenceLayer;

import tiendaForja.domain.Material;
import tiendaForja.domain.Socio;
import tiendaForja.domain.vistas.MaterialSocio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el MaterialSocio.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class MaterialSocioDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado insertaMaterialSocio
	 * y anhade un nuevo material al socio en la base de datos.
	 * @param nomSocio
	 * @param nomMaterial
	 * @param unidades
	 * @return true si se ha anhadido con exito, false en caso contrario.
	 */
	public boolean insertMaterialSocio(int idsocio, String nomMaterial, int unidades) {
		
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaMaterialSocio(?, ?, ?, ?)}");
			cstmt.setInt("idSocio", idsocio);
			cstmt.setString("nombreMaterial", nomMaterial);
			cstmt.setInt("unidades", unidades);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el material: " + nomMaterial + 
					" en el socio con id: " + idsocio);
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo para obtener la fila de materialSocio mediante los id de ambos.
	 * @param idsocio
	 * @param idmaterial
	 * @return La fila de la tabla materialSocio indicada.
	 */
	public MaterialSocio selectMaterialSocioById(int idsocio, int idmaterial) {
		MaterialSocio resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT s.*, m.idmaterial, m.nombre AS nombreMat, ms.unidades AS unidadesMat " +
			"FROM socio s INNER JOIN materialSocio ms ON ms.idsocio = s.idsocio " +
			"INNER JOIN material m ON m.idmaterial = ms.idmaterial " + 
			"WHERE ms.idsocio = " + idsocio + " AND ms.idmaterial = " + idmaterial;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarMaterialSocio(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el material con id: " +
					idmaterial + " para el socio con id: " + idsocio + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener todos los materiales del socio indicado por su id.
	 * @param idsocio
	 * @return una lista de MaterialesSocio del socio
	 */
	public List<MaterialSocio> selectAllMaterialesFromSocio(int idsocio) {
		List<MaterialSocio> materialesSocio = new LinkedList<MaterialSocio>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT s.*, m.idmaterial, m.nombre AS nombreMat, ms.unidades AS unidadesMat " +
			"FROM material m INNER JOIN materialSocio ms ON ms.idmaterial = m.idmaterial " +
			"INNER JOIN socio s ON s.idsocio = ms.idsocio " + 
			"WHERE s.idsocio = " + idsocio;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialesSocio = resultSetToMaterialSocio(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales para el socio");
			e.printStackTrace();
		}
		return materialesSocio;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de materialSocio.
	 * @param idmaterial
	 * @param idsocio
	 * @return la lista de materialSocio de la base de datos.
	 */
	public List<MaterialSocio> selectAllMaterialesSocio() {
		List<MaterialSocio> materialSocio = new LinkedList<MaterialSocio>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT s.*, m.idmaterial, m.nombre AS nombreMat, ms.unidades AS unidadesMat " +
					"FROM socio s INNER JOIN materialSocio ms ON ms.idsocio = s.idsocio " +
					"INNER JOIN material m ON m.idmaterial = mt.idmaterial ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			materialSocio = resultSetToMaterialSocio(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los materiales del socio");
			e.printStackTrace();
		}
		return materialSocio;
	}
	
	/**
	 * Como hay un trigger que evita el borrado se estableceran las unidades
	 * del material para el socio a 0.
	 * @param idmaterial
	 * @param idsocio
	 * @return true si se ha activado el trigger con exito, false en caso contrario.
	 */
	public boolean deleteMaterialSocio(int idmaterial, int idsocio) {
		String deleteTxt = "DELETE FROM materialSocio WHERE idmaterial = " + idmaterial
				+ " AND idsocio = " + idsocio;
		return (!SqlServerConnectionManager.executeSqlStatement(deleteTxt, "Excepcion al intentar borrar el material: " +
				idmaterial + " del socio con id: " + idsocio));
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado actualizaMaterialSocio
	 * y cambia el numero de unidades del material para el socio en la base
	 * de datos.
	 * @param idoscio
	 * @param nomMaterial
	 * @param nuevasUds
	 * @return true si se ha actualizado con exito, false en otro caso.
	 */
	public boolean updateUnidadesMaterialSocio(int idsocio, String nomMaterial, int nuevasUds) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.actualizaMaterialSocio(?, ?, ?, ?)}");
			cstmt.setInt("idSocio", idsocio);
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
	 * Metodo auxiliar para crear instancias del MaterialSocio a partir de un
	 * conjunto de MaterialesSocio recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de MaterialSocio creadas
	 */
	private List<MaterialSocio> resultSetToMaterialSocio(ResultSet resultados){
		List<MaterialSocio> materialesSocio = new LinkedList<MaterialSocio>();
		
		// Material
		int idmaterial; String nombreMat;
		
		// Socio
		int idsocio; int idusuario; Date fechaSocio;
		
		// MaterialSocio
		int unidadesMat;
		
		try {
			while (resultados.next()) {
				// Socio
				idsocio = resultados.getInt("idsocio");
				idusuario = resultados.getInt("idusuario");
				fechaSocio = resultados.getDate("fechaSocio");
				
				// Material
				idmaterial = resultados.getInt("idmaterial");
				nombreMat = resultados.getString("nombreMat");
				
				// MaterialObjeto
				unidadesMat = resultados.getInt("unidadesMat");
				
				materialesSocio.add(new MaterialSocio(new Material(idmaterial, nombreMat), new Socio(idsocio, idusuario, fechaSocio), unidadesMat));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materialesSocio;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo materialSocio
	 * a partir de datos de una unica materialSocio seleccionado 
	 * recibida como parametro.
	 * @param resultados
	 * @return Un MaterialSocio ya creado con sus atributos.
	 */
	private MaterialSocio procesarMaterialSocio(ResultSet resultados) {
		MaterialSocio resultado = null;
		try {
			// Socio
			int idsocio = resultados.getInt("idsocio");
			int idusuario = resultados.getInt("idusuario");
			Date fechaSocio = resultados.getDate("fechaSocio");
			
			// Material
			int idmaterial = resultados.getInt("idmaterial");
			String nombreMat = resultados.getString("nombreMat");
			
			// MaterialSocio
			int unidadesMat = resultados.getInt("unidadesMat");
			
			resultado = new MaterialSocio(new Material(idmaterial, nombreMat), new Socio(idsocio, idusuario, fechaSocio), unidadesMat);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
