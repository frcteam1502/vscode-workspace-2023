package frc.robot.commands.Autonomous.Simple;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Move extends CommandBase {
  private final DriveTrain drive;
  
  private final double FwdSpeed;
  private final double CrabSpeed;
  private final boolean isFieldOriented;

  public Move(DriveTrain drive, double FwdSpeed, double CrabSpeed, boolean isFieldOriented) {
    this.drive = drive;
    this.FwdSpeed = FwdSpeed;
    this.CrabSpeed = CrabSpeed;
    this.isFieldOriented = isFieldOriented;
    addRequirements(drive);
  }

  public Move(DriveTrain drive, double FwdSpeed, double CrabSpeed) {
    this(drive, FwdSpeed, CrabSpeed, true);
    addRequirements(drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.drive(FwdSpeed, CrabSpeed, 0, isFieldOriented);
    System.out.print("Yes!");
  }


  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}