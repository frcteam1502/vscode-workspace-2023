package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.DriveTrain;

public class DriveByController extends CommandBase {
  private final DriveTrain drive;
  
  //Setup SlewRateLimiters for fwd and strafe speeds
  private final SlewRateLimiter fwdspeedlimiter = new SlewRateLimiter(1.7);
  private final SlewRateLimiter strafespeedlimiter = new SlewRateLimiter(1.7);
  private final SlewRateLimiter turnrateLimiter = new SlewRateLimiter(3);

  public DriveByController(DriveTrain drive) {
    this.drive = drive;
    addRequirements(drive);
    
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    // Get the forward speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var fwdSpeed =
        -fwdspeedlimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftY(), 0.1))
            * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;

    // Get the sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var strafeSpeed =
        -strafespeedlimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getLeftX(), 0.1))
            * Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot =
        -turnrateLimiter.calculate(MathUtil.applyDeadband(Joysticks.DRIVE_CONTROLLER.getRightX(), 0.1))
            * Constants.DriveConstants.MAX_ROTATION_RADIANS_PER_SECOND;
    
    //Set up the Drivetrain setpoints
    if(Constants.XboxButtons.DRIVER_RIGHT_BUMPER.getAsBoolean()) drive.drive(fwdSpeed/2, strafeSpeed/2, rot/2, true);
    else drive.drive(fwdSpeed, strafeSpeed, rot, true);

  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
