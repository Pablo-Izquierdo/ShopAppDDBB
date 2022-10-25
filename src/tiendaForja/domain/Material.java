package tiendaForja.domain;

/**
 * 
 * Clase del dominio que representa el Material
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class Material {
	
	/* Atributos de la clase */
	private int idmaterial;
	private String nombre;
	
	/**
	 * Constructor de la clase Material que recibe el idmaterial como parametro.
	 * @param idmaterial
	 * @param nombre
	 */
	public Material(int idmaterial, String nombre) {
		this.idmaterial = idmaterial;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor de la clase Material que no recibe el idmaterial como parametro.
	 * @param nombre
	 */
	public Material(String nombre) {
		this.nombre = nombre;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdmaterial() {
		return idmaterial;
	}

	public void setIdmaterial(int idmaterial) {
		this.idmaterial = idmaterial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el Material con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Material con id: " + this.getIdmaterial() + " y con nombre " + this.getNombre() + " ]";
	}
}
