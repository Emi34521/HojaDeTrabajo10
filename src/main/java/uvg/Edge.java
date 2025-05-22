package uvg;

import java.util.EnumMap;
import java.util.Map;


public class Edge<V> {
    private V from;  // Ciudad origen
    private V to;    // Ciudad destino

    // Mapa que guarda un peso diferente para cada clima
    private Map<Clima, Double> pesos;

    // Constructor: recibe las ciudades y los 4 pesos correspondientes
    public Edge(V from, V to, double normal, double lluvia, double nieve, double tormenta) {
        this.from = from;
        this.to = to;
        this.pesos = new EnumMap<>(Clima.class);
        pesos.put(Clima.NORMAL, normal);
        pesos.put(Clima.LLUVIA, lluvia);
        pesos.put(Clima.NIEVE, nieve);
        pesos.put(Clima.TORMENTA, tormenta);
    }

    // Devuelve la ciudad de origen
    public V getFrom() {
        return from;
    }

    // Devuelve la ciudad de destino
    public V getTo() {
        return to;
    }

    // Devuelve el peso asociado al clima indicado
    public double getPeso(Clima clima) {
        return pesos.getOrDefault(clima, Double.POSITIVE_INFINITY);
    }

    // (Opcional) para depuración o impresión
    @Override
    public String toString() {
        return from + " → " + to + " " + pesos.toString();
    }
}
