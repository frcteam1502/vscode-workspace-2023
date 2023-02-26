package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

  //Adding a pneumatic subsystem
  private final IntakeSubsystem IntakeSubsystem = new IntakeSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(IntakeSubsystem::OnPressed).andThen(new WaitCommand(.25)));
    Constants.XboxButtons.LEFT_BUMPER.onFalse(new InstantCommand(IntakeSubsystem::OnReleased).andThen(new WaitCommand(.25)));
  }
}