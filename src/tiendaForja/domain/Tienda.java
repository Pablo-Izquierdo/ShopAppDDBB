package tiendaForja.domain;

import java.math.BigDecimal;

/**
 * 
 * Clase del dominio que representa la tienda
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Tienda {

	/* Atributos de la clase */
	private int idtienda;
	private String nombre;
	private BigDecimal dineroTienda;
	
	/**
	 * Constructor de la clase de tienda que recibe el idtienda como parametro.
	 * @param idtienda
	 * @param nombre
	 * @param dineroTienda
	 * @param idobjeto
	 */
	public Tienda(int idtienda, String nombre, BigDecimal dineroTienda) {
		this.idtienda = idtienda;
		this.nombre = nombre;
		this.dineroTienda = dineroTienda;
	}
	
	/**
	 * Constructor de la clase de tienda que no recibe el idtienda como parametro.
	 * @param nombre
	 * @param dineroTienda
	 * @param idobjeto
	 */
	public Tienda(String nombre, BigDecimal dineroTienda) {
		this.nombre = nombre;
		this.dineroTienda = dineroTienda;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdtienda() {
		return idtienda;
	}

	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getDineroTienda() {
		return dineroTienda;
	}

	public void setDineroTienda(BigDecimal dineroTienda) {
		this.dineroTienda = dineroTienda;
	}
	
	@Override
	public String toString() {
		return "[ Tienda con id: " + this.getIdtienda() + ", nombre: " + this.getNombre() +
				", y fondos: " + this.getDineroTienda() + "€. ]" ;
		
	}
	
	
}
