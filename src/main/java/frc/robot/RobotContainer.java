package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.XboxButtons;
import frc.robot.subsystems.GripperSubsystem;

public class RobotContainer {

  //Subsystems
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    XboxButtons.RIGHT_BUMPER.toggleOnTrue(new InstantCommand(gripperSubsystem::toggleGripper));
  }

  public Command getAutonomousCommand() {
    return null;
  } 
}