package tiendaForja.presentation;

import fundamentos.Menu;

/**
 * 
 * @author Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastmodified 25/04/2020
 *
 */
public class GestionMenu {
	

	GestionMenuOperations gestor = new GestionMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu
	
	
	public void run() {
		
		int option;
		//Creamos un menú e insertamos las opciones sin acentos por si acaso
		Menu gestorMenu = new Menu("Menu de gestion del juego");
		gestorMenu.insertaOpcion("Obtener material socio", 1);
		gestorMenu.insertaOpcion("Obtener material tienda", 2);
		gestorMenu.insertaOpcion("Crear tienda",3);
		gestorMenu.insertaOpcion("Crear forja",4);
		gestorMenu.insertaOpcion("Crear material",5);
		gestorMenu.insertaOpcion("Crear estadistica",6);
		gestorMenu.insertaOpcion("Crear objeto",7);
		gestorMenu.insertaOpcion("Crear arma",8);
		gestorMenu.insertaOpcion("Crear efecto",9);
		gestorMenu.insertaOpcion("Salir del menu de gestion del juego",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en él
		do {
			option = gestorMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		gestorMenu.cierra();
		
	}
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			gestor.obtenerMaterialUsuario(); 
			break;
			
		case 2:
			 gestor.obtenerMaterialTienda(); 
	         break;
		
		case 3:
			gestor.crearTienda(); 
	        break;
			
		case 4:
			gestor.crearForja(); 
			break;
			
		case 5:
			gestor.crearMaterial(); 
			break;
			
		case 6:
			gestor.crearEstadistica(); 
			break; 
		
		case 7:
			gestor.crearObjeto(); 
			break;
			
		case 8:
			gestor.crearArma(); 
			break;
			
		case 9:
			gestor.crearEfecto(); 
			break; 	
			
		default:
			break;
			
		
		}
	}
}
