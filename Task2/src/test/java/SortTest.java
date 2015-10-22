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
    public class PersonBean implements java.io.Serializable {
        private String name;
        private String surname;
        private Integer age;


        public PersonBean() {
        }

        public String getName() {
            return (this.name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return (this.surname);
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Integer getAge() {
            return (this.age);
        }

        public void setAge(int age) {
            this.age = age;
        }
}


    private static final MergeSort<Double> SORT = new MergeSort<Double>();
    private static final Object[][] TEST_DATA = {
            {SORT, Arrays.asList(new Double[]{1.0, 2.0, 3.0})},
            {SORT, Arrays.asList(new Double[]{3.0, 2.0, 1.0})},
            {SORT, Arrays.asList(new Double[]{3.0, 2.0, 7.0, 5.0, 5.0,3.12})},
            {SORT, Arrays.asList(new Double[]{})}
    };
    private static final Comparator<Double> DOUBLE_ASCENDING_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };

    private static final Comparator<Double> DOUBLE_DESCENDING_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return -o1.compareTo(o2);
        }
    };

    private static final Comparator<PersonBean> PERSONBEAN_NAME_SURNAME_COMPARATOR = new Comparator<PersonBean>() {
        public int compare(final PersonBean o1, final PersonBean o2) {
            int temp = o1.getSurname().compareTo(o2.getSurname()) ;
            if (temp == 0)
                return o1.getName().compareTo(o2.getName());
            else
              return temp;
        }
    };

    private static final Comparator<PersonBean> PERSON_BEAN_AGE_COMPARATOR = new Comparator<PersonBean>() {
        public int compare(final PersonBean o1, final PersonBean o2) {
            return o1.getAge().compareTo(o2.getAge());
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
    public void test_double_ascending() {
        List<Double> result = sort.mergesort(input, DOUBLE_ASCENDING_COMPARATOR);
        Assert.assertTrue("Result array should be sorted in ascending order", testSorted(result, DOUBLE_ASCENDING_COMPARATOR));
        Assert.assertEquals("Result array length should be equal to original", input.size(), result.size());
        Assert.assertTrue("Result array should contain all elements of original",hasEachElementOf(input, result));
    }

    @Test
    public void test_double_descending() {
        List<Double> result = sort.mergesort(input, DOUBLE_DESCENDING_COMPARATOR);
        Assert.assertTrue("Result array should be sorted in ascending order", testSorted(result, DOUBLE_DESCENDING_COMPARATOR));
        Assert.assertEquals("Result array length should be equal to original", input.size(), result.size());
        Assert.assertTrue("Result array should contain all elements of original",hasEachElementOf(input, result));
    }

    private static<T> boolean testSorted(List<T> array,Comparator<T> comparator) {
        for (int i = 0; i < array.size() - 1; i++) {
            if (comparator.compare(array.get(i), array.get(i + 1)) > 0)
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
