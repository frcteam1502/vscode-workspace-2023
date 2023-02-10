package frc.robot.commands.Autonomous.Simple;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  private final DriveTrain drive;
  private final double tolerance = .25;

  public AutoBalance(DriveTrain drive) {
    this.drive = drive;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    drive.drive(-drive.getPitch()/4, 0, 0, true);
  }

  @Override
  public void end(boolean interrupted) {}

  public boolean atPosition() {
    return drive.getPitch() >= -tolerance && drive.getPitch() <= tolerance;
  }

  @Override
  public boolean isFinished() {
    return atPosition();
  }
}
