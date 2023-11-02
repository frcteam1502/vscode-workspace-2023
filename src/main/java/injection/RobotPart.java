package injection;

import java.lang.reflect.Constructor;

public class RobotPart {

    public static Class<?>[] getInterfaces(Class<?> partClass) {
        return partClass.getInterfaces();
    } 
    
    public static Constructor<?> getConstructor(Class<?> partClass) {
        Constructor<?> constructor = null;
        Constructor<?>[] constructors = partClass.getDeclaredConstructors(); // get the array of constructors
        for (Constructor<?> candidate : constructors) {
            Class<?>[] parameterTypes = candidate.getParameterTypes(); // get the array of parameter types
            System.out.println(candidate); // print the constructor
            for (Class<?> p : parameterTypes) {
                if (p.equals(Configuration.class)) {
                    constructor = candidate;
                }
            }
        }
        return constructor;
    }

    String name;

    public RobotPart(Class<?> partClass) {
        name = partClass.getSimpleName();
        name = name.substring(name.lastIndexOf('.') + 1);
     
    }
}
