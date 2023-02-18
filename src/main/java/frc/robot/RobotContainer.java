package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.commands.IntakeCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.XboxButtons;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem(ArmConstants.LEAD_DEVICE_ID, ArmConstants.FOLOW_DEVICE_ID, ArmConstants.EXTEND_DEVICE_ID);
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(IntakeConstants.INTAKE_DEVICE_ID);
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    intakeSubsystem.setDefaultCommand(new IntakeCommand(intakeSubsystem));
    //XboxButtons.LEFT_BUMPER.whileTrue(new InstantCommand(IntakeSubsystem::TurnOnIntake)); // TODO: assign trigger
  
    XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::GoToTop));
    XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::GoToMiddle));
    XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::GoToFloor));
    XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::GoToStow));
  }
}