package tiendaForja.domain;

import java.math.BigDecimal;

/**
 * Clase del dominio que representa al objeto
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Objeto {

	/* Atributos de la clase */
	private int idobjeto;
	private String nombre;
	private int nivel;
	private BigDecimal precio;
	private int diasCreacion;
	private int idtipoobjeto;
	private int idarma;
	private int idefecto;
	
	/**
	 * Constructor de la clase Objeto que recibe el idobjeto como parametro.
	 * @param idobjeto
	 * @param nombre
	 * @param nivel
	 * @param precio
	 * @param diasCreacion
	 * @param idtipoobjeto
	 * @param idarma
	 * @param idefecto
	 */
	public Objeto(int idobjeto, String nombre, int nivel, BigDecimal precio, int diasCreacion, int idtipoobjeto, int idarma, int idefecto) {
		this.idobjeto = idobjeto;
		this.nombre = nombre;
		this.precio = precio;
		this.diasCreacion = diasCreacion;
		this.idtipoobjeto = idtipoobjeto;
		this.idarma = idarma;
		this.idefecto = idefecto;
	}
	
	/**
	 * Constructor de la clase Objeto que no recibe el idobjeto como parametro.
	 * @param nombre
	 * @param nivel
	 * @param precio
	 * @param diasCreacion
	 * @param idtipoobjeto
	 * @param idarma
	 * @param idefecto
	 */
	public Objeto(String nombre, int nivel, BigDecimal precio, int diasCreacion, int idtipoobjeto, int idarma, int idefecto) {
		this.nombre = nombre;
		this.precio = precio;
		this.diasCreacion = diasCreacion;
		this.idtipoobjeto = idtipoobjeto;
		this.idarma = idarma;
		this.idefecto = idefecto;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdobjeto() {
		return idobjeto;
	}

	public void setIdobjeto(int idobjeto) {
		this.idobjeto = idobjeto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getDiasCreacion() {
		return diasCreacion;
	}

	public void setDiasCreacion(int diasCreacion) {
		this.diasCreacion = diasCreacion;
	}

	public int getIdtipoobjeto() {
		return idtipoobjeto;
	}

	public void setIdtipoobjeto(int idtipoobjeto) {
		this.idtipoobjeto = idtipoobjeto;
	}

	public int getIdarma() {
		return idarma;
	}

	public void setIdarma(int idarma) {
		this.idarma = idarma;
	}

	public int getIdefecto() {
		return idefecto;
	}

	public void setIdefecto(int idefecto) {
		this.idefecto = idefecto;
	}
	
	/**
	 * Sobreescribe el metodo toString() de la clase
	 * Objeto para mostrarlo con todos sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Objeto con id: " + this.getIdobjeto() + ", nombre: " + this.getNombre() + ", precio: " + this.getPrecio() + 
				"€, tarda en crearse: " + this.getDiasCreacion() + ", con tipo de objeto: " + 
				this.getIdtipoobjeto() + ", tipo de arma: " + this.getIdarma() + " y efecto: " + this.getIdefecto() + " ]";
	}
	
}
