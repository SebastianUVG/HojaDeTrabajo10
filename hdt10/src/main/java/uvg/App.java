package uvg;

import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    private static Grafo grafo = new Grafo();

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
    public static void main( String[] args )
    {
        boolean bandera = true;
        CargarGrafo("guategrafo.txt");
                    grafo.imprimirGrafo();
        Scanner sc = new Scanner(System.in);
        while (bandera){
            System.out.println("Por favor ingrese la opciona a realizar:");
            System.out.println("1. Ciudad de inicio a ciudad final");
            System.out.println("2. Ciudad del centro del grafo");
            System.out.println("3. Modificar Grafo");
            System.out.println("4. Salir");
            int opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    grafo.inicializarMatrices();
                    grafo.floydWarshall();
                    System.out.println("Ingrese la ciudad de inicio: ");
                    String inicio = sc.next();
                    System.out.println("Ingrese la ciudad final: ");
                    String fin = sc.next();
                    System.out.println(inicio + " a " + fin + ": ");
                    grafo.rutaMasCorta(inicio, fin);

                    break;

                case 2: 
                    break;

                case 3: 
                    break;

                case 4:
                    bandera = false;
                    System.out.println("Adios");
                    break;
            
            
            }
        }

       


    }
}
