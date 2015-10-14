

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Vector;

@RunWith(Parameterized.class)
public class SortTest {
    private static final MergeSort<Double> SORT = new MergeSort();
    private static final Object[][] TEST_DATA = {
            {SORT, new Double[]{1.0, 2.0, 3.0}},
            {SORT, new Double[]{3.0, 2.0, 1.0}},
            {SORT, new Double[]{1., 1., 1.}},
            {SORT, new Double[]{-131., 2524., 23., -352.5}},
    };
    private static final Comparator<Double> DOUBLE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(TEST_DATA);
    }

    private MergeSort<Double> sort;
    private Double[] input;

    public SortTest(MergeSort<Double> sort, Double[] input) {
        this.sort = sort;
        this.input = input;
    }

    @Test
    public void test() {
        Double[] result = sort.mergesort(input, DOUBLE_COMPARATOR);
        Assert.assertTrue("Result array should be sorted in ascending order", testAscendingOrder(result));
        Assert.assertEquals("Result array length should be equal to original", input.length, result.length);
        // Assert.assertTrue("Resalt array should contain all elemets of original",hasEachElementOf(,input, result));
    }

    private static boolean testAscendingOrder(Double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

        /*private static boolean hasEachElementOf(Double[] input, Double[] result) {
            Vector<Double> temp = Vector(result);
            return temp.containsAll(input);
        }*/
}
