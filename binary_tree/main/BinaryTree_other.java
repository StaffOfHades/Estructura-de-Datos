package main;

// Container class that implements specifics methods from DataStructure utlizing the binary tree method

public class BinaryTree implements DataStructure {
	
	private Node root;
	
	public BinaryTree() {
		this.root = null;
	}
	
	public BinaryTree(int value) {
		this.root = new Node(value);
	}

	// Verify root exists. If not, add the new value as root.
	// Otherwise, enter a recursive add
	@Override
	public boolean add(int num) {
		if (this.root == null) {
			this.root = new Node(num);
			return true;
		}

		return add(num, this.root);
	}

	//Search for place to add value. If it already exists, it cannot be added.
	// If there is space add it there, otherwise navigate to where it can be added
	private boolean add(int value, Node node) {
		if (value == node.value) {
			return false;
		} else if (value < node.value && node.left == null) { 
			node.left = new Node(value);
			return true;
		} else if (value > node.value && node.right == null) {
			node.right = new Node(value);
			return true;
		}
		return add(value, value < node.value ? node.left : node.right);
	}

	// If root doesnt exist, there is nothing to erase
	// Otherwise, recursively erase all
	@Override
	public boolean erase() {
		if (this.root == null) {
			return true;
		}

		erase(this.root);
		this.root = null;
		return true;
	}

	// Until we find a node with no child, keep going down. Once found, start erasing from bottom up.
	private void erase(Node node) {
		if (node.left == null && node.right == null) {
			return;
		}
		if (node.left != null) {
			erase(node.left);
			node.left = null;
		}
		if (node.right != null) {
			erase(node.right);
			node.right = null;
		}
	}

	// Check if root exists. If it doesn't, the number cannot be erased.
    // If the number is the root, find the appropriate case to erase it.
    // Otherwise, travel the tree and try to find number to erase.
	@Override
	public boolean erase(int num) {
        if (this.root == null) {
            return false;
        }
else
        /*if (this.root.value == num) {
            if (this.root.left == null && this.root.right == null) {
                System.out.println("Case: No children");
                this.root = null;
            } else if (this.root.left == null) {
                System.out.println("Case: One child (right)");
                this.root = this.root.right;
            } else if (this.root.right == null) {
                System.out.println("Case: One child (left)");
                this.root = this.root.left;
            } else {
                System.out.println("Case: Two children");
                Node big = this.root.left;
                while (big.right != null) {
                    big = big.right;
                }
                Node small = this.root.right;
                while (small.left != null) {
                    small = small.left;
                }

                if (big.left == null) {
                    System.out.print("& successor left no child");
                    if (big != this.root.left) {
                        big.left = this.root.left;
                    }
                    big.right = this.root.right;
                    this.root = big;
                } else if (small.right == null) {
                    System.out.print("& successor right no child");
                    if (small != this.root.right) {
                        small.right = this.root.right;
                    }
                    small.left = this.root.left;
                    this.root = big;
                } else {
                    System.out.print("& successor left has child");
                    if (big != this.root.left) {
                        big.left.left = this.root.left;
                    }
                    big.right = this.root.right;
                    this.root = big;
                }
            }
            return true;
        }*/

        return erase(num, this.root);
    }

    // If number is foun, find the case to erase it. Otherwise, try to find number.
    // If the number doesn't exists, return false
	private boolean erase(int num, Node node) {
		Node big,child;
                Node padre; // INGRID: padre del sucesor
         // INGRID Falta revisar si el dato esta en la raiz
        if(node!=null){
        if(node== root && node.value == num){
            System.out.println("Es la raiz");
            if(node.left == null && node.right == null) 
                root = null;
            else
                if(node.left == null)
                    root = node.right;
                else 
                    if(node.right==null)
                        root = node.left;
                    else{
                        System.out.println("Case: Root two children");
                        big = node.left;
                        padre = null;
			while (big.right != null) {
                            padre = big;
                            big = big.right;
			}
                        System.out.println("Successor: "+big.value);
                        root.value = big.value;
                        if (big.left == null){ // o sea es hoja
                            if(padre!=null && padre!=root)
                                padre.right = null;
                            else
                                root.left = null;
                        }
                        else
                            root.left = big.left;
                             
                        
                    }
            return true;
        }
        else if (node.left != null && node.left.value == num) {
            System.out.println("Found value on left");
			child = node.left;
			padre = null;
			if (child.left == null && child.right == null) {
                System.out.println("Case: No children");
				node.left = null;
			} else if (child.left == null) {
                System.out.println("Case: One child (right)");
				node.left = child.right;
                node.right = null;
			} else if (child.right == null) {
                System.out.println("Case: One child (left)");
				node.left = child.left;
			} else {
                System.out.println("Case: Two children");
				big = child.left;
				while (big.right != null) {
                                    padre = big;
					big = big.right;
				}
            /* INGRID dejémoslo buscar solo del lado izq el mayor.
				Node small = child.right;
				while (small.left != null) {
                                    padre = small;
					small = small.left;
				}
		*/	
       // INGRID:el error esta aca, no debes re-asignar los hijos de node y de big asi
      // sino que primero copia dato del sucesor a child ( value no puede ser final! )
           System.out.println("Successor: "+big.value);
            child.value = big.value;
		if (big.left == null) {// o sea es hoja
                    System.out.print("& successor left no child"); 

     // y elimina luego al sucesor.
                if(padre!=null && padre!=child)
                     padre.right = null;
                else
                    child.left = null;
                } 
       /*             
					if (big != child.left) {
						big.left = child.left;
					}
					big.right = child.right;
					node.left = big;
      
				} else if (small.right == null) {
                    System.out.print("& successor right no child");
   
					if (small != child.right) {
						small.right = child.right;
					}
					small.left = child.left;
					node.left = small;
         */
		else {
                    System.out.print("& successor left has child");
                            
     // y elimina luego al sucesor.
                     padre.right = big.left;
                     /*
					if (big != child.left) {
						big.left.left = child.left;
					}
					big.right = child.right;
					node.left = big;
				}*/
                }
                        }
		return true;
                        
		} else if (node.right != null && node.right.value == num) {
            System.out.println("Found value on right");

			child = node.right;
			padre = null;
			if (child.left == null && child.right == null) {
                System.out.println("Case: No children");
				node.right = null;
			} else if (child.left == null) {
                System.out.println("Case: One child (right)");
				node.right = child.right;
			} else if (child.right == null) {
                System.out.println("Case: One child (left)");
				node.right = child.left;
                node.left = null;
			} else {
                System.out.println("Case: Two children");
				big = child.left;
				while (big.right != null) {
                                       padre=big;
					big = big.right;
				}
				/*Node small = child.right;
				while (small.left != null) {
					small = small.left;
				}
			*/
      // INGRID: copia dato del sucesor a child
                     // value no puede ser final!                  
                    child.value = big.value;
                    
                    if (big.left == null) { 
                    System.out.print("& successor left no child");

                    // luego elimina sucesor...
                    if(padre!=null&&padre!=child)
                     padre.right = null;
                else
                    child.left = null;
/*					if (big != child.left) {
						big.left = child.left;
					}
					big.right = child.right;
					node.right = big;

				} else if (small.right == null) {
                    System.out.print("& successor right no child");
					if (small != child.right) {
						small.right = child.right;
					}
					small.left = child.left;
					node.right = small;*/
				} else {
                    System.out.print("& successor left has child");
                    padre.right = big.left;
					/*if (big != child.left) {
						big.left.left = child.left;
					}
					big.right = child.right;
					node.right = big;
				}*/
			}
                        }
			return true;
                        
		} 
        
		return erase(num, num < node.value ? node.left : node.right);
        }
        else return false;
	}

	// Tree has a root
	@Override
	public boolean hasElement() {
        return this.root != null;
	}

	// Tree has a root & number can be found
	@Override
	public boolean hasElement(int num) {
        return this.root != null && hasElement(num, this.root);
    }

    // Travel the tree
	private boolean hasElement(int num, Node node) {
		if (node == null) {
			return false;
		} else if (node.value == num) {
			return true;
		}
		return hasElement(num, num < node.value ? node.left : node.right);
	}

	// Space always available
	@Override
	public boolean hasSpace() {
		return true;
	}

	// Tree always in a certain order.
	@Override
	public void order() {

	}

	// Recursively travel tree in order to display it
	@Override
	public String toString() {
		return toString(1, this.root);
	}

	// As long a node exists, first print all the nodes on the right, then the node itself
    // and finally all the nodes on the left.
	private String toString(int lvl, Node node) {
		String s = "";
                int i;
                if (node != null) {
                    s += toString(lvl + 1, node.right);
                    s += "\n";
                    for(i=0;i<lvl;i++)
                        s+="   -    ";
                    s += node.value;
                    s += toString(lvl + 1, node.left);
                }
		return s;
	}
}