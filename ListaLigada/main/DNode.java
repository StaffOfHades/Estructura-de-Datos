package main;

/**
 * DNode is a holder class for DoubleLinkedList to hold a value,
 * as well as references to the DNodes connected above
 * and below.
 *
 * @see DoubleLinkedList
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * Septemeber 27, 2016
 */
class DNode {

    //region Properties
    DNode parent, child;
    int value;
    //endregion

    //region Constructor
    /**
     * Basic constructor for DNode that receives both the parent
     * DNOde above, as as the value to hold.
     * @param parent reference
     * @param value to hold
     */
    DNode(DNode parent, int value) {
        this.parent = parent;
        this.value = value;
    }
    //endregion
}
