public class LinkedListDeque<T> {

    /** Node class */
    private class Node{
        private T first;
        private Node prev;
        private Node next;

        private Node(Node p, T item, Node n){
            first = item;
            prev = p;
            next = n;
        }
    }

    private int size;

    /* Create sentinels */
    private Node frontSentinel = new Node(null, null, null);
    private Node backSentinel = new Node(null,null, null);

    /** Creates an empty linked list deque */
    public LinkedListDeque(){
        size = 0;
    }

    /** Add an item to the front of the list */
    public void addFirst(T item){
        Node firstItem = new Node (null, item, null);
        firstItem.next = frontSentinel.next;
        frontSentinel.next = firstItem.prev;
        size += 1;
    }

    /** Add an item to the back of the list */
    public void addLast(T item){
        Node lastItem = new Node (null, item, null);
        lastItem.prev = backSentinel.prev;
        backSentinel.prev = lastItem.next;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**Returns the number of items in the deque.*/
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        if (size == 0){
            return;
        }
        Node p = frontSentinel.next;
        for (int i = 0; i < size; i++) {
            StdOut.printf("%d ", p.first);
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        T frontItem = frontSentinel.next.first;
        frontSentinel.next = frontSentinel.next.next;
        frontSentinel.next.prev = frontSentinel;
        size --;
        return frontItem;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast(){
        if (size == 0){
            return null;
        }
        T backItem = backSentinel.prev.first;
        backSentinel.prev = backSentinel.prev.prev;
        backSentinel.prev.next = backSentinel;
        size --;
        return backItem;
    }

    /**Gets the item at the given index. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (index < 0 || size == 0 || index >= size){
            return null;
        }

        Node p = frontSentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.first;
    }

    /**Gets the item at the given index with recursion */
    public T getRecursive(int index){
        if (index < 0 || size == 0 || index >= size){
            return null;
        }
        return getRecursiveHelper(index, frontSentinel.next) ;
    }

    public T getRecursiveHelper(int index, Node N){
        if (index == 0){
            return N.first;
        }
        return getRecursiveHelper(index - 1, N.next);
    }
}
