package tiendaForja.persistenceLayer;

import tiendaForja.domain.Encargo;

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
 * relacionadas con el encargo.
 * @author Carlos Jimeno, Sara Grela
 * @lastmodified 29/04/2020
 */
public class EncargoDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado nuevoEncargo
	 * y genera un nuevo encargo para la base de datos.
	 * @param nombreSocio
	 * @param idforja
	 * @return true si se ha insertado con exito, false en otro caso.
	 */
	public boolean insertEncargo(String nombreSocio, int idforja) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.nuevoEncargo(?, ?, ?)}");
			cstmt.setString("nombresocio", nombreSocio);
			cstmt.setInt("idforja", idforja);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el encargo para el socio con"
					+ " nombre de usuario: " + nombreSocio);
			e.printStackTrace();
			return false;
		}
		
		if (error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que selecciona un encargo de la base de datos mediante
	 * el id recibido como parametro y crea una nueva instancia de ella.
	 * @param idencargo
	 * @return la instancia de la clase Encargo rellenada.
	 */
	public Encargo selectEncargoById(int idencargo) {
		Encargo resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT * FROM encargo WHERE idencargo = " + idencargo + "";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesarEncargo(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener un encargo con id: " + idencargo + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que selecciona todas los encargos que realiza un usuario mediante
	 * el id de usuario recibido como parametro. 
	 * @param idsocio
	 * @return Una lista con los encargos del socio
	 */
	public List<Encargo> selectEncargosBySocio(int idsocio) {
		List<Encargo> encargosSocio = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM encargo WHERE idsocio = " + idsocio + "";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			encargosSocio = resultSetToEncargo(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los encargos del socio con id: " + idsocio);
			e.printStackTrace();
		}
		return encargosSocio;
	}
	
	/**
     * Metodo que retorna una lista con todos los encargos de una forja.
     * @param idForja
     * @return una lista de las instancias de la clase Encargo.
     */
    public List<Encargo> selectAllEncargosByIdForja(int idForja) {
        List<Encargo> encargos = null;
        Connection con = SqlServerConnectionManager.getConnection();
        try {
            Statement selectStm = con.createStatement();
            String SelectTxt = "SELECT * FROM encargo WHERE idforja = "+ idForja;
            ResultSet resultados = selectStm.executeQuery(SelectTxt);
            encargos = resultSetToEncargo(resultados);
            selectStm.close();
        } catch (SQLException e) {
            System.out.println("Excepcion al obtener todas las compras");
            e.printStackTrace();
        }
        return encargos;
    }
	
	/**
	 * Metodo que retorna una lista con todos los encargos de la base de datos.
	 * @return una lista de las instancias de la clase Encargo.
	 */
	public List<Encargo> selectAllEncargos() {
		List<Encargo> encargos = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String SelectTxt = "SELECT * FROM encargo";
			ResultSet resultados = selectStm.executeQuery(SelectTxt);
			encargos = resultSetToEncargo(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener todas las compras");
			e.printStackTrace();
		}
		return encargos;
	}
	
	/**
	 * Como se ejecuta el trigger evitando el borrado del encargo
	 * pasemos un delete que haga que salte el trigger.
	 * @param idencargo
	 * @return true si se ha ejecutado el trigger, false en otro caso
	 */
	public boolean deleteEncargo(int idencargo) {
		String deleteTxt = "DELETE FROM encargo WHERE idencargo = " + idencargo + "";
		return !SqlServerConnectionManager.executeSqlStatement(deleteTxt, "Excepcion al " +
				"borrar el encargo con id: " + idencargo);
	}
	
	/**
	 * Metodo que permite actualizar el estado del encargo.
	 * @param idEncargo
	 * @param estado
	 * @return true si se ha podido actualizar, false si el estado es distinto
	 * a "en preparación", "terminado" o "entregado" o no se ha podido actualizar.
	 */
	public boolean updateEstadoEncargo(int idEncargo, String estado) {
		if(estado != "en preparación" && estado != "terminado" && estado != "entregado") return false;
		String updateTxt = "UPDATE encargo SET estado = '" + estado + "' WHERE idencargo = " +
				idEncargo + "";
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al intentar actualizar el estado del encargo");
	}
	
	/**
     * Método que implementa la función encargoRecogido
     * @param nombreSocio
     * @param idForja
     * @param fechaEncargo
     * @return true si se ha ejecutado la función o false en caso contrario
     */
    public boolean encargoRecogido(String nombreSocio, int idForja, Date fechaEncargo) {

        int error = 0;
        Connection con = SqlServerConnectionManager.getConnection();

        try {
            CallableStatement cstmt = null;
            cstmt = con.prepareCall("{call dbo.encargoRecogido(?, ?, ?)}");
            cstmt.setString("nombresocio", nombreSocio);
            cstmt.setInt("idforja", idForja);
            cstmt.setDate("fechaEncargo", fechaEncargo);
            cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
            cstmt.execute();
            error = cstmt.getInt("error");
        } catch (SQLException e) {
            System.out.println("Error al recoger el encargo.");
            e.printStackTrace();
            return false;
        }

        if (error==-1) return false;
        else return true;
    }
	
	/**
	 * Metodo auxiliar para crear instancias del encargo a partir de un
	 * conjunto de encargo recibidads como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de encargos creadas
	 */
	private List<Encargo> resultSetToEncargo(ResultSet resultados){
		List<Encargo> encargos = new LinkedList<Encargo>();
		
		int idencargo; int idsocio; int idforja; Date fechaEntrega; Date fechaEncargo;
		String estado;
			
		try {
			while(resultados.next()) {
				idencargo = resultados.getInt("idencargo");
				idsocio = resultados.getInt("idsocio");
				idforja = resultados.getInt("idforja");
				fechaEntrega = resultados.getDate("fechaEntrega");
				fechaEncargo = resultados.getDate("fechaEncargo");
				estado = resultados.getString("estado");
				encargos.add(new Encargo(idencargo, idsocio, idforja, fechaEntrega, fechaEncargo, estado, null));
				// Guardamos nulo como precio total para sustituirlo en la capa de negocio
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return encargos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear un nuevo encargo a partir
	 * de datos de un unico arma seleccionada recibida como parametro.
	 * @param resultados
	 * @return Un encargo ya creado con sus atributos.
	 */
	private Encargo procesarEncargo(ResultSet resultados) {
		Encargo resultado = null;
		try {
			int idencargo = resultados.getInt("idencargo");
			int idsocio = resultados.getInt("idsocio");
			int idforja = resultados.getInt("idforja");
			Date fechaEntrega = resultados.getDate("fechaEntrega");
			Date fechaEncargo = resultados.getDate("fechaEncargo");
			String estado = resultados.getString("estado");
			resultado = new Encargo(idencargo, idsocio, idforja, fechaEntrega, fechaEncargo, estado, null);
			// Guardamos nulo como precio total para sustituirlo en la capa de negocio
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
