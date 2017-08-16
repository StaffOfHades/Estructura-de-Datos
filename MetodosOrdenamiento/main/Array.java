package main;

/**
 * Array container class with all necessary interaction methods
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
     * As long as there is space, increase the global index by one
     * every time a new number is added to array
     * @param num to be added to the array
     * @return if number was able to be added
     */
    boolean add(int num) {
        if (index == max)
            return false;
        array[index++] = num;
        return true;
    }

    /**
     * Every time an element match is found, the array moves all elements
     * down one index, reduces the global index by one, and starts searching from
     * the same position.
     * @return if a number was successfully found & erased
     */
    boolean erase(int num) {
        // Current position for comparison, and a int counter
        int
                pos = 0,
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
     * Calls the quickSort function with the initial borders of the array
     */
    void order() {
        quickSort(0, index - 1);
    }

    /**
     * Recursive function that separates numbers into two sides,
     * depending on whether they are smaller or bigger than a fixed pivot,
     * starting with the whole array, and progressively working in
     * smaller halves until the halves are size 1
     * @param left  local leftmost index in array
     * @param right local rightmost index in array
     */
    private void quickSort(int left, int right) {
        // As long as the left index is smaller than the right one,
        // keep dividing into halves. In other words, as long as the border indexes
        // do not meet or cross.
        if (left < right) {
            // Fixed pivot is the local rightmost number in the array
            final int pivot = array[right];
            // Create position trackers that start on the borders of its "half"
            int
                    i = left - 1,
                    j = right;

            //As long as the trackers do not meet, keep running the loop
            while (true) {
                // Keep looking for a number bigger than the pivot on the left side,
                // as long as the tracker is smaller than the local rightmost index
                while (array[++i] < pivot && i < right);
                // Keep looking for a number smaller than the pivot on the right side,
                // as long as the tracker is bigger than the local leftmost index
                while (array[--j] > pivot && j > left);
                // If the trackers meet or cross, exit the loop
                if (i >= j)
                    break;
                // Otherwise swap the numbers found in the left side & right side
                swap(i, j);
            }
            // Swap the pivot to be in the center of the local area
            swap(i, right);
            // Sort the left half minus the pivot, & then sort the right half minus the pivot
            quickSort(left, i - 1);
            quickSort(i + 1, right);
        }
    }

    /**
     * Swaps two numbers in the array
     * @param i index of number to switch
     * @param j index of number to switch
     */
    private void swap(int i, int j) {
        final int temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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
