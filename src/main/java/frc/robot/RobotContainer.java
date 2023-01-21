package frc.robot;

import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

public class RobotContainer {
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem(Constants.Motors.motor);

  private final ExampleCommand command = new ExampleCommand(m_exampleSubsystem);

  public RobotContainer() {
    configureBindings();
  }


  private void configureBindings() {
    m_exampleSubsystem.setDefaultCommand(command);
  }

 
//   public Command getAutonomousCommand() {
//     // An example command will be run in autonomous
//     return Autos.exampleAuto(m_exampleSubsystem);
//   }
}
