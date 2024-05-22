/** 
 * @author Sebastian Garcia
 * @version 1.0
*/

package uvg;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.util.*;

/**
 * Clase principal de la aplicación.
 */
public class App 
{
    /**
     * Objeto de grafo que contiene la información de las ciudades y las aristas entre ellas.
     */
    private static Grafo grafo = new Grafo();

    /**
     * Carga un grafo desde un archivo de texto.
     * @param archivo El nombre del archivo de texto.
     */
    private static void CargarGrafo(String archivo){
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\sebas\\OneDrive\\Escritorio\\Github\\EstructuraDeDatos\\HojaDeTrabajo10\\hdt10\\src\\main\\java\\uvg\\"+archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                String ciudad1 = partes[0];
                String ciudad2 = partes[1];
                int km = Integer.parseInt(partes[2]);

                grafo.agregarArista(ciudad1, ciudad2, km);
            }
            System.out.println("Grafo cargado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Interrumpe el tráfico entre dos ciudades.
     * @param sc El objeto Scanner utilizado para leer la entrada del usuario.
     */
    private static void interrumpirTrafico(Scanner sc) {
        grafo.inicializarMatrices(); // Asegurar que las matrices estén inicializadas
        System.out.print("Ingrese la primera ciudad: ");
        String ciudad1 = sc.next();
        System.out.print("Ingrese la segunda ciudad: ");
        String ciudad2 = sc.next();
        grafo.interrupcionDeTrafico(ciudad1, ciudad2);
        System.out.println("Se interrumpió el tráfico entre " + ciudad1 + " y " + ciudad2);
    }

    /**
     * Establece una conexión entre dos ciudades.
     * @param sc El objeto Scanner utilizado para leer la entrada del usuario.
     */
    private static void establecerConexion(Scanner sc) {
        grafo.inicializarMatrices(); // Asegurar que las matrices estén inicializadas
        System.out.print("Ingrese la primera ciudad: ");
        String ciudad1 = sc.next();
        System.out.print("Ingrese la segunda ciudad: ");
        String ciudad2 = sc.next();
        System.out.print("Ingrese la distancia entre las ciudades: ");
        int distancia = sc.nextInt();
        grafo.establecerConexion(ciudad1, ciudad2, distancia);
        System.out.println("Se estableció una conexión entre " + ciudad1 + " y " + ciudad2 + " con una distancia de " + distancia + " km");
    }

    /**
     * Método principal de la aplicación. Permite cargar un grafo, interactuar con él y salir.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Bandera para controlar el bucle while principal
        boolean bandera = true;
        
        // Cargar el grafo desde el archivo guategrafo.txt
        CargarGrafo("guategrafo.txt");
        grafo.imprimirGrafo();
        
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner sc = new Scanner(System.in);
        
        // Bucle principal
        while (bandera) {
            // Imprimir opciones disponibles
            System.out.println();
            System.out.println();
            System.out.println("Grafo cargado.......");
            grafo.imprimirGrafo();
            System.out.println("");
            System.out.println("");
            System.out.println("Por favor ingrese la opcion a realizar:");
            System.out.println("1. Ruta más corta entre dos ciudades");
            System.out.println("2. Ciudad en el centro del grafo");
            System.out.println("3. Interrumpir tráfico entre ciudades");
            System.out.println("4. Establecer conexión entre ciudades");
            System.out.println("5. Salir");
            
            // Leer la opción seleccionada
            int opcion = sc.nextInt();
            
            // Realizar la acción correspondiente a la opción seleccionada
            switch (opcion) {
                case 1:
                    // Calcular rutas más cortas y centro del grafo
                    grafo.inicializarMatrices();
                    grafo.floydWarshall();
                    
                    // Leer ciudades de inicio y fin
                    System.out.println("Ingrese la ciudad de inicio: ");
                    String inicio = sc.next();
                    System.out.println("Ingrese la ciudad final: ");
                    String fin = sc.next();
                    
                    // Imprimir la ruta más corta entre las ciudades
                    System.out.println(inicio + " a " + fin + ": ");
                    grafo.rutaMasCorta(inicio, fin);
                    break;
                
                case 2: 
                    // Imprimir la ciudad en el centro del grafo
                    String ciudadCentro = grafo.ciudadCentroDelGrafo();
                    System.out.println("La ciudad en el centro del grafo es: " + ciudadCentro);
                    break;
                
                case 3: 
                    // Interrumpir tráfico entre ciudades
                    interrumpirTrafico(sc);
                    break;
                
                case 4:
                    // Establecer conexión entre ciudades
                    establecerConexion(sc);
                    break;
                
                case 5:
                    // Salir del programa
                    bandera = false;
                    System.out.println("Adios");
                    break;
            }
        }
    }

}


