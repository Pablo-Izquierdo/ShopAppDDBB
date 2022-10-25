package tiendaForja.persistenceLayer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.Usuario;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el usuario.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class UsuarioDataMapper {
	
	/**
	 * Metodo para insertar un usuario a la base de datos.
	 * @param u
	 * @return true si se ha podido insertar con exito, false en otro caso.
	 */
	public boolean insertUsuario(Usuario u) {
		String insertTxt = "INSERT INTO usuario(nombreUsuario, dinero, fechaRegistro) VALUES ('" +
				u.getNombreUsuario() + "', " + u.getDinero() + ", '" + u.getFechaRegistro() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertTxt, "Excepcion al anhadir el usuario: " + 
				u.getNombreUsuario() + " a la base de datos");
	}
	
	/**
	 * Metodo que selecciona un usuario de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idusuario
	 * @return la instancia de la clase usuario rellenada
	 */
	public Usuario selectUsuarioById(int idusuario) {
		Usuario resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM usuario WHERE idusuario = " + idusuario + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarUsuario(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener un usuario con id: " + idusuario + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona un usuario de la base de datos mediante
	 * el nombre recibido como parametro y crea una nueva instancia de ella.
	 * @param nombre
	 * @return la instancia de la clase Usuario rellenada
	 */
	public Usuario selectUsuarioByNombre(String nombre) {
		Usuario resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM usuario WHERE nombreUsuario = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarUsuario(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener un usuario: " + nombre + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todos los usuarios de la base de datos.
	 * @return una lista de las instancias de la clase usuario
	 */
	public List<Usuario> selectAllSocios() {
		List<Usuario> usuarios = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM usuario";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			usuarios = resultSetToUsuario(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener todas los usuarios");
			e.printStackTrace();
		}
		return usuarios;
	}
	
	/**
	 * Metodo que permite el borrado de un usuario de la base de datos.
	 * @param idusuario
	 * @return True si se ha podido borrar, false en otro caso.
	 */
	public boolean deleteUsuario(int idusuario) {
		String deleteTxt1 = "DELETE FROM compra WHERE idusuario = " + idusuario;
		String deleteTxt2 = " DELETE FROM socio WHERE idusuario = " + idusuario;
		String deleteTxt3 = " DELETE FROM usuario WHERE idusuario = " + idusuario;
		String exceptionMsg = "No se ha podido borrar el usuario con id: " + idusuario + 
				" de la base de datos";
		return (SqlServerConnectionManager.executeSqlStatement(deleteTxt1, exceptionMsg) && 
				SqlServerConnectionManager.executeSqlStatement(deleteTxt2, exceptionMsg) &&
				SqlServerConnectionManager.executeSqlStatement(deleteTxt3, exceptionMsg));
	}
	
	/**
	 * Metodo que permite actualizar el dinero del usuario pasando su id como parametro.
	 * @param idusuario
	 * @param dinero
	 * @return True si se ha actualizado con exito, false en otro caso
	 */
	public boolean updateDineroUsuarioById(int idusuario, BigDecimal dinero) {
		String updateTxt = "UPDATE usuario SET dinero = " + dinero + " WHERE idusuario = " + idusuario;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar el dinero del usuario");
	}
	
	/**
	 * Metodo que permite actualizar el dinero del usuario pasando su nombre como parametro.
	 * @param nombreUsuario
	 * @param dinero
	 * @return True si se ha actualizado con exito, false en otro caso
	 */
	public boolean updateDineroUsuarioByNombre(String nombreUsuario, BigDecimal dinero) {
		String updateTxt = "UPDATE usuario SET dinero = " + dinero + " WHERE nombreUsuario = " + nombreUsuario;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar el dinero del usuario");
	}
	
	/**
	 * Metodo auxiliar para crear instancias del usuario a partir de un
	 * conjunto de usuarios recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de usuarios creados
	 */
	private List<Usuario> resultSetToUsuario(ResultSet resultados){
		List<Usuario> usuarios = new LinkedList<Usuario>();
		
		int idusuario; String nombreUsuario; BigDecimal dinero;
		Date fechaRegistro;
		
		try {
			while (resultados.next()) {
				idusuario = resultados.getInt("idusuario");
				nombreUsuario = resultados.getString("nombreUsuario");
				dinero = resultados.getBigDecimal("dinero");
				fechaRegistro = resultados.getDate("fechaRegistro");
				usuarios.add(new Usuario(idusuario, nombreUsuario, dinero, fechaRegistro));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo socio a partir
	 * de datos de un unico socio seleccionado recibido como parametro.
	 * @param resultados
	 * @return Un Usuario ya creado con sus atributos.
	 */
	private Usuario procesarUsuario(ResultSet resultados) {
		Usuario resultado = null;
		try {
			int idusuario = resultados.getInt("idusuario");
			String nombreUsuario = resultados.getString("nombreUsuario");
			BigDecimal dinero = resultados.getBigDecimal("dinero");
			Date fechaRegistro = resultados.getDate("fechaRegistro");
			resultado = new Usuario(idusuario, nombreUsuario, dinero, fechaRegistro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
