package tiendaForja.domain;

/**
 * Clase del dominio que representa la forja
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Forja {

	/* Atributos de la clase */
	private int idforja;
	private int idtienda;
	
	/**
	 * Constructor de la clase forja que recibe el idforja como parametro.
	 * @param idforja
	 * @param idtienda
	 */
	public Forja(int idforja, int idtienda) {
		this.idforja = idforja;
		this.idtienda = idtienda;
	}
	
	/**
	 * Constructor de la clase forja que no recibe el idforja como parametro.
	 * @param idtienda
	 */
	public Forja(int idtienda) {
		this.idtienda = idtienda;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdforja() {
		return idforja;
	}

	public void setIdforja(int idforja) {
		this.idforja = idforja;
	}

	public int getIdtienda() {
		return idtienda;
	}

	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}
	
	/**
	 * Sobreescribimos el metodo toString() para mostrar
	 * a la forja con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Forja con id: " + this.getIdforja() + " perteneciente a la tienda con id: " +
				this.getIdtienda() + " ]";
	}
	
}
