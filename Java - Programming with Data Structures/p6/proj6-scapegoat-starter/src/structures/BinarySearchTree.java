package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
    boolean get = (get(t) != null);
		return get;
	}

	public boolean remove(T t) {
		if (t == null) throw new NullPointerException();
    
    boolean exists = contains(t);

		if (exists) root = removeFromSubtree(root, t);
		return exists;

	}

	protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
		if (t == null) throw new NullPointerException();

    T val = getHelper(t, root);

		return val;
	}
	
	private T getHelper(T t, BSTNode<T> currNode) {
		if (currNode == null) return null;

		int compare = t.compareTo(currNode.getData());

		if (compare == 0){
      return currNode.getData();
    }
		else if(compare < 0){
      return getHelper(t, currNode.getLeft());
    }
		else{
      return getHelper(t, currNode.getRight());
    }
	}


	public void add(T t) {
		if (t == null) throw new NullPointerException();

		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		if (root == null) return null;

		return getMinimum(root);
	}
	
	private T getMinimum(BSTNode<T> node) {
		if (node.getLeft() == null)	return node.getData();
		else return getMinimum(node.getLeft());
	}


	@Override
	public T getMaximum() {
		if (root == null) return null;

		return getHighestValue(root);
	}


	@Override
	public int height() {
		return heightHelper(root);
	}
	
	private int heightHelper(BSTNode<T> node) {
		if (node == null) return -1;

		return 1 + Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight()));
	}


	public Iterator<T> preorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		return equalsHelper(root, other.getRoot());
	}
	
	private boolean equalsHelper(BSTNode<T> main, BSTNode<T> other) {
		if (main == null && other == null) {
      return true;
    }
		else if(main == null || other == null){
      return false;
    }
		else{
			if (!main.getData().equals(other.getData())) return false;

			return equalsHelper(main.getLeft(), other.getLeft()) && equalsHelper(main.getRight(), other.getRight());
		}
	}


	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
		Iterator<T> iteratorMain = this.inorderIterator();
		Iterator<T> iteratorOther = other.inorderIterator();

		while (iteratorMain.hasNext() && iteratorOther.hasNext()){
			if (!iteratorMain.next().equals(iteratorOther.next())) return false;
    }

		return !iteratorMain.hasNext() && !iteratorOther.hasNext();
	}

	@Override
	public boolean isBalanced() {
		return Math.pow(2, height()) <= size() && size() < Math.pow(2, height() + 1);
	}

	@Override
    @SuppressWarnings("unchecked")

	public void balance() {

		Iterator<T> iterator = this.inorderIterator();
		T[] comparer = (T[]) new Comparable[size()];
		int index = 0;
		while (iterator.hasNext()) {
			comparer[index] = iterator.next();
			index++;
		}
		root = BSTConverter(comparer, 0, comparer.length-1);
	}
	
	private BSTNode<T> BSTConverter(T[] comparer, int lower, int upper) {
		if (lower > upper) return null;

		int mid = (lower + upper)/2;

		BSTNode<T> currNode = new BSTNode<T>(comparer[mid], BSTConverter(comparer, lower, mid-1), BSTConverter(comparer, mid+1, upper));

		return currNode;
	}


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}
