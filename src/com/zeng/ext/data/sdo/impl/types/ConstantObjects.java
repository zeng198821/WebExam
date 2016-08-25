package com.zeng.ext.data.sdo.impl.types;

import commonj.sdo.Property;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by zeng on 2016-07-25.
 */
public class ConstantObjects
{
    public static final Object NULL_VALUE = new Object();
    public static final String ANONYMOUS_TYPE_NAME = "anonymous";
    public static final ListIterator NULL_ITERATOR = new ListIterator()
    {
        public void set(Object o) {
        }

        public void remove() {
        }

        public int previousIndex() {
            return -1;
        }

        public Object previous() {
            return null;
        }

        public int nextIndex() {
            return -1;
        }

        public Object next() {
            return null;
        }

        public boolean hasPrevious() {
            return false;
        }

        public boolean hasNext() {
            return false;
        }

        public void add(Object o)
        {
        }
    };

    public static final List NULL_LIST = new List()
    {
        public Object[] toArray(Object[] a) {
            return a;
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public List subList(int fromIndex, int toIndex) {
            throw new IndexOutOfBoundsException();
        }

        public int size() {
            return 0;
        }

        public Object set(int index, Object element) {
            throw new IndexOutOfBoundsException();
        }

        public boolean retainAll(Collection c) {
            return false;
        }

        public boolean removeAll(Collection c) {
            return true;
        }

        public Object remove(int index) {
            throw new IndexOutOfBoundsException();
        }

        public boolean remove(Object o) {
            return true;
        }

        public ListIterator listIterator(int index) {
            throw new IndexOutOfBoundsException();
        }

        public ListIterator listIterator() {
            return ConstantObjects.NULL_ITERATOR;
        }

        public int lastIndexOf(Object o) {
            return -1;
        }

        public Iterator iterator() {
            return ConstantObjects.NULL_ITERATOR;
        }

        public boolean isEmpty() {
            return true;
        }

        public int indexOf(Object o) {
            return -1;
        }

        public Object get(int index) {
            throw new IndexOutOfBoundsException();
        }

        public boolean containsAll(Collection c) {
            return false;
        }

        public boolean contains(Object o) {
            return false;
        }

        public void clear() {
        }

        public boolean addAll(int index, Collection c) {
            throw new IndexOutOfBoundsException();
        }

        public boolean addAll(Collection c) {
            return false;
        }

        public void add(int index, Object element) {
            throw new IndexOutOfBoundsException();
        }

        public boolean add(Object o) {
            return false;
        }
    };

    public static final Set<Property> NULL_SET = new Set()
    {
        public Object[] toArray(Object[] a) {
            return new Object[0];
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public int size() {
            return 0;
        }

        public boolean retainAll(Collection c) {
            return false;
        }

        public boolean removeAll(Collection c) {
            return false;
        }

        public boolean remove(Object o) {
            return false;
        }

        public Iterator iterator() {
            return ConstantObjects.NULL_ITERATOR;
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean containsAll(Collection c) {
            return false;
        }

        public boolean contains(Object o) {
            return false;
        }

        public void clear() {
        }

        public boolean addAll(Collection c) {
            return false;
        }

        public boolean add(Object o) {
            return false;
        }
    };
}