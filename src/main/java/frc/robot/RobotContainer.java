package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {

  //Adding a pneumatic subsystem
  private final IntakeSubsystem IntakeSubsystem = new IntakeSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(IntakeSubsystem::OnPressed));
    Constants.XboxButtons.LEFT_BUMPER.onFalse(new InstantCommand(IntakeSubsystem::OnReleased));
  }
}
