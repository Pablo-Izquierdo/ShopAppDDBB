package tiendaForja.domain.vistas;

import tiendaForja.domain.Material;
import tiendaForja.domain.Objeto;

/**
 * Clase del dominio que representa la vista entre un objeto y los
 * materiales que lo componen.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 */
public class MaterialObjeto {
	
	/* Atributos de la clase */
	private Material material;
	private Objeto objeto;
	private int unidades;
	
	/**
	 * Constructor de la clase asigna al objeto una cantidad
	 * de material/es necesario.
	 * @param material
	 * @param objeto
	 * @param unidades
	 */
	public MaterialObjeto(Material material, Objeto objeto, int unidades) {
		this.material = material;
		this.objeto = objeto;
		this.unidades = unidades;		
	}

	/* Getters y Setters de los atributos de la clase */
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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
	 * al objeto con la cantidad de material/es que necesita
	 * para construirse.
	 */
	@Override
	public String toString() {
		return "[ El objeto: " + this.getObjeto().getNombre() + " necesita: " + this.getUnidades() +  
				" del material/es: " + this.getMaterial().getNombre() + " ]";
	}
}
