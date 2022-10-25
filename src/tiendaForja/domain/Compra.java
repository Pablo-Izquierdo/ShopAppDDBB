package tiendaForja.domain;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Clase del dominio que representa la compra.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class Compra {
	
	/* Atributos de la clase */
	private int idcompra;
	private int idusuario;
	private int idtienda;
	private Date fechaCompra;
	private BigDecimal precioTotal;
	
	/**
	 * Constructor de la clase Compra que recibe el idcompra como parametro.
	 * @param idcompra
	 * @param idusuario
	 * @param fechaCompra
	 */
	public Compra(int idcompra, int idusuario, int idtienda, Date fechaCompra, BigDecimal precioTotal) {
		this.idcompra = idcompra;
		this.idusuario = idusuario;
		this.idtienda = idtienda;
		this.fechaCompra = fechaCompra;
		this.precioTotal = precioTotal;
	}
	
	/**
	 * Constructor de la clase Compra que no recibe el idcompra como parametro.
	 * @param idusuario
	 * @param fechaCompra
	 */
	public Compra(int idusuario, int idtienda, Date fechaCompra, BigDecimal precioTotal) {
		this.idusuario = idusuario;
		this.idtienda = idtienda;
		this.fechaCompra = fechaCompra;
		this.precioTotal = precioTotal;
	}

	/* Getters y Setters de los atributos */
	public int getIdcompra() {
		return idcompra;
	}

	public void setIdcompra(int idcompra) {
		this.idcompra = idcompra;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public int getIdtienda() {
		return idtienda;
	}

	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}

	/**
	 * Sobreescribe el metodo toString() para devolver
	 * la Compra con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Compra con id: " + this.getIdcompra() + ", hecha por el usuario con id: " +
				this.getIdusuario() + " a la tienda: " + this.getIdtienda() + " con fecha de realizacion: " + this.getFechaCompra() + 
				" y precio total: " + this.getPrecioTotal() + "€ ]";
	}

}

