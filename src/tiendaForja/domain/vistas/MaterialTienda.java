package tiendaForja.domain.vistas;

import tiendaForja.domain.Material;
import tiendaForja.domain.Tienda;

/**
 * Clase del dominio que representa la vista entre una tienda y los
 * materiales de los que dispone.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 */
public class MaterialTienda {

	/* Atributos de la clase */
	private Material material;
	private Tienda tienda;
	private int unidades;
	
	/**
	 * Constructor de la clase asigna a la tienda una cantidad
	 * de material/es de los que dispone.
	 * @param material
	 * @param socio
	 * @param unidades
	 */
	public MaterialTienda(Material material, Tienda tienda, int unidades) {
		this.material = material;
		this.tienda = tienda;
		this.unidades = unidades;		
	}

	/* Getters y Setters de los atributos de la clase */
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	/**
	 * Sobreescribimos el metodo toString() para mostrar
	 * a la tienda con la cantidad de material/es que dispone.
	 */
	@Override
	public String toString() {
		return "[ La tienda: " + this.getTienda().getNombre() + " dispone de: " + this.getUnidades() +  
				" del material/es: " + this.getMaterial().getNombre() + " ]";
	}
	
}
