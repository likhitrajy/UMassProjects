package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	@Override
	public void add(T t) {
		// TODO
		if (t == null) throw new NullPointerException();

		upperBound++;

		BSTNode<T> newNode = new BSTNode<T>(t, null, null);

		root = addToSubtree(root, newNode);
		if (height() > (Math.log(upperBound) / Math.log((double)3/2))) {
			BSTNode<T> child = newNode;
			BSTNode<T> parent = newNode.parent;

			while ((double)subtreeSize(child)/ subtreeSize(parent) <= (double)2/3) {
				parent = parent.parent;
				child = child.parent;
			}

			ScapegoatTree<T> scapegoatTree = new ScapegoatTree<T>();

			scapegoatTree.root = parent;
			BSTNode<T> rootParent = parent.parent;
			
      scapegoatTree.balance();

			if (rootParent.getLeft() == parent) {
        rootParent.setLeft(scapegoatTree.root);
      }
			else {
        rootParent.setRight(scapegoatTree.root);
      }
		}
	}
	
	@Override
	public boolean remove(T t) {
		// TODO
		if (t == null) throw new NullPointerException();
    boolean exists = contains(t);
		if (exists) root = removeFromSubtree(root, t);
		
		if (upperBound > 2*size()){
			balance();
			upperBound = size();
		}

		return exists;
	}
}
