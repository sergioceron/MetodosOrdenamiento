import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSort<T extends Comparable> implements ISort {
    private T[] elements;

    public MergeSort(final T[] elements) {
        this.elements = elements;
    }

    private T[] recursiveMerge(T[] partition) {
        int n = partition.length;

        if (n > 1) {
            int m = (int) (Math.ceil(n / 2.0));
            T[] part1 = (T[]) java.lang.reflect.Array.newInstance(elements.getClass().getComponentType(), m);
            T[] part2 = (T[]) java.lang.reflect.Array.newInstance(elements.getClass().getComponentType(), n - m);

            System.arraycopy(partition, 0, part1, 0, m);

            for (int i = m; i < n; i++) {
                part2[i - m] = partition[i];
            }
            partition = merge(recursiveMerge(part1), recursiveMerge(part2));
        }
        return partition;
    }

    private T[] delete(T[] part) {
        T[] L = (T[]) java.lang.reflect.Array.newInstance(elements.getClass().getComponentType(), part.length - 1);
        for (int i = 1; i < part.length; i++) {
            L[i - 1] = part[i];
        }
        return L;
    }

    private T[] merge(T[] part1, T[] part2) {
        T[] L = (T[]) java.lang.reflect.Array.newInstance(elements.getClass().getComponentType(), part1.length + part2.length);
        int i = 0;
        while ((part1.length != 0) && (part2.length != 0)) {
            if (part1[0].compareTo(part2[0]) < 0) {
                L[i++] = part1[0];
                part1 = delete(part1);
                if (part1.length == 0) {
                    while (part2.length != 0) {
                        L[i++] = part2[0];
                        part2 = delete(part2);
                    }
                }
            } else {
                L[i++] = part2[0];
                part2 = delete(part2);
                if (part2.length == 0) {
                    while (part1.length != 0) {
                        L[i++] = part1[0];
                        part1 = delete(part1);
                    }
                }
            }
        }
        return L;
    }

    @Override
    public void sort() {
        recursiveMerge(elements);
    }

    @Override
    public String getTitle() {
        return "Merge";
    }
}