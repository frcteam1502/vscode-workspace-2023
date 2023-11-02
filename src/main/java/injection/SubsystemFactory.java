package injection;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class SubsystemFactory extends RobotPart {
    public static boolean isSubsystem(Class<?> candidate) {
        return Subsystem.class.isAssignableFrom(candidate);
    }

    public SubsystemFactory(Class<Subsystem> subsystemClass) {
        super(subsystemClass);
        //TODO Auto-generated constructor stub
    }
    
}
