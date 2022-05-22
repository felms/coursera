import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<T> implements Iterable<T> {

    private T[] items;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue(){
        this.capacity = 1;
        this.items = (T[])new Object[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return this.size;
    }

    // add the item
    public void enqueue(T item){

        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null element.");
        }

        if (this.size == this.capacity) {
            resize(2 * this.capacity);
        }

        this.items[this.size] = item;
        this.size++;
        this.tail++;
    }

    // remove and return a random item
    public T dequeue(){

        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from an empty queue");
        }

        int pos = StdRandom.uniform(this.head, this.tail + 1);
        T item = null;
        if (pos == this.head) {
            item = this.items[pos];
            this.items[this.head] = null;
            this.head++;
        } else if (pos == this.tail) {
            item = this.items[pos];
            this.items[this.tail] = null;
            this.tail--;
        } else {
            while (this.items[pos] == null) {
                pos ++;
            }
            item = this.items[pos];
            this.items[pos] = null;
        }
        this.size--;
        if (this.size <= this.capacity / 4) {
            resize(capacity / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public T sample(){

        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot sample from an empty queue");
        }

        int pos = StdRandom.uniform(this.head, this.tail + 1);

        while (this.items[pos] == null) {
            pos ++;
        }

        return this.items[pos];
    }

    // return an independent iterator over items in random order
    public Iterator<T> iterator(){
        return new RQIterator();
    }

    private class RQIterator implements Iterator<T> {

        private boolean[] visited;
        private T[] copy;

        public RQIterator() {
            this.visited = new boolean[size];
            for (int i = 0; i < this.visited.length; i++) {
                this.visited[i] = false;
            }
            this.copy = (T[])new Object[size];
            int head0 = head;
            int copied = 0;
            for (int i = 0; copied < size; i++) {
                while(items[head0] == null) {
                    head0++;
                }
                T item = items[head0];
                copy[i] = item;
                copied++;
                head0++;
            }

        }

        @Override
        public boolean hasNext() {
            boolean allVisited = true;
            for (boolean b : visited) {
                allVisited = allVisited && b;
            }
            return !allVisited;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException("There's no 'next' element");
            }

            int pos = StdRandom.uniform(0, copy.length);
            while (visited[pos]) {
                pos = StdRandom.uniform(0, copy.length);
            }
            T item = copy[pos];
            visited[pos] = true;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

    private void resize(int capacity) {
        T[] copy = (T[])new Object[capacity];
        int head = this.head;
        int copied = 0;
        for (int i = 0; copied < this.size; i++) {
            while(this.items[head] == null) {
                head++;
            }
            T item = this.items[head];
            copy[i] = item;
            copied++;
            head++;
        }
        this.head = 0;
        this.tail = this.size - 1;
        this.items = copy;
        this.capacity = capacity;
    }

    // unit testing (required)
    public static void main(String[] args){
        // Testing isEmpty, size and enqueue
        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();
        StdOut.println("Is empty: " + stringRandomizedQueue.isEmpty());
        StdOut.println("Size: " + stringRandomizedQueue.size());
        stringRandomizedQueue.enqueue(" - Item0");
        StdOut.println("Is empty after adding: " + stringRandomizedQueue.isEmpty());
        StdOut.println("Size after adding: " + stringRandomizedQueue.size());

        // Repeated adds, removes and iterator
        StdOut.println("\nAfter adds");

        stringRandomizedQueue.enqueue(" - Item1");
        stringRandomizedQueue.enqueue(" - Item2");
        stringRandomizedQueue.enqueue(" - Item3");
        stringRandomizedQueue.enqueue(" - Item4");
        stringRandomizedQueue.enqueue(" - Item5");
        stringRandomizedQueue.enqueue(" - Item6");
        stringRandomizedQueue.enqueue(" - Item7");
        stringRandomizedQueue.enqueue(" - Item8");
        stringRandomizedQueue.enqueue(" - Item9");
        for (String s : stringRandomizedQueue) {
            StdOut.println(s);
        }

        StdOut.println("\nOther iterator");
        for (String s : stringRandomizedQueue) {
            StdOut.println(s);
        }

        StdOut.println("Size after adding: " + stringRandomizedQueue.size());

        StdOut.println("\nAfter removing:");
        stringRandomizedQueue.dequeue();
        stringRandomizedQueue.dequeue();
        stringRandomizedQueue.dequeue();
        stringRandomizedQueue.dequeue();
        stringRandomizedQueue.dequeue();
        for (String s : stringRandomizedQueue) {
            StdOut.println(s);
        }

        StdOut.println("Size after removing: " + stringRandomizedQueue.size());

        StdOut.println("\nSampling:");
        StdOut.println(stringRandomizedQueue.sample());
        StdOut.println(stringRandomizedQueue.sample());
        StdOut.println(stringRandomizedQueue.sample());
        StdOut.println(stringRandomizedQueue.sample());
        StdOut.println(stringRandomizedQueue.sample());

    }

}