/**
 * Created by IntelliJ IDEA.
 * User: Sergio Ceron Figueroa
 * Date: 10/04/12
 * Time: 06:47 PM
 */
public class BBubbleSort<T extends Comparable> implements ISort {
    private T[] elements;
    private int left, right, last;

    public BBubbleSort(final T[] elements) {
        this.elements = elements;
        left = 1;
        right = elements.length;
        last = elements.length - 1;
    }

    @Override
    public void sort() {
        do {
            for (int i = elements.length - 1; i > 0; i--) {
                if (elements[i - 1].compareTo(elements[i]) > 0) {
                    T aux = elements[i];
                    elements[i] = elements[i - 1];
                    elements[i - 1] = aux;
                    last = i;
                }
            }
            left = last + 1;
            for (int j = 1; j < elements.length; j++) {
                if (elements[j - 1].compareTo(elements[j]) > 0) {
                    T aux = elements[j];
                    elements[j] = elements[j - 1];
                    elements[j - 1] = aux;
                    last = j;
                }
            }
            right = last - 1;
        } while (right >= left);
    }

    @Override
    public String getTitle() {
        return "Bid Bubble";
    }
}
