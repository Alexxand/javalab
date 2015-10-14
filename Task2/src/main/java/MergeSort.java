import java.util.Comparator;

public class MergeSort<T> {
    private static <T> T[] merge(T[] arr1, T[] arr2, Comparator<T> comparator) {
        int l1 = arr1.length;
        int l2 = arr2.length;
        T[] rez = (T[]) new Object[l1 + l2];
        int i = 0;
        int j = 0;
        while (i < l1 && j < l2) {
            if (comparator.compare(arr1[i], arr2[j]) < 0) {
                rez[i + j] = arr1[i];
                ++i;
            } else {
                rez[i + j] = arr2[j];
                ++j;
            }
        }
        if (i == l1) {
            for (; j < l2; ++j)
                rez[i + j] = arr2[j];
        } else {
            for (; i < l1; ++i)
                rez[i + j] = arr1[i];
        }
        return rez;
    }

    private static <T> T[] newarr(T[] arr, int first, int length) {
        T[] rez = (T[])new Object[length];
        for (int i = 0; i < length; ++i)
            rez[i] = arr[first + i];
        return rez;
    }

    public T[] mergesort(T[] arr, Comparator<T> comparator) {
        int l = arr.length;
        if (l != 1) {
            T[] arr1 = newarr(arr, 0, l / 2);
            T[] arr2 = newarr(arr, l / 2, (l + 1) / 2);
            arr1 = mergesort(arr1, comparator);
            arr2 = mergesort(arr2, comparator);
            return merge (arr1, arr2, comparator);
        } else {
            return arr;
        }
    }
}
