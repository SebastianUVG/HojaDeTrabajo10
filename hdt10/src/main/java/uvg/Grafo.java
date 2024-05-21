package uvg;
import java.io.*;
import java.util.*;
public class Grafo {

    private Map<String, List<Arista>> adjList;
    
    public Grafo(){
        this.adjList = new HashMap<>();
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

    static class Arista {
        String destino;
        int km;

        public Arista(String destino, int km) {
            this.destino = destino;
            this.km = km;
        }
    }
   
}
