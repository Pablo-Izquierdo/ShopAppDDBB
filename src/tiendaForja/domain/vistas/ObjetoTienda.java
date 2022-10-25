package tiendaForja.domain.vistas;

import tiendaForja.domain.Objeto;
import tiendaForja.domain.Tienda;

/**
 * Clase del dominio que representa una vista de la tienda con los objetos de los que dispone.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 */
public class ObjetoTienda {
	
	/* Atributos de la clase  */
	private Tienda tienda;
	private Objeto objeto;
	private int unidades;
	
	/**
	 * Constructor de la clas ObjetoTienda
	 * @param tienda
	 * @param objeto
	 * @param unidades
	 */
	public ObjetoTienda(Tienda tienda, Objeto objeto, int unidades) {
		this.tienda = tienda;
		this.objeto = objeto;
		this.unidades = unidades;
	}

	/* Getters y Setters de los atributos de la clase */
	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	/**
	 * Sobreescribimos el metodo toString() para mostrar
	 * a la tienda con la cantidad de objeto/s que dispone.
	 */
	@Override
	public String toString() {
		return "[ La tienda: " + this.getTienda().getNombre() + " dispone de: " + this.getUnidades() +  
				" del objeto/s: " + this.getObjeto().getNombre() + " ]";
	}
	
}
