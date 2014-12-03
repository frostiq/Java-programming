package Bazhanau.Task5;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Invoker {
    private JTextField thisParamField;
    private ArrayList<JTextField> paramFields;
    private Method method;

    public Invoker(Method method, ArrayList<JTextField> paramFields, JTextField thisParamField) {
        this.method = method;
        this.paramFields = paramFields;
        this.thisParamField = thisParamField;
    }

    public Object invoke() throws IllegalAccessException, InvocationTargetException {
        Object thisParam = null;
        Class[] paramsClass = method.getParameterTypes();
        Object[] params = new Object[method.getParameterCount()];
        if (!Modifier.isStatic(method.getModifiers())) {
            thisParam = Parser.parse(thisParamField.getText(), method.getDeclaringClass());
        }

        for (int i = 0; i < paramFields.size(); i++) {
            params[i] = Parser.parse(paramFields.get(i).getText(), paramsClass[i]);
        }

        return method.invoke(thisParam, params);
    }
}
