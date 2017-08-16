package main;

/**
 * DataStructure is an interface defining how a data structure
 * should behave, allowing for search, addition, removal, etc.
 *
 * @see DoubleLinkedList
 * @see  Array
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * Septemeber 27, 2016
 */
public interface DataStructure {

    /**
     * add tries to add a single number into the structure.
     *
     * @param num to add into the structure
     * @return if a number was able to be added into the structure
     */
    boolean add(int num);

    /**
     * erase tries to remove all the numbers inside the structure.
     *
     * @return if all numbers where able to be erased from the structure
     */
    boolean erase();

    /**
     * erase tries to erase a single number from the structure.
     *
     * @param num to erase from the structure
     * @return if a number was successfully erased from the structure
     */
    boolean erase(int num);

    /**
     * hasElement does a search in the structure for at least a single number.
     *
     * @return if structure has at least one number
     */
    boolean hasElement();

    /**
     * hasElement does a search in the structure for a given number.
     *
     * @param num to search inside the structure
     * @return if search was able to find number inside the structure
     */
    boolean hasElement(int num);

    /**
     * if a structure has a limit in size, hasSpace verifies if there is free space available.
     *
     * @return if structure can add more numbers
     */
    boolean hasSpace();

    /**
     * orders the numbers inside the structure an in ascending fashion.
     */
    void order();

}
