public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    /** Creates an empty linked list deque */
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Add an item to the front of the list */
    public void addFirst(T item){
        if (size == items.length){
            resize(2);
        }

        size ++;
        items[nextFirst] = item;

        if (nextFirst == 0){
            nextFirst = items.length - 1;
        }
        else{
            nextFirst --;
        }
    }

    /** Add an item to the back of the list */
    public void addLast(T item){
        if (size == items.length){
            resize(2);
        }

        size ++;
        items[nextLast] = item;

        if (nextLast == items.length - 1){
            nextLast = 0;
        }
        else{
            nextLast ++;
        }
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

    /** Returns the number of items in the deque.*/
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        if (isEmpty()){
            return;
        }
        else{
            for (int i = nextFirst + 1; i != nextLast; i++) {
                if(i >= items.length){
                    i = 0;
                }
                System.out.print(items[i]);
            }
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst(){
        if (isEmpty()){
            return null;
        }

        if (++nextFirst >= items.length){
            nextFirst = 0;
        }

        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size --;

        /* resize the array if it is too empty*/
        if(size < items.length / 4 && items.length > 8){
            resize(0.5);
        }
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast(){
        if (isEmpty()){
            return null;
        }

        if (--nextLast < 0){
            nextFirst = items.length - 1;
        }

        T lastItem = items[nextLast];
        items[nextLast] = null;
        size --;

        /* resize the array if it is too empty*/
        if(size < items.length / 4 && items.length > 8){
            resize(0.5);
        }

        return lastItem;
    }

    /** Gets the item at the given index. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (index < 0 || size == 0 || index >= size){
            return null;
        }
        return items[(nextFirst + index + 1) % items.length];
    }

    /** Gets the item at the given index with recursion */
    public T getRecursive(int index){
        return null;
    }

    /** Resize the array */
    public void resize(double refactor){
        T[] newArray = (T[]) new Object[(int) (size * refactor)];
        for (int i = nextFirst + 1, j = 0 ; j < size; i++, j++) {
            if (i >= items.length){
                i = 0;
            }
            newArray[j] = items[i];
        }
        nextLast = size;
        nextFirst = newArray.length - 1;
        items = newArray;
    }
}
