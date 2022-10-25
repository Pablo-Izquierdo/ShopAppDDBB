package tiendaForja.persistenceLayer;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.Arma;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el arma.
 * @author Carlos Jimeno
 * @lastmodified 29/04/2020
 */
public class ArmaDataMapper {

	/**
	 * Metodo que selecciona un Arma de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idarma
	 * @return la instancia de la clase arma rellenada.
	 */
	public Arma selectArmaById(int idarma) {
		Arma resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM arma WHERE idarma = " + idarma + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarArma(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener arma con id: " + idarma + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona un Arma de la base de datos mediante
	 * el nombre recibido como parametro y crea una nueva instancia de ella.
	 * @param idarma
	 * @return la instancia de la clase arma rellenada.
	 */
	public Arma selectArmaByNombre(String arma) {
		Arma resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM arma WHERE nombre = '" + arma + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarArma(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener arma con nombre: " + arma + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas las Armas de la base de datos.
	 * @return lista de instancias de la clase Arma.
	 */
	public List<Arma> selectAllArmas() {
		List<Arma> armas = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM arma";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			armas = resultSetToArmas(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas las armas");
			e.printStackTrace();
		}
		return armas;
	}

	/**
	 * Metodo que permite borrar el arma de la base de datos.
	 * @param idarma
	 * @return true si se ha borrado con exito, false en otro caso.
	 */
	public boolean deleteArma(int idarma) {
		String deleteTxt1 = "DELETE FROM objeto WHERE idarma = " + idarma + "";
		String deleteTxt2 = "DELETE FROM arma WHERE idarma = " + idarma + "";
		String exceptionMsg = "No se ha podido borrar el arma con id: " + idarma + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) &&
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg));
	}
	
	/**
	 * Metodo para insertar un arma a la base de datos.
	 * @param a
	 * @return True si se ha podido insertar con exito, False en caso contrario.
	 */
	public boolean insertArma(Arma a) {
		String insertTxt = "INSERT INTO arma(nombre) VALUES (" + "'" + a.getNombre() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir el arma: " + a.getNombre());
	}
	
	/**
	 * Metodo auxiliar para crear instancias del arma a partir de un
	 * conjunto de armas recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de armas creadas
	 */
	private List<Arma> resultSetToArmas(ResultSet resultados){
		List<Arma> armas = new LinkedList<Arma>();
		
		int idarma; String nombre;
		
		try {
			while (resultados.next()) {
				idarma = resultados.getInt("idarma");
				nombre = resultados.getString("nombre");
				armas.add(new Arma(idarma, nombre));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return armas;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo arma a partir
	 * de datos de un unico arma seleccionada recibida como parametro.
	 * @param resultados
	 * @return Un arma ya creada con sus atributos.
	 */
	private Arma procesarArma(ResultSet resultados) {
		Arma resultado = null;
		try {
			int idarma = resultados.getInt("idarma");
			String nombre = resultados.getString("nombre");
			resultado = new Arma(idarma, nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
