package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.IBrownOutDetector;
import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.DriveTrain;

public class DriveByController extends CommandBase {
  private final DriveTrain drive;
  private final AdaptiveSpeedController speedController;
  
  class SpeedCommand {
    public double forwardSpeed;
    public double strafeSpeed;
    public double rotationSpeed;

    public SpeedCommand(double forwardSpeed, double strafeSpeed, double rotationSpeed) {
      this.forwardSpeed = forwardSpeed;
      this.strafeSpeed = strafeSpeed;
      this.rotationSpeed = rotationSpeed;
    }
  }

  class AdaptiveSpeedController {
    private final IBrownOutDetector brownOutDetector;
    private double slewRate;
    private double brakeRatio;
    private double maxRatio;
    private AdaptiveSlewRateLimiter fwdspeedlimiter;
    private AdaptiveSlewRateLimiter strafespeedlimiter;
    private AdaptiveSlewRateLimiter turnrateLimiter;

    public AdaptiveSpeedController(IBrownOutDetector brownOutDetector, double slewRate, double brakeRatio, double maxRatio)
    {
      this.brownOutDetector = brownOutDetector;
      this.slewRate = slewRate;
      this.brakeRatio = brakeRatio;
      this.maxRatio = maxRatio;
      fwdspeedlimiter = new AdaptiveSlewRateLimiter(slewRate);
      strafespeedlimiter = new AdaptiveSlewRateLimiter(slewRate);
      turnrateLimiter = new AdaptiveSlewRateLimiter(slewRate);
    }
    
    public SpeedCommand GetSpeedCommand(double forward, double strafe, double rotation, Boolean brake)
    {
      var ratio = maxRatio;
      if (brake) {
        ratio *= brakeRatio;
      }
      var fwdSpeed = fwdspeedlimiter.calculate(forward) * ratio;
      var strafeSpeed = strafespeedlimiter.calculate(strafe) * ratio;
      var rot = turnrateLimiter.calculate(rotation) * ratio;
      
      CheckBrownouts();

      return new SpeedCommand(fwdSpeed, strafeSpeed, rot);
    }

    void CheckBrownouts() {
      if (brownOutDetector.HasBrownout()) {
        if (slewRate > 2.0) {
          ChangeSlewRate(slewRate -= 0.1);
        } else if (maxRatio > 0.5) {
          maxRatio -= 0.05;
        }
      }
      SmartDashboard.putNumber("SlewRate", slewRate);
      SmartDashboard.putNumber("Max Ratio", maxRatio);
    }

    void ChangeSlewRate(double rate) {
      fwdspeedlimiter.ChangeRate(rate);
      strafespeedlimiter.ChangeRate(rate);
      turnrateLimiter.ChangeRate(rate);
    }
  }

  // since SlewRateLimiters can't change their rate, we will swap them out
  class AdaptiveSlewRateLimiter {
    private SlewRateLimiter speedLimiter;
    private double value;
    public AdaptiveSlewRateLimiter(double rateLimit) {
      speedLimiter = new SlewRateLimiter(rateLimit);
    }
    
    public double calculate(double input){
      value = speedLimiter.calculate(MathUtil.applyDeadband(input, 0.1));
      return value;
    }
    
    public void ChangeRate(double rate) {
      speedLimiter = new SlewRateLimiter(rate);
      speedLimiter.reset(value);
    }
  }

  public DriveByController(DriveTrain drive, IBrownOutDetector brownOutDetector) {
    this.drive = drive;
    this.speedController = new AdaptiveSpeedController(brownOutDetector, 3.0, 0.5, Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND);
    addRequirements(drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
    var speedCommand = speedController.GetSpeedCommand(
      Joysticks.DRIVE_CONTROLLER.getLeftY(), // Forward
      Joysticks.DRIVE_CONTROLLER.getLeftX(), // Strafe
      Joysticks.DRIVE_CONTROLLER.getRightX(), // Rotate
      Constants.XboxButtons.DRIVER_RIGHT_BUMPER.getAsBoolean()); // brake
  
    // invert the Y for forward, and also the X as we want left-positive values
    drive.drive(-speedCommand.forwardSpeed, -speedCommand.strafeSpeed, -speedCommand.rotationSpeed, true);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
