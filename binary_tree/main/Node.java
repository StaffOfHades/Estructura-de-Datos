package main;

// Container class that holds reference to child nodes on either side, as well as the value of the node itself
class Node {
	
	Node left, right;
	final int value;
	
	Node(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

}