package injection;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandFactory extends RobotPart {
    public static boolean isSubsystem(Class<?> candidate) {
        return Command.class.isAssignableFrom(candidate);
    }

    public CommandFactory(Class<Command> subsystemClass) {
        super(subsystemClass);
        //TODO Auto-generated constructor stub
    }
    
}
