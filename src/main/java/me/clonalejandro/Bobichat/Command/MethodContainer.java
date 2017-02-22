package me.clonalejandro.Bobichat.Command;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author TheJeterLP
 */
public class MethodContainer {

    private final HashMap<BaseCommand.Sender, Method> methods;

    public MethodContainer(HashMap<BaseCommand.Sender, Method> map) {
        methods = map;
    }
      
    public Method getMethod(BaseCommand.Sender s) {
        return methods.get(s);
    }

    public Collection<Method> getMethods() {
        return methods.values();
    }

    public HashMap<BaseCommand.Sender, Method> getMethodMap() {
        return methods;
    }
    
    

}
