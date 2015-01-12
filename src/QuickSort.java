/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class QuickSort<T extends Comparable> implements ISort {
    private T[] elements;

    public QuickSort(final T[] elements) {
        this.elements = elements;
    }

    private int partition(T[] a, int left, int right) {
        T tmp;
        T v = a[right];
        int i = left - 1;
        int j = right;
        for ( ; ; ) {
            while(a[++i].compareTo(v) < 0);
            while (v.compareTo(a[--j]) < 0) {
                if (j == left) break;
            }
            if (i >= j) break;

            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }

        a[right] = a[i];
        a[i] = v;

        return i;
    }

    private void recursiveSort(T[] a, int left, int right) {
        if (right <= left) return;
        int i = partition(a, left, right);
        recursiveSort(a, left, i - 1);
        recursiveSort(a, i + 1, right);
    }

    @Override
    public void sort() {
        recursiveSort(elements, 0, elements.length - 1);
    }

    @Override
    public String getTitle() {
        return "Quick";
    }


}