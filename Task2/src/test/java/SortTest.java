import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class SortTest<T> {

    private static final Sort SORT = new MergeSort();

    private static final Comparator<Double> DOUBLE_ASCENDING_COMPARATOR = Double::compareTo;
    private static final Comparator<Double> DOUBLE_DESCENDING_COMPARATOR = (o1, o2) -> -o1.compareTo(o2);
    private static final Comparator<PersonBean> PERSON_BEAN_NAME_SURNAME_COMPARATOR = (o1, o2) -> {
        int temp = o1.getSurname().compareTo(o2.getSurname());
        if (temp == 0)
            return o1.getName().compareTo(o2.getName());
        else
            return temp;
    };
    private static final Comparator<PersonBean> PERSON_BEAN_AGE_COMPARATOR = (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge());

    private Sort sort;
    private Comparator<T> comparator;
    private List<T> input;

    public SortTest(Sort sort, Comparator<T> comparator, List<T> input) {
        this.sort = sort;
        this.input = input;
        this.comparator = comparator;
    }

    private static <E> List<E> listFromStringArr(String[] stringArr, FromStringCreator<E> creator) {
            List<E> result = new ArrayList<>();
            for (String s : stringArr) {
                if (!s.trim().isEmpty()) {
                    result.add(creator.create(s));
                }
            }
            return result;
    }

    private static <E> List<Object[]> getData(Sort sort, Comparator<E> comparator, FromStringCreator creator, String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            List<Object[]> result = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stringArr = line.split(", ");
                Object[] listElem = new Object[]{sort, comparator, listFromStringArr(stringArr, creator)};
                result.add(listElem);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("File with tests can't be opened", e);
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        List<Object[]> testData = new ArrayList<>();
        testData.addAll(getData(SORT, DOUBLE_ASCENDING_COMPARATOR, new DoubleFromStringCreator(), "doubletest.txt"));
        testData.addAll(getData(SORT, DOUBLE_DESCENDING_COMPARATOR, new DoubleFromStringCreator(), "doubletest.txt"));
        testData.addAll(getData(SORT, PERSON_BEAN_NAME_SURNAME_COMPARATOR, new PersonBeanFromStringCreator(), "personbeantest.txt"));
        testData.addAll(getData(SORT, PERSON_BEAN_AGE_COMPARATOR, new PersonBeanFromStringCreator(), "personbeantest.txt"));
        return testData;
    }

    @Test
    public void Test() {
        List<T> result = sort.sort(input, comparator);
        Assert.assertTrue("Result array should be sorted in ascending order", testSorted(result, comparator));
        Assert.assertEquals("Result array length should be equal to original", input.size(), result.size());
        Assert.assertTrue("Result array should contain all elements of original", hasEachElementOf(input, result));
    }

    private static <T> boolean testSorted(List<T> array, Comparator<T> comparator) {
        for (int i = 0; i < array.size() - 1; i++) {
            if (comparator.compare(array.get(i), array.get(i + 1)) > 0)
                return false;
        }
        return true;
    }

    private static <T> boolean hasEachElementOf(List<T> input, List<T> result) {
        List<T> input_temp = new ArrayList<>(input);
        List<T> result_temp = new ArrayList<>(result);
        while (!input_temp.isEmpty()) {
            T element = input_temp.get(0);
            while (result_temp.remove(element)) {
                if (!(input_temp.remove(element)))
                    return false;
            }
            if (input_temp.contains(element))
                return false;
        }
        return true;
    }
}
