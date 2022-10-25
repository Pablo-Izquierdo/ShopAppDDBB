package tiendaForja.domain.vistas;

import tiendaForja.domain.Compra;
import tiendaForja.domain.Objeto;
import java.math.BigDecimal;

/**
 * Clase del dominio que representa la vista de la compra de un objeto.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class LineaCompra {
	
	/* Atributos de la clase */
	private Compra compra;
	private Objeto objeto;
	private BigDecimal precioUnitario;
	private int unidades;
	
	/**
	 * Constructor de la clase que genera una compra con su objeto/s,
	 * precio por unidad y unidades compradas del objeto.
	 * @param compra
	 * @param objeto
	 * @param precioUnitario
	 * @param unidades
	 */
	public LineaCompra(Compra compra, Objeto objeto, BigDecimal precioUnitario, int unidades) {
		this.compra = compra;
		this.objeto = objeto;
		this.precioUnitario = precioUnitario;
		this.unidades = unidades;
	}

	/* Getters and Setters de los atributos de la clase */
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	/**
	 * Sobreescribimos el metodo toString() para mostrar
	 * la compra con sus objeto/s, precio por unidad y
	 * unidades compradas.
	 */
	@Override
	public String toString() {
		return "[ La compra: " + this.getCompra().getIdcompra() + " del objeto/s: " + this.getObjeto().getNombre() +
				"cuyo precio unitario es: " + this.getPrecioUnitario() + "€ y con " + this.getUnidades() + " unidades ]";
	}
	
}
