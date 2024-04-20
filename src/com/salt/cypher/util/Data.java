package com.salt.cypher.util;

import java.util.ArrayList;

public class Data<K, V> {
    public ArrayList<K> keys;
    public ArrayList<V> values;
    public int size;

    public Data() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        size = 0;
    }

    public void add(K key, V value) {
        if (keys.contains(key)) {
            throw new IllegalArgumentException();
        }
        keys.add(key);
        values.add(value);
        size++;
    }

    public V remove(K key) {
        int i = keys.indexOf(key);
        keys.remove(i);
        size--;
        return values.remove(i);
    }

    public void set(K key, V value) {
        if (keys.contains(key)) {
            values.set(keys.indexOf(key), value);
        } else {
            keys.add(key);
            values.add(value);
            size++;
        }
    }

    public V get(K key) {
        return values.get(keys.indexOf(key));
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i < size; i++) {
            ret += keys.get(i) + "=" + values.get(i) + ", ";
        }

        if (size == 0) {
            return "length=0";
        }

        return ret.substring(0, ret.length()-2) + " length=" + size;
    }
}
