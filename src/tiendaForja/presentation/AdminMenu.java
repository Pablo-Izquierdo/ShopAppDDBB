package tiendaForja.presentation;

import fundamentos.Menu;

/**
 *
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 02/05/2020
 *
 */
public class AdminMenu {


	AdminMenuOperations admin = new AdminMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu

	/**
	 * MÃ©todo run al que se llama desde el exterior para lanzar el menÃº
	 */
	public void run() {

		int option;
		//Creamos un menÃº e insertamos las opciones
		Menu AdminMenu = new Menu("MenÃº de gestiÃ³n de los Administradores");
		AdminMenu.insertaOpcion("Ver historial de las compras", 1);
		AdminMenu.insertaOpcion("Ver historial de las encargos", 2);
		AdminMenu.insertaOpcion("Ver encargos en preparación",3);
		AdminMenu.insertaOpcion("Reponer objeto",4);
		AdminMenu.insertaOpcion("Ver socios",5);
		AdminMenu.insertaOpcion("Anhadir objeto a la tienda", 6);
		AdminMenu.insertaOpcion("Salir del menÃº de Administradores",99);

		//Mientras no se seleccione la opciÃ³n de salir del menÃº, se continÃºa en el menÃº
		do {
			option = AdminMenu.leeOpcion(); //se lee la opciÃ³n seleccionada del menÃº
			this.optionAction(option); //llamamos al mÃ©todo para ejecutar la opciÃ³n seleccionada
		}while(option!=99);

		//Al salir del bucle se cierra el menÃº
		AdminMenu.cierra();

	}

	/**
	 * MÃ©todo que maneja con un switch la opciÃ³n seleccionada en el menÃº
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			admin.verCompras(); 
			break;

		case 2:
			admin.verEncargos(); 
			break;

		case 3:
			admin.verEncargosPreparacion(); 
			break;

		case 4:
			admin.reponerObjeto(); 
			break;

		case 5:
			admin.verSocios(); 
			break; 
			
		case 6:
			admin.anhadirObjetoATienda();
			break;

		default:
			break;


		}
	}
}
