package uvg;

// Importación de clases necesarias
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase que representa un grafo genérico
public class Graph<V> {
    // Mapa que almacena los vértices del grafo
    private Map<V, Vertex<V>> vertices;
    // Lista que almacena las aristas del grafo
    private List<Edge<V>> edges;
    // Indica si el grafo es dirigido o no
    private boolean directed;

    // Constructor que inicializa el grafo
    public Graph(boolean directed) {
        this.directed = directed; // Establece si el grafo es dirigido
        vertices = new HashMap<>(); // Inicializa el mapa de vértices
        edges = new ArrayList<>(); // Inicializa la lista de aristas
    }

    // Método para agregar un vértice al grafo
    public void addVertex(V label) {
        // Solo agrega el vértice si no existe ya en el mapa
        if (!vertices.containsKey(label)) {
            vertices.put(label, new Vertex<>(label)); // Crea un nuevo vértice
        }
    }

    // Método para agregar una arista entre dos vértices
    public void addEdge(V from, V to, double normal, double lluvia, double nieve, double tormenta) {
        addVertex(from); // Asegura que el vértice de origen existe
        addVertex(to); // Asegura que el vértice de destino existe
        // Agrega una nueva arista con los pesos correspondientes
        edges.add(new Edge<>(from, to, normal, lluvia, nieve, tormenta));
    }

    // Método para construir una matriz de adyacencia basada en el clima actual
    public Map<V, Map<V, Double>> construirMatriz(Clima clima) {
        Map<V, Map<V, Double>> matriz = new HashMap<>(); // Mapa para almacenar la matriz de adyacencia

        // Inicializa la matriz con distancias infinitas
        for (V v : vertices.keySet()) {
            matriz.put(v, new HashMap<>());
            for (V w : vertices.keySet()) {
                if (v.equals(w)) {
                    matriz.get(v).put(w, 0.0); // Distancia a sí mismo es 0
                } else {
                    matriz.get(v).put(w, Double.POSITIVE_INFINITY); // Distancia a otros es infinita
                }
            }
        }

        // Llena la matriz con los pesos de las aristas
        for (Edge<V> e : edges) {
            double peso = e.getPeso(clima); // Obtiene el peso de la arista según el clima
            matriz.get(e.getFrom()).put(e.getTo(), peso); // Asigna el peso en la matriz
            if (!directed) {
                matriz.get(e.getTo()).put(e.getFrom(), peso); // Asigna el peso en la matriz para grafo no dirigido
            }
        }

        return matriz; // Retorna la matriz de adyacencia
    }

    // Método que implementa el algoritmo de Floyd-Warshall para encontrar las distancias más cortas
    public Map<V, Map<V, Double>> floydWarshall(Clima clima) {
        Map<V, Map<V, Double>> dist = construirMatriz(clima); // Construye la matriz de distancias

        // Aplica el algoritmo de Floyd-Warshall
        for (V k : vertices.keySet()) {
            for (V i : vertices.keySet()) {
                for (V j : vertices.keySet()) {
                    double ik = dist.get(i).get(k); // Distancia de i a k
                    double kj = dist.get(k).get(j); // Distancia de k a j
                    double ij = dist.get(i).get(j); // Distancia de i a j
                    if (ik + kj < ij) {
                        dist.get(i).put(j, ik + kj); // Actualiza la distancia si se encuentra una ruta más corta
                    }
                }
            }
        }

        return dist; // Retorna la matriz de distancias más cortas
    }

    // Método para encontrar la ruta más corta entre dos vértices
    public void rutaMasCorta(V origen, V destino, Clima clima) {
        Map<V, Map<V, Double>> distancias = floydWarshall(clima); // Obtiene las distancias más cortas
        double resultado = distancias.get(origen).get(destino); // Obtiene la distancia del origen al destino

        // Verifica si hay una ruta
        if (resultado == Double.POSITIVE_INFINITY) {
            System.out.println("No hay ruta desde " + origen + " a " + destino + " en clima " + clima);
        } else {
            System.out.println("La ruta más corta desde " + origen + " a " + destino + " en clima " + clima + " es de " + resultado + " unidades.");
        }
    }

    // Método para encontrar el centro del grafo
    public void encontrarCentro(Clima clima) {
        List<V> nodos = new ArrayList<>(vertices.keySet()); // Lista de nodos
        int n = nodos.size(); // Número de nodos
        double[][] dist = new double[n][n]; // Matriz de distancias

        // Inicializa la matriz de distancias
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Double.POSITIVE_INFINITY); // Llena con infinito
            dist[i][i] = 0; // Distancia a sí mismo es 0
        }

        // Llena la matriz con las distancias de las aristas
        for (Edge<V> edge : edges) {
            int fromIndex = nodos.indexOf(edge.getFrom()); // Índice del vértice de origen
            int toIndex = nodos.indexOf(edge.getTo()); // Índice del vértice de destino
            double peso = edge.getPeso(clima); // Obtiene el peso de la arista
            dist[fromIndex][toIndex] = peso; // Asigna el peso en la matriz

            if (!directed) {
                dist[toIndex][fromIndex] = peso; // Asigna el peso en la matriz para grafo no dirigido
            }
        }

        // Algoritmo de Floyd-Warshall para hallar todas las rutas más cortas
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j]; // Actualiza la distancia si se encuentra una ruta más corta
                    }
                }
            }
        }

        // Buscar el vértice con menor excentricidad (máxima distancia más corta a otro)
        double minEccentricity = Double.POSITIVE_INFINITY; // Inicializa la excentricidad mínima
        V centro = null; // Inicializa el centro del grafo

        // Encuentra el vértice con la menor excentricidad
        for (int i = 0; i < n; i++) {
            double maxDist = 0; // Inicializa la máxima distancia para el nodo actual
            for (int j = 0; j < n; j++) {
                maxDist = Math.max(maxDist, dist[i][j]); // Encuentra la máxima distancia desde el nodo actual
            }
            if (maxDist < minEccentricity) {
                minEccentricity = maxDist; // Actualiza la excentricidad mínima
                centro = nodos.get(i); // Actualiza el centro del grafo
            }
        }

        // Imprime el resultado
        if (centro != null) {
            System.out.println("Centro del grafo bajo clima " + clima + ": " + centro +
                    " (máxima distancia más corta: " + minEccentricity + ")");
        } else {
            System.out.println("No se pudo determinar el centro del grafo.");
        }
    }
}
