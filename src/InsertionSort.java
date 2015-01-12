public class InsertionSort<T extends Comparable> implements ISort {
    private T[] elements;

    public InsertionSort(final T[] elements) {
        this.elements = elements;
    }

    @Override
    public void sort() {
        for (int i = 1; i < elements.length; i++) {
            int j;
            T aux = elements[i];
            for (j = i - 1; j >= 0 && elements[j].compareTo(aux) > 0; j--) {
                elements[j + 1] = elements[j];
            }
            elements[j + 1] = aux;
        }
    }

    @Override
    public String getTitle() {
        return "Insertion";
    }
}