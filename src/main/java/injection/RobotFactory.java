package injection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.annotations.*;

public class RobotFactory {
    public static Class<SubsystemInfo> subsystemAnnotation = SubsystemInfo.class;
    private static Class<Robot> robotClass = Robot.class;
    private static String subsystemPackageName = "frc.robot.subsystems";
    private static String commandPackageName = "frc.robot.commands";

    public static RobotFactory Create() throws ClassNotFoundException {
        return new RobotFactory();
    }

    private RobotFactory() {

    }

    private Set<Subsystem> subsystems = new HashSet<>();

    public Set<Subsystem>  createSubsystems() {
        Set<Class<Subsystem>> enabled = getSubsystems();
        //enabled.forEach((Class<?> s) -> { s.getConstructor().createNewInstance() } };
        for (Class<Subsystem> subsystemClass : enabled) {
            try {
                java.lang.reflect.Constructor<Subsystem> subsystemCtor = subsystemClass.getConstructor();
                System.out.println(subsystemClass.getName() + " has a constructor()");
                if (false) {
                    Subsystem subsystem = subsystemCtor.newInstance();
                }
                //subsystems.add(subsystem);
                // Do something with obj
            } catch (NoSuchMethodException nsm) {
                System.out.println(subsystemClass.getName() + " does not have a constructor()");
                System.err.println(nsm);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
                // Handle exception
                System.err.println(e);
            } catch (InvocationTargetException ie) {
                System.err.println(ie);
            }
        }
        return subsystems;
    }
    
    public Set<Class<Subsystem>> getSubsystems() {
        Set<Class<Subsystem>> subsystems = getAllSubsystems();
        Set<Class<Subsystem>> enabled = subsystems.stream()
        .filter(s -> isEnabled(s))
        .collect(Collectors.toSet());
        return enabled;
    }

    private boolean isEnabled(Class<?> subsystem) {
        SubsystemInfo annotation = subsystem.getDeclaredAnnotation(subsystemAnnotation);
        return annotation == null ? true : !annotation.disabled();   
    }

    private Set<Class<Subsystem>> getAllSubsystems() {
        ClassLoader loader = robotClass.getClassLoader();
        InputStream stream = loader.getResourceAsStream(subsystemPackageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, subsystemPackageName))
            .filter(line -> SubsystemFactory.isSubsystem(line))
            .map(line -> (Class<Subsystem>)line)
            .collect(Collectors.toSet());
    }

    private Set<Class<Command>> getAllCommands() {
        ClassLoader loader = robotClass.getClassLoader();
        InputStream stream = loader.getResourceAsStream(commandPackageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, commandPackageName))
            .filter(line -> CommandFactory.isSubsystem(line))
            .map(line -> (Class<Command>)line)
            .collect(Collectors.toSet());
    }
 

    private Class<?> getClass(String className, String packageName) {
        try {
            Class<?> candidate = Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
            return candidate;
            
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    private Class<?> isAssignableFrom(Class<?> candidate) {
        Class<?> superClass = candidate.getSuperclass();
        do {
            if (superClass == null) return null; //
            
            String name = superClass.getName();
            if (name == "edu.wpi.first.wpilibj2.command.SubsystemBase") {
                return (Class<SubsystemBase>) candidate;
            }
            else if (name == "java.lang.Object") {
                return null;
            } else {
                superClass = superClass.getSuperclass();
            }


        } while(true);

    }

}
