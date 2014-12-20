package Bazhanau.Task5;

public class Parser {
    public static Object parse(String s, Class paramClass) {
        if (paramClass.equals(int.class)) {
            return Integer.parseInt(s);
        } else if (paramClass.equals(double.class)) {
            return Double.parseDouble(s);
        } else if (paramClass.equals(String.class)) {
            return s;
        } else {
            throw new ClassCastException("Unsupported type");
        }
    }
}
