package tiendaForja.presentation;

import fundamentos.Lectura;
import fundamentos.Mensaje;
import tiendaForja.business.Cliente;

/**
 *
 * @author Carlos Jimeno, Sara Grela
 * @param <E>
 * @lastmodified 02/05/2020
 *
 */
public class CompraMenuOperations {

	@SuppressWarnings("unused")
	private CompraMenu am; //menú al que referencia

	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public CompraMenuOperations(CompraMenu menu) {

		this.am = menu;
	}
	
	public boolean recibeParametrosNuevaCompra() {
				
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para realizar la compra");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("Nombre_objeto", "Espada_basica");
		lec.creaEntrada("Unidades", 1);
		lec.creaEntrada("Nombre_tienda", "Tienda1");
		
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		String nombreUsuario = lec.leeString("Nombre_usuario");
		String nombreObjeto = lec.leeString("Nombre_objeto");
		int unidades = lec.leeInt("Unidades");
		String nombreTienda = lec.leeString("Nombre_tienda");
		
		return realizarCompra(nombreUsuario, nombreObjeto, unidades, nombreTienda);
		
	}

	public boolean realizarCompra(String nombreUsuario, String nombreObjeto, int unidades, String nombreTienda) {

		if(nombreUsuario.equals("null") || nombreObjeto.equals("null")) {
			return false;
		}
		
		try {
			
			boolean success = new Cliente().nuevaCompra(nombreUsuario, nombreObjeto, unidades, nombreTienda);

			//String para mostrar
			String txt;

			if(success) txt = "Éxito al procesar la compra.";
			else txt="Error al procesar la compra. Cierre el menu y vuelva a intentar realizar su compra.";

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
	
	
	public boolean recibeParametrosLineaCompra() {
		
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para añadir un nuevo artículo a su compra");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("Nombre_objeto", "Espada_basica");
		lec.creaEntrada("Unidades", 1);
		lec.creaEntrada("Nombre_tienda", "Tienda1");
		
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		String nombreUsuario = lec.leeString("Nombre_usuario");
		String nombreObjeto = lec.leeString("Nombre_objeto");
		int unidades = lec.leeInt("Unidades");
		String nombreTienda = lec.leeString("Nombre_tienda");
		
		return anhadeObjetoACompra(nombreUsuario, nombreObjeto, unidades, nombreTienda);
		
	}
	
	public boolean anhadeObjetoACompra(String nombreUsuario, String nombreObjeto, int unidades, String nombreTienda) {
		
		try {
			
			boolean success = new Cliente().nuevaLineaCompra(nombreUsuario, nombreObjeto, unidades, nombreTienda);

			//String para mostrar
			String txt;

			if(success) txt = "Éxito al añadir el objeto a la compra.";
			else txt="Error. Cierre el menu y vuelva a intentar realizar su compra.";

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
