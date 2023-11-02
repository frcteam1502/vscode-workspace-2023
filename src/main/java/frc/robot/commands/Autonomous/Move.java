package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IDrive;

public class Move extends CommandBase {
  private final IDrive drive;
  
  private final double FwdSpeed;
  private final double CrabSpeed;
  private final boolean isFieldOriented;

  public Move(IDrive drive, double FwdSpeed, double CrabSpeed, boolean isFieldOriented) {
    this.drive = drive;
    this.FwdSpeed = FwdSpeed;
    this.CrabSpeed = CrabSpeed;
    this.isFieldOriented = isFieldOriented;
    addRequirements((Subsystem)drive);
  }

  public Move(IDrive drive, double FwdSpeed, double CrabSpeed) {
    this(drive, FwdSpeed, CrabSpeed, true);
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