package tiendaForja.persistenceLayer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.Compra;


/**
 * Clase para realizar la seleccion y manipulacion de datos
 * relacionadas con la compra.
 * @author Carlos Jimeno
 * @lastmodified 29/04/2020
 */
public class CompraDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado nuevaCompra
	 * y anhade una nueva compra a la base de datos.
	 * @param usuario
	 * @param objeto
	 * @param unidades
	 * @return True si se ha podido anhadir, false en caso contrario.
	 */
	public boolean insertCompra(String objeto, String usuario, int unidades, String tienda) {
		
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.nuevaCompra(?, ?, ?, ?, ?)}");
			cstmt.setString("usuario", usuario);
			cstmt.setString("objeto", objeto);
			cstmt.setInt("unidades", unidades);
			cstmt.setString("tienda", tienda);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar la compra para el usuario: '" +
					usuario + "'");
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que selecciona una compra de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idcompra
	 * @return la instancia de la clase Compra rellenada
	 */
	public Compra selectCompraById(int idcompra) {
		Compra resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM compra WHERE idcompra = " + idcompra + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarCompra(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener una compra con id: " + idcompra + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona todas las compras que realiza un usuario mediante
	 * el id de usuario recibido como parametro. 
	 * @param idusuario
	 * @return Una lista con las compras del usuario
	 */
	public List<Compra> selectComprasByUsuario(int idusuario) {
		List<Compra> comprasUsuario = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM compra WHERE idusuario = " + idusuario + "";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			comprasUsuario = resultSetToCompra(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener las compras del usuario con id: " + idusuario);
			e.printStackTrace();
		}
		return comprasUsuario;
	}
	
	/**
	 * Metodo que retorna una lista con todas las compras de la base de datos.
	 * @return una lista de las instancias de la clase Compra
	 */
	public List<Compra> selectAllCompras() {
		List<Compra> compras = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM compra";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			compras = resultSetToCompra(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener todas las compras");
			e.printStackTrace();
		}
		return compras;
	}
	
	/**
	 * Metodo que corresponde al borrado de una compra, como se ha implementado
	 * un trigger en vez de borrarla mostrara un mensaje indicando "No puedes borrar
	 * las compras".
	 * @param compra
	 * @return True si retorna el mensaje, false en caso contrario.
	 */
	public boolean deleteCompra(int compra) {
		String deleteTxt = "DELETE FROM compra WHERE idcompra = " + compra + "";
		return !SqlServerConnectionManager.executeSqlStatement(deleteTxt, "Excepcion al " +
				"borrar la compra con id: " + compra);
	}
	
	
	/**
	 * Metodo auxiliar para crear instancias de la compra a partir de un
	 * conjunto de compras recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de compras creadas
	 */
	private List<Compra> resultSetToCompra(ResultSet resultados){
		List<Compra> compras = new LinkedList<Compra>();
		
		int idcompra; int idusuario; Date fechaCompra; int idtienda;
		
		try {
			while(resultados.next()) {
				idcompra = resultados.getInt("idcompra");
				idusuario = resultados.getInt("idusuario");
				fechaCompra = resultados.getDate("fechaCompra");
				idtienda = resultados.getInt("idtienda");
				compras.add(new Compra(idcompra, idusuario, idtienda, fechaCompra, null));
				// Guardamos nulo como precio total para sustituirlo en la capa de negocio
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compras;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva compra
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una compra ya creada con sus atributos.
	 */
	private Compra procesarCompra(ResultSet resultados) {
		Compra resultado = null;
		try {
			int idcompra = resultados.getInt("idcompra");
			int idusuario = resultados.getInt("idusuario");
			Date fechaCompra = resultados.getDate("fechaCompra");
			int idtienda = resultados.getInt("idtienda");
			resultado = new Compra(idcompra, idusuario, idtienda, fechaCompra, null);
			// Guardamos nulo como precio total para sustituirlo en la capa de negocio
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
