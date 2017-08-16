package graph;

/**
 * Pair is a holder object, saving two variables inside a single entity
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * November 16, 2016
 */
class Pair {

	//region Variables
	String key;
	int value;
	//endregion


	//region Constructor

	/**
	 * Base constructor, with default values
	 */
	Pair() {
		key = "";
		value = 0;
	}

	/**
	 * Defined constructor where you want to add your own default values
	 * @param key string
	 * @param value number
	 */
	Pair(String key, int value) {
		this.key = key;
		this.value = value;
	}
	//endregion

}