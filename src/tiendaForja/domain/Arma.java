package tiendaForja.domain;

/**
 * 
 * Clase del dominio que representa la arma.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Arma {
	
	/* Atributos de la clase */
	private int idarma;
	private String nombre;
	
	/**
	 * Constructor de la clase Arma que recibe el idarma como parametro.
	 * @param idarma
	 * @param nombre
	 */
	public Arma(int idarma, String nombre) {
		this.idarma = idarma;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor de la clase Arma que no recibe el idarma como parametro.
	 * @param nombre
	 */
	public Arma(String nombre) {
		this.nombre = nombre;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdarma() {
		return idarma;
	}

	public void setIdarma(int idarma) {
		this.idarma = idarma;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el Arma con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Arma con id: " + this.getIdarma() + " y con nombre: " + this.getNombre() +" ]";
	}	
}
