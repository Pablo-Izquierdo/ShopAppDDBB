package tiendaForja.presentation;

import java.math.BigDecimal;


import fundamentos.Lectura;
import fundamentos.Mensaje;
import tiendaForja.business.GestionJuego;
import tiendaForja.domain.Arma;
import tiendaForja.domain.Efecto;
import tiendaForja.domain.TipoObjeto;
import tiendaForja.persistenceLayer.ArmaDataMapper;
import tiendaForja.persistenceLayer.EfectoDataMapper;
import tiendaForja.persistenceLayer.TipoObjetoDataMapper;

/**
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 02/05/2020
 *
 */
public class GestionMenuOperations {


	@SuppressWarnings("unused")
	private GestionMenu am; //menú al que referencia

	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public GestionMenuOperations(GestionMenu menu) {

		this.am = menu;
	}


	public void obtenerMaterialUsuario() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para obtener un material");
		lec.creaEntrada("ID_socio", 1);
		lec.creaEntrada("Nombre_material", "Madera");
		lec.creaEntrada("Unidades", 1);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			int idsocio = lec.leeInt("ID_socio");
			String nombreMaterial = lec.leeString("Nombre_material");
			int unidades = lec.leeInt("Unidades");

			boolean success = new GestionJuego().obtenerMaterialesSocio(idsocio, nombreMaterial, unidades);

			//String para mostrar
			String txt;

			if(success) txt = "Material obtenido correctamente";
			else txt="Error al obtener el material. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al obtener el material. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}

	public void obtenerMaterialTienda() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para obtener un material");
		lec.creaEntrada("Nombre_tienda", "tienda");
		lec.creaEntrada("Nombre_material", "Madera");
		lec.creaEntrada("Unidades", 1);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreTienda = lec.leeString("Nombre_tienda");
			String nombreMaterial = lec.leeString("Nombre_material");
			int unidades = lec.leeInt("Unidades");
			
			boolean success = new GestionJuego().obtenerMaterialesTienda(nombreTienda, nombreMaterial, unidades);

			//String para mostrar
			String txt;

			if(success) txt = "Material obtenido correctamente";
			else txt="Error al obtener el material. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al obtener el material. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}



	public void crearTienda() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear la tienda");
		lec.creaEntrada("Nombre_tienda", "Tienda1");
		lec.creaEntrada("Dinero", 0.0);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreTienda = lec.leeString("Nombre_tienda");
			BigDecimal dinero = BigDecimal.valueOf(lec.leeDouble("Dinero"));

			boolean success = new GestionJuego().crearTienda(nombreTienda, dinero);

			//String para mostrar
			String txt;

			if(success) txt = "Tienda creada correctamente";
			else txt="Error al crear la tienda. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear la tienda. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}



	public void crearForja() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear la forja");
		lec.creaEntrada("Id_tienda", 1);
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			int idTienda = lec.leeInt("Id_tienda");

			boolean success = new GestionJuego().crearForja(idTienda);

			//String para mostrar
			String txt;

			if(success) txt = "Forja creada correctamente";
			else txt="Error al crear la forja. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear la forja. Compruebe que los datos están en el formato adecuado.");
			e.printStackTrace();
		}

	}

	public void crearMaterial() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear un material");
		lec.creaEntrada("Nombre_material", "Madera");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreMaterial = lec.leeString("Nombre_material");

			boolean success = new GestionJuego().crearMaterial(nombreMaterial);

			//String para mostrar
			String txt;

			if(success) txt = "Material creado correctamente";
			else txt="Error al crear el material. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear el material. Compruebe que los datos están en el formato adecuado.");
			e.printStackTrace();
		}
	}



	public void crearEstadistica() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear la estadística");
		lec.creaEntrada("idTipoObjeto", 1);
		lec.creaEntrada("Nombre_estadistica", "Fuerza");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			int idTipoObjeto = lec.leeInt("idTipoObjeto");
			String nombreEstadistica = lec.leeString("Nombre_estadistica");

			boolean success = new GestionJuego().crearEstadistica(idTipoObjeto, nombreEstadistica);

			//String para mostrar
			String txt;

			if(success) txt = "Estadistica creada correctamente";
			else txt="Error al crear la estadistica. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear la estadistica. Compruebe que los datos están en el formato adecuado.");
			e.printStackTrace();
		}
	}



	public void crearObjeto() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear un objeto");
		lec.creaEntrada("Nombre_objeto", "Espada basica");
		lec.creaEntrada("Nivel", 40);
		lec.creaEntrada("Precio", 5000.00);
		lec.creaEntrada("Dias_creacion", 5);
		lec.creaEntrada("Tipo_de_objeto", "Arma");
		lec.creaEntrada("Arma", "Espada");
		lec.creaEntrada("Efecto", "Venenoso");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón


		try {

			String nombreObjeto = lec.leeString("Nombre_objeto");
			int nivel = lec.leeInt("Nivel");
			BigDecimal precio = BigDecimal.valueOf(lec.leeDouble("Precio"));
			int diasCreacion = lec.leeInt("Dias_creacion");
			String TipoObjeto= lec.leeString("Tipo_de_objeto");
			String Arma = lec.leeString("Arma");
			String Efecto = lec.leeString("Efecto");
			
			TipoObjeto to = new TipoObjetoDataMapper().selectTipoObjetoByNombre(TipoObjeto);
			int idTO = to.getIdtipoobjeto();
			Arma a = new ArmaDataMapper().selectArmaByNombre(Arma);
			Efecto e = new EfectoDataMapper().selectEfectoByNombre(Efecto);
			
			// Si el objeto es una poción, el campo "arma" será el NULL
			if (idTO == 2) {
				a = new ArmaDataMapper().selectArmaById(0);
			// Si el objeto es un arma, el campo "poción" será el NULL	
			} else if (idTO == 0) {
				e = new EfectoDataMapper().selectEfectoById(0);
			}

			boolean success = new GestionJuego().crearObjeto(nombreObjeto, nivel, precio, diasCreacion,
					to.getIdtipoobjeto(), a.getIdarma(), e.getIdefecto());

			//String para mostrar
			String txt;

			if(success) txt = "Objeto creado correctamente";
			else txt="Error al crear el objeto. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear el objeto. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}

	public void crearArma() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear un arma");
		lec.creaEntrada("Nombre_arma", "Espada basica");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreArma = lec.leeString("Nombre_arma");

			boolean success = new GestionJuego().crearArma(nombreArma);

			//String para mostrar
			String txt;

			if(success) txt = "Arma creada correctamente";
			else txt="Error al crear un arma. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear un arma. Comprueba que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}



	public void crearEfecto() {
		//Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporcione los datos para crear un efecto");
		lec.creaEntrada("Nombre_efecto", "Venenoso");
		lec.esperaYCierra();//espera a que introduzcamos el valor y pulsemos el botón

		try {

			String nombreEfecto = lec.leeString("Nombre_efecto");

			boolean success = new GestionJuego().crearEfecto(nombreEfecto);

			//String para mostrar
			String txt;

			if(success) txt = "Efecto creado correctamente";
			else txt="Error al crear el efecto. Compruebe que los datos sean correctos y que se cumplan todas las restricciones de la tabla.";

			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);

			//Controlamos que los tipos de datos se han introducido correctamente. 
			//De no ser así, capturamos la excepción y mostramos el error.
		} catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al crear el efecto. Compruebe que los datos estén en el formato adecuado.");
			e.printStackTrace();
		}
	}


}
