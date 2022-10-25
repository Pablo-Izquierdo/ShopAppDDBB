package tiendaForja.persistenceLayer;

import tiendaForja.domain.Socio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con el socio.
 * @author Carlos Jimeno
 * @lastmodified 29/04/2020
 */
public class SocioDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado nuevoSocio
	 * e inserta un nuevo socio a la base de datos.
	 * @param usuario
	 * @return true si se ha podido anhadir, false en otro caso.
	 */
	public boolean insertSocio(int idusuario) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.nuevoSocio(?, ?)}");
			cstmt.setInt("idusuario", idusuario);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el socio");
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que selecciona un socio de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idsocio
	 * @return la instancia de la clase Socio rellenada
	 */
	public Socio selectSocioById(int idusuario) {
		Socio resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM socio WHERE idusuario = " + idusuario + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarSocio(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener un socio con id: " + idusuario + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que retorna una lista con todos los socios de la base de datos.
	 * @return una lista de las instancias de la clase socio
	 */
	public List<Socio> selectAllSocios() {
		List<Socio> socios = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM socio";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			socios = resultSetToSocio(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener todos los socios");
			e.printStackTrace();
		}
		return socios;
	}
	
	/**
	 * Metodo que llama al procedimiento almacenado elimiarSocio
	 * y elimina al socio de la base de datos.
	 * @param socio
	 * @return true si se ha eliminado con exito, false en otro caso.
	 */
	public boolean deleteSocio(int socio) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.eliminarSocio(?, ?)}");
			cstmt.setInt("idusuario", socio);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al eliminar el socio");
			e.printStackTrace();
			return false;
		}
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo auxiliar para crear instancias del socio a partir de un
	 * conjunto de socios recibidos como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de socios creados
	 */
	private List<Socio> resultSetToSocio(ResultSet resultados){
		List<Socio> socios = new LinkedList<Socio>();
		
		int idsocio; int idusuario; Date fechaSocio;
		
		try {
			while (resultados.next()) {
				idsocio = resultados.getInt("idsocio");
				idusuario = resultados.getInt("idusuario");
				fechaSocio = resultados.getDate("fechaSocio");
				socios.add(new Socio(idsocio, idusuario, fechaSocio));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return socios;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo socio a partir
	 * de datos de un unico socio seleccionado recibido como parametro.
	 * @param resultados
	 * @return Un tipoObjeto ya creado con sus atributos.
	 */
	private Socio procesarSocio(ResultSet resultados) {
		Socio resultado = null;
		try {
			int idsocio = resultados.getInt("idsocio");
			int idusuario = resultados.getInt("idusuario");
			Date fechaSocio = resultados.getDate("fechaSocio");
			resultado = new Socio(idsocio, idusuario, fechaSocio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
