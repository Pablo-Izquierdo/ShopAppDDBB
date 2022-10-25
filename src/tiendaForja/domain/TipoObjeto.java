package tiendaForja.domain;

/**
 * 
 * Clase del dominio que representa el TipoObjeto.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class TipoObjeto {

	/* Atributos de la clase */
	private int idtipoobjeto;
	private String nombre;
	
	/**
	 * Constructor de la clase TipoObjeto que recibe el idtipoobjeto como parametro.
	 * @param idtipoobjeto
	 * @param nombre
	 */
	public TipoObjeto(int idtipoobjeto, String nombre) {
		this.idtipoobjeto = idtipoobjeto;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor de la clase TipoObjeto que no recibe el idtipoobjeto como parametro.
	 * @param nombre
	 */
	public TipoObjeto(String nombre) {
		this.nombre = nombre;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdtipoobjeto() {
		return idtipoobjeto;
	}

	public void setIdtipoobjeto(int idtipoobjeto) {
		this.idtipoobjeto = idtipoobjeto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el TipoObjeto con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ tipo de objeto con el id:" + this.getIdtipoobjeto() + " y con nombre: " 
				+ this.getNombre() +" ]";
	}
}
