package tiendaForja.domain.vistas;

import tiendaForja.domain.Material;
import tiendaForja.domain.Socio;

/**
 * Clase del dominio que representa la vista entre un socio y los
 * materiales de los que dispone.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 */
public class MaterialSocio {
	
	/* Atributos de la clase */
	private Material material;
	private Socio socio;
	private int unidades;
	
	/**
	 * Constructor de la clase asigna al socio una cantidad
	 * de material/es de los que dispone.
	 * @param material
	 * @param socio
	 * @param unidades
	 */
	public MaterialSocio(Material material, Socio socio, int unidades) {
		this.material = material;
		this.socio = socio;
		this.unidades = unidades;		
	}

	/* Getters y Setters de los atributos de la clase */
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	/**
	 * Sobreescribimos el metodo toString() para mostrar
	 * al socio con la cantidad de material/es que porta
	 * consigo.
	 */
	@Override
	public String toString() {
		return "[ El socio con id: " + this.getSocio().getIdsocio() + " dispone de: " + this.getUnidades() +  
				" del material/es: " + this.getMaterial().getNombre() + " ]";
	}
	
}
