package tiendaForja.persistenceLayer;

import tiendaForja.domain.Encargo;
import tiendaForja.domain.Objeto;
import tiendaForja.domain.vistas.LineaEncargo;

import java.math.BigDecimal;
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
 * relacionadas con la LineaEncargo
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class LineaEncargoDataMapper {
	
	/**
	 * Metodo que llama al procedimiento almacenado anhadirObjetoEncargo
	 * y anhade un nuevo objeto al encargo del usuario.
	 * @param socio
	 * @param idforja
	 * @param objeto
	 * @param unidades
	 * @return true si se ha anhadido con exito, false en otro caso.
	 */
	public boolean insertLineaEncargo(String socio, int idforja, String objeto, int unidades) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.anhadirObjetoEncargo(?, ?, ?, ?, ?)}");
			cstmt.setString("nombresocio", socio);
			cstmt.setString("nombreobjeto", objeto);
			cstmt.setInt("idforja", idforja);
			cstmt.setInt("unidades", unidades);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el objeto para el socio: '" +
					socio + "'");
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que permite seleccionar el encargo del objeto mediante sus ids.
	 * @param idobjeto
	 * @param idencargo
	 * @return la fila de la tabla lineaEncargo.
	 */
	public LineaEncargo selectLineaEncargoById(int idencargo, int idobjeto) {
		LineaEncargo resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT le.precioUnitario, le.unidades AS unidadesCompradas, " +
					"e.*, o.* FROM encargo e " +
					"INNER JOIN lineaEncargo le ON le.idencargo = e.idencargo " +
					"INNER JOIN objeto o ON o.idobjeto = le.idobjeto " + 
					"WHERE lc.idobjeto = " + idobjeto + " AND le.idencargo = " + idencargo;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesaLineaEncargo(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener el encargo con id: " +
					idencargo + " para el objeto con id: " + idobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que permite seleccionar todas los encargos de un objeto.
	 * @param nombre
	 * @param idencargo
	 * @param idobjeto
	 * @return la lista de lineasEncargos de un unico objeto.
	 */
	public List<LineaEncargo> selectAllEncargosFromObjeto(String nombre) {
		List<LineaEncargo> encargosDelObjeto = new LinkedList<LineaEncargo>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT le.precioUnitario, le.unidades AS unidadesCompradas, " +
					"e.*, o.* FROM encargo e " +
					"INNER JOIN lineaEncargo le ON le.idencargo = e.idcompra " +
					"INNER JOIN objeto o ON o.idobjeto = lc.idobjeto " +
					"WHERE o.nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			encargosDelObjeto = resultSetToLineaEncargo(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los encargos del objeto");
			e.printStackTrace();
		}
		return encargosDelObjeto;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de lineaEncargo.
	 * @param idencargo
	 * @param idobjeto
	 * @return la lista de lineaEncargo de un unico objeto.
	 */
	public List<LineaEncargo> selectAllLineaEncargo() {
		List<LineaEncargo> lineasEncargo = new LinkedList<LineaEncargo>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT le.precioUnitario, le.unidades AS unidadesCompradas, " +
					"e.*, o.* FROM encargo e " +
					"INNER JOIN lineaEncargo le ON le.idencargo = e.idencargo " +
					"INNER JOIN objeto o ON o.idobjeto = le.idobjeto ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			lineasEncargo = resultSetToLineaEncargo(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener los encargos del objeto");
			e.printStackTrace();
		}
		return lineasEncargo;
	}
	
	/**
	 * Metodo que permite actualizar el precio unitario de la fila de
	 * la tabla lineaencargo
	 * @param nuevoPrecio
	 * @param idobjeto
	 * @return true si se ha podido actualizar, false en otro caso.
	 */
	public boolean updatePrecioUnitario(BigDecimal nuevoPrecio, int idobjeto) {
		String updateTxt = "UPDATE lineaEncargo SET precioUnitario = " + nuevoPrecio + 
				" WHERE idobjeto = " + idobjeto;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar el precioUnitario");
	}
	
	/**
	 * Metodo auxiliar para crear instancias de la LineaEncargo a partir de un
	 * conjunto de LineasEncargo recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de LineaEncargo creadas
	 */
	private List<LineaEncargo> resultSetToLineaEncargo(ResultSet resultados){
		List<LineaEncargo> lineasEncargos = new LinkedList<LineaEncargo>();
	
		// Objeto
		int idobjeto; String nombre; int nivel; BigDecimal precio; int diasCreacion;
		int idtipoobjeto; int idarma; int idefecto;
		
		// Encargo
		int idencargo; int idsocio; int idforja; Date fechaEntrega; Date fechaEncargo; String estado;
		BigDecimal precioTotal;
		
		// LineaEncargo
		BigDecimal precioUnitario; int unidadesEncargadas;
		
		try {
			while (resultados.next()) {
				// Objeto
				idobjeto = resultados.getInt("idobjeto");
				nombre = resultados.getString("nombre");
				nivel = resultados.getInt("nivel");
				precio = resultados.getBigDecimal("precio");
				diasCreacion = resultados.getInt("diasCreacion");
				idtipoobjeto = resultados.getInt("idtipoobjeto");
				idarma = resultados.getInt("idarma");
				idefecto = resultados.getInt("idefecto");
				
				// Encargo
				idencargo = resultados.getInt("idencargo");
				idsocio = resultados.getInt("idsocio");
				idforja = resultados.getInt("idforja");
				fechaEntrega = resultados.getDate("fechaEntrega");
				fechaEncargo = resultados.getDate("fechaEncargo");
				estado = resultados.getString("estado");
				precioTotal = resultados.getBigDecimal("precioTotal");
				
				// LineaCompra
				precioUnitario = resultados.getBigDecimal("precioUnitario");
				unidadesEncargadas = resultados.getInt("unidadesEncargadas");
				
				lineasEncargos.add(new LineaEncargo(new Encargo(idencargo, idsocio, idforja, fechaEntrega, fechaEncargo, estado, precioTotal), 
						new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto),
						precioUnitario, unidadesEncargadas));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lineasEncargos;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva lineaEncargo
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una LineaEncargo ya creada con sus atributos.
	 */
	private LineaEncargo procesaLineaEncargo(ResultSet resultados) {
		LineaEncargo resultado = null;
		try {
			// Objeto
			int idobjeto = resultados.getInt("idobjeto");
			String nombre = resultados.getString("nombre");
			int nivel = resultados.getInt("nivel");
			BigDecimal precio = resultados.getBigDecimal("precio");
			int diasCreacion = resultados.getInt("diasCreacion");
			int idtipoobjeto = resultados.getInt("idtipoobjeto");
			int idarma = resultados.getInt("idarma");
			int idefecto = resultados.getInt("idefecto");
			
			// Encargo
			int idencargo = resultados.getInt("idencargo");
			int idsocio = resultados.getInt("idsocio");
			int idforja = resultados.getInt("idforja");
			Date fechaEntrega = resultados.getDate("fechaEntrega");
			Date fechaEncargo = resultados.getDate("fechaEncargo");
			String estado = resultados.getString("estado");
			BigDecimal precioTotal = resultados.getBigDecimal("precioTotal");
			
			// LineaEncargo
			BigDecimal precioUnitario = resultados.getBigDecimal("precioUnitario");
			int unidadesEncargadas = resultados.getInt("unidadesEncargadas");
			
			resultado = new LineaEncargo(new Encargo(idencargo, idsocio, idforja, fechaEntrega, fechaEncargo, estado, precioTotal), 
					new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
					precioUnitario, unidadesEncargadas);
			
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
