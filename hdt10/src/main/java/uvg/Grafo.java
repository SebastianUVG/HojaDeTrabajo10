package uvg;
import java.io.*;
import java.util.*;
public class Grafo {

    private Map<String, List<Arista>> adjList;
    private Map<String, Integer> indices;
    private String[] ciudades;
    private int[][] distancias;
    private int[][] caminos;

    public Grafo() {
        this.adjList = new HashMap<>();
        this.indices = new HashMap<>();
    }

    public void agregarArista(String ciudad1, String ciudad2, int km) {
        this.adjList.putIfAbsent(ciudad1, new ArrayList<>());
        this.adjList.putIfAbsent(ciudad2, new ArrayList<>());
        this.adjList.get(ciudad1).add(new Arista(ciudad2, km));
        this.adjList.get(ciudad2).add(new Arista(ciudad1, km)); // Si el grafo es no dirigido
    }

    public void imprimirGrafo() {
        for (String ciudad : adjList.keySet()) {
            System.out.print(ciudad + " -> ");
            for (Arista arista : adjList.get(ciudad)) {
                System.out.print(arista.destino + "(" + arista.km + " km), ");
            }
            System.out.println();
        }
    }

    public void inicializarMatrices() {
        int n = adjList.size();
        ciudades = adjList.keySet().toArray(new String[n]);
        distancias = new int[n][n];
        caminos = new int[n][n];

        for (int i = 0; i < n; i++) {
            indices.put(ciudades[i], i);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = Integer.MAX_VALUE / 2;
                }
                caminos[i][j] = j;
            }
        }

        for (String ciudad : adjList.keySet()) {
            int i = indices.get(ciudad);
            for (Arista arista : adjList.get(ciudad)) {
                int j = indices.get(arista.destino);
                distancias[i][j] = arista.km;
            }
        }
    }

    public void floydWarshall() {
        int n = adjList.size();
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][j] > distancias[i][k] + distancias[k][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        caminos[i][j] = caminos[i][k];
                    }
                }
            }
        }
    }

    public void rutaMasCorta(String inicio, String fin) {
        if (!indices.containsKey(inicio) || !indices.containsKey(fin)) {
            System.out.println("Una o ambas ciudades no existen en el grafo.");
            return;
        }

        int i = indices.get(inicio);
        int j = indices.get(fin);

        if (distancias[i][j] == Integer.MAX_VALUE / 2) {
            System.out.println("No hay ruta posible de " + inicio + " a " + fin);
            return;
        }

        LinkedList<String> ruta = new LinkedList<>();
        while (i != j) {
            ruta.add(ciudades[i]);
            i = caminos[i][j];
        }
        ruta.add(ciudades[j]);

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
   
}
