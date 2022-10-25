package tiendaForja.presentation;
import fundamentos.*;

/**
 * Clase para la implementación del menú principal
 * de la aplicación.
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 25/04/2020
 *
 */
public class MainMenu {

	
	public void run() {


		int option;

		//Menú para manejar las acciones (no llevan acentos por si acaso)
		Menu mainMenu = new Menu("Identificacion obligatoria");
		mainMenu.insertaOpcion("Soy cliente", 1);
		mainMenu.insertaOpcion("Soy administrador", 2);
		mainMenu.insertaOpcion("Gestion de juego", 3);
		mainMenu.insertaOpcion("Salir de la aplicacion", 99);

		//Mientras no se seleccione la opción de salir del menú, se continúa en él
		do {
			option = mainMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);

		//Al salir del bucle se cierra el menú
		mainMenu.cierra();

	}


	private void optionAction(int option) {
		switch(option) {
		case 1:
			new ClienteMenu().run(); //lanzamos el menú de gestión de los clientes
			break;

		case 2:
			new AdminMenu().run(); //lanzamos el menú de gestión de los administradores
			break;

		case 3:
			new GestionMenu().run(); //lanzamos el menú de gestión del juego
			break;

		default:
			break;

		}
	}
}