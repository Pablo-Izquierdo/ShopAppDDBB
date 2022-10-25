package tiendaForja.persistenceLayer;

import tiendaForja.domain.Compra;
import tiendaForja.domain.Objeto;
import tiendaForja.domain.vistas.LineaCompra;

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
 * relacionadas con la LineaCompra.
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 29/04/2020
 */
public class LineaCompraDataMapper {

	/**
	 * Metodo que llama al procedimiento almacenado anhadirObjetoACompra
	 * y anhade un nuevo objeto a la compra del usuario.
	 * @param usuario
	 * @param objeto
	 * @param unidades
	 * @return True si se ha podido anhadir, false en caso contrario.
	 */
	public boolean insertLineaCompra(String usuario, String objeto, int unidades, String tienda) {
		int error = 0;
		Connection con = SqlServerConnectionManager.getConnection();
		
		try {
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call dbo.anhadirObjetoACompra(?, ?, ?, ?, ?)}");
			cstmt.setString("usuario", usuario);
			cstmt.setString("objeto", objeto);
			cstmt.setInt("unidades", unidades);
			cstmt.setString("tienda", tienda);
			cstmt.registerOutParameter("error", java.sql.Types.INTEGER);
			cstmt.execute();
			error = cstmt.getInt("error");
		} catch (SQLException e) {
			System.out.println("Error al insertar el objeto para el usuario: '" +
					usuario + "'");
			e.printStackTrace();
			return false;
		}
		
		if(error==-1) return false;
		else return true;
	}
	
	/**
	 * Metodo que permite seleccionar la compra del objeto mediante sus ids.
	 * @param idobjeto
	 * @param idcompra
	 * @return la fila de la tabla lineaCompra.
	 */
	public LineaCompra selectLineaCompraById(int idcompra, int idobjeto) {
		LineaCompra resultado = null;
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT lc.precioUnitario, lc.unidades AS unidadesCompradas, " +
					"c.*, o.* FROM compra c " +
					"INNER JOIN lineaCompra lc ON lc.idcompra = c.idcompra " +
					"INNER JOIN objeto o ON o.idobjeto = lc.idobjeto " + 
					"WHERE lc.idobjeto = " + idobjeto + " AND lc.idcompra = " + idcompra;
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			if (resultados.next()) {
				resultado = this.procesaLineaCompra(resultados);
			}
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener la compra con id: " +
					idcompra + " para el objeto con id: " + idobjeto + "");
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Metodo que permite seleccionar todas las compras de un objeto.
	 * @param nombre
	 * @param idcompra
	 * @param idobjeto
	 * @return la lista de lineasCompras de un unico objeto.
	 */
	public List<LineaCompra> selectAllComprasFromObjeto(String nombre) {
		List<LineaCompra> comprasDelObjeto = new LinkedList<LineaCompra>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT lc.precioUnitario, lc.unidades AS unidadesCompradas, " +
					"c.*, o.* FROM compra c " +
					"INNER JOIN lineaCompra lc ON lc.idcompra = c.idcompra " +
					"INNER JOIN objeto o ON o.idobjeto = lc.idobjeto " +
					"WHERE o.nombre = '" + nombre + "'";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			comprasDelObjeto = resultSetToLineaCompra(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener las compras del objeto");
			e.printStackTrace();
		}
		return comprasDelObjeto;
	}
	
	/**
	 * Metodo que permite seleccionar todas las filas de lineaCompra.
	 * @param idcompra
	 * @param idobjeto
	 * @return la lista de LineaCompra de un unico objeto.
	 */
	public List<LineaCompra> selectAllLineaCompra() {
		List<LineaCompra> lineasCompra = new LinkedList<LineaCompra>();
		Connection con = SqlServerConnectionManager.getConnection();
		try {
			Statement selectStm = con.createStatement();
			String selectTxt = "SELECT lc.precioUnitario, lc.unidades AS unidadesCompradas, " +
					"c.*, o.* FROM compra c " +
					"INNER JOIN lineaCompra lc ON lc.idcompra = c.idcompra " +
					"INNER JOIN objeto o ON o.idobjeto = lc.idobjeto ";
			ResultSet resultados = selectStm.executeQuery(selectTxt);
			lineasCompra = resultSetToLineaCompra(resultados);
			selectStm.close();
		} catch (SQLException e) {
			System.out.println("Excepcion al obtener las compras del objeto");
			e.printStackTrace();
		}
		return lineasCompra;
	}
	
	/**
	 * Metodo que permite actualizar el precio unitario de la fila de
	 * la tabla lineacompra
	 * @param nuevoPrecio
	 * @param idobjeto
	 * @return true si se ha podido actualizar, false en otro caso.
	 */
	public boolean updatePrecioUnitario(BigDecimal nuevoPrecio, int idobjeto) {
		String updateTxt = "UPDATE lineaCompra SET precioUnitario = " + nuevoPrecio + 
				" WHERE idobjeto = " + idobjeto;
		return SqlServerConnectionManager.executeSqlStatement(updateTxt, "Excepcion al actualizar el precioUnitario");
	}
		
	/**
	 * Metodo auxiliar para crear instancias de la LineaCompra a partir de un
	 * conjunto de LineasCompra recibidas como un conjunto de parametros.
	 * @param resultados
	 * @return la lista de LineaCompra creadas
	 */
	private List<LineaCompra> resultSetToLineaCompra(ResultSet resultados){
		List<LineaCompra> lineasCompras = new LinkedList<LineaCompra>();
	
		// Objeto
		int idobjeto; String nombre; int nivel; BigDecimal precio; int diasCreacion;
		int idtipoobjeto; int idarma; int idefecto;
		
		// Compra
		int idcompra; int idusuario; int idtienda; Date fechaCompra; BigDecimal precioTotal;
		
		// LineaCompra
		BigDecimal precioUnitario; int unidadesCompradas;
		
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
				
				// Compra
				idcompra  = resultados.getInt("idcompra");
				idusuario = resultados.getInt("idusuario");
				idtienda = resultados.getInt("idtienda");
				fechaCompra = resultados.getDate("fechaCompra");
				precioTotal = resultados.getBigDecimal("precioTotal");
				
				// LineaCompra
				precioUnitario = resultados.getBigDecimal("precioUnitario");
				unidadesCompradas = resultados.getInt("unidadesCompradas");
				
				lineasCompras.add(new LineaCompra(new Compra(idcompra, idusuario, idtienda, fechaCompra, precioTotal), 
						new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
						precioUnitario, unidadesCompradas));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lineasCompras;
	}
	
	/**
	 * Metodo auxiliar que nos permite crear una nueva lineaCompra
	 * a partir de un conjunto de datos seleccionados recibido como parametro.
	 * @param resultados
	 * @return Una LineaCompra ya creada con sus atributos.
	 */
	private LineaCompra procesaLineaCompra(ResultSet resultados) {
		LineaCompra resultado = null;
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
			
			// Compra
			int idcompra  = resultados.getInt("idcompra");
			int idusuario = resultados.getInt("idusuario");
			int idtienda = resultados.getInt("idtienda");
			Date fechaCompra = resultados.getDate("fechaCompra");
			BigDecimal precioTotal = resultados.getBigDecimal("precioTotal");
			
			// LineaCompra
			BigDecimal precioUnitario = resultados.getBigDecimal("precioUnitario");
			int unidadesCompradas = resultados.getInt("unidadesCompradas");
			
			resultado = new LineaCompra(new Compra(idcompra, idusuario, idtienda, fechaCompra, precioTotal), 
					new Objeto(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto), 
					precioUnitario, unidadesCompradas);
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
