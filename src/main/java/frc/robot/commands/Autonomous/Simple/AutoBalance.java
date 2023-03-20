package frc.robot.commands.Autonomous.Simple;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoBalance extends CommandBase {
  private final DriveTrain drive;

  private final PIDController balancePID = new PIDController(.4, 0, .05);

  private final SlewRateLimiter balanceSlew = new SlewRateLimiter(.09);

  private final double MAXSPEED = .15;

  private double lastPose = 0;

  public AutoBalance(DriveTrain drive) {
    this.drive = drive;

    addRequirements(drive);

    balancePID.setTolerance(9);
    balancePID.setSetpoint(0);
  }

  @Override
  public void execute() {
    drive.drive(balanceSlew.calculate(MathUtil.clamp(balancePID.calculate(drive.getRoll())*5, -MAXSPEED, MAXSPEED)), 0, 0, true);
    if(Math.abs(drive.getRoll() - lastPose) > .5) {
      drive.drive(0, 0, 0, true);
      Timer.delay(1.1);
    }  
    lastPose = drive.getRoll();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return balancePID.atSetpoint();
  }
}