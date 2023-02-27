package frc.robot.commands.Autonomous.Simple;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  private final DriveTrain drive;

  private final PIDController balancePID = new PIDController(.3, 0, 0);

  private final double MAXSPEED = .1;


  public AutoBalance(DriveTrain drive) {
    this.drive = drive;

    addRequirements(drive);

    balancePID.setTolerance(2);
    balancePID.setSetpoint(0);
  }

  @Override
  public void execute() {
    drive.drive(MathUtil.clamp(-balancePID.calculate(drive.getRoll())*5, -MAXSPEED, MAXSPEED), 0, 0, true);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}