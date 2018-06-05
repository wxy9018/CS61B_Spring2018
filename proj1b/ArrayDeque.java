public class ArrayDeque<T> implements Deque<T> {
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
    @Override
    public void addFirst(T item) {
        if (size >= memory.length - 1) { // current memory is full, need upsize
            resize(2);
        }
        int pos = (start + memory.length - 1) % memory.length; // find the insert position which is start position - 1, then taking account of the circular operation
        memory[pos] = item;
        start = pos;
        size++;
    }
    @Override
    public void addLast(T item) {
        if (size >= memory.length - 1) { // current memory is full, need upsize
            resize(2);
        }
        int pos = (start + size) % memory.length; // find the insert position which is start position + size, then taking account of the circular operation
        memory[pos] = item;
        size++;
    }
    @Override
    public boolean isEmpty() {
        boolean t = (size == 0);
        return t;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        for (int i = start; i < start + size; i++) {
            System.out.print(memory[i % memory.length] + " ");
        }
        System.out.println("");
    }
    @Override
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
    @Override
    public T removeLast() {
        T result = memory[(start + size -1) % memory.length];
        memory[(start + size -1) % memory.length] = null;
        size--;
        if (size < memory.length*0.25) {
            resize(0.5);
        }
        return result;
    }
    @Override
    public T get(int index) {
        return memory[(start + index) % memory.length];
    }
}
