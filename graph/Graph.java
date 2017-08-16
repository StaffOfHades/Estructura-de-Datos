package graph;

import java.io.*;

/**
 * Graph object, for reading & hold the data, as well as for calculatling the shortes route
 * from point A to point B
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * November 16, 2016
 */
class Graph {

    //region Global
	private static final int SIZE = 15;

    private boolean[] used;         // to avoid route overlap
    private int[][] connections;
    private int[][] copy;           // to avoid permanent modifcation of data

    private String[] countries;
    //endregion


    //region Constructor

    /**
     *  Only construct for class graph, which pulls all the data neccesary to create the graph
     *  from the specefifed file
     * @param path of the file to get data from
     * @throws IOException
     */
    Graph(String path) throws IOException {
        // Allocate all global & local variables
        connections = new int[SIZE][SIZE];
        copy = new int[SIZE][SIZE];
        countries = new String[SIZE];
        used = new boolean[SIZE];

        int i, j;
        String line;
        String[] array;


        // Ready file reading
        FileReader input = new FileReader(new File(path));
        BufferedReader reader = new BufferedReader(input);

        // Read lines
        while ( (line = reader.readLine()) != null) {
            //System.out.println("Found line: " + line);

            // Separate connections && find appropriate index for it
            array = line.split(",");
            i = findString( array[0].trim() );
            j = findString( array[1].trim() );

            // Save it in the proper space for the array
            connections[i][j] = Integer.parseInt( array[2].trim() );
            connections[j][i] = Integer.parseInt( array[2].trim() );

            //System.out.println("First country has index of: " + i);
            //System.out.println("Second country has index of: " + j);
        }

    }
    //endregion

    //region Public
    public String getCountryName(int index) {
        return countries[index];
    }


    public void printCountries() {
        System.out.println();
        for (int i = 0; i < countries.length; i++) {
            System.out.println("Country " + i + ": " + countries[i]);
        }
    }
    //endregion

    //region Local

    /**
     *  Parses string values of countries into appropiate index to make the search
     *
     * @param from country name
     * @param to country name
     * @return cheapest route & price
     */
    Pair findCheapest(String from, String to) {
        final int i = findString( from.trim() );
        final int j = findString( to.trim() );

        //System.out.println("\nFrom " + from + ": " + i + ", to " + to + ": " + j);

        return findCheapest(i, j);
    }

    /**
     *  Searches from top to bottom the cheapest route
     *
     * @param from country name
     * @param to country name
     * @return cheapest route & price
     */
    Pair findCheapest(int from, int to) {
        // Allocate local variables
        Pair cheapest = new Pair("", Integer.MAX_VALUE);
        Pair price;

        // Copy data so it can be safely modified
        copy();

        //System.out.println("Begging search from " + from + " to " + to);

        // Search for a route from start location
        for (int j = 0; j < SIZE; j++) {
            // From cannot be used again
            used[from] = true;

            // As long as there exists a path
            if (copy[from][j] > 0) {
                //System.out.println("Found connection from " + from + " to " + j + ": " + copy[from][j]);

                // The end location cannot be used again
                used[j] = true;

                // Search recursively
                price = findPath(from, j, to);

                // As long as a valid route was found
                if (price.value > 0) {

                    // Add the value total
                    price.value += copy[from][j];

                    // And add final parts of the path
                    if (price.key.isEmpty()) {
                        price.key = countries[from] + " -> " + countries[to] + " -> " + countries[from];
                    } else {
                        price.key = countries[from] + " -> " + countries[j] + " -> " + price.key
                                + " -> " + countries[j] + " -> " + countries[from];
                    }


                    //System.out.println("Final price from " + from + " to " + to + ": " + price.value);
                    //System.out.println("Final route from " + from + " to " + to + ": " + price.key);

                    // Save the cheapest route between the two
                    cheapest = price.value < cheapest.value ? price : cheapest;
                }
            }
        }

        // If there is no connection between the nodes, clear any remaining values
        if (cheapest.value == Integer.MAX_VALUE || cheapest.value == 0) {
            //System.out.println("Found no connection from " + from + " to " + to);
            cheapest = new Pair();
        }

        return cheapest;
    }
    //endregion

    //region Private

    /**
     *  Local search for the index of a string value.
     *  If a value exists, return its index.
     *  Otherwsie, asing a new index to it.
     *
     * @param string to search for
     * @return index of string
     */
    private int findString(String string) {
        int index = 0;

        // Look only in spaces already occupoed
        while (countries[index] != null) {

            // And if a match is found, return its index
            if ( countries[index].equals(string) ) {
                return index;
            }

            index++;
        }

        // If space is empty, assing the string
        if (countries[index] == null) {
            countries[index] = string;
        }

        return index;
    }


    /**
     *
     * @param start index of route start
     * @param to index of current point of origin
     * @param end index of route end
     * @return best route from origin to end
     */
    private Pair findPath(int start, int to, int end) {
        // If a direct route is available, return it & reset part of the search
        if (copy[start][end] > 0) {
            // Reset route options
            unused();

            final int temp = copy[start][end];
            copy[start][end] = 0;
            //System.out.println("Direct connection from " + start + " to " + end + ": " + temp);

            return new Pair("", temp);
        }

        if (copy[to][end] > 0) {
            // Reset route options
            unused();

            final int temp = copy[to][end];
            copy[to][end] = 0;
            //System.out.println("Direct connection from " + to + " to " + end + ": " + temp);

            return new Pair(countries[end], temp);
        }

        // Allocate local variables
        Pair price = new Pair();

        // Search for a route from current point of origin
        for (int j = 0; j < SIZE; j++) {

            // Make sure a route exists, and the end node is not repeated
            if ( copy[to][j] > 0 && !used[j] ) {
                //System.out.println("Found connection from " + to + " to " + j + ": " + copy[to][j]);

                // The end node cannot be used again
                used[j] = true;

                // Find route
                price = findPath(to, j, end);

                // If route exists
                if (price.value > 0) {

                    // Add price
                    price.value += copy[to][j];

                    // And current route path
                    price.key = countries[j] + " -> " + price.key + " -> " + countries[j];

                    //System.out.println("Price from " + j + " to " + end + ": " + price.value);
                    //System.out.println("Route from " + j + " to " + end + ": " + price.key);

                }

                // Return route found
                return price;
            }
        }

        // Backup in case no route is found
        return price;
    }

    /**
     * Copy data in a new matrix so it can be modifies safely
     */
    private void copy() {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(connections[i], 0, copy[i], 0, SIZE);
        }
    }

    /**
     * Reset route nodes usage
     */
    private void unused() {
        for (int i = 0; i < SIZE; i++) {
            used[i] = false;
        }
    }
    //endregion

}
