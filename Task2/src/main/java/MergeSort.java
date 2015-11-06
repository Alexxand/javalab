import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;


public class MergeSort<T> implements Sort<T>{
    private static <T> List<T> merge(List<T> arr1, List<T> arr2, Comparator<T> comparator) {
        int l1 = arr1.size();
        int l2 = arr2.size();
        List<T> rez = new ArrayList<>(l1 + l2);
        int i = 0;
        int j = 0;
        while (i < l1 && j < l2) {
            if (comparator.compare(arr1.get(i), arr2.get(j)) < 0) {
                rez.add(arr1.get(i++));
            } else {
                rez.add(arr2.get(j++));
            }
        }
        if (i == l1) {
            for (; j < l2; ++j)
                rez.add(arr2.get(j));
        } else {
            for (; i < l1; ++i)
                rez.add(arr1.get(i));
        }
        return rez;
    }

    public List<T> sort (Collection<T> arr, Comparator<T> comparator)
    {
        List<T> arrAsList = new ArrayList<T>(arr);
        return sortRecursion(arrAsList,comparator);
    }

    private List<T> sortRecursion (List<T> arr, Comparator<T> comparator) {
        int l = arr.size();
        if (l > 1) {
            List<T> arr1 = arr.subList(0, l / 2);
            List<T> arr2 = arr.subList(l / 2, (2 * l + 1) / 2);
            arr1 = sort(arr1, comparator);
            arr2 = sort(arr2, comparator);
            return merge(arr1, arr2, comparator);
        } else {
            return arr;
        }
    }
}
