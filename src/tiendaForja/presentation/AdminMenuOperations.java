package tiendaForja.presentation;

import java.util.List;

import fundamentos.Lectura;
import fundamentos.Mensaje;
import tiendaForja.domain.Encargo;
import tiendaForja.domain.Socio;
import tiendaForja.business.Administrador;
import tiendaForja.domain.Compra;

/**
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 02/05/2020
 *
 */
public class AdminMenuOperations {


	@SuppressWarnings("unused")
	private AdminMenu am; //menu al que referencia

	/**
	 * Constructor. Se le pasa el menÃº.
	 * @param menu
	 */
	public AdminMenuOperations(AdminMenu menu) {

		this.am = menu;
	}


	public void verCompras() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para mostrar las compras");
		lec.creaEntrada("Nombre_Usuario", "usuario");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		String nombreUsuario = lec.leeString("Nombre_Usuario");

		//Creamos una lista de cursos y usamos la capa de negocio para obtenerlos
		List<Compra> compras = new Administrador().verHistorialCompras(nombreUsuario);

		//Para cada curso retornado, guardamos sus datos en un String
		String txt = new String();
		for(Compra a: compras) {
			txt=txt+a.toString()+"\n";
		}

		//Mostramos por pantalla los datos
		Mensaje msg = new Mensaje();
		msg.escribe(txt);
	}


	public void verEncargos() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para mostrar los encargos");
		lec.creaEntrada("Nombre_Usuario", "usuario");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		String nombreUsuario = lec.leeString("Nombre_Usuario");

		List<Encargo> encargos = new Administrador().verHistorialEncargos(nombreUsuario);

		//Para cada curso retornado, guardamos sus datos en un String
		String txt = new String();
		for(Encargo a: encargos) {
			txt=txt+a.toString()+"\n";
		}

		//Mostramos por pantalla los datos
		Mensaje msg = new Mensaje();
		msg.escribe(txt);
	}



	public void verEncargosPreparacion() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para mostrar los encargos en preparacion");
		lec.creaEntrada("Id_Forja", 1);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		int forja = lec.leeInt("Id_Forja");

		List<Encargo> encargos = new Administrador().verEncargosEnPreparacion(forja);

		//Para cada curso retornado, guardamos sus datos en un String
		String txt = new String();
		for(Encargo a: encargos) {
			txt=txt+a.toString()+"\n";
		}

		//Mostramos por pantalla los datos
		Mensaje msg = new Mensaje();
		msg.escribe(txt);
	}


	//
	public void reponerObjeto() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para reponer las unidades del objeto");
		lec.creaEntrada("Nombre_Objeto", "objeto");
		lec.creaEntrada("Unidades_objeto", 1);
		lec.creaEntrada("Tienda", "tienda");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		try {

			String objeto = lec.leeString("Nombre_Objeto");
			String tienda = lec.leeString("Tienda");
			int unidades = lec.leeInt("Unidades_objeto");

			boolean success = new Administrador().reponerObjeto(objeto, unidades, tienda);

			//String para mostrar
			String txt;

			if(success) txt = "Se han repuesto las unidades del objeto correctamente";
			else txt="Error al reponer unidades. Comprueba que los datos sean correctos y que se cumplan todas las restricciones de la tabla";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser asÃ­, capturamos la excepciÃ³n y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al reponer unidades. Comprueba que los datos estÃ¡n en el formato adecuado");
			e.printStackTrace();
		}
	}



	public void verSocios() {

		List<Socio> socios = new Administrador().verSocios();

		
		String txt = new String();
		for(Socio a: socios) {
			txt=txt+a.toString()+"\n";
		}

		//Mostramos por pantalla los datos
		Mensaje msg = new Mensaje();
		msg.escribe(txt);
	}
	
	public void anhadirObjetoATienda() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos para anhadir el objeto a la tienda");
		lec.creaEntrada("Nombre_Objeto", "objeto");
		lec.creaEntrada("Nombre_Tienda", "tienda");
		lec.creaEntrada("Unidades_objeto", 1);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botÃ³n

		try {

			String objeto = lec.leeString("Nombre_Objeto");
			String tienda = lec.leeString("Nombre_Tienda");
			int unidades = lec.leeInt("Unidades_objeto");

			boolean success = new Administrador().anhadirObjeto(objeto, tienda, unidades);

			//String para mostrar
			String txt;

			if(success) txt = "Se ha anhadido el objeto correctamente";
			else txt="Error al añadir el objeto. Comprueba que los datos sean correctos y que se cumplan todas las restricciones de la tabla";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser asÃ­, capturamos la excepciÃ³n y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al añadir el objeto. Comprueba que los datos estÃ¡n en el formato adecuado");
			e.printStackTrace();
		}
	}



}
