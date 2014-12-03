package Bazhanau.Task5;

import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodsParser {
    private static final Pattern regex = Pattern.compile("([\\w\\.]+)\\.(\\w+)\\(([\\w\\.,]*)\\)");

    private static Map<String, Class> builtInMap = new HashMap<String, Class>();

    static {
        builtInMap.put("int", Integer.TYPE);
        builtInMap.put("long", Long.TYPE);
        builtInMap.put("double", Double.TYPE);
        builtInMap.put("float", Float.TYPE);
        builtInMap.put("bool", Boolean.TYPE);
        builtInMap.put("char", Character.TYPE);
        builtInMap.put("byte", Byte.TYPE);
        builtInMap.put("void", Void.TYPE);
        builtInMap.put("short", Short.TYPE);
    }

    public static Collection<Method> parse(String methodsString) throws ClassNotFoundException, NoSuchMethodException {
        String[] methods = methodsString.split("\\|");
        ArrayList<Method> res = new ArrayList<>(methods.length);
        for (String methodSignature : methods) {
            Matcher matcher = regex.matcher(methodSignature);
            if (matcher.find()) {
                String className = matcher.group(1);
                String methodName = matcher.group(2);
                String[] paramNames = matcher.group(3).split(",");

                Class[] params = getParams(paramNames);
                Class methodClass = Class.forName(className);
                Method method = methodClass.getMethod(methodName, params);
                res.add(method);
            } else throw new InvalidParameterException("Method signatures is invalid");
        }
        return res;
    }

    private static Class[] getParams(String[] paramNames) throws ClassNotFoundException {
        if (paramNames[0].isEmpty()) {
            return null;
        }

        Class[] params = new Class[paramNames.length];
        for (int i = 0; i < paramNames.length; i++) {
            if (builtInMap.containsKey(paramNames[i])) {
                params[i] = builtInMap.get(paramNames[i]);
            } else {
                params[i] = Class.forName(paramNames[i]);
            }
        }

        return params;
    }
}
