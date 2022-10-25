package tiendaForja.persistenceLayer;

import tiendaForja.domain.Objeto;
import tiendaForja.domain.Tienda;
import tiendaForja.domain.vistas.ObjetoTienda;

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
 * relacionadas con el objetoTienda.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class ObjetoTiendaDataMapper {

	/**
	 * Metodo que llama al procedimiento almacenado insertaObjeto e inserta
	 * un nuevo objeto para la tienda en la base de datos.
	 * @param nombreObjeto
	 * @param nombreTienda
	 * @param unidades
	 * @return true si se ha podido insertar con exito, false en otro caso.
	 */
	public boolean insertObjetoATienda(String nombreObjeto, String nombreTienda, int unidades) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.insertaObjeto(?, ?, ?, ?)}");
			cstmt.setString("nombreObjeto", nombreObjeto);
			cstmt.setString("nombreTienda", nombreTienda);
			cstmt.setInt("unidades", unidades);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el objeto: " + nombreObjeto + " a la tienda: " + nombreTienda);
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
		
	/**
	 * Metodo para obtener la fila de ObjetoTienda mediante los id de ambos.
	 * @param idtienda
	 * @param idobjeto
	 * @return La fila de la tabla ObjetoTienda indicada.
	 */
	public ObjetoTienda selectObjetoTiendaById(int idtienda, int idobjeto) {
		ObjetoTienda resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT o.*, t.idtienda, t.nombre as nombreTienda, t.dinero " +
			"FROM tienda t INNER JOIN objetoTienda ot ON ot.idtienda = t.idtienda " +
			"INNER JOIN objeto o ON o.idobjeto = ot.idobjeto " + 
			"WHERE ot.idtienda = " + idtienda + " AND ot.idobjeto = " + idobjeto;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarObjetoTienda(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el objeto con id: " +
					idobjeto + " para la tienda con id: " + idtienda + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener todos los objetos de la tienda indicada por su nombre.
	 * @param nombre
	 * @return una lista de ObjetosTienda de la tienda
	 */
	public List<ObjetoTienda> selectAllObjetosFromTienda(String nombre) {
		List<ObjetoTienda> objetosTienda = new LinkedList<ObjetoTienda>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT mc.*, ot.idtienda, ot.unidades FROM dbo.muestra_catalogo mc INNER JOIN objetoTienda ot "
					+ "on mc.idobjeto = ot.idobjeto WHERE ot.idtienda = (SELECT idtienda FROM tienda WHERE nombre = '" + nombre + "')";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			objetosTienda = resultSetToObjetoTienda(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los objetos para la tienda: " + nombre);
			e.printStackTrace();
		}
		return objetosTienda;
	}
	
	/**
	 * Metodo para obtener todos las filas de objetoTienda.
	 * @return una lista de ObjetosTienda de la tienda
	 */
	public List<ObjetoTienda> selectAllObjetosTienda() {
		List<ObjetoTienda> objetosTienda = new LinkedList<ObjetoTienda>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT o.*, t.idtienda, t.nombre as nombreTienda, t.dinero " +
					"FROM tienda t INNER JOIN objetoTienda ot ON ot.idtienda = t.idtienda " +
					"INNER JOIN objeto o ON o.idobjeto = ot.idobjeto ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			objetosTienda = resultSetToObjetoTienda(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los objetos para la tienda");
			e.printStackTrace();
		}
		return objetosTienda;
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado actualizaUnidadesObjeto
	 * y cambia el numero de unidades que existen de un objeto.
	 * @param nombre
	 * @param unidades
	 * @param nombretienda
	 * @return true si se ha podido actualizar, false en otro caso.
	 */
	public boolean updateUnidadesObjeto(String nombre, int unidades, String nombreTienda) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
	
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.actualizaUnidadesObjeto(?, ?, ?, ?)}");
			cstmt.setString("nombreObjeto", nombre);
			cstmt.setInt("unidades", unidades);
			cstmt.setString("nombreTienda", nombreTienda);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al actualizar las unidades del objeto: " + nombre);
			e.printStackTrace();
			return false;
		}
	
		if(error==-1) return false;
		else return true;
	}
	
	
	/**
	 * Metodo auxiliar para crear instancias del ObjetoTienda a partir de un
	 * conjunto de objetosTienda recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de ObjetoTienda creadas
	 */
	private List<ObjetoTienda> resultSetToObjetoTienda(ResultSet resultados){
		List<ObjetoTienda> objetosTienda = new LinkedList<ObjetoTienda>();
		
		// Objeto
		int idobjeto; String nombreObj; int nivel; BigDecimal precio; int diasCreacion;
		int idtipoobjeto; int idarma; int idefecto;
		
		// Tienda
		int idtienda; String nombre; BigDecimal dineroTienda;
		
		// ObjetoTienda
		int unidades;
		
		try {
			while (resultados.next()) {
				// Tienda
				idtienda = resultados.getInt("idtienda");
				nombre = resultados.getString("nombreTienda");
				dineroTienda = resultados.getBigDecimal("dinero");
				
				// Objeto
				idobjeto = resultados.getInt("idobjeto");
				nombreObj = resultados.getString("nombre");
				nivel =  resultados.getInt("nivel");
				precio = resultados.getBigDecimal("precio");
				diasCreacion = resultados.getInt("diasCreacion");
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				idarma = resultados.getInt("idarma");
				idefecto = resultados.getInt("idefecto");
				
				// MaterialObjeto
				unidades = resultados.getInt("unidades");
				
				objetosTienda.add(new ObjetoTienda(new Tienda(idtienda, nombre, dineroTienda), 
						new Objeto(idobjeto, nombreObj, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
						unidades));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objetosTienda;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo ObjetoTienda
	 * a partir de datos de una unica ObjetoTienda seleccionado 
	 * recibida como parametro.
	 * @param resultados
	 * @return Un ObjetoTienda ya creado con sus atributos.
	 */
	private ObjetoTienda procesarObjetoTienda(ResultSet resultados) {
		ObjetoTienda resultado = null;
		try {
			// Tienda
			int idtienda = resultados.getInt("idtienda");
			String nombre = resultados.getString("nombreTienda");
			BigDecimal dineroTienda = resultados.getBigDecimal("dinero");
			
			// Objeto
			int idobjeto = resultados.getInt("idobjeto");
			String nombreObj = resultados.getString("nombre");
			int nivel =  resultados.getInt("nivel");
			BigDecimal precio = resultados.getBigDecimal("precio");
			int diasCreacion = resultados.getInt("diasCreacion");
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			int idarma = resultados.getInt("idarma");
			int idefecto = resultados.getInt("idefecto");
			
			// MaterialObjeto
			int unidades = resultados.getInt("unidades");
			
			resultado = new ObjetoTienda(new Tienda(idtienda, nombre, dineroTienda), 
					new Objeto(idobjeto, nombreObj, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
					unidades);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

}
