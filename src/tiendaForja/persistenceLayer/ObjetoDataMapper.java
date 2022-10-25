package tiendaForja.persistenceLayer;

import tiendaForja.domain.Objeto;
import tiendaForja.domain.vistas.ObjetoCompleto;
import tiendaForja.domain.Material;

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
 * relacionadas con el objeto.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class ObjetoDataMapper {

	/**
	 * Metodo que llama al procedimiento almacenado insertaObjeto
	 * y anhade un nuevo objeto a la base de datos.
	 * @return true si se ha podido anhadir, false en caso contrario.
	 */
	public boolean insertObjeto(String nombre, int nivel, BigDecimal precio, 
			int diasCreacion, int idtipoobjeto, int idarma, int idefecto) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.creaObjeto(?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setString("nombre", nombre);
			cstmt.setInt("nivel", nivel);
			cstmt.setBigDecimal("precio", precio);
			cstmt.setInt("dias", diasCreacion);
			cstmt.setInt("idtipoobjeto", idtipoobjeto);
			cstmt.setInt("idarma", idarma);
			cstmt.setInt("idefecto", idefecto);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el objeto");
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que selecciona un Objeto de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idobjeto
	 * @return la instancia de la clase objeto rellenada.
	 */
	public Objeto selectObjetoById(int idobjeto) {
		Objeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM objeto WHERE idobjeto = " + idobjeto + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener objeto con id: " + idobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona un Objeto de la base de datos mediante
	 * el nombre recibido como parametro y crea una nueva instancia de ella.
	 * @param idobjeto
	 * @return la instancia de la clase objeto rellenada.
	 */
	public Objeto selectObjetoByNombre(String nombre) {
		Objeto resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM objeto WHERE nombre = " + nombre + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarObjeto(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el objeto: " + nombre + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todas los Objetos de la base de datos.
	 * @return lista de instancias de la clase objeto.
	 */
	public List<ObjetoCompleto> selectAllObjetos() {	 
		List<ObjetoCompleto> objetos = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM muestra_catalogo";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			objetos = resultSetToObjeto(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el listado de objetos");
			e.printStackTrace();
		}
		return objetos;
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado borraObjeto y
	 * borra el objeto de la base de datos.
	 * @param nombre
	 * @return true si se ha podido borrar, false en otro caso.
	 */
	public boolean deleteObjeto(String nombre) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.borraObjeto(?)}");
			cstmt.setString("nombre", nombre);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al borrar el objeto: " + nombre);
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		return true;
	}
	
	/**
	 * Metodo que llama al procedmiento almacenado actualziaPrecioObjeto
	 * y cambia el precio de un objeto.
	 * @param nombre
	 * @param precio
	 * @return true si se ha podido actualizar, false en otro caso.
	 */
	public boolean updatePrecioObjeto(String nombre, BigDecimal precio) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.actualizaPrecioObjeto(?, ?)}");
			cstmt.setString("nombre", nombre);
			cstmt.setBigDecimal("precio", precio);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al actualizar el precio del objeto: " + nombre);
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	

	
	/**
	 * Metodo auxiliar para crear instancias del objeto (con sus materiales y tipo de arma
	 * o efecto dependiendo de si es un arma, armadura o una pocion) a partir de un
	 * conjunto de objetos recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de objetos creados
	 */
	private List<ObjetoCompleto> resultSetToObjeto(ResultSet resultados){
		List<ObjetoCompleto> objetos = new LinkedList<ObjetoCompleto>();
		
		int idobjeto; String nombre; int nivel; BigDecimal precio;
		int diasCreacion; int idtipoobjeto; int idarma; int idefecto; String nombreArma;
		String nombreEfecto; List<Material> materiales = new LinkedList<Material>();
		
		try {
			while (resultados.next()) {
				idobjeto = resultados.getInt("idobjeto");
				nombre = resultados.getString("nombre");
				nivel = resultados.getInt("nivel");
				precio = resultados.getBigDecimal("precio");
				diasCreacion = resultados.getInt("diasCreacion");
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				idarma = resultados.getInt("idarma");
				idefecto = resultados.getInt("idefecto");
				nombreArma = resultados.getString("tipoArma");
				nombreEfecto = resultados.getString("efecto");
				materiales.add(new Material(resultados.getString("material")));
				objetos.add(new ObjetoCompleto(idobjeto, nombre, nivel, precio, diasCreacion, 
						idtipoobjeto, idarma, idefecto, nombreArma, nombreEfecto, materiales));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objetos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo objeto a partir
	 * de datos de un unico objeto seleccionado recibido como parametro.
	 * @param resultados
	 * @return Un Objeto ya creado con sus atributos.
	 */
	private Objeto procesarObjeto(ResultSet resultados) {
		Objeto resultado = null;
		try {
			int idobjeto = resultados.getInt("idobjeto");
			String nombre = resultados.getString("nombre");
			int nivel = resultados.getInt("nivel");
			BigDecimal precio = resultados.getBigDecimal("precio");
			int diasCreacion = resultados.getInt("diasCreacion");
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			int idarma = resultados.getInt("idarma");
			int idefecto = resultados.getInt("idefecto");
			resultado = new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
