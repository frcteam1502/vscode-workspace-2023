package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.Constants.XboxButtons;

public class RobotContainer {
  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    XboxButtons.BUTTON_A.whileTrue(intakeSubsystem.runIntakeCommand);
  }

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}