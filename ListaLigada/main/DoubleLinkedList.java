package main;

/**
 * DoubleLinkedList is a container class for integer type,
 * inheriting all necessary interactions from DataStructure,
 * utilizing DNode for holding the integers and links.
 *
 * @see DataStructure
 * @see DNode
 * @version 1.0
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * Septemeber 27, 2016
 */
public class DoubleLinkedList implements DataStructure {

    //region Properties
    private DNode root;
    //endregion

    //region Constructor
    /**
     * Basic constructor for DoubleLinkedList, setting the root node to null
     */
    public DoubleLinkedList() {
        root = null;
    }
    //endregion

    //region DataStructure
    /**
     * add inserts the number in ordered fashion into the linked list.
     *
     * @param num to add into the linked list
     * @return true, since there is no reason to fail
     */
    @Override
    public boolean add(int num) {
        if (root == null)
            root = new DNode(null, num);
        else {
            DNode directory = root;
            while (directory.value < num && directory.child != null)
                directory = directory.child;

            if (directory.child == null && directory.value < num) {
                directory.child = new DNode(directory, num);
            } else if (directory.parent == null) {
                DNode file = new DNode(null, num);
                root.parent = file;
                file.child = root;
                root = file;
            } else {
                DNode file = new DNode(directory.parent, num);
                directory.parent.child = file;
                directory.parent = file;
                file.child = directory;
            }
        }

        return true;
    }

    /**
     * erase cleans the lists of all numbers.
     *
     * @return true, since there is no reason to fail
     */
    @Override
    public boolean erase() {
        DNode parent = root;
        DNode file;
        while (parent != null) {
            file = parent.child;
            parent.child = null;
            if (file != null)
                file.parent = null;
            parent = file;
        }

        return true;
    }

    /**
     * erase looks for all number that match the given number,
     * erases them list, and finally concatenates the linked list.
     *
     * @param num to erase from the linked list
     * @return if a number was found to be erased
     */
    @Override
    public boolean erase(int num) {
        boolean found = false;
        DNode file = root;
        DNode directory, child;

        while (file != null &&  file.value <= num) {
            child = file.child;

            if (file.value == num) {
                directory = file.parent;
                if (child == null) {
                    if (directory != null) {
                        directory.child = null;
                        file.parent = null;
                    } else
                        root = null;
                } else if (directory == null) {
                    root = child;
                    root.parent = null;
                    file.child = null;
                } else {
                    directory.child = child;
                    child.parent = directory;
                    file.parent = null;
                    file.child = null;
                }

                found = true;
            }

            file = child;
        }

        return found;
    }


    /**
     * hasElement verifies if the linked list is not empty.
     *
     * @return if root is not null
     */
    @Override
    public boolean hasElement() {
        return root != null;
    }

    /**
     * hasElement does a search for a given number inside the linked list.
     *
     * @param num to search inside the linked list
     * @return if search was able to find number inside the linked list
     */
    @Override
    public boolean hasElement(int num) {
        DNode file = root;

        while (file != null && file.value <= num) {
            if (file.value == num)
                return true;
            file = file.child;
        }

        return false;
    }

    /**
     * @return true, since a linked list always has more space
     */
    @Override
    public boolean hasSpace() {
        return true;
    }

    /**
     * Since the linked list add numbers in order, order is not used
     */
    @Deprecated
    @Override
    public void order() {

    }
    //endregion

    //region Object
    /**
     * toString returns the string representation of the linked list.
     *
     * @return all the numbers inside the linked list, separated by a space character
     */
    @Override
    public String toString() {
        String string = "";
        DNode file = root;
        while (file != null) {
            string += file.value + "\n";
            file = file.child;
        }

        if (string.length() == 0)
            return string;

        return string.substring(0, string.length()-1);
    }
    //endregion

}
