package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        if (key.compareTo(p.key) == 0) return p.value;
        else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        }
        else {
            return getHelper(key, p.right);
        }
        //throw new UnsupportedOperationException();
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, this.root);
        //throw new UnsupportedOperationException();
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            size++;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
        //throw new UnsupportedOperationException();
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        this.root = putHelper(key, value, this.root);
        //throw new UnsupportedOperationException();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
        //throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    public void keySetHelper(Set<K> keys, Node p) {
        if (p != null) {
            keys.add(p.key);
            keySetHelper(keys, p.left);
            keySetHelper(keys, p.right);
        }
    }


        /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, this.root);
        return keys;
        //throw new UnsupportedOperationException();
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) return null;
        removeHelper(key, root);
        return value;
        // throw new UnsupportedOperationException();
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            size--;
            if (p.right == null) return p.left;
            else if (p.left == null) return p.right;
            else {
                Node temp = min(p.right);
                temp.left = p.left;
                temp.right = minDeletion(p.right);
                return temp;
            }
        } else if (cmp < 0) {
            p.left = removeHelper(key, p.left);
            return p;
        } else {
            p.right = removeHelper(key, p.right);
            return p;
        }
        // throw new UnsupportedOperationException();
    }

    // finds the min from the tree
    private Node min(Node p) {
        if (p == null) return null;
        else if (p.left == null) return p;
        else return min(p.left);
    }

    // deletes the min from the tree. returns the root of the tree after deletion.
    private Node minDeletion(Node p) {
        if (p == null) return null;
        if (p.left == null) {
            p = p.right;
        } else {
            p.left = minDeletion(p.left);
        }
        return p;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V _value = get(key);
        if (_value == null || !_value.equals(value)) return null;
        return remove(key);
        // throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        Queue<K> keys = new LinkedList<>();
        addN(keys, root);
        return keys.iterator();
        // return new mapIterator();
        // a even easier implementation is: return keySet().iterator();
    }

    private void addN (Queue keys, Node p) {
        if (p == null) return;
        addN(keys, p.left);
        keys.add(p.key);
        addN(keys, p.right);
        return;
    }

    // alternate way to get an iterator: write the iterator class myself.
    private class mapIterator implements Iterator<K> {
        Queue<K> keys;
        mapIterator() {
            keys = new LinkedList<>();
            addNodes(root);
        }

        private void addNodes(Node p) {
            if (p == null) return;
            addNodes(p.left);
            keys.add(p.key);
            addNodes(p.right);
            return;
        }

        @Override
        public K next() {
            return keys.poll();
        }
        @Override
        public boolean hasNext() {
            return keys == null;
        }
    }

}
