public class ShellSort<T extends Comparable> implements ISort {
    private T[] elements;

    public ShellSort(final T[] elements) {
        this.elements = elements;
    }

    @Override
    public void sort() {
        for (int increment = elements.length / 2; increment > 0; increment /= 2) {
            for (int i = increment; i < elements.length; i++) {
                int j = i - increment;
                while (j >= 0) {
                    if (elements[j].compareTo(elements[j + increment]) > 0) {
                        T swap = elements[j];
                        elements[j] = elements[j + increment];
                        elements[j + increment] = swap;
                        j -= increment;
                    } else {
                        j = -1;
                    }
                }
            }
        }
    }

    @Override
    public String getTitle() {
        return "Shell";
    }
}