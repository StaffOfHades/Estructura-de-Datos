package main;

/**
 * Array container class with all neccesary interaction methods
 *
 *  @author Mauricio Graciano Álvarez
 *
 * 149605
 * August 26, 2016
 */
class Array {

    //region Properties
    // Global index, as well as max array size
    private int
            index,
            max;
    private int[] array;
    //endregion

    //region Array

    /**
     * Default constructor for Array. Initializes all values
     * @param size desired for array
     */
    Array(int size) {
        array = new int[size];
        index = 0;
        max = size;
    }

    /**
     * @return if global index is greater than 0
     */
    boolean hasElements() {
        return index > 0;
    }

    /**
     * @return if global index is smaller than max
     */
    boolean hasSpace() {
        return index < max;
    }

    /**
     * Every time an element match is found, the array moves all elements
     * down one index, reduces the global index by one, and starts searching from
     * the same position.
     * @return if a number was successfully found & erased
     */
    boolean erase(int num) {
        // Current position for comparison, and a int counter
        int pos = 0,
                i;

        // Boolean to check if ANY match was found.
        boolean found = false;

        // Only run the loop as long as the current position we are searching
        // is less than the global index
        while (pos < index) {
            // If a match is found, move all subsequent numbers one index over
            //  starting form the current position.
            // Otherwise, increase the current position by 1;
            if (array[pos] == num) {
                for (i = pos; i < (index - 1); i++) {
                    array[i] = array[i + 1];
                }
                index--;
                found = true;
            } else {
                pos++;
            }
        }

        return found;
    }

    /**
     * add increases global index by one every time a new number is added to array
     * @param num to be added to the array
     * @return if number was able to be added
     */
    boolean add(int num) {
        if (index == max)
            return false;
        array[index++] = num;
        return true;
    }
    //endregion

    //region Object

    /**
     *
     * @return the numbers in the array line by line
     */
    @Override
    public String toString() {
        // Add number by number to the string
        String string = "";
        int i;
        for (i = 0; i < index; i++)
            string += array[i] + "\n";
        return string;
    }
    //endregion

}
