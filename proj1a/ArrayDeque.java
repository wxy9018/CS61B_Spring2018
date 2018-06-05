public class ArrayDeque<T> {
    private T[] memory;
    private int size;
    private int start;

    public ArrayDeque() {
        memory = (T[]) new Object[8]; // this is the strange cast syntax needed for creating an array of generic objects
        size = 0;
        start = 0;
    }

    private void resize(double factor) { // upsize the array to factor * length
        int newSize = (int) (Math.floor(factor * memory.length));
        T[] resized = (T[])new Object[newSize];
        int start_pos = start;
        int end_pos = (start + size)% memory.length;
        if (start_pos <= end_pos) {// data is stored consecutively
            System.arraycopy(memory,start_pos,resized,0,size);
        } else {
            System.arraycopy(memory,start_pos,resized,0,memory.length-start_pos); // copy the start position till the memory end
            System.arraycopy(memory, 0, resized, memory.length-start_pos, end_pos); // copy the memory beginning till the end position
        }
        start = 0;
        memory = resized;
    }

    public void addFirst(T item) {
        if (size >= memory.length - 1) { // current memory is full, need upsize
            resize(2);
        }
        int pos = (start + memory.length - 1) % memory.length; // find the insert position which is start position - 1, then taking account of the circular operation
        memory[pos] = item;
        start = pos;
        size++;
    }

    public void addLast(T item) {
        if (size >= memory.length - 1) { // current memory is full, need upsize
            resize(2);
        }
        int pos = (start + size) % memory.length; // find the insert position which is start position + size, then taking account of the circular operation
        memory[pos] = item;
        size++;
    }

    public boolean isEmpty() {
        boolean t = (size == 0);
        return t;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = start; i < start + size; i++) {
            System.out.print(memory[i % memory.length] + " ");
        }
        System.out.println("");
    }

    public T removeFirst() {
        T result = memory[start];
        memory[start] = null;
        start = (start + 1) % memory.length;
        size--;
        if (size < memory.length*0.25) {
            resize(0.5);
        }
        return result;
    }

    public T removeLast() {
        T result = memory[(start + size -1) % memory.length];
        memory[(start + size -1) % memory.length] = null;
        size--;
        if (size < memory.length*0.25) {
            resize(0.5);
        }
        return result;
    }

    public T get(int index) {
        return memory[(start + index) % memory.length];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        a.printDeque();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.printDeque();
        a.addLast(4);
        a.addLast(5);
        a.printDeque();
        System.out.println(a.size());
        System.out.println(a.isEmpty());

        int s1 = (int)a.get(0);
        int s2 = (int)a.get(a.size()-1);
        System.out.println(s1 + " " + s2);

        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeLast());
        a.printDeque();
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        System.out.println(a.removeLast());
        System.out.println(a.size());
        for(int i = 0; i < 20; i++) {
            a.addLast(i);
        }
        for(int i = 0; i < 20; i++) {
            a.addFirst(-i);
        }
        a.printDeque();
        for(int i = 0; i < 18; i++) {
            a.removeLast();
        }
        for(int i = 0; i < 18; i++) {
            a.removeFirst();
        }
        a.printDeque();
        System.out.println(a.removeLast()); // should error out
    }
}