package tiendaForja.domain;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Clase del dominio que representa el encargo
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Encargo {
	
	/* Atributos de la clase */
	private int idencargo;
	private int idsocio;
	private int idforja;
	private Date fechaEntrega;
	private Date fechaEncargo;
	private String estado;
	private BigDecimal precioTotal;

	/**
	 * Constructor de la clase encargo que recibe el idencargo como parametro.
	 * @param idencargo
	 * @param idsocio
	 * @param idforja
	 * @param fechaEntrega
	 * @param fechaEncargo
	 * @param estado
	 */
	public Encargo(int idencargo, int idsocio, int idforja, Date fechaEntrega, Date fechaEncargo, String estado, BigDecimal precioTotal) {
		this.idencargo = idencargo;
		this.idsocio = idsocio;
		this.idforja = idforja;
		this.fechaEntrega = fechaEntrega;
		this.fechaEncargo = fechaEncargo;
		this.estado = estado;
		this.precioTotal = precioTotal;
	}
	
	/**
	 * Constructor de la clase encargo que no recibe el idencargo como parametro.
	 * @param idsocio
	 * @param idforja
	 * @param fechaEntrega
	 * @param fechaEncargo
	 * @param estado
	 */
	public Encargo(int idsocio, int idforja, Date fechaEntrega, Date fechaEncargo, String estado, BigDecimal precioTotal) {
		this.idsocio = idsocio;
		this.idforja = idforja;
		this.fechaEntrega = fechaEntrega;
		this.fechaEncargo = fechaEncargo;
		this.estado = estado;
		this.precioTotal = precioTotal;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdencargo() {
		return idencargo;
	}

	public void setIdencargo(int idencargo) {
		this.idencargo = idencargo;
	}

	public int getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(int idsocio) {
		this.idsocio = idsocio;
	}

	public int getIdforja() {
		return idforja;
	}

	public void setIdforja(int idforja) {
		this.idforja = idforja;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaEncargo() {
		return fechaEncargo;
	}

	public void setFechaEncargo(Date fechaEncargo) {
		this.fechaEncargo = fechaEncargo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	/**
	 * Sobreescribe el metodo toString() para mostrar
	 * el encargo con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Encargo con id: " + this.getIdencargo() + ", cuyo socio es: " + this.getIdsocio() + 
				", realizado a la forja: " + this.getIdforja() + ", con fecha de entrega: " + this.getFechaEntrega() +
				", fecha de encargo: " + this.getIdencargo() + ", estado: " + this.getEstado() + "y precio total: " +
				this.getPrecioTotal() +"€ ]";
	}
	
}
