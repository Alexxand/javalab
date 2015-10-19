import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class SortTest {
    private static final MergeSort<Double> SORT = new MergeSort<Double>();
    private static final Object[][] TEST_DATA = {
            {SORT, Arrays.asList(new Double[]{1.0, 2.0, 3.0})},
            {SORT, Arrays.asList(new Double[]{3.0, 2.0, 1.0})},
            {SORT, Arrays.asList(new Double[]{3.0, 2.0, 7.0, 5.0, 5.0,3.12})},
            {SORT, Arrays.asList(new Double[]{})}
    };
    private static final Comparator<Double> DOUBLE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };

    @Parameterized.Parameters
    public static Collection<Object[]> testData()
    {

        return Arrays.asList(TEST_DATA);
    }

    private MergeSort<Double> sort;
    private List<Double> input;

    public SortTest(MergeSort<Double> sort, List<Double> input) {
        this.sort = sort;
        this.input = input;
    }

    @Test
    public void test() {
        List<Double> result = sort.mergesort(input, DOUBLE_COMPARATOR);
        Assert.assertTrue("Result array should be sorted in ascending order", testAscendingOrder(result));
        Assert.assertEquals("Result array length should be equal to original", input.size(), result.size());
        Assert.assertTrue("Result array should contain all elements of original",hasEachElementOf(input, result));
    }

    private static boolean testAscendingOrder(List<Double> array) {
        for (int i = 0; i < array.size() - 1; i++) {
            if (array.get(i) > array.get(i + 1))
                return false;
        }
        return true;
    }

        private static boolean hasEachElementOf(List<Double> input, List<Double> result) {
            List<Double> input_temp = new ArrayList<Double>(input);
            List<Double> result_temp = new ArrayList<Double>(result);
            while (!input_temp.isEmpty()) {
                Double element = input_temp.get(0);
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
