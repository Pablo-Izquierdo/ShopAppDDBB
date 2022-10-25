package tiendaForja.persistenceLayer;

import tiendaForja.domain.Estadistica;
import tiendaForja.domain.Objeto;
import tiendaForja.domain.vistas.EstadisticaObjeto;

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
 * relacionadas con la estadistica-objeto.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class EstadisticaObjetoDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado insertaEstadistica e inserta una nueva
	 * estadistica para un determinado objeto en la base de datos. 
	 * @param nombreObjeto
	 * @param nombreEstadistica
	 * @param duracion
	 * @param porcentaje
	 * @return true si se ha podido insertar, false en otro caso.
	 */
	public boolean insertEstadisticaObjeto(String nombreObjeto, String nombreEstadistica, int duracion, BigDecimal porcentaje) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaEstadistica(?, ?, ?, ?, ?)}");
			cstmt.setString("nombreObjeto", nombreObjeto);
			cstmt.setString("nombreEstadistica", nombreEstadistica);
			cstmt.setInt("duracion", duracion);
			cstmt.setBigDecimal("porcentaje", porcentaje);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar la estadistica para el objeto: " +
					nombreObjeto);
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que permite seleccionar la estadistica del objeto mediante sus ids.
	 * @param idobjeto
	 * @param idestadistica
	 * @return la fila de la tabla estadisticaObjeto.
	 */
	public EstadisticaObjeto selectEstadisticaObjetoById(int idobjeto, int idestadistica) {
		EstadisticaObjeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT eo.idestadistica, eo.duracion, eo.porcentaje, " +
					"e.nombre AS nombreEst, o.* FROM estadistica e " +
					"INNER JOIN estadisticaObjeto eo ON eo.idestadistica = e.idestadistica " + 
					"INNER JOIN objeto o ON o.idobjeto = eo.idobjeto " + 
					"WHERE eo.idobjeto = " + idobjeto + " AND eo.idestadistica = " + idestadistica;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarEstadisticaObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener la estadistica con id: " +
					idestadistica + " para el objeto con id: " + idobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que permite seleccionar todas las estadisticas de un objeto.
	 * @param nombre
	 * @param idestadistica
	 * @param idobjeto
	 * @return la lista de estadisticasObjeto de un unico objeto.
	 */
	public List<EstadisticaObjeto> selectAllEstadisticasFromObjeto(String nombre) {
		List<EstadisticaObjeto> estadisticasDelObjeto = new LinkedList<EstadisticaObjeto>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT eo.idestadistica, eo.duracion, eo.porcentaje, " +
					"e.nombre AS nombreEst, o.* FROM estadistica e " +
					"INNER JOIN estadisticaObjeto eo ON eo.idestadistica = e.idestadistica " + 
					"INNER JOIN objeto o ON o.idobjeto = eo.idobjeto " + 
					"WHERE o.nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			estadisticasDelObjeto = resultSetToEstadisticaObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener las estadisticas del objeto");
			e.printStackTrace();
		}
		return estadisticasDelObjeto;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de estadisticaObjeto.
	 * @param idestadistica
	 * @param idobjeto
	 * @return la lista de estadisticasObjeto de la base de datos.
	 */
	public List<EstadisticaObjeto> selectAllEstadisticasObjeto() {
		List<EstadisticaObjeto> estadisticasDelObjeto = new LinkedList<EstadisticaObjeto>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT eo.idestadistica, eo.duracion, eo.porcentaje, " +
					"e.nombre AS nombreEst, o.* FROM estadistica e " +
					"INNER JOIN estadisticaObjeto eo ON eo.idestadistica = e.idestadistica " + 
					"INNER JOIN objeto o ON o.idobjeto = eo.idobjeto ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			estadisticasDelObjeto = resultSetToEstadisticaObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener las estadisticas del objeto");
			e.printStackTrace();
		}
		return estadisticasDelObjeto;
	}
	
	/**
	 * Metodo que permite actualizar la duracion de una estadistica para un objeto.
	 * @param idObjeto
	 * @param idEstadistica
	 * @param duracion
	 * @return true si se ha podido actualizar, false en otro caso
	 */
	public boolean updateDuracionEstadisticaOfObjeto(int idObjeto, int idEstadistica, int duracion) {
		String updateTxt = "UPDATE estadisticaObjeto SET duracion = " + duracion + " WHERE idobjeto = " + 
				idObjeto + " AND idestadistica = " + idEstadistica;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar la duracion");
	}
	
	/**
	 * Metodo que permite actualizar el porcentaje de una estadistica para un objeto.
	 * @param idObjeto
	 * @param idEstadistica
	 * @param duracion
	 * @return true si se ha podido actualizar, false en otro caso
	 */
	public boolean updatePorcentajeEstadistica(int idObjeto, int idEstadistica, BigDecimal porcentaje) {
		String updateTxt = "UPDATE estadisticaObjeto SET porcentaje = " + porcentaje + " WHERE idobjeto = " + 
				idObjeto + " AND idestadistica = " + idEstadistica;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar el porcentaje");
		
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado borraEstadistica y elimina
	 * la estadistica del objeto en la base de datos.
	 * @param objeto
	 * @param estadistica
	 * @return true si se ha podido eliminar, false en otro caso.
	 */
	public boolean deleteEstadisticaFromObjeto(String objeto, String estadistica) {
		
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.borraEstadistica(?, ?, ?)}");
			cstmt.setString("nombreObjeto", objeto);
			cstmt.setString("nombreEstadistica", estadistica);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al borrar la estadistica para el objeto: " +
					objeto);
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo auxiliar para crear instancias de la EstadisticaObjeto a partir de un
	 * conjunto de EstadisticasObjeto recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de EstadisticaObjeto creadas
	 */
	private List<EstadisticaObjeto> resultSetToEstadisticaObjeto(ResultSet resultados){
		List<EstadisticaObjeto> estadisticasObjetos = new LinkedList<EstadisticaObjeto>();
		
		// Estadistica
		int idestadistica; /* cojemos idtipoobjeto del objeto */ String nombreEst;
		
		// Objeto
		int idobjeto; String nombre; int nivel; BigDecimal precio; int diasCreacion;
		int idtipoobjeto; int idarma; int idefecto;
		
		// EstadisticaObjeto
		int duracion; BigDecimal porcentaje;
		
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
				
				// Estadistica
				idestadistica = resultados.getInt("idestadistica");
					// idtipoobjeto se recoge en el objeto
				nombreEst = resultados.getString("nombreEst");
				
				// EstadisticaObjeto
				duracion = resultados.getInt("duracion");
				porcentaje = resultados.getBigDecimal("porcentaje");
				
				estadisticasObjetos.add(new EstadisticaObjeto(new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
							new Estadistica(idestadistica, idtipoobjeto, nombreEst), duracion, porcentaje));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return estadisticasObjetos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo estadisticaObjeto 
	 * a partir de datos de una unica estadisticaObjeto seleccionado 
	 * recibida como parametro.
	 * @param resultados
	 * @return Una EstadisticaObjeto ya creado con sus atributos.
	 */
	private EstadisticaObjeto procesarEstadisticaObjeto(ResultSet resultados) {
		EstadisticaObjeto resultado = null;
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
			
			// Estadistica
			int idestadistica = resultados.getInt("idestadistica");
				// idtipoobjeto se recoge en el objeto
			String nombreEst = resultados.getString("nombreEst");
			
			// EstadisticaObjeto
			int duracion = resultados.getInt("duracion");
			BigDecimal porcentaje = resultados.getBigDecimal("porcentaje");
			
			resultado = new EstadisticaObjeto(new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto),
						new Estadistica(idestadistica, idtipoobjeto, nombreEst), duracion, porcentaje);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
