package tiendaForja.presentation;

import java.sql.Date;
import java.util.List;

import fundamentos.Lectura;
import fundamentos.Mensaje;
import tiendaForja.domain.vistas.ObjetoTienda;
import tiendaForja.business.Cliente;

/**
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 02/05/2020
 *
 */
public class ClienteMenuOperations {


	@SuppressWarnings("unused")
	private ClienteMenu am; //menú al que referencia

	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public ClienteMenuOperations(ClienteMenu menu) {

		this.am = menu;
	}


	public void verCatalogo() {

        //Creamos una ventana de lectura para leer los datos a insertar
        Lectura lec = new Lectura ("Inserte el nombre de una tienda para saber su catálogo.");
        lec.creaEntrada("Nombre_tienda", "Tienda1");
        lec.esperaYCierra();//espera a que introduzcamos el valor
        String nombre = lec.leeString("Nombre_tienda");

        List<ObjetoTienda> catalogo = new tiendaForja.business.Cliente().verCatalogoTienda(nombre);

        String txt = new String();
        for(ObjetoTienda a: catalogo) {
            txt=txt+a.toString()+"\n";
        }

        //Mostramos por pantalla los datos
        Mensaje msg = new Mensaje();
        msg.escribe(txt);
    }


	
	public void recogeEncargo() {

		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Indique qué encargo desea recoger");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.creaEntrada("Id_Forja", 1);
		lec.creaEntrada("Fecha_realizacion_encargo", "2019-12-01");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreUsuario = lec.leeString("Nombre_usuario");
			int idForja = lec.leeInt("Id_Forja");
			String fechaEncargoString = lec.leeString("Fecha_realizacion_encargo");
			Date fechaEncargo;

			if(fechaEncargoString.isEmpty()) fechaEncargo = null;
			else fechaEncargo = Date.valueOf(fechaEncargoString);

			boolean success = new Cliente().recogerEncargo(nombreUsuario, idForja, fechaEncargo);

			//String para mostrar
			String txt;

			if(success) txt = "Encargo recogido correctamente";
			else txt="Error al recoger el encargo. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al recoger el encargo. Compruebe que los datos estén en el formato adecuad.o");
			e.printStackTrace();
		}

	}





	public void hacerteSocio() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para hacerte socio");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		try {

			String nombreUsuario = lec.leeString("Nombre_usuario");

			boolean success = new Cliente().hacermeSocio(nombreUsuario);

			//String para mostrar
			String txt;

			if(success) txt = "Ya es socio de nuestra tienda";
			else txt="Error al hacerse socio. Comprueba que los datos sean correctos y que se cumplan todas las restricciones de la tabla";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser asÃ­, capturamos la excepciÃ³n y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al hacerse socio. Comprueba que los datos estÃ¡n en el formato adecuado");
			e.printStackTrace();
		}

	}



	public void darteDeBaja() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para darte de baja");
		lec.creaEntrada("Nombre_usuario", "Usuario1");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		try {

			String nombreUsuario = lec.leeString("Nombre_usuario");

			boolean success = new Cliente().darmeDeBaja(nombreUsuario);

			//String para mostrar
			String txt;

			if(success) txt = "Ya no es socio de nuestra tienda";
			else txt="Error al darse de baja. Comprueba que los datos sean correctos y que se cumplan todas las restricciones de la tabla";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser asÃ­, capturamos la excepciÃ³n y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al darse de baja. Comprueba que los datos estÃ¡n en el formato adecuado");
			e.printStackTrace();
		}

	}



}
