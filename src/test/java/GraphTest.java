import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import uvg.Clima;
import uvg.Graph;

import java.io.*;

public class GraphTest {

    @Test
    public void testRutaMasCortaConSalida() {
        Graph<String> grafo = new Graph<>(true);
        grafo.addEdge("A", "B", 5, 10, 15, 20);
        grafo.addEdge("B", "C", 5, 10, 15, 20);
        grafo.addEdge("A", "C", 15, 20, 25, 30);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        grafo.rutaMasCorta("A", "C", Clima.NORMAL);

        System.setOut(originalOut);

        String resultado = salida.toString().trim();
        assertTrue(resultado.contains("Ruta más corta de A a C: [A, B, C] con distancia 10.0"));
    }

    @Test
    public void testGenerarMatrizDistancias() {
        Graph<String> grafo = new Graph<>(true);
        grafo.addEdge("A", "B", 2, 5, 5, 5);
        grafo.addEdge("B", "C", 3, 5, 5, 5);
        grafo.addEdge("A", "C", 10, 5, 5, 5);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        grafo.construirMatriz(Clima.NORMAL);

        System.setOut(originalOut);

        String resultado = salida.toString();
        assertTrue(resultado.contains("0.00")); // A a A
        assertTrue(resultado.contains("2.00")); // A a B
        assertTrue(resultado.contains("5.00")); // B a C
    }

    @Test
    public void testCentroDelGrafo() {
        Graph<String> grafo = new Graph<>(false);
        grafo.addEdge("X", "Y", 2, 2, 2, 2);
        grafo.addEdge("Y", "Z", 2, 2, 2, 2);
        grafo.addEdge("X", "Z", 5, 5, 5, 5);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        grafo.encontrarCentro(Clima.NORMAL);

        System.setOut(originalOut);

        String resultado = salida.toString();
        assertTrue(resultado.contains("Centro del grafo bajo clima NORMAL: Y"));
    }
}
