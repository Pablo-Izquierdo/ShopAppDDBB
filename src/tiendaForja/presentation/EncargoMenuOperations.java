package tiendaForja.presentation;

import java.sql.Date;

import fundamentos.Lectura;
import fundamentos.Mensaje;
import tiendaForja.business.Cliente;

/**
 * 
 * @author Carlos Jimeno, Sara Grela
 * @lastmodified 02/05/2020
 *
 */

public class EncargoMenuOperations {

	@SuppressWarnings("unused")
	private EncargoMenu am; //menú al que referencia
	
	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public EncargoMenuOperations(EncargoMenu menu) {
		
		this.am = menu;
	}

	public boolean recibeParametrosNuevoEncargo() {
		
		//Creamos una nueva ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura("Proporcione los datos para realizar el encargo");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("ID_Forja", 1);
		
		lec.esperaYCierra(); // espera a que introduzcamos el valor y pulsemos el botón
		
		String nombreUsuario = lec.leeString("Nombre_usuario");
		int idForja = lec.leeInt("ID_Forja");
		
		return realizarEncargo(nombreUsuario, idForja);
	}
	
	public boolean realizarEncargo(String nombreUsuario, int idforja) {
		if(nombreUsuario.equals("null") || idforja <= 0) {
			return false;
		}
		
		try {
			
			boolean success = new Cliente().nuevoEncargo(nombreUsuario, idforja);

			//String para mostrar
			String txt;

			if(success) txt = "Éxito al procesar el encargo.";
			else txt="Error al procesar el encargo. Cierre el menu y vuelva a intentar realizar su pedido.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}	

		return true;
	}
	
	public boolean recibeParametrosLineaEncargo() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para añadir un nuevo artículo al encargo");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("Nombre_objeto", "Espada_basica");
		lec.creaEntrada("Unidades", 1);
		lec.creaEntrada("ID_Forja", 1);
		
		lec.esperaYCierra(); // espera a que introduzcamos el valor y pulsemos el botón
		
		String nombreUsuario = lec.leeString("Nombre_usuario");
		String nombreObjeto = lec.leeString("Nombre_objeto");
		int unidades = lec.leeInt("Unidades");
		int idForja = lec.leeInt("ID_Forja");
		
		return anhadeObjetoAEncargo(nombreUsuario, idForja, nombreObjeto, unidades);
	}
	
	public boolean anhadeObjetoAEncargo(String nombreUsuario, int idForja, String nombreObjeto, int unidades) {
		try {
			
			boolean success = new Cliente().nuevaLineaEncargo(nombreUsuario, idForja, nombreObjeto, unidades);

			//String para mostrar
			String txt;

			if(success) txt = "Éxito al añadir el objeto al encargo.";
			else txt="Error. Cierre el menu y vuelva a intentar realizar su pedido.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
			
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error. Comprueba que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}	

		return true;
	}
	
	public boolean recogeEncargo() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para recoger su encargo");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("ID_Forja", 1);
		lec.creaEntrada("Fecha_expedida_encargo", "AAAA-MM-DD");
		
		lec.esperaYCierra(); // espera a que introduzcamos el valor y pulsemos el botón
		
		String nombreUsuario = lec.leeString("Nombre_usuario");
		int idforja = lec.leeInt("ID_Forja");
		Date fechaEncargo = Date.valueOf(lec.leeString("Fecha_expedida_encargo"));
		
		try {
			
			boolean success = new Cliente().recogerEncargo(nombreUsuario, idforja, fechaEncargo);
			//String para mostrar
			String txt;

			if(success) txt = "Éxito al recoger el encargo.";
			else txt="Error. Cierre el menu y vuelva a intentar realizar su pedido.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
			
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}	
		
		return true;	
	}
}
