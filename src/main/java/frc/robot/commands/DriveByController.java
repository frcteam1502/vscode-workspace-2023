package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Constants.Joysticks;
import frc.robot.libraries.IBrownOutDetector;
import frc.robot.subsystems.IDrive;
import frc.robot.SwerveConstants;

public class DriveByController extends CommandBase {
  private final IDrive drive;
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
    private AdaptiveSlewRateLimiter forwardSpeedlimiter;
    private AdaptiveSlewRateLimiter strafespeedlimiter;
    private AdaptiveSlewRateLimiter turnrateLimiter;

    public AdaptiveSpeedController(IBrownOutDetector brownOutDetector, double slewRate, double brakeRatio, double maxRatio) {
      this.brownOutDetector = brownOutDetector;
      this.slewRate = slewRate;
      this.brakeRatio = brakeRatio;
      this.maxRatio = maxRatio;
      forwardSpeedlimiter = new AdaptiveSlewRateLimiter(slewRate);
      strafespeedlimiter = new AdaptiveSlewRateLimiter(slewRate * .95);
      turnrateLimiter = new AdaptiveSlewRateLimiter(slewRate * 1.8);
    }
    
    public SpeedCommand GetSpeedCommand(double forward, double strafe, double rotation, Boolean brake) {
      var ratio = maxRatio;
      if (brake) {
        ratio *= brakeRatio;
      }
      var forwardSpeed = forwardSpeedlimiter.calculate(forward) * ratio;
      var strafeSpeed = strafespeedlimiter.calculate(strafe) * ratio;
      var rot = turnrateLimiter.calculate(rotation) * ratio;
      
      CheckBrownouts();

      return new SpeedCommand(forwardSpeed, strafeSpeed, rot);
    }

    void CheckBrownouts() {
      if (brownOutDetector.NeedsLimiting()) {
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
      forwardSpeedlimiter.ChangeRate(rate);
      strafespeedlimiter.ChangeRate(rate * .95);
      turnrateLimiter.ChangeRate(rate * 1.8);
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

  public DriveByController(IDrive drive, IBrownOutDetector brownOutDetector) {
    this.drive = drive;
    this.speedController = new AdaptiveSpeedController(brownOutDetector, 3.0, 0.3, SwerveConstants.DriveConstants.MAX_SPEED_METERS_PER_SECOND);
    addRequirements((Subsystem)drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    var speedCommand = speedController.GetSpeedCommand(
      Joysticks.DRIVE_CONTROLLER.getLeftY(), // Forward
      Joysticks.DRIVE_CONTROLLER.getLeftX(), // Strafe
      Joysticks.DRIVE_CONTROLLER.getRightX(), // Rotate
      Constants.XboxButtons.DRIVER_LEFT_BUMPER.getAsBoolean()); // brake
  
    drive.drive(-speedCommand.forwardSpeed, -speedCommand.strafeSpeed, -speedCommand.rotationSpeed, true);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}