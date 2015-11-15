public class PersonBeanFromStringCreator extends FromStringCreator {
    public Object factoryMethod(String s) {
        String[] stringArr = s.split(" ");
        return new PersonBean(stringArr[0], stringArr[1], new Integer(stringArr[2]));
    }
}
