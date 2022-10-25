package tiendaForja.business;

import java.util.ArrayList;
import java.util.List;

import tiendaForja.domain.Compra;
import tiendaForja.domain.Encargo;
import tiendaForja.domain.Socio;
import tiendaForja.domain.Usuario;
import tiendaForja.persistenceLayer.CompraDataMapper;
import tiendaForja.persistenceLayer.EncargoDataMapper;
import tiendaForja.persistenceLayer.ObjetoTiendaDataMapper;
import tiendaForja.persistenceLayer.SocioDataMapper;
import tiendaForja.persistenceLayer.UsuarioDataMapper;

/**
* Clase con las operaciones de negocio del administrador de la tienda (tendero).
* Es con esta clase con la que la interfaz gráfica (capa de presentación)
* se comunica.
* 
* @author Sara Grela
* @lastmodified 29/04/2020
*
*/
public class Administrador {
	
	/**
	 * Método que permite ver las compras que ha realizado un usuario según su nombre.
	 * @param nombreUsuario
	 * @return historial de las compras que ha realizado
	 */
	public List<Compra> verHistorialCompras(String nombreUsuario) {
		
		Usuario u = new UsuarioDataMapper().selectUsuarioByNombre(nombreUsuario);
		
		return new CompraDataMapper().selectComprasByUsuario(u.getIdusuario());
	
	}
	
	/**
	 * Método que permite ver los encargos que ha realizado un socio según su nombre
	 * de usuario.
	 * @param nombreUsuario
	 * @return historial de los encargos que ha realizado
	 */
	public List<Encargo> verHistorialEncargos(String nombreUsuario) {
		
		Usuario u = new UsuarioDataMapper().selectUsuarioByNombre(nombreUsuario);
		
		Socio s = new SocioDataMapper().selectSocioById(u.getIdusuario());
		
		return new EncargoDataMapper().selectEncargosBySocio(s.getIdsocio());
		
	}	
	
	/**
	 * Método que permite reponer un objeto en el almacén principal cuando
	 * quedan pocas (o ninguna) unidades del mismo.
	 * @param nombreObjeto
	 * @param unidades
	 * @param nombreTienda
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean reponerObjeto(String nombreObjeto, int unidades, String nombreTienda) {
	
		return new ObjetoTiendaDataMapper().updateUnidadesObjeto(nombreObjeto, unidades, nombreTienda);
		
	}
	
	/**
	 * Método que permite ver los encargos en preparación de una forja.
	 * @return encargosEnPreparación
	 */
	public List<Encargo> verEncargosEnPreparacion(int idForja) {
		
		List<Encargo> encargosEnPreparacion = new ArrayList<Encargo>();
		
		List<Encargo> encargos = new EncargoDataMapper().selectAllEncargosByIdForja(idForja);
		
		for(Encargo e: encargos) {
			if (e.getEstado().equals("en preparación")) {
				encargosEnPreparacion.add(e);
			}
		}
		
		return encargosEnPreparacion;
		
	}
	
	/**
	 * Método que permite ver los socios que pueden forjar.
	 * @return socios
	 */
	public List<Socio> verSocios() {
		
		return new SocioDataMapper().selectAllSocios();
	}
	
	/**
     * Metodo que permite anhadir un objeto a la tienda especificada
     * @param objeto
     * @param tienda
     * @param unidades
     * @return true si se ha podido insertar, false en otro caso
     */
    public boolean anhadirObjeto(String objeto, String tienda, int unidades) {
        return new ObjetoTiendaDataMapper().insertObjetoATienda(objeto, tienda, unidades);
    }
	
}
