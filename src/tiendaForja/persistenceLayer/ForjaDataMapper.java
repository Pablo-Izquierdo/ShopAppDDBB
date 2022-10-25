package tiendaForja.persistenceLayer;

import tiendaForja.domain.Forja;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con la forja.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class ForjaDataMapper {
	
	/**
	 * Metodo para insertar una forja a la base de datos.
	 * @param f
	 * @return true si se ha podido insertar con exito, false en otro caso.
	 */
	public boolean insertForja(Forja f) {
		String insertTxt = "INSERT INTO forja(idtienda) VALUES (" + f.getIdtienda() + ")";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir la forja: " + 
				f.getIdforja() + " a la base de datos");
	}
	
	/**
	 * Metodo que selecciona una forja de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idforja
	 * @return la instancia de la calse forja rellenada.
	 */
	public Forja selectForjaById(int idforja) {
		Forja resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM forja WHERE idforja = " + idforja + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarForja(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener forja con id: " + idforja + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas las forjas de la base de datos.
	 * @return lista de instancias de la clase Forja.
	 */
	public List<Forja> selectAllForjas() {
		List<Forja> forjas = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM forja";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			forjas = resultSetToForja(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas las forjas");
			e.printStackTrace();
		}
		return forjas;
	}
	
	/**
	 * Metodo que permite el borrado de una forja de la base de datos.
	 * @param idforja
	 * @return true si se borro con exito, false en otro caso
	 */
	public boolean deleteForja(int idforja) {
		String deleteTxt1 = "DELETE FROM encargo WHERE idforja = " + idforja;
		String deleteTxt2 = " DELETE FROM forja WHERE idforja = " + idforja;
		String exceptionMsg = "No se ha podido borrar la forja con id: " + idforja + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) && 
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg));
	
	}
	
	/**
	 * Metodo auxiliar para crear instancias de la forja a partir de un
	 * conjunto de forjas recibidads como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de forjas creadas
	 */
	private List<Forja> resultSetToForja(ResultSet resultados){
		List<Forja> forjas = new LinkedList<Forja>();
		
		int idforja; int idtienda;
		
		try {
			while(resultados.next()) {
				idforja = resultados.getInt("idforja");
				idtienda = resultados.getInt("idtienda");
				forjas.add(new Forja(idforja, idtienda));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forjas;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva forja.
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una forja ya creada con sus atributos.
	 */
	private Forja procesarForja(ResultSet resultados) {
		Forja resultado = null;
		try {
			int idforja = resultados.getInt("idforja");
			int idtienda = resultados.getInt("idtienda");
			resultado = new Forja(idforja, idtienda);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
