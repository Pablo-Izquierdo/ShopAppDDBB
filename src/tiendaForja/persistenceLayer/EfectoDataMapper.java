package tiendaForja.persistenceLayer;

import tiendaForja.domain.Efecto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el efecto.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class EfectoDataMapper {
	
	/**
	 * Inserta un nuevo efecto en la base de datos.
	 * @param e
	 * @return True si se ha insertado con exito, false en caso contrario.
	 */
	public boolean insertEfecto(Efecto e) {
		String insertTxt = "INSERT INTO efecto(nombre) VALUES ('" + e.getNombre() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir el efecto: " + e.getNombre());
	}
	
	/**
	 * Metodo que selecciona un efecto de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idefecto
	 * @return la instancia de la clase Efecto rellenada.
	 */
	public Efecto selectEfectoById(int idefecto) {
		Efecto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM efecto WHERE idefecto = " + idefecto + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesaEfecto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener efecto con id: " + idefecto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona un efecto de la base de datos mediante
	 * el nombre recibido como parametro y crea una nueva instancia de ella.
	 * @param idefecto
	 * @return la instancia de la clase Efecto rellenada.
	 */
	public Efecto selectEfectoByNombre(String efecto) {
		Efecto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM efecto WHERE nombre = '" + efecto + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesaEfecto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener efecto con nombre: " + efecto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas los efectos de la base de datos.
	 * @return lista de instancias de la clase Efecto.
	 */
	public List<Efecto> selectAllArmas() {
		List<Efecto> efectos = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM efecto";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			efectos = resultSetToEfectos(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas los efectos");
			e.printStackTrace();
		}
		return efectos;
	}
	
	/**
	 * Metodo que permite borrar el efecto de la base de datos.
	 * @param idefecto
	 * @return true si se ha borrado con exito, false en otro caso.
	 */
	public boolean deleteEfecto(int idefecto) {
		String deleteTxt1 = "DELETE FROM objeto WHERE idefecto = " + idefecto + "";
		String deleteTxt2 = " DELETE FROM efecto WHERE idefecto = " + idefecto + "";
		String exceptionMsg = "No se ha podido borrar el efecto con id: " + idefecto + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) &&
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg));
	}
	
	/**
	 * Metodo auxiliar para crear instancias del efecto a partir de un
	 * conjunto de efectos recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de Efectos creadas
	 */
	private List<Efecto> resultSetToEfectos(ResultSet resultados){
		List<Efecto> efectos = new LinkedList<Efecto>();
		
		int idefecto; String nombre;
		
		try {
			while (resultados.next()) {
				idefecto = resultados.getInt("idefecto");
				nombre = resultados.getString("nombre");
				efectos.add(new Efecto(idefecto, nombre));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return efectos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo efecto
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Un efecto ya creado con sus atributos.
	 */
	private Efecto procesaEfecto(ResultSet resultados) {
		Efecto resultado = null;
		try {
			int idefecto = resultados.getInt("idefecto");
			String nombre = resultados.getString("nombre");
			resultado = new Efecto(idefecto, nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}

