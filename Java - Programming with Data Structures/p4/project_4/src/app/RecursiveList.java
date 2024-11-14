package app;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {
  
  private int size;
  private Node<T> head = null;

  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) {
      //TODO: Implement this method.
      Node<T> temp = new Node<T>(elem, head);
      temp.setNext(head);
      head = temp;
      size++;
  }

  @Override
  public void insertLast(T elem) {
      //TODO: Implement this method.
      Node<T> temp = new Node<T>(elem, null);

      if(head == null) head = temp;
      else insertLastHelper(head, temp);

      size++;
  }

  private void insertLastHelper(Node<T> presentNode, Node<T> insNode){
    if(presentNode.getNext() == null){
        presentNode.setNext(insNode);
        return;
    }
    insertLastHelper(presentNode.getNext(), insNode);
  }

  @Override
  public void insertAt(int index, T elem) {
      //TODO: Implement this method.
      if(index < 0 || index > size){ 
        throw new IndexOutOfBoundsException();
      }
      if(elem == null) {
        throw new NullPointerException();
      }

      Node<T> temp = new Node<T>(elem, head);

      if(index == 0){
        temp.setNext(head);
        head = temp;
      }
      else{
        insertAtHelper(head, temp, index);
      }
      size++;

  }
  
  private void insertAtHelper(Node<T> presentNode, Node<T> insNode, int index){
    if(index == 1){
        insNode.setNext(presentNode.getNext());
        presentNode.setNext(insNode);
      }
      else{
        insertAtHelper(presentNode.getNext(), insNode, index - 1);
      }
      
  }



  @Override
  public T removeFirst() {
    T removedItem = null;
      //TODO: Implement this method.
    if(isEmpty()){  
      throw new IllegalStateException();
    }
    
    if(head == null) {
      return null;
    }
    removedItem = head.getData();
    head = head.getNext();
    size--;
  return removedItem;
  }

  @Override
  public T removeLast() {
    T removedItem = null;
      //TODO: Implement this method.
    if(isEmpty()) {
      throw new IllegalStateException();
    }

    if(head == null) {
      return removedItem;
    }

    if(head.getNext() == null){ 
      return removeFirst();
    }
    else{
     return removeLastHelper(head);
    }
  }

  public T removeLastHelper(Node<T> presentNode){
    if(presentNode.getNext().getNext() == null){
      T removedItem =presentNode.getNext().getData();
      presentNode.setNext(null);
			size--;
      return removedItem;
    }
    return removeLastHelper(presentNode.getNext());
  }

  @Override
  public T removeAt(int i) {
    T removedItem = null;
      //TODO: Implement this method.
      if(i < 0 || i >= size){

      throw new IndexOutOfBoundsException();
      }
      if(i == 0){
        removedItem = head.getData();
        head = head.getNext();
      }
      else{
        removedItem = removeAtHelper(head, i);
      }
      size--;
    return removedItem;
  }

  private T removeAtHelper(Node<T> presentNode, int index){
    if(index == 1){
      T removedItem = presentNode.getNext().getData();
      presentNode.setNext(presentNode.getNext().getNext());
      return removedItem;
    }
    return removeAtHelper(presentNode.getNext(), index - 1);
  }

  @Override
  public T getFirst() {
    T item = null;
      //TODO: Implement this method.
      if(isEmpty()) {
        throw new IllegalStateException();
      }
      item = get(0);
    return item;
  }

  @Override
  public T getLast() {
    T item = null;
      //TODO: Implement this method.
      if(isEmpty()) {
        throw new IllegalStateException();
      }
      item = get(size - 1);
    return item;
  }

  @Override
  public T get(int i) {
    T item = null;
      //TODO: Implement this method.
      if( i < 0 || i >= size) {
        throw new IndexOutOfBoundsException();
      }
      item = getHelper(head, i);
    return item;
  }

  private T getHelper(Node<T> presentNode, int index){
      if(index == 0){
        return presentNode.getData();
      }
      return getHelper( presentNode.getNext(), index - 1);

  }

  @Override
  public void remove(T elem) {
      //TODO: Implement this method.
      int indexOfRemoval = indexOf(elem);
      if(elem == null){
        throw new NullPointerException();
      }
      if(indexOfRemoval == -1) {
        throw new ItemNotFoundException();
      }
      removeAt(indexOfRemoval);
  }

  @Override
  public int indexOf(T elem) {
    int index = 0;
      //TODO: Implement this method.
      if(elem == null){
        throw new NullPointerException();
      }
      if(head.getData() == null){
        index = -1;
      }
      int a = index;
      index = indexOfHelper(head, elem, a);
      return index;
  }

  private int indexOfHelper(Node<T> presentNode, T searchElem, int index){
    int a = index;
    if(searchElem == null){ 
      throw new NullPointerException();
    }
    if(presentNode.getData().equals(searchElem)){
      return index;
    }
    if(presentNode.getNext() == null){
      return -1;
    }
    else{
      return indexOfHelper(presentNode.getNext(), searchElem, a+1);
    }

  }

  @Override
  public boolean isEmpty() {
    boolean empty = false;
      //TODO: Implement this method.
      if(head == null) {
        empty = true;
      }
    return empty;
  }

  public Iterator<T> iterator() {
    Iterator<T> iter = null;
      //TODO: Implement this method.
    iter = new LinkedNodeIterator<T>(head);
   return iter;
  }
}