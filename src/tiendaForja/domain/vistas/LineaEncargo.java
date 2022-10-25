package tiendaForja.domain.vistas;

import tiendaForja.domain.Encargo;
import tiendaForja.domain.Objeto;
import java.math.BigDecimal;

/**
 * Clase del dominio que representa la vista del encargo de un objeto.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 *
 */
public class LineaEncargo {

	/* Atributos de la clase */
	private Encargo encargo;
	private Objeto objeto;
	private BigDecimal precioUnitario;
	private int unidades;
	
	/**
	 * Constructor de la clase que genera un encargo con su objeto/s, precio
	 * unitario y unidades encargadas.
	 * @param encargo
	 * @param objeto
	 * @param precioUnitario
	 * @param unidades
	 */
	public LineaEncargo(Encargo encargo, Objeto objeto, BigDecimal precioUnitario, int unidades) {
		this.encargo = encargo;
		this.objeto = objeto;
		this.precioUnitario = precioUnitario;
		this.unidades = unidades;
	}

	/* Getters y Setters de los atributos de la clase */
	public Encargo getEncargo() {
		return encargo;
	}

	public void setEncargo(Encargo encargo) {
		this.encargo = encargo;
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
	 * Sobreescribimos el metodo toString() para mostrar la clase
	 * con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ El encargo: " + this.getEncargo().getIdencargo() + ", del objeto/s: " + this.getObjeto().getNombre() +
				" cuyo precio unitario es: " + this.getPrecioUnitario() + "€ y con: " + this.getUnidades() + " unidades ]";
	}
	
}
