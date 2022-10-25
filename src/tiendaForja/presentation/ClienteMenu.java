package tiendaForja.presentation;

import fundamentos.Menu;

/**
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 25/04/2020
 *
 */
public class ClienteMenu {
	

	ClienteMenuOperations cliente = new ClienteMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu
	
	
	public void run() {
		
		int option;
		//Creamos un menú e insertamos las opciones sin acentos por si acaso
		Menu clienteMenu = new Menu("Menu de gestion de los clientes");
		clienteMenu.insertaOpcion("Mostrar catalogo", 1);
		clienteMenu.insertaOpcion("Nueva compra", 2);
		clienteMenu.insertaOpcion("Nuevo encargo",3);
		clienteMenu.insertaOpcion("Recoger encargo",4);
		clienteMenu.insertaOpcion("Unirse como socio",5);
		clienteMenu.insertaOpcion("Dar de baja como socio",6);
		clienteMenu.insertaOpcion("Salir del menu de clientes",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = clienteMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		clienteMenu.cierra();
		
	}
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menÃº
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			cliente.verCatalogo(); 
			break;
			
		case 2:
			 new CompraMenu().run(); 
	         break;
		
		case 3:
			new EncargoMenu().run(); 
	        break;
			
		case 4:
			cliente.recogeEncargo(); 
			break;
			
		case 5:
			cliente.hacerteSocio(); 
			break;
			
		case 6:
			cliente.darteDeBaja(); 
			break; 
		
		default:
			break;
			
		
		}
	}
}
