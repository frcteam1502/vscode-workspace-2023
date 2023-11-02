import java.util.Set;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//import org.reflections.Reflections;
import static org.junit.jupiter.api.Assertions.assertEquals;

import frc.robot.annotations.*;
import frc.robot.subsystems.PdpSubsystem;
import injection.RobotFactory;

public class DITests {

    static Class<SubsystemInfo> subsystemAnnotation;

    @Test
    public void createFactorySubsystems() throws ClassNotFoundException {
        RobotFactory factory = RobotFactory.Create();
        Set<SubsystemBase> enabled = factory.createSubsystems();

        assertEquals(4, enabled.size(), "only expected 5");    
        
    }

    @Test
    public void GetFactorySubsystems() throws ClassNotFoundException {
        RobotFactory factory = RobotFactory.Create();
        Set<Class<SubsystemBase>> enabled = factory.getSubsystems();

        assertEquals(1, enabled.size(), "only expected 5");    
        
    }

    @Test
    public void Test1() throws ClassNotFoundException {
        subsystemAnnotation = (Class<SubsystemInfo>)Class.forName("frc.robot.annotations.SubsystemInfo");
     
        Set<Class<?>> subsystems = GetSubsystems();
        Set<Class<?>> enabled = subsystems.stream()
            .filter(s -> IsEnabled(s))
            .collect(Collectors.toSet());

        assertEquals(4, enabled.size(), "only expected 5");    
        
        
    }
    
    private boolean IsEnabled(Class<?> subsystem) {
        SubsystemInfo annotation = subsystem.getDeclaredAnnotation(subsystemAnnotation);
        return annotation == null ? true : !annotation.disabled();   
    }

    private Set<Class<?>> GetSubsystems() {
        String packageName = "frc.robot.subsystems";
        Class<?> robotClass;
        try {
            robotClass = Class.forName("frc.robot.Robot");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
        ClassLoader loader = robotClass.getClassLoader();
        InputStream stream = loader.getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, packageName))
            .collect(Collectors.toSet());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }


}