package tiendaForja.business;

import java.math.BigDecimal;

import tiendaForja.domain.Arma;
import tiendaForja.domain.Efecto;
import tiendaForja.domain.Estadistica;
import tiendaForja.domain.Forja;
import tiendaForja.domain.Tienda;
import tiendaForja.persistenceLayer.ArmaDataMapper;
import tiendaForja.persistenceLayer.EfectoDataMapper;
import tiendaForja.persistenceLayer.EstadisticaDataMapper;
import tiendaForja.persistenceLayer.EstadisticaObjetoDataMapper;
import tiendaForja.persistenceLayer.ForjaDataMapper;
import tiendaForja.persistenceLayer.MaterialDataMapper;
import tiendaForja.persistenceLayer.MaterialObjetoDataMapper;
import tiendaForja.persistenceLayer.MaterialSocioDataMapper;
import tiendaForja.persistenceLayer.MaterialTiendaDataMapper;
import tiendaForja.persistenceLayer.ObjetoDataMapper;
import tiendaForja.persistenceLayer.TiendaDataMapper;

/**
 * Clase con las operaciones de negocio de la gestión del juego.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Sara Grela
 * @lastmodified 02/05/2020
 *
 */
public class GestionJuego {
	
	/**
	 * Método que permite crear una tienda en la Base de Datos.
	 * @param nombre
	 * @param dineroTienda
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearTienda(String nombre, BigDecimal dineroTienda) {
		
		Tienda t = new Tienda(nombre, dineroTienda);
		
		//Inserto la tienda haciendo uso del DataMapper
		return new TiendaDataMapper().insertTienda(t);	
	}
	
	/**
	 * Método que permite crear una forja en la Base de Datos.
	 * @param idtienda
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearForja(int idtienda) {
		
		Forja f = new Forja(idtienda);
		
		//Inserto la forja haciendo uso del DataMapper
		return new ForjaDataMapper().insertForja(f);
	}
	
	/**
	 * Método que permite crear un material en la Base de Datos.
	 * @param nombre
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearMaterial(String nombre) {
		
		//Inserto el material haciendo uso del DataMapper
		return new MaterialDataMapper().insertMaterial(nombre);
		
	}
	
	/**
	 * Método que permite al socio obtener materiales (no gestionamos cómo, simplemente los obtiene).
	 * @param idsocio
	 * @param nomMaterial
	 * @param unidades
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean obtenerMaterialesSocio(int idsocio, String nomMaterial, int unidades) {
		
		//Anhado el material para el socio haciendo uso del DataMapper
		return new MaterialSocioDataMapper().insertMaterialSocio(idsocio, nomMaterial, unidades);
	}
	
	/**
	 * Método que permite a la tienda obtener material (no gestionamos cómo, simplemente los obtiene).
	 * @param nomTienda
	 * @param nomMaterial
	 * @param unidades
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean obtenerMaterialesTienda(String nomTienda, String nomMaterial, int unidades) {
		
		//Anhado el material para la tienda haciendo uso del DataMapper
		return new MaterialTiendaDataMapper().insertMaterialTienda(nomTienda, nomMaterial, unidades);
	}
	
	/**
	 * Método que permite insertar una estadística a la Base de Datos.
	 * @param idtipoobjeto
	 * @param nombre
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearEstadistica(int idtipoobjeto, String nombre) {
		
		//Inserto la estadística haciendo uso del DataMapper
		Estadistica e = new Estadistica(idtipoobjeto, nombre);
		
		return new EstadisticaDataMapper().insertEstadistica(e);
	}
	
	/**
	 * Método que permite crear un objeto en la Base de Datos.
	 * @param nombre
	 * @param nivel
	 * @param precio
	 * @param diasCreacion
	 * @param idtipoobjeto
	 * @param idarma
	 * @param idefecto
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearObjeto(String nombre, int nivel, BigDecimal precio,
			int diasCreacion, int idtipoobjeto, int idarma, int idefecto) {
		
		//Inserto el objeto haciendo uso del DataMapper
		return new ObjetoDataMapper().insertObjeto(nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto);	
	}
	
	/**
	 * Método que permite insertar los materiales de creación de un objeto.
	 * @param nombreMaterial
	 * @param nombreObjeto
	 * @param unidades
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean insertaMaterialObjeto(String nombreMaterial, String nombreObjeto, int unidades) {
		
		//Inserto el material para el objeto haciendo uso del DataMapper
		return new MaterialObjetoDataMapper().insertMaterialObjeto(nombreMaterial, nombreObjeto, unidades);
	}
	
	/**
	 * Método que permite insertar las estadísticas que aporta un objeto.
	 * @param nombreObjeto
	 * @param nombreEstadistica
	 * @param duracion
	 * @param porcentaje
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean insertaEstadisticasObjeto(String nombreObjeto, String nombreEstadistica, int duracion, BigDecimal porcentaje) {
		
		//Inserto las estadísticas del objeto haciendo uso del DataMapper
		return new EstadisticaObjetoDataMapper().insertEstadisticaObjeto(nombreObjeto, nombreEstadistica, duracion, porcentaje);
	}
	
	/**
	 * Método que permite crear nuevas armas para la Base de Datos.
	 * @param nombre
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearArma(String nombre) {
		
		Arma a = new Arma(nombre);
		
		//Inserto el arma haciendo uso del DataMapper
		return new ArmaDataMapper().insertArma(a);	
	}
	
	/**
	 * Método que permite crear nuevos efectos para las pociones de la Base de Datos.
	 * @param nombre
	 * @return true si no ha habido errores y false en caso contrario
	 */
	public boolean crearEfecto(String nombre) {
		
		Efecto e = new Efecto(nombre);
		
		//Inserto el efecto de la poción haciendo uso del DataMapper
		return new EfectoDataMapper().insertEfecto(e);		
	}
	
}
