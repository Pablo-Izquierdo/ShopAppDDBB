package tiendaForja.persistenceLayer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.Tienda;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con la tienda.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class TiendaDataMapper {
	
	/**
	 * Metodo para insertar una tienda a la base de datos.
	 * @param t
	 * @return true si se ha podido insertar con exito, false en otro caso.
	 */
	public boolean insertTienda(Tienda t) {
		String insertTxt = "INSERT INTO tienda(nombre, dinero) VALUES ('" +
				t.getNombre() + "', " + t.getDineroTienda() + ")";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir la tienda: " + 
				t.getNombre() + " a la base de datos");
	}
	
	/**
	 * Metodo que selecciona una tienda de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idtienda
	 * @return la instancia de la clase tienda rellenada.
	 */
	public Tienda selectTiendaById(int idtienda) {
		Tienda resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tienda WHERE idtienda = " + idtienda + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarTienda(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener tienda con id: " + idtienda + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona una tienda de la base de datos mediante
	 * el nombre de la tienda como parametro y crea una nueva instancia de ella.
	 * @param idtienda
	 * @return la instancia de la clase tienda rellenada.
	 */
	public Tienda selectTiendaByNombre(String nombre) {
		Tienda resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tienda WHERE nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarTienda(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener tienda: " + nombre + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas las tiendas de la base de datos.
	 * @return lista de instancias de la clase Tienda.
	 */
	public List<Tienda> selectAllTiendas() {
		List<Tienda> forjas = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM tienda";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			forjas = resultSetToTienda(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de todas las tiendas");
			e.printStackTrace();
		}
		return forjas;
	}
	
	/**
	 * Metodo que permite el borrado de una tienda de la base de datos.
	 * @param idtienda
	 * @return True si se ha podido borrar, false en otro caso.
	 */
	public boolean deleteTienda(int idtienda) {
		String deleteTxt1 = "DELETE FROM materialTienda WHERE idtienda = " + idtienda;
		String deleteTxt2 = " DELETE FROM forja WHERE idtienda = " + idtienda;
		String deleteTxt3 = " DELETE FROM tienda WHERE idtienda = " + idtienda;
		String exceptionMsg = "No se ha podido borrar la tienda con id: " + idtienda + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) && 
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg) &&
				SqlServerConnectionManager.executeSqlStatement(deleteTxt3, exceptionMsg));
	}
	
	/**
	 * Metodo que permite actualizar el dinero del que dispone una tienda.
	 * @param idtienda
	 * @param dinero
	 * @return true si se ha actualizado con exito, false en otro caso.
	 */
	public boolean updateDineroTienda(int idtienda, BigDecimal dinero) {
		String updateTxt = "UPDATE tienda SET dinero = " + dinero + " WHERE idtienda = " +
				idtienda + "";
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al intentar actualizar el dinero de la tienda");
	}
	
	/**
	 * Metodo auxiliar para crear instancias de la tienda a partir de un
	 * conjunto de tiendas recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de compras creadas
	 */
	private List<Tienda> resultSetToTienda(ResultSet resultados){
		List<Tienda> tiendas = new LinkedList<Tienda>();
		
		int idtienda; String nombre; BigDecimal dineroTienda;
		
		try {
			while(resultados.next()) {
				idtienda = resultados.getInt("idtienda");
				nombre = resultados.getString("nombre");
				dineroTienda = resultados.getBigDecimal("dinero");
				tiendas.add(new Tienda(idtienda, nombre, dineroTienda));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tiendas;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva tienda
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una tienda ya creada con sus atributos.
	 */
	private Tienda procesarTienda(ResultSet resultados) {
		Tienda resultado = null;
		try {
			int idtienda = resultados.getInt("idtienda");
			String nombre = resultados.getString("nombre");
			BigDecimal dineroTienda = resultados.getBigDecimal("dinero");
			resultado = new Tienda(idtienda, nombre, dineroTienda);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
