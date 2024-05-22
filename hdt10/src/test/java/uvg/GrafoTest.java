package uvg;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
public class GrafoTest {
    private Grafo grafo;

    @Test
    public void testRutaMasCorta() {
        // Crear un grafo de ejemplo
        Grafo grafo = new Grafo();
        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("B", "C", 15);
        grafo.agregarArista("C", "D", 20);
        grafo.inicializarMatrices();
        grafo.floydWarshall();

        // Verificar una ruta válida
        try {
            grafo.rutaMasCorta("A", "D");
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción");
        }

        // Verificar una ruta inexistente
        try {
            grafo.rutaMasCorta("A", "E");
            
        } catch (Exception e) {
            // Se espera que lance una excepción
            fail("Se esperaba una excepción");
        }
    }

    @Test
    public void testCiudadCentroDelGrafo() {
        // Crear un grafo de ejemplo
        Grafo grafo = new Grafo();
        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("B", "C", 15);
        grafo.agregarArista("C", "D", 20);
        grafo.inicializarMatrices();
        grafo.floydWarshall();

        // Verificar la ciudad centro
        assertEquals("B", grafo.ciudadCentroDelGrafo());
    }


}
