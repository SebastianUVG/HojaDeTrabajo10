/** 
 * @author Sebastian Garcia
 * @version 1.0
*/


/**
 * Esta clase representa un grafo no dirigido. Cada ciudad está representada por un String,
 * y las aristas están representadas por objetos Arista.
 */
package uvg;

import java.io.*;
import java.util.*;

public class Grafo {

    /**
     * Un mapa que mapea cada ciudad a una lista de aristas de salida.
     */
    Map<String, List<Arista>> adjList;

    /**
     * Un mapa que mapea cada ciudad a su índice dentro del arreglo de ciudades.
     */
    private Map<String, Integer> indices;

    /**
     * Un arreglo de Strings que almacena el nombre de cada ciudad en el grafo.
     */
    private String[] ciudades;

    /**
     * Un arreglo de enteros que almacena las distancias entre cada par de ciudades.
     */
    private int[][] distancias;

    /**
     * Un arreglo de enteros que almacena el camino más corto entre cada par de ciudades.
     */
    private int[][] caminos;

    /**
     * Construye un grafo vacío.
     */
    public Grafo() {
        this.adjList = new HashMap<>();
        this.indices = new HashMap<>();
    }

    /**
     * Agrega una arista al grafo. Si la ciudad no existe en el grafo, se agrega también.
     * @param ciudad1 La ciudad de origen de la arista.
     * @param ciudad2 La ciudad de destino de la arista.
     * @param km La distancia entre las ciudades.
     */
    public void agregarArista(String ciudad1, String ciudad2, int km) {
        this.adjList.putIfAbsent(ciudad1, new ArrayList<>());
        this.adjList.putIfAbsent(ciudad2, new ArrayList<>());
    
        // Verificar si la arista ya existe
        for (Arista arista : adjList.get(ciudad1)) {
            if (arista.destino.equals(ciudad2)) {
                // Si la arista ya existe, actualizar la distancia
                arista.km = km;
                return; // Salir del método
            }
        }
    
        // Si la arista no existe, agregarla
        this.adjList.get(ciudad1).add(new Arista(ciudad2, km));
        this.adjList.get(ciudad2).add(new Arista(ciudad1, km)); // Si el grafo es no dirigido
    }

    /**
     * Imprime el grafo en la consola.
     */
    public void imprimirGrafo() {
        for (String ciudad : adjList.keySet()) {
            System.out.print(ciudad + " -> ");
            for (Arista arista : adjList.get(ciudad)) {
                System.out.print(arista.destino + "(" + arista.km + " km), ");
            }
            System.out.println();
        }
    }

    /**
     * Inicializa las matrices de distancias y caminos.
     * También inicializa el mapa de índices de ciudades.
     */
    public void inicializarMatrices() {
        // Obtener el número de ciudades
        int n = adjList.size();

        // Inicializar el arreglo de ciudades
        ciudades = adjList.keySet().toArray(new String[n]);

        // Inicializar la matriz de distancias
        distancias = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = Integer.MAX_VALUE / 2;
                }
            }
        }

        // Inicializar la matriz de caminos
        caminos = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                caminos[i][j] = j;
            }
        }

        // Limpiar el mapa de índices
        indices.clear();

        // Asignar el índice de cada ciudad en el mapa de índices
        for (int i = 0; i < n; i++) {
            indices.put(ciudades[i], i);
        }

        // Llenar la matriz de distancias con los valores del grafo
        for (String ciudad : adjList.keySet()) {
            int i = indices.get(ciudad);
            for (Arista arista : adjList.get(ciudad)) {
                int j = indices.get(arista.destino);
                distancias[i][j] = arista.km;
            }
        }
    }
    /**
     * Calcula las distancias y caminos más cortos entre todas las ciudades del grafo utilizando el algoritmo de Floyd-Warshall.
     */
    public void floydWarshall() {
        int n = adjList.size();
        // Para cada ciudad como ciudad central
        for (int k = 0; k < n; k++) {
            // Para cada par de ciudades
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Si la distancia desde i hasta j pasando por k es más corta
                    if (distancias[i][j] > distancias[i][k] + distancias[k][j]) {
                        // Actualizar la distancia y la ciudad a través de la cual se debe ir
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        caminos[i][j] = caminos[i][k];
                    }
                }
            }
        }
    }

    /**
     * Calcula la ruta más corta entre dos ciudades del grafo utilizando la matriz de distancias y caminos calculada por el algoritmo de Floyd-Warshall.
     * @param inicio La ciudad de origen.
     * @param fin La ciudad de destino.
     */
    public void rutaMasCorta(String inicio, String fin) {
        // Verificar si las ciudades existen en el grafo
        if (!indices.containsKey(inicio) || !indices.containsKey(fin)) {
            System.out.println("Una o ambas ciudades no existen en el grafo.");
            return;
        }

        int i = indices.get(inicio);
        int j = indices.get(fin);

        // Si no hay ruta posible entre las ciudades
        if (distancias[i][j] == Integer.MAX_VALUE / 2) {
            System.out.println("No hay ruta posible de " + inicio + " a " + fin);
            return;
        }

        LinkedList<String> ruta = new LinkedList<>();
        // Recorrer las ciudades a través de las cuales se debe ir
        while (i != j) {
            ruta.add(ciudades[i]);
            i = caminos[i][j];
        }
        // Agregar la ciudad de destino a la ruta
        ruta.add(ciudades[j]);

        // Imprimir la ruta y la distancia total
        System.out.println("Ruta más corta de " + inicio + " a " + fin + ": " + ruta + " con una distancia de " + distancias[indices.get(inicio)][indices.get(fin)] + " km");
    }
    // Clase interna Arista
    static class Arista {
        String destino;
        int km;

        public Arista(String destino, int km) {
            this.destino = destino;
            this.km = km;
        }
    }
   
    /**
     * Encuentra la ciudad con la menor suma de distancias a todas las demás ciudades.
     * @return La ciudad con la menor suma de distancias.
     */
    public String ciudadCentroDelGrafo() {
        // Si el grafo está vacío, devolver un mensaje de error
        if (adjList.isEmpty()) {
            return "El grafo está vacío";
        }
    
        // Obtener el número de ciudades
        int n = adjList.size();
    
        // Inicializar la matriz de distancias y la matriz de caminos
        int[][] distancias = new int[n][n];
        int[][] caminos = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE / 2);
            Arrays.fill(caminos[i], -1);
            distancias[i][i] = 0;
        }
    
        // Llenar la matriz de distancias con los valores del grafo
        for (String ciudad : adjList.keySet()) {
            int i = indices.get(ciudad);
            for (Arista arista : adjList.get(ciudad)) {
                int j = indices.get(arista.destino);
                distancias[i][j] = arista.km;
                caminos[i][j] = j;
            }
        }
    
        // Ejecutar el algoritmo de Floyd-Warshall
        // para calcular las distancias y caminos más cortos
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE &&
                            distancias[i][j] > distancias[i][k] + distancias[k][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        caminos[i][j] = caminos[i][k];
                    }
                }
            }
        }
    
        // Encontrar la ciudad con la menor suma de distancias
        int minSuma = Integer.MAX_VALUE;
        String ciudadCentro = null;
        for (int i = 0; i < n; i++) {
            int sumaDistancias = 0;
            for (int j = 0; j < n; j++) {
                sumaDistancias += distancias[i][j];
            }
            if (sumaDistancias < minSuma) {
                minSuma = sumaDistancias;
                ciudadCentro = ciudades[i];
            }
        }
    
        // Devolver la ciudad con la menor suma de distancias
        return ciudadCentro;
    }


    /**
     * Interrumpe el tráfico entre las ciudades indicadas.
     * @param ciudad1 La primera ciudad.
     * @param ciudad2 La segunda ciudad.
     */
    public void interrupcionDeTrafico(String ciudad1, String ciudad2) {
        // Verificar si la conexión entre las ciudades existe
        if (adjList.containsKey(ciudad1) && adjList.containsKey(ciudad2)) {
            // Eliminar la arista correspondiente en ambas direcciones
            adjList.get(ciudad1).removeIf(arista -> arista.destino.equals(ciudad2));
            adjList.get(ciudad2).removeIf(arista -> arista.destino.equals(ciudad1));
        } else {
            // Si alguna de las ciudades no existe en el grafo, mostrar mensaje de error
            System.out.println("Al menos una de las ciudades no existe en el grafo.");
        }
        // Recalcular rutas más cortas y centro del grafo
        floydWarshall();
    }

    /**
     * Establece una conexión entre las ciudades indicadas.
     * @param ciudad1 La primera ciudad.
     * @param ciudad2 La segunda ciudad.
     * @param distancia La distancia entre las ciudades.
     */
    public void establecerConexion(String ciudad1, String ciudad2, int distancia) {
        // Verificar si las ciudades ya están en el grafo
        if (adjList.containsKey(ciudad1) && adjList.containsKey(ciudad2)) {
            // Agregar la nueva arista en ambas direcciones
            agregarArista(ciudad1, ciudad2, distancia);
            agregarArista(ciudad2, ciudad1, distancia);
        } else {
            // Si alguna de las ciudades no existe en el grafo, mostrar mensaje de error
            System.out.println("Al menos una de las ciudades no existe en el grafo.");
        }
        // Recalcular rutas más cortas y centro del grafo
        floydWarshall();
    }

    
    public int[][] obtenerDistancias() {
        return distancias;
    }

    public Map<String, Integer> obtenerIndices() {
        return indices;
    }

}
