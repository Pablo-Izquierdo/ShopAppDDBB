package tiendaForja.domain.vistas;

import tiendaForja.domain.Objeto;
import tiendaForja.domain.Estadistica;
import java.math.BigDecimal;

/**
 * Clase del dominio que representa una vista entre estadistica y objeto. 
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * @author Carlos-GF63
 *
 */
public class EstadisticaObjeto {

	/* Atributos de la clase */
	private Objeto objeto;
	private Estadistica estadistica;
	private int duracion;
	private BigDecimal porcentaje;
	
	/**
	 * Constructor de la clase que genera un objeto que recoge la estadistica,
	 * el objeto que la posee y el numero de unidades
	 * @param objeto
	 * @param estadistica
	 * @param duracion
	 */
	public EstadisticaObjeto(Objeto objeto, Estadistica estadistica, int duracion, BigDecimal porcentaje) {
		this.objeto = objeto;
		this.estadistica = estadistica;
		this.duracion = duracion;
		this.porcentaje = porcentaje;
	}

	/* Getters y Setters de los atributos de la clase */
	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Estadistica getEstadistica() {
		return estadistica;
	}

	public void setEstadistica(Estadistica estadistica) {
		this.estadistica = estadistica;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	/**
	 * Sobreescribe el metodo toString() para que muestre
	 * el objeto con su estadistica, duracion y porcentaje.
	 */
	@Override
	public String toString() {
		return "[ Objeto con nombre: " + this.getObjeto().getNombre() + ", estadistica: " + 
				this.getEstadistica().getNombre() + ", con duracion: " + this.getDuracion() +
				" y porcentaje: " + this.getPorcentaje() + " ]";
	}

	
}
