package graph;

import java.io.IOException;
import java.util.Scanner;

/**
 * MainGraphe is the exucitioner for the graph class, allowing interacion with the user
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * November 16, 2016
 */
public class MainGraph {

    //region Variables
    private static final int CONT = 0;   // Flags for stopping the lopp or starting
    private static final int STOP = 1;
    //region Variables

    //region Runtime
    /**
     *
     * @throws IOException when no file is found from specified path
     */
    public static void main(String[] args) throws IOException {
        // Local variable allocation
        final  Scanner scanner = new Scanner(System.in);

        int cont = CONT;
        int from = -1, to = -1;

        Pair cheapest;

        System.out.println("Bienvenido al optimizador de vuelos");

        Graph graph = new Graph("/Users/mauriciog/IdeaProjects/Estructura de Datos/src/graph/Conexiones.txt");

        // As long as user want to keep searching
        while (cont == CONT) {
            try {
                // Get a valid origin
                System.out.println("\nPara empezar escoja un lugar de salida, del 0 al 14");
                from = scanner.nextInt();
                while (from < 0 || from > 14) {
                    System.out.println("La salida deber ser un valor del 0 al 14");
                    from = scanner.nextInt();
                }
                System.out.println("Saliendo de " + graph.getCountryName(from));

                // And a valid destination
                System.out.println("Ahora escoja un destino, del 0 al 14");
                to = scanner.nextInt();
                while (to < 0 || to > 14) {
                    System.out.println("El destino deber ser un valor del 0 al 14");
                    to = scanner.nextInt();
                }
                System.out.println("LLegando a " + graph.getCountryName(to));

                // Verify if a search is even needed
                if (to == from) {
                    System.out.println("\nEl destino es el mismo que el lugar de salida, no hay necesidad de viajar!");
                } else {
                    // Get the cheapest route
                    cheapest = graph.findCheapest(from, to);

                    // And print it
                    System.out.println("\nViaje propuesto por la ruta mas barata:\n\t" + cheapest.key);
                    System.out.println("Precio de la ruta mas barata:\n\t " + cheapest.value);
                }

                // Ask user for continuation
                System.out.println("\nDesea buscar otra ruta?: (1 para no, 0 para si)");
                cont = scanner.nextInt();
                while (cont < 0 || cont > 1) {
                    System.out.println("0 para realizar otra busqueda, 1 para salir");
                    cont = scanner.nextInt();
                }

            } catch (Exception e) {
                // Check if user typed a non-number
                String s = scanner.next();

                // And if its a valid exception value
                switch (s) {
                    case "e":
                    case "exit":
                        // Allows early exit of program
                        cont = STOP;
                        break;
                    case "?":
                    case "help":
                        // If user need to see the countries list
                        graph.printCountries();
                        break;
                    default:
                        // Otherwise, user tried to input an invalid data
                        System.out.println("\nError al ingresar un dato, empieze de nuevo\n");
                        cont = CONT;
                        break;
                }
            }
        }
    }
    //endregion

}
