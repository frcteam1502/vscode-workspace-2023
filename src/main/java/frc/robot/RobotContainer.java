package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem(ArmConstants.LEAD_DEVICE_ID, ArmConstants.FOLOW_DEVICE_ID, ArmConstants.EXTEND_DEVICE_ID);
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(IntakeSubsystem.INTAKE_CONSTANTS.INTAKE_DEVICE_ID);
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(IntakeSubsystem::OnPressed).andThen(new WaitCommand(.25)));
    Constants.XboxButtons.LEFT_BUMPER.onFalse(new InstantCommand(IntakeSubsystem::OnReleased).andThen(new WaitCommand(.25)));
  }
}