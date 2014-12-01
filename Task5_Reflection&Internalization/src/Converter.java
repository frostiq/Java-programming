public class Converter {
    public static Object convert(String s, Class paramClass) {
        if (paramClass.equals(int.class)) {
            return Integer.parseInt(s);
        } else if (paramClass.equals(String.class)) {
            return s;
        } else {
            throw new ClassCastException("unsupported type");
        }
    }
}
