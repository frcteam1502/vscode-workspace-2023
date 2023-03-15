package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PdpSubsystem;

public class RobotContainer {
  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain();
  private final PdpSubsystem pdpSubsystem;

  public RobotContainer() {
    pdpSubsystem = new PdpSubsystem();
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
  }

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}