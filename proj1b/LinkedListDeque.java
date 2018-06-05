public class LinkedListDeque<T> implements Deque<T> {
    private class dequeElement{
        private T item;
        private dequeElement prev;
        private dequeElement next;

        public dequeElement(T item, dequeElement prev, dequeElement next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private dequeElement sentinel;
    private int size;

    public LinkedListDeque() { // Empty linked list deque
        sentinel = new dequeElement(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T elem) {
        dequeElement t = new dequeElement(elem, sentinel, sentinel.next);
        sentinel.next.prev = t;
        sentinel.next = t;
        size++;
    }
    @Override
    public void addLast(T elem) {
        dequeElement t = new dequeElement(elem, sentinel.prev, sentinel);
        sentinel.prev.next = t;
        sentinel.prev = t;
        size ++;
    }
    @Override
    public boolean isEmpty() { // return true if the deque is empty
        boolean result = (size == 0);
        return result;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() { // Prints the items in the deque from first to last, separated by a space.
        dequeElement q = sentinel.next;
        if (!isEmpty()) {
            while (q != sentinel) {
                System.out.print(q.item + " ");
                q = q.next;
            }
            System.out.println("");
        }
    }
    @Override
    public T removeFirst() {
        T result =  sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return result;
    }
    @Override
    public T removeLast() {
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return result;
    }
    @Override
    public T get(int index) { // the available index starts from 0 and ends at size-1
        if (index > size - 1){
            System.out.println("Deque Overflow: index greater than size!");
            return null;
        }
        dequeElement p = sentinel.next;
        while(index > 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }
    public T getRecursive(int index) { // recursive version of get()
        if (index > size - 1){
            System.out.println("Deque Overflow: index greater than size!");
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, dequeElement start) {
        if (index == 0) {
            return start.item;
        }
        int i = index-1;
        return getRecursiveHelper(i, start.next);
    }
}
