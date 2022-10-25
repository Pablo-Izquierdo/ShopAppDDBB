package tiendaForja.domain;

/**
 * 
 * Clase del dominio que representa el Efecto. 
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class Efecto {

	/* Atributos de la clase */
	private int idefecto;
	private String nombre;
	
	/**
	 * Constructor de la clase Efecto que recibe el parametro idefecto.
	 * @param idefecto
	 * @param nombre
	 */
	public Efecto(int idefecto, String nombre) {
		this.idefecto = idefecto;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor de la clase Efecto que no recibe el parametro idefecto.
	 * @param nombre
	 */
	public Efecto(String nombre) {
		this.nombre = nombre;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdefecto() {
		return idefecto;
	}

	public void setIdefecto(int idefecto) {
		this.idefecto = idefecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el Efecto con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Efecto con id: "+ this.getIdefecto() + " y con nombre: "+ this.getNombre() +" ]";
	}
}
