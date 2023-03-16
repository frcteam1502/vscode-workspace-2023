package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.DriveTrain;

public class DriveByController extends CommandBase {
  private final DriveTrain drive;
  
  //TODO: Setup SlewRateLimiters for fwd and strafe speeds
  private final SlewRateLimiter fwdspeedlimiter = new SlewRateLimiter(1.25);
  private final SlewRateLimiter strafespeedlimiter = new SlewRateLimiter(1.25);
  private final SlewRateLimiter turnrateLimiter = new SlewRateLimiter(2.5);

  public DriveByController(DriveTrain drive) {
    this.drive = drive;
    addRequirements(drive);
    
  }

  @Override
  public void execute() {

    final var fwdSpeed =
        -fwdspeedlimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftY(), 0.1))
            * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;

    final var strafeSpeed =
        -strafespeedlimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftX(), 0.1))
            * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;

    final var rot =
        -turnrateLimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getRightX(), 0.1))
            * Constants.DriveConstants.MAX_ROTATION_RADIANS_PER_SECOND;
    
    if(Constants.XboxButtons.DRIVER_LEFT_BUMPER.getAsBoolean()) drive.drive(fwdSpeed/4, strafeSpeed/4, rot/4, true);
    else drive.drive(fwdSpeed, strafeSpeed, rot, true);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
