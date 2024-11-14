package priorityqueue;

import java.util.Comparator;

import javax.management.Query;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;

  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
    this.comparator = comparator;
    this.isMaxHeap = isMaxHeap;
    heap = (T[]) new Object[INIT_SIZE];
    numElements = 0;
  }
  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
    T bubbleUp = heap[numElements - 1];

    while (index > 0 && (compare(heap[getParentIndex(index)], bubbleUp) < 0) ) {
      heap[index] = heap[getParentIndex(index)];
      index = getParentIndex(index);
    }
    heap[index] = bubbleUp;
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) {
    T root = heap[0];
    int a = 0;
    boolean finish = true;
      while (finish) {
        int childIndex = getLeftChildIndex(a);
        if (childIndex <= index) {
          T child = heap[getLeftChildIndex(a)];
          if (getRightChildIndex(a) <= index && compare(heap[getRightChildIndex(a)], child) > 0) {
            childIndex = getRightChildIndex(a);
            child = heap[getRightChildIndex(a)];
          }
          if (compare(child, root) > 0) {
            heap[a] = child;
            a = childIndex;
          } 
          else {
            finish = false;
          }
        } 
        else {
          finish = false;
        }
      }
    heap[a] = root;
}

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
    
    if(numElements == 0) {
      isEmpty = true;
    }
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size() {
    int size = -100;
    size = numElements;
    return size;
  }

/**
 * Compare method to implement max/min heap behavior.  It calls the comparae method from the 
 * comparator object and multiply its output by 1 and -1 if max and min heap respectively.
 * TODO: implement the heap compare method
 * @param element1 first element to be compared
 * @param element2 second element to be compared
 * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, negative int otherwise
 */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

/**
 * Return the element with highest (or lowest if min heap) priority in the heap 
 * without removing the element.
 * @return T, the top element
 * @throws QueueUnderflowException if empty
 */
  public T peek() throws QueueUnderflowException {
    T data = heap[0];
    if (data == null) throw new QueueUnderflowException();
    return data;
  }

/**
 * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
 * @return T, the top element
 * @throws QueueUnderflowException if empty
 */
  public T dequeue() throws QueueUnderflowException {
    T data = heap[0];
    if(isEmpty()) throw new QueueUnderflowException();
    T last = heap[numElements - 1];
    if (numElements - 1 > 0){
      heap[0] = last;
      bubbleDown(numElements-1);
    }
    heap[numElements - 1] = null;
    numElements--;

    return data;
    }

/**
  * Enqueue the element.
  * @param the new element
  */
  public void enqueue(T newElement) {
    expandCapacity();
    heap[numElements++] = newElement;
    bubbleUp(numElements - 1);
  }

  
  private void expandCapacity() {
    if (numElements == heap.length) {
      T[] temp = (T[]) new Object[heap.length * 2];
      for (int i = 0; i < numElements; i++) temp[i] = heap[i];
      heap = temp;
    }
  }

  private int getParentIndex(int i) {
    return (i - 1) / 2;
  }

  private int getLeftChildIndex(int i) {
    return (i * 2) + 1;
  }

  private int getRightChildIndex(int i) {
    return (i * 2) + 2;
  }



}