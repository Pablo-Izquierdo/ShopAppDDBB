package tiendaForja.domain;

/**
 * Clase del dominio que representa la estadistica del objeto.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class Estadistica {

	/* Atributos de la clase */
	private int idestadistica;
	private int idtipoobjeto;
	private String nombre;
	
	/**
	 * Constructor de la clase Estadistica que recibe el idestadistica como parametro.
	 * @param idestadistica
	 * @param idtipoobjeto
	 * @param nombre
	 */
	public Estadistica(int idestadistica, int idtipoobjeto, String nombre) {
		this.idestadistica = idestadistica;
		this.idtipoobjeto = idtipoobjeto;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor de la clase Estadistica que no recibe el idestadistica como parametro.
	 * @param idtipoobjeto
	 * @param nombre
	 */
	public Estadistica(int idtipoobjeto, String nombre) {
		this.idtipoobjeto = idtipoobjeto;
		this.nombre = nombre;
	}

	/* Getters y Setters de la clase */
	public int getIdestadistica() {
		return idestadistica;
	}

	public void setIdestadistica(int idestadistica) {
		this.idestadistica = idestadistica;
	}

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
	 * Sobreescribe el metodo toString para mostrar la estadistica
	 * con todos sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Estadistica con id: " + this.getIdestadistica() + ", para el objeto: " + 
				this.getIdtipoobjeto() + "y nombre: " + this.getNombre() + " ]";
	}
}
