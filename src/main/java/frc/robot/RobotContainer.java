package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.commands.Auto.EmptyAutoCommand;
import frc.robot.commands.DriveByController;
import frc.robot.commands.pneumaticCommand;

import frc.robot.Constants.Motors;
import frc.robot.Constants.XboxButtons;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.pneumaticSubsystem;

public class RobotContainer {
  //Subsystems
  // The robot's subsystems and commands are defined here...
  
  private final DriveTrain driveSubsystem = new DriveTrain();
  
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(Motors.INTAKE_MOTOR);
  
  private final pneumaticSubsystem pneumaticSubsystem = new pneumaticSubsystem();
  private final pneumaticCommand pneumaticCommand = new pneumaticCommand(pneumaticSubsystem);
  
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem(Motors.INTAKE_MOTOR);
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
  
    XboxButtons.BUTTON_A.whileTrue(intakeSubsystem.runIntakeCommand()); // TODO: assign trigger
  
    XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveOut));
    XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveIn));
  }

  // TODO: implement SendableChooser
  public Command getAutonomousCommand() {
    return new EmptyAutoCommand(m_exampleSubsystem);
  }
}