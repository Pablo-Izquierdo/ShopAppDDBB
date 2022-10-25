package tiendaForja.persistenceLayer;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para obtener la conexión al servidor SQL Server.
 * 
 * @author Pablo Sánchez Barreiro
 * @coauthor Diego García Saiz
 * 
 * @modifiedBy Carlos Jimeno, Sara Grela, Pablo Izquierdo y Esmeralda Madrazo
 * @lastModified 25/03/2020
 */
public class SqlServerConnectionManager {
	
	
	protected static Connection connection; //atributo estático que almacena la conexión
	
	/*
	 * Atributos de acceso a la Base de Datos.
	 * 
	 * ATENCIÓN: CAMBIAR CON LOS VUESTROS
	 */
	protected static String dbName = "TandF_TOOMNOOK"; //Nombre de la base de datos
	protected static String user = "userCursos"; //Usuario de la base de datos
	protected static String pass = "passCursos"; //Contraseña del usuario de la base de datos
	protected static String ipPort = "127.0.0.1:1433"; //IP y puerto de acceso a la base de datos
	
	/**
	 * Método estático para obtener la conexión.
	 * Si el atributo "connection" no ha sido inicializado (null)
	 * se inicializa creando una nueva conexión con el servidor
	 * y la base de datos en cuestión
	 * @return
	 */
	public static Connection getConnection() {
		
		if (connection == null) { //Si la conexión no fue inicializada, lo hacemos ahora
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //comprueba que el driver de sql server está instalado
				connection = DriverManager.getConnection("jdbc:sqlserver://"+ipPort, user, pass); // conexión a la BD
				SqlServerConnectionManager.executeSqlStatement("use "+dbName, "Error al hacer 'use "+dbName+"'"); //ejecutar "use" para usar la base de datos
			} catch (SQLException e) {
				System.out.println("Database connection not available");
				System.out.println("Error Code =" + e.getErrorCode());
				System.out.println("Error State = "+e.getSQLState());
				System.out.println(e);
			} catch (ClassNotFoundException e) {
				System.out.println("SQLServer connector driver not found");
			}
		}
		return connection; //retorna la conexión
	}
	
	/**
	 * Método estático para ejecutar operaciones SQL y manejar los errores.
	 * Recibe como parámetros la instrucción (statement) en formato tipo String
	 * y un mensaje de error personalizado para mostrarlo en caso de que la
	 * instrucción no funcione correctamente.
	 * 
	 * IMPORTANTE: este método sólo puede ser llamado para operaciones de INSERT, UPDATE
	 * y DELETE. No debe usarse para realizar SELECTs ni llamadas a PROCEDIMIENTO, las ejecuciones
	 * de ese tipo de operaciones tendrán que implementarse en sus respectivos métodos.
	 * 
	 * @param stringStatement
	 * @param exceptionMsg
	 */
	public static boolean executeSqlStatement(String stringStatement, String exceptionMsg) {
		Connection con = SqlServerConnectionManager.getConnection(); //conectamos con la base de datos
		try {
			System.out.println("Ejecutando: "+stringStatement);
			Statement stm = con.createStatement(); //nuevo statement
			stm.execute(stringStatement); //ejecuta el statement dado como parámetro
			stm.close(); //cierra el statement
			
			
		} catch (SQLException e) {
			System.out.println("Error Code =" + e.getErrorCode());
			System.out.println("Error State = "+e.getSQLState());
			System.out.println("Error Messange = "+e.getMessage());
			System.out.println("User personalized error message: '"+exceptionMsg+"'"); //si se produce una exceción del SQL, se muestra el error personalizado del usuario
			e.printStackTrace();
			return false; //Si se llega hasta aquí, algo falló en la ejecución. Retornamos false.
		} 
		
		return true; //si se ha llegado hasta aquí es porque la ejecución ha sido correcta
	}
	
	

}
