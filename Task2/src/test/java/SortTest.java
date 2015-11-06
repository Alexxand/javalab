import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.lang.Class;

@RunWith(Parameterized.class)
public class SortTest<T> {
    public static class PersonBean implements java.io.Serializable {
        private String name;
        private String surname;
        private int age;

        /*public PersonBean(String name, String surname, int age){
            this.name = name;
            this.surname = surname;
            this.age = age;
        }*/

        public PersonBean(String s){
            String[] stringArr = s.split(" ");
            this.name = stringArr[0];
            this.surname = stringArr[1];
            this.age = new Integer(stringArr[2]);
        }

        public String getName() {
            return (this.name);
        }

        /*public void setName(String name) {
            this.name = name;
        }*/

        public String getSurname() {
            return (this.surname);
        }

        /*public void setSurname(String surname) {
            this.surname = surname;
        }*/

        public int getAge() {
            return (this.age);
        }

        /*public void setAge(int age) {
            this.age = age;
        }*/
}


    private static final Class<? extends Sort> SORT = MergeSort.class;



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
            return o1.getAge() - o2.getAge();
        }
    };





    private static<E> List<E> ListFromStringArr (String[] stringArr,Class<E> elementClass)
    {
        try{
            Class types[] = new Class[] {String.class};
            Constructor<E> elementConstructor = elementClass.getConstructor(types);
            List<E> result = new ArrayList<>();
            for(String s: stringArr) {
                Object args[] = new Object[] {s};
                if (!s.trim().isEmpty()) {
                    result.add(elementConstructor.newInstance(args));
                }
            }
            return result;

        }
        catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.toString());
            return new ArrayList<>();
        }
    }

    /*private<E> List<E> GetList(){

    }*/

    private static<E> List<Object[]> GetData(Class<? extends Sort> sort, Comparator<E> comparator,Class<E> typeElem, String fileName) {
        try {
            List<Object[]> result = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stringArr = line.split(", ");
                Object[] listElem = new Object[] {sort,comparator, ListFromStringArr(stringArr,typeElem)};
                result.add(listElem);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.toString());
            return new ArrayList<>();
        }
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData()
    {
        List<Object[]> testData = new ArrayList<>();

        try {
            testData.addAll(GetData(SORT, DOUBLE_ASCENDING_COMPARATOR, Double.class, "doubletest.txt"));
            testData.addAll(GetData(SORT, DOUBLE_DESCENDING_COMPARATOR, Double.class, "doubletest.txt"));
            testData.addAll(GetData(SORT, PERSON_BEAN_NAME_SURNAME_COMPARATOR, PersonBean.class, "personbeantest.txt"));
            testData.addAll(GetData(SORT, PERSON_BEAN_AGE_COMPARATOR, PersonBean.class, "personbeantest.txt"));
            return testData;
        }
        catch(NullPointerException e) {
            System.out.println(e.toString());
            return testData;
        }

    }

    private Sort<T> sort;
    private Comparator<T> comparator;
    private List<T> input;

    public SortTest(Class<? extends Sort<T>> sort, Comparator<T> comparator, List<T> input) {
        try {
            this.sort = sort.newInstance();
            this.input = input;
            this.comparator = comparator;
        }
        catch(InstantiationException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void test() {

        List<T> result = sort.sort( input, comparator);
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
