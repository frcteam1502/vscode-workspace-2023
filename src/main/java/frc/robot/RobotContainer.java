package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.Auto.EmptyAutoCommand;

public class RobotContainer {
  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(frc.robot.Constants.Motors.INTAKE_MOTOR);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    XboxButtons.BUTTON_A.whileTrue(intakeSubsystem.runIntakeCommand()); // TODO: assign trigger
  }

  // TODO: implement SendableChooser
  public Command getAutonomousCommand() {
    return new EmptyAutoCommand();
  }
}