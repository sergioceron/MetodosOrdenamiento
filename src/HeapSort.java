public class HeapSort<T extends Comparable> implements ISort {
    private T[] elements;

    public HeapSort(final T[] elements) {
        this.elements = elements;
    }

    private void downheap(T a[], int k, int N) {
        T e = a[k - 1];
        while (k <= N / 2) {
            int j = k + k;
            if ((j < N) && (a[j - 1].compareTo(a[j]) < 0)) {
                j++;
            }
            if ( e.compareTo(a[j - 1]) >= 0 ) {
                break;
            } else {
                a[k - 1] = a[j - 1];
                k = j;
            }
        }
        a[k - 1] = e;
    }

    @Override
    public void sort() {
        int N = elements.length;
        for (int k = N / 2; k > 0; k--) {
            downheap(elements, k, N);
        }
        do {
            T e = elements[0];
            elements[0] = elements[N - 1];
            elements[N - 1] = e;
            N = N - 1;
            downheap(elements, 1, N);
        } while (N > 1);
    }

    @Override
    public String getTitle() {
        return "Heap";
    }
}