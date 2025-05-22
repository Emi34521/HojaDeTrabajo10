package uvg;

public class Vertex<E> {
    private E label;         // Nombre o identificador del vértice (por ejemplo: "Ciudad1")
    private boolean visited; // Indica si este vértice ha sido visitado (para recorridos como BFS, DFS, etc.)

    // Constructor
    public Vertex(E label) {
        this.label = label;
        this.visited = false; // Al crear el vértice, no ha sido visitado
    }

    // Devuelve la etiqueta o nombre del vértice
    public E label() {
        return label;
    }

    // Marca el vértice como visitado y devuelve su estado anterior
    public boolean visit() {
        boolean old = visited;
        visited = true;
        return old;
    }

    // Verifica si el vértice ya fue visitado
    public boolean isVisited() {
        return visited;
    }

    // Resetea el estado del vértice (no visitado)
    public void reset() {
        visited = false;
    }

    // Compara este vértice con otro (por etiqueta)
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            Vertex<?> other = (Vertex<?>) o;
            return label.equals(other.label);
        }
        return false;
    }

    // (Opcional) Para imprimir bonito el vértice
    @Override
    public String toString() {
        return label.toString();
    }
}

