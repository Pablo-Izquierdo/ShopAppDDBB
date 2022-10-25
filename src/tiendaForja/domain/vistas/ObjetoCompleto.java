package tiendaForja.domain.vistas;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import tiendaForja.domain.Material;
import tiendaForja.domain.Objeto;

/**
 * Clase del dominio que representa una vista del objeto con sus materiales y tipo de arma,
 * efecto y tipo objeto, no recoge ninguna tabla del problema de informacion propuesto,
 * su uso exclusivo es para el catalogo en la capa de negocio.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 */
public class ObjetoCompleto extends Objeto {

	/* Atributos de la clase */
	private String nombreArma;
	private String nombreEfecto;
	private List<Material> materiales = new LinkedList<Material>();
	
	/**
	 * Constructor de la clase ObjetoCompleto que recibe el idObjeto como parametro y llama
	 * a la clase Objeto de la que hereda sus atributos.
	 * @param idobjeto
	 * @param nombre
	 * @param nivel
	 * @param precio
	 * @param diasCreacion
	 * @param idtipoobjeto
	 * @param idarma
	 * @param idefecto
	 * @param nombreArma
	 * @param nombreEfecto
	 * @param materiales
	 */
	public ObjetoCompleto(int idobjeto, String nombre, int nivel, BigDecimal precio, int diasCreacion, 
			int idtipoobjeto, int idarma, int idefecto, 
			String nombreArma, String nombreEfecto, List<Material> materiales) {
		super(idobjeto, nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto);
		this.nombreArma = nombreArma;
		this.nombreEfecto = nombreEfecto;
		this.materiales = materiales;
	}
	
	/**
	 * Constructor de la clase ObjetoCompleto que no recibe el idObjeto como parametro y llama
	 * a la clase Objeto de la que hereda sus atributos.
	 * @param nombre
	 * @param nivel
	 * @param precio
	 * @param unidades
	 * @param diasCreacion
	 * @param idtipoobjeto
	 * @param idarma
	 * @param idefecto
	 * @param nombreArma
	 * @param nombreEfecto
	 * @param materiales
	 */
	public ObjetoCompleto(String nombre, int nivel, BigDecimal precio,
			int unidades, int diasCreacion, int idtipoobjeto, int idarma, int idefecto, 
			String nombreArma, String nombreEfecto, List<Material> materiales) {
		super(nombre, nivel, precio, diasCreacion, idtipoobjeto, idarma, idefecto);
		this.nombreArma = nombreArma;
		this.nombreEfecto = nombreEfecto;
		this.materiales = materiales;
	}

	/* Getters y Setters de la clase */
	public String getNombreArma() {
		return nombreArma;
	}

	public void setNombreArma(String nombreArma) {
		this.nombreArma = nombreArma;
	}

	public String getNombreEfecto() {
		return nombreEfecto;
	}

	public void setNombreEfecto(String nombreEfecto) {
		this.nombreEfecto = nombreEfecto;
	}

	public List<Material> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<Material> materiales) {
		this.materiales = materiales;
	}
	
	// No cambiamos el metodo toString() al heredarlo de la clase objeto, el resto de atributos se mostraran en la capa de negocio.
	
}
