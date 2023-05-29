public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    /** Creates an empty linked list deque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Add an item to the front of the list */
    @Override
    public void addFirst(T item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }

        size++;
        items[nextFirst] = item;

        if (nextFirst > 0) {
            nextFirst--;
        } else {
            nextFirst = items.length - 1;
        }
    }

    /** Add an item to the back of the list */
    @Override
    public void addLast(T item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }

        size++;
        items[nextLast] = item;

        if (nextLast < items.length - 1) {
            nextLast++;
        } else {
            nextLast = 0;
        }
    }

    /** Returns true if deque is empty, false otherwise.*/
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the deque.*/
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        } else {
            for (int i = nextFirst + 1; i != nextLast; i++) {
                if (i >= items.length) {
                    i = 0;
                }
                System.out.print(items[i]);
            }
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (++nextFirst >= items.length) {
            nextFirst = 0;
        }

        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size--;

        /* resize the array if it is too empty*/
        if (size < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (--nextLast < 0) {
            nextLast = items.length - 1;
        }

        T lastItem = items[nextLast];
        items[nextLast] = null;
        size--;

        /* resize the array if it is too empty*/
        if (size < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }

        return lastItem;
    }

    @Override
    /** Gets the item at the given index. If no such item exists, returns null.*/
    public T get(int index) {
        if (index < 0 || size == 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + index + 1) % items.length];
    }

    /** Resize the array */
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        for (int i = nextFirst + 1, j = 0; j < size; i++, j++) {
            if (i >= items.length) {
                i = 0;
            }
            newArray[j] = items[i];
        }
        nextLast = size;
        nextFirst = newArray.length - 1;
        items = newArray;
    }
}
