package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PdpSubsystem;
import frc.robot.subsystems.TimeOfFlightSubsystem;

public class RobotContainer {
  //Subsystems
  private final DriveTrain driveSubsystem;
  private final PdpSubsystem pdpSubsystem;
  private final TimeOfFlightSubsystem tofSubsystem;

  public RobotContainer() {
    driveSubsystem = new DriveTrain();
    pdpSubsystem = new PdpSubsystem();
    tofSubsystem = new TimeOfFlightSubsystem();
    
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem, pdpSubsystem));
  }

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}