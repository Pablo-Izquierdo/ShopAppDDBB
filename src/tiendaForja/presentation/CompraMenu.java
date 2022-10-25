package tiendaForja.presentation;

import fundamentos.*;


/**
 * 
 * @author Carlos Jimeno, Sara Grela
 * @lastmodified 25/04/2020
 *
 */
public class CompraMenu {

	CompraMenuOperations compra = new CompraMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu
	
	boolean success = false;
	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		int option;
		//Creamos un menú e insertamos las opciones sin acentos por si acaso
		Menu compraMenu = new Menu("Menu de gestion de los compras");
		compraMenu.insertaOpcion("Nueva compra", 1);
		compraMenu.insertaOpcion("Insertar objeto en compra", 2);
		compraMenu.insertaOpcion("Cerrar compra", 99);
		
		//Mientras no se seleccione la opción de salir del menú, se continóa en el menú
		do {
			option = compraMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		} while(option!=99);
		
		//Al salir del bucle se cierra el menú
		compraMenu.cierra();
		
	}
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		String txt;
		switch(option) {
		
			case 1:
				
				success = compra.recibeParametrosNuevaCompra();
				
				if (!success) {
					txt = "Error al crear la compra. Introduzca los datos antes de procesar su compra.";
	
			        //Escribimos el mensaje por pantalla
			        Mensaje msg = new Mensaje();
			        msg.escribe(txt);
				}
				
				break;
				
			case 2:
				
				success = compra.recibeParametrosLineaCompra();
				
				if (!success) {
					txt = "Error al añadir el objeto a la compra. Introduzca los datos antes de procesar su compra.";
	
			        //Escribimos el mensaje por pantalla
			        Mensaje msg = new Mensaje();
			        msg.escribe(txt);
				}
								
			default:
				break;
			
		
		}
	}
}
