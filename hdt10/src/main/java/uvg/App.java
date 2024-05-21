package uvg;

import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        boolean bandera = true;

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
