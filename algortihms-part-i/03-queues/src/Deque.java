import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<T> implements Iterable<T> {

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private class Node {
        T item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return this.head == null;
    }

    // return the number of items on the deque
    public int size(){
        return this.size;
    }

    // add the item to the front
    public void addFirst(T item){

        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null element.");
        }

        Node n = new Node();
        n.item = item;
        n.previous = null;
        n.next = null;

        if (size == 0) {
            this.head = n;
            this.tail = n;
        } else {
            n.next = this.head;
            this.head.previous = n;
            this.head = n;
        }

        size++;
    }

    // add the item to the back
    public void addLast(T item){

        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null element.");
        }

        Node n = new Node();
        n.item = item;
        n.previous = null;
        n.next = null;

        if (size == 0) {
            this.head = n;
            this.tail = n;
        } else {
            n.previous = this.tail;
            this.tail.next = n;
            this.tail = n;
        }

        size++;
    }

    // remove and return the item from the front
    public T removeFirst(){

        if (size > 0) {
            T item = this.head.item;
            this.head = this.head.next;
            size--;

            if (size == 0) {
                this.tail = null;
            } else {
                this.head.previous = null;
            }

            return item;
        }

        throw new java.util.NoSuchElementException("Cannot remove from an empty Deque");
    }

    // remove and return the item from the back
    public T removeLast(){
        if (size > 0) {
            T item = this.tail.item;
            this.tail = this.tail.previous;
            size--;

            if (size == 0) {
                this.head = null;
            } else {
                this.tail.next = null;
            }

            return item;
        }

        throw new java.util.NoSuchElementException("Cannot remove from an empty Deque");
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator(){
        return new Iterator<T>() {

            private Node current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException("Not supported.");
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {

        // Testing isEmpty, size and addFirst
        Deque<String> stringDeque = new Deque<>();
        StdOut.println("Is empty: " + stringDeque.isEmpty());
        StdOut.println("Size: " + stringDeque.size());
        stringDeque.addFirst(" - First Item");
        StdOut.println("Is empty after adding: " + stringDeque.isEmpty());
        StdOut.println("Size after adding: " + stringDeque.size());

        // Repeated adds, removes and iterator
        stringDeque.addLast(" - Last Item");
        StdOut.println("\nAfter adds");

        stringDeque.addLast(" - Item0");
        stringDeque.addLast(" - Item1");
        stringDeque.addFirst(" - Item01");
        stringDeque.addLast(" - Item2");
        stringDeque.addFirst(" - Item02");
        stringDeque.addLast(" - Item3");
        stringDeque.addFirst(" - Item03");
        for (String s : stringDeque) {
            StdOut.println(s);
        }

        StdOut.println("Size after adding: " + stringDeque.size());

        StdOut.println("\nAfter removing:");
        stringDeque.removeLast();
        stringDeque.removeFirst();
        stringDeque.removeLast();
        stringDeque.removeFirst();
        stringDeque.removeLast();
        for (String s : stringDeque) {
            StdOut.println(s);
        }

        StdOut.println("Size after removing: " + stringDeque.size());

    }

}
