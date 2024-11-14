package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure to allow for
 * unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

  public LLNode<T> top;
  public int sizeList;


  /** {@inheritDoc} */
  @Override
  public T pop() throws StackUnderflowException {
    
    if (isEmpty() == false) {
      T tempElement = top.getData();
      top = top.getNext();
      sizeList = sizeList - 1;
      return tempElement;
    }
    throw new StackUnderflowException("Cannot pop an empty stack");
  }

  /** {@inheritDoc} */
  @Override
  public T top() throws StackUnderflowException {
    
    if (isEmpty() == false) {
      return top.getData();
    }
    throw new StackUnderflowException("Cannot return top element in an empty stack");
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    
    return top == null;
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    
    return sizeList;
  }

  /** {@inheritDoc} */
  @Override
  public void push(T elem) {
    
    LLNode<T> push = new LLNode<T>(elem);
    push.setNext(top);
    top = push;
    sizeList = sizeList + 1;
  }
}
