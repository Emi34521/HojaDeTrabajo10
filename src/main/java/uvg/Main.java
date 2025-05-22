package uvg;

// Importación de clases necesarias para la lectura de archivos y entrada de usuario
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Clase principal que contiene el método main
public class Main {
    public static void main(String[] args) {
        // Crea un grafo dirigido de tipo String
        Graph<String> grafo = new Graph<>(true);
        Scanner scanner = new Scanner(System.in); // Inicializa el escáner para la entrada del usuario

        // Leer archivo logistica.txt y construir el grafo
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/logistica.txt"))) {
            String linea;
            // Lee el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                // Divide la línea en partes usando la coma como delimitador
                String[] partes = linea.split(",");
                // Verifica que la línea tenga el número correcto de partes
                if (partes.length == 6) {
                    // Extrae y limpia los datos de la línea
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    double normal = Double.parseDouble(partes[2].trim());
                    double lluvia = Double.parseDouble(partes[3].trim());
                    double nieve = Double.parseDouble(partes[4].trim());
                    double tormenta = Double.parseDouble(partes[5].trim());

                    // Agrega una arista al grafo con los datos extraídos
                    grafo.addEdge(origen, destino, normal, lluvia, nieve, tormenta);
                }
            }
        } catch (IOException e) {
            // Manejo de excepciones en caso de error al leer el archivo
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return; // Termina el programa si hay un error
        }

        // Variable para controlar el bucle del menú
        boolean continuar = true;

        // Bucle principal del menú
        while (continuar) {
            // Muestra el menú de opciones al usuario
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Buscar ruta más corta");
            System.out.println("2. Calcular centro del grafo");
            System.out.println("3. Salir");
            System.out.print("Elige una opción (1-3): ");

            String opcion = scanner.nextLine().trim(); // Lee la opción elegida por el usuario

            // Manejo de las opciones del menú
            switch (opcion) {
                case "1":
                    // Opción para buscar la ruta más corta
                    System.out.print("Ciudad de origen: ");
                    String origen = scanner.nextLine().trim(); // Lee la ciudad de origen

                    System.out.print("Ciudad de destino: ");
                    String destino = scanner.nextLine().trim(); // Lee la ciudad de destino

                    System.out.print("Clima (NORMAL, LLUVIA, NIEVE, TORMENTA): ");
                    String climaStrRuta = scanner.nextLine().trim().toUpperCase(); // Lee el clima y lo convierte a mayúsculas

                    try {
                        // Intenta convertir la entrada del clima a un valor del enum Clima
                        Clima climaRuta = Clima.valueOf(climaStrRuta);
                        // Llama al método para encontrar la ruta más corta
                        grafo.rutaMasCorta(origen, destino, climaRuta);
                    } catch (IllegalArgumentException e) {
                        // Manejo de excepciones si el clima no es válido
                        System.out.println("Clima no válido. Usa: NORMAL, LLUVIA, NIEVE o TORMENTA.");
                    }
                    break;

                case "2":
                    // Opción para calcular el centro del grafo
                    System.out.print("Clima para calcular el centro (NORMAL, LLUVIA, NIEVE, TORMENTA): ");
                    String climaStrCentro = scanner.nextLine().trim().toUpperCase(); // Lee el clima para el centro

                    try {
                        // Intenta convertir la entrada del clima a un valor del enum Clima
                        Clima climaCentro = Clima.valueOf(climaStrCentro);
                        // Llama al método para encontrar el centro del grafo
                        grafo.encontrarCentro(climaCentro);
                    } catch (IllegalArgumentException e) {
                        // Manejo de excepciones si el clima no es válido
                        System.out.println("Clima no válido. Usa: NORMAL, LLUVIA, NIEVE o TORMENTA.");
                    }
                    break;

                case "3":
                    // Opción para salir del programa
                    continuar = false; // Cambia la variable para salir del bucle
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    // Manejo de opciones no válidas
                    System.out.println("Opción no válida. Intenta con 1, 2 o 3.");
            }
        }

        scanner.close(); // Cierra el escáner al finalizar
    }
}
