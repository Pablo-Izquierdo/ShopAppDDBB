package tiendaForja.domain;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 
 * Clase del dominio que representa a los usuarios.
 * @author Carlos Jimeno
 * @lastmodified 25/04/2020
 * 
 */
public class Usuario {

	/* Atributos de la clase */
	private int idusuario;
	private String nombreUsuario;
	private BigDecimal dinero;
	private Date fechaRegistro;
	
	/**
	 * Constructor de la clase que recibe el idusuario como parametro.
	 * @param idusuario
	 * @param nombreUsuario
	 * @param dinero
	 * @param fechaRegistro
	 */
	public Usuario(int idusuario, String nombreUsuario, BigDecimal dinero,
				Date fechaRegistro) {
		this.idusuario = idusuario;
		this.nombreUsuario = nombreUsuario;
		this.dinero = dinero;
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * Constructor de la clase que no recibe el idusuario como parametro.
	 * @param nombreUsuario
	 * @param dinero
	 * @param fechaRegistro
	 */
	public Usuario(String nombreUsuario, BigDecimal dinero, Date fechaRegistro) {
		this.nombreUsuario = nombreUsuario;
		this.dinero = dinero;
		this.fechaRegistro = fechaRegistro;
	}

	/* Getters y Setters de los atributos de la clase */
	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public BigDecimal getDinero() {
		return dinero;
	}

	public void setDinero(BigDecimal dinero) {
		this.dinero = dinero;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * Sobreescribe el metodo toString() para devolver
	 * el Usuario con sus atributos.
	 */
	@Override
	public String toString() {
		return "[ Usuario con id: " + this.getIdusuario() + ", nombre de usuario: "
				+ this.getNombreUsuario() + ", dispone de " + this.getDinero() 
				+ "€, con fecha de registro: " + this.getFechaRegistro() + " ]";
	}
}
