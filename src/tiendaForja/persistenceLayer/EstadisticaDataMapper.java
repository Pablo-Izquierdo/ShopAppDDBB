package tiendaForja.persistenceLayer;

import tiendaForja.domain.Estadistica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con la estadistica.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class EstadisticaDataMapper {
	
	/**
	 * Metodo que inserta una estadistica en la base de datos.
	 * @param e
	 * @return true si la insercion ha tenido exito, false en caso contrario.
	 */
	public boolean insertEstadistica(Estadistica e) {
		String insertTxt = "INSERT INTO estadistica(idtipoobjeto, nombre) VALUES (" + e.getIdtipoobjeto() +
				", '" + e.getNombre() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir la estadistica: " + e.getNombre());
	}
	
	/**
	 * Metodo que selecciona una estadistica por su id.
	 * @param idestadistica
	 * @return Una instancia de la clase Estadistica.
	 */
	public Estadistica selectEstadisticaById(int idestadistica) {
		Estadistica resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM estadistica WHERE idestadistica = " + idestadistica + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarEstadistica(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener estadistica con id: " + idestadistica + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona todas las estadisticas de un tipo de objeto.
	 * @param tipoObjeto
	 * @return lista de estadisticas del tipo de objeto
	 */
	public List<Estadistica> selectEstadisticaByTipoObjeto(int tipoObjeto) {
		List<Estadistica> estadisticasTipoObjeto = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM estadistica WHERE idtipoobjeto = " + tipoObjeto + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			estadisticasTipoObjeto = resultSetToEstadistica(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener estadistica con id: " + tipoObjeto + "");
			e.printStackTrace();
		}
		return estadisticasTipoObjeto;
	}
	
	/**
	 * Metodo que retorna una lista con todas las estadisticas de la base
	 * de datos.
	 * @return lista de instancias de la clase Estadistica
	 */
	public List<Estadistica> selectAllEstadisticas() {
		List<Estadistica> estadisticas = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM estadistica";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			estadisticas = resultSetToEstadistica(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas las estadisticas");
			e.printStackTrace();
		}
		return estadisticas;
	}
	
	/**
	 * Metodo auxiliar para crear instancias de la compra a partir de un
	 * conjunto de compras recibidads como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de compras creadas
	 */
	private List<Estadistica> resultSetToEstadistica(ResultSet resultados){
		List<Estadistica> estadisticas = new LinkedList<Estadistica>();
		
		int idestadistica; int idtipoobjeto; String nombre;
		
		try {
			while(resultados.next()) {
				idestadistica = resultados.getInt("idestadistica");
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				nombre = resultados.getString("nombre");
				estadisticas.add(new Estadistica(idestadistica, idtipoobjeto, nombre));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return estadisticas;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva estadistica
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una estadistica ya creada con sus atributos.
	 */
	private Estadistica procesarEstadistica(ResultSet resultados) {
		Estadistica resultado = null;
		try {
			int idestadistica = resultados.getInt("idestadistica");
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			String nombre = resultados.getString("nombre");
			resultado = new Estadistica(idestadistica, idtipoobjeto, nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
