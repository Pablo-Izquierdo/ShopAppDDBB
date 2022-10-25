package tiendaForja.persistenceLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.TipoObjeto;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el tipoObjeto.
 * @author Carlos Jimeno, Sara Grela
 * @lastmodified 29/04/2020
 */
public class TipoObjetoDataMapper {

	/**
	 * Metodo para insertar un tipoObjeto a la base de datos.
	 * @param to
	 * @return True si se ha podido insertar con exito, False en caso contrario.
	 */
	public boolean insertTipoObjeto(TipoObjeto to) {
		String insertTxt = "INSERT INTO tipoObjeto(nombre) VALUES (" + "'" + to.getNombre() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir el tipoObjeto: " + to.getNombre());
	}
	
	/**
	 * Metodo que selecciona un tipoObjeto de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idtipoobjeto
	 * @return la instancia de la clase TipoObjeto rellenada.
	 */
	public TipoObjeto selectTipoObjetoById(int idtipoobjeto) {
		TipoObjeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tipoObjeto WHERE idtipoobjeto = " + idtipoobjeto + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarTipoObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener tipoObjeto con id: " + idtipoobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona un tipoObjeto de la base de datos mediante
	 * el nombre recibido como parametro y crea una nueva instancia de ella.
	 * @param nombreTipoObjeto
	 * @return la instancia de la clase TipoObjeto rellenada.
	 */
	public TipoObjeto selectTipoObjetoByNombre(String nombreTipoObjeto) {
		TipoObjeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tipoObjeto WHERE nombre = '" + nombreTipoObjeto + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarTipoObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener tipoObjeto con nombre: " + nombreTipoObjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas los tiposObjeto de la base de datos.
	 * @return lista de instancias de la clase Arma.
	 */
	public List<TipoObjeto> selectAllArmas() {
		List<TipoObjeto> tiposObjeto = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tipoObjeto";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			tiposObjeto = resultSetToTipoObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas los tiposObjeto");
			e.printStackTrace();
		}
		return tiposObjeto;
	}
	
	/**
	 * Metodo que permite borrar el tipoObjeto de la base de datos.
	 * @param idtipoobjeto
	 * @return true si se ha borrado con exito, false en otro caso.
	 */
	public boolean deleteTipoObjeto(int idtipoobjeto) {
		String deleteTxt1 = "DELETE FROM objeto WHERE idtipoobjeto = " + idtipoobjeto + " ";
		String deleteTxt2 = "DELETE FROM efecto WHERE idefecto = " + idtipoobjeto + " ";
		String exceptionMsg = "No se ha podido borrar el tipo de objeto con id: " + idtipoobjeto + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) &&
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg));
	}

	
	/**
	 * Metodo auxiliar para crear instancias del tipoObjeto a partir de un
	 * conjunto de tipoObjetos recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de tipoObjetos creados
	 */
	private List<TipoObjeto> resultSetToTipoObjeto(ResultSet resultados){
		List<TipoObjeto> tipoObjetos = new LinkedList<TipoObjeto>();
		
		int idtipoobjeto; String nombre;
		
		try {
			while (resultados.next()) {
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				nombre = resultados.getString("nombre");
				tipoObjetos.add(new TipoObjeto(idtipoobjeto, nombre));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tipoObjetos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo tipoObjeto a partir
	 * de datos de un unico tipoObjeto seleccionado recibido como parametro.
	 * @param resultados
	 * @return Un tipoObjeto ya creado con sus atributos.
	 */
	private TipoObjeto procesarTipoObjeto(ResultSet resultados) {
		TipoObjeto resultado = null;
		try {
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			String nombre = resultados.getString("nombre");
			resultado = new TipoObjeto(idtipoobjeto, nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
