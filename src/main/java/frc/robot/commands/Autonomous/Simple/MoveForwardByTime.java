package frc.robot.commands.Autonomous.Simple;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class MoveForwardByTime extends CommandBase {
  private final DriveTrain drive;
  
  private final Timer timer = new Timer();

  private final double time;
  private final double speed;
  private final boolean isFieldOriented;

  public MoveForwardByTime(DriveTrain drive, double speed, double time, boolean isFieldOriented) {
    this.drive = drive;
    this.time = time;
    this.speed = speed;
    this.isFieldOriented = isFieldOriented;
    addRequirements(drive);
  }

  public MoveForwardByTime(DriveTrain drive, double speed, double time) {
    this(drive, speed, time, true);
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    drive.drive(speed, 0, 0, isFieldOriented);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(time);
  }
}