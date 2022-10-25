package tiendaForja.domain;

import java.sql.Date;
/**
 * 
 * Clase del dominio que representa a los socios.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Socio {

	/* Atributos de la clase */
	private int idsocio;
	private int idusuario;
	private Date fechaSocio;


	/**
	 * Constructor de la clase Socio que recibe el idSocio como parametro.
	 * @param idsocio
	 * @param idusuario
	 * @param fechaSocio
	 */
	public Socio(int idsocio, int idusuario, Date fechaSocio) {
		this.idsocio = idsocio;
		this.idusuario = idusuario;
		this.fechaSocio = fechaSocio;
	}
	
	/**
	 * Constructor de la clase Socio que no recibe el idSocio como parametro.
	 * @param idusuario
	 * @param fechaSocio
	 */
	public Socio(int idusuario, Date fechaSocio) {
		this.idusuario = idusuario;
		this.fechaSocio = fechaSocio;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(int idsocio) {
		this.idsocio = idsocio;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public Date getFechaSocio() {
		return fechaSocio;
	}

	public void setFechaSocio(Date fechaSocio) {
		this.fechaSocio = fechaSocio;
	}
	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el Socio con sus atributos
	 */
	@Override
	public String toString() {
		return "[ Socio con id: " + this.getIdsocio() + ", cuyo id de usuario es: " + 
				this.getIdusuario() + " y fecha de alta: " + this.fechaSocio + " ]";
	}
}
