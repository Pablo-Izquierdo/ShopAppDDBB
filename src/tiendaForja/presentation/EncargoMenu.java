package tiendaForja.presentation;

import fundamentos.*;

/**
 * 
 * @author Carlos Jimeno, Sara Grela
 * @lastmodified 02/05/2020
 *
 */
public class EncargoMenu {

	EncargoMenuOperations encargo = new EncargoMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu

	boolean success = false;
	
	
	public void run() {
		
		int option;
		//Creamos un menú e insertamos las opciones sin acentos por si acaso
		Menu EncargoMenu = new Menu("Menu de gestion de los encargos");
		EncargoMenu.insertaOpcion("Crea un nuevo encargo", 1);
		EncargoMenu.insertaOpcion("Añade objetos al encargo", 2);
		EncargoMenu.insertaOpcion("Recoger un encargo", 3);
		EncargoMenu.insertaOpcion("Cerrar procesa encargo", 99);
		
		//Mientras no se seleccione la opción de salir del menú, se continóa en el menú
		do {
			option = EncargoMenu.leeOpcion(); //se lee la opción seleccionada del menÃº
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		EncargoMenu.cierra();
		
	}
	
	
	private void optionAction(int option) {
		String txt;
		
		switch(option) {
		case 1:
			success = encargo.recibeParametrosNuevoEncargo();
			if (!success) {
				txt = "Error al crear el encargo. Introduzca los datos antes de procesar su pedido.";

		        //Escribimos el mensaje por pantalla
		        Mensaje msg = new Mensaje();
		        msg.escribe(txt);
			}
			break;
			
		case 2:
			success = encargo.recibeParametrosLineaEncargo();
			if (!success) {
				txt = "Error al insertar objetos al encargo. Introduzca los datos antes de procesar su pedido.";

		        //Escribimos el mensaje por pantalla
		        Mensaje msg = new Mensaje();
		        msg.escribe(txt);
			}
			break;
		
		case 3:
			success = encargo.recogeEncargo();
			if (!success) {
				txt = "Error al recoger al encargo. Introduzca los datos antes de procesar su pedido.";

		        //Escribimos el mensaje por pantalla
		        Mensaje msg = new Mensaje();
		        msg.escribe(txt);
			}
			
			break;
			
		default:
			break;
			
		
		}
	}
}
