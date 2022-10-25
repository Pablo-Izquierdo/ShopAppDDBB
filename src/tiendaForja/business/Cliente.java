package tiendaForja.business;

import tiendaForja.persistenceLayer.CompraDataMapper;
import tiendaForja.persistenceLayer.EncargoDataMapper;
import tiendaForja.persistenceLayer.LineaCompraDataMapper;
import tiendaForja.persistenceLayer.LineaEncargoDataMapper;
import tiendaForja.persistenceLayer.ObjetoTiendaDataMapper;
//Importamos el modelo de dominio del socio y el data mapper
import tiendaForja.persistenceLayer.SocioDataMapper;
import tiendaForja.persistenceLayer.UsuarioDataMapper;
import tiendaForja.domain.Usuario;
import tiendaForja.domain.vistas.ObjetoTienda;

import java.sql.Date;
import java.util.List;


/**
 * Clase con las operaciones de negocio del cliente.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Sara Grela
 * @lastmodified 01/05/2020
 *
 */
public class Cliente {
	
	/**
	 * Método que permite visualizar el catálogo completo.
	 * @return objetos del catálogo
	 */
	public List<ObjetoTienda> verCatalogoTienda(String nombreTienda) {
		
		//Devuelvo los objetos de la tienda haciendo uso del DataMapper
		return new ObjetoTiendaDataMapper().selectAllObjetosFromTienda(nombreTienda);
			
	}
	 
	/**
	 * Método que inserta una nueva compra en la Base de Datos.
	 * @param nombreUsuario
	 * @param nombreTienda
	 * @param objetos
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean nuevaCompra(String nombreUsuario, String nombreObjeto, int unidades, String nombreTienda) {
		
		//Inserto la compra haciendo uso del DataMapper
		return new CompraDataMapper().insertCompra(nombreUsuario, nombreObjeto, unidades, nombreTienda) ;
		
	}
	
	/**
	 * Método que inserta una nueva línea en la compra.
	 * @param nombreUsuario
	 * @param nombreTienda
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean nuevaLineaCompra(String nombreUsuario, String nombreObjeto, int unidades, String nombreTienda) {
		
		//Inserto la línea de compra haciendo uso del DataMapper
		return new LineaCompraDataMapper().insertLineaCompra(nombreUsuario, nombreObjeto, unidades, nombreTienda) ;
		
	}
	
	
	/**
	 * Método que inserta a un nuevo socio en la base de datos 
	 * @param nombreUsuario 
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean hacermeSocio(String nombreUsuario) {
		
		Usuario u = new UsuarioDataMapper().selectUsuarioByNombre(nombreUsuario);
		
		//Inserto al socio haciendo uso del DataMapper
		return new SocioDataMapper().insertSocio(u.getIdusuario());
		
	}
	
	/**
	 * Elimina de la base de datos al socio cuyo nombre de usuario se pasa como parámetro
	 * @param nombreUsuario
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean darmeDeBaja(String nombreUsuario) {
		
		Usuario u = new UsuarioDataMapper().selectUsuarioByNombre(nombreUsuario);
		
		//Elimino al socio haciendo uso del DataMapper
		return new SocioDataMapper().deleteSocio(u.getIdusuario());
	}
	
	/**
	 * Método que inserta un nuevo encargo en la base de datos 
	 * @param idSocio
	 * @param idForja
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean nuevoEncargo(String nombreUsuario, int idForja) {
		
		//Inserto el encargo haciendo uso del DataMapper
		return new EncargoDataMapper().insertEncargo(nombreUsuario, idForja);
		
	}
	
	/**
	 * Método que inserta una nueva línea en el encargo.
	 * @param nombreUsuario
	 * @param nombreForja
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean nuevaLineaEncargo(String nombreUsuario, int idForja, String nombreObjeto, int unidades) {
		
		//Inserto la línea de encargo haciendo uso del DataMapper
		return new LineaEncargoDataMapper().insertLineaEncargo(nombreUsuario, idForja, nombreObjeto, unidades) ;
		
	}
	
	/**
	 * Método que recoge el encargo seleccionado.
	 * @param nombreUsuario
	 * @param idForja
	 * @param fechaEncargo
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean recogerEncargo(String nombreUsuario, int idForja, Date fechaEncargo) {
		
		//Recojo la línea de encargo haciendo uso del DataMapper
		return new EncargoDataMapper().encargoRecogido(nombreUsuario, idForja, fechaEncargo);
	}

}
