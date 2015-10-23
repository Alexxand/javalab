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
import java.lang.Class;

@RunWith(Parameterized.class)
public class SortTest {
    public static class PersonBean implements java.io.Serializable {
        private String name;
        private String surname;
        private Integer age;


        public PersonBean() {
        }

        public PersonBean(String name, String surname, int age){
            this.name = name;
            this.surname = surname;
            this.age = age;
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


    private static final Class<? extends Sort> SORT = MergeSort.class;
    //private static final Class<? extends Sort> SORT_BEAN = MergeSort.class;


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

    private static final Comparator<PersonBean> PERSON_BEAN_NAME_SURNAME_COMPARATOR = new Comparator<PersonBean>() {
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


    private static final PersonBean bean1 = new PersonBean("Александр","Петухов",19);
    private static final PersonBean bean2 = new PersonBean("Сергей","Макаров",20);

    private static final Object[][] TEST_DATA = {
            {SORT, DOUBLE_ASCENDING_COMPARATOR, Arrays.asList(new Double[]{1.0, 2.0, 3.0})},
            {SORT, DOUBLE_ASCENDING_COMPARATOR, Arrays.asList(new Double[]{3.0, 2.0, 1.0})},
            {SORT, DOUBLE_DESCENDING_COMPARATOR, Arrays.asList(new Double[]{3.0, 2.0, 7.0, 5.0, 5.0,3.12})},
            {SORT, DOUBLE_DESCENDING_COMPARATOR, Arrays.asList(new Double[]{})},
            {SORT, PERSON_BEAN_NAME_SURNAME_COMPARATOR,Arrays.asList(new PersonBean[]{})},
            {SORT, PERSON_BEAN_NAME_SURNAME_COMPARATOR,Arrays.asList(new PersonBean[]{bean1,bean2})}
    };


    @Parameterized.Parameters
    public static Collection<Object[]> testData()
    {
        return Arrays.asList(TEST_DATA);
    }

    private Sort sort;
    private Comparator comparator;
    private List<?> input;

    public SortTest(Class<? extends Sort> sort, Comparator comparator, List<Double> input) {
        try {
            this.sort = sort.newInstance();
            this.input = input;
            this.comparator = comparator;
        }
        catch(InstantiationException e) {
            System.out.println(e.toString());
        }
        catch(IllegalAccessException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void test() {
        List result = sort.sort(input, comparator);
        Assert.assertTrue("Result array should be sorted in ascending order", testSorted(result, comparator));
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

        private static <T> boolean hasEachElementOf(List<T> input, List<T> result) {
            List<T> input_temp = new ArrayList<T>(input);
            List<T> result_temp = new ArrayList<T>(result);
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
