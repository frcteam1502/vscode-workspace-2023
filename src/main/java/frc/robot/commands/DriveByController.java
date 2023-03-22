package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
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
  /* 
    NOTE: all of this is linear, and needs plenty of data to be tuned
    If voltage dips are detected, the slew rates are reduced bit to try to prevent it.
    Lowering all power is probably also required, but seems to be dependent on current "resting V" above brownout V.
    Current logic drops slew rates, but when at there minimumn, reduces max power instead.
    */
    private final class SLEW_CONSTANTS {
      // forward/backward and strafe
      public final static double SLEW_RATE = 3.2;
      public final static double SLEW_RATE_MIN = 2.0; // After this point, start reducing power
      public final static double SLEW_RATE_REDUCTION = 0.1;
      
      public final static double RATE_REDUCTION = 0.05; // reduce total power step
      public final static double RATE_MIN = 0.5; // Do not reduce power more than this
      
      // rotation
      public final static double SLEW_ROTATION_RATE = 2.5;
      public final static double SLEW_ROTATION_RATE_MIN = 2.0;
      public final static double SLEW_ROTATION_REDUCTION = 0.05;
      
      // boost and brake
      // This BOOST scheme is faultly, can't boost past 1.0 (MAX motor speed) because 1) `desaturateWheelSpeeds()` and 2) Motors can't go faster than 1.0
      public final static double BOOST = 1.5; // perhaps useful when otherwise limited i.e., when battery low
      public final static double BRAKE = 0.5;
    }

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
    private double slewRate = SLEW_CONSTANTS.SLEW_RATE;
    private double rotateRate = SLEW_CONSTANTS.SLEW_ROTATION_RATE;
    private double brakeRatio = SLEW_CONSTANTS.BRAKE;
    private double boostRatio = SLEW_CONSTANTS.BOOST;
    private double maxRatio = 1.0;
    private AdaptiveSlewRateLimiter fwdspeedlimiter;
    private AdaptiveSlewRateLimiter strafespeedlimiter;
    private AdaptiveSlewRateLimiter turnrateLimiter;

    public AdaptiveSpeedController(IBrownOutDetector brownOutDetector) {
      this.brownOutDetector = brownOutDetector;
    }

    public AdaptiveSpeedController(IBrownOutDetector brownOutDetector, double slewRate, double brakeRatio, double maxRatio)
    {
      this.brownOutDetector = brownOutDetector;
      this.slewRate = slewRate;
      this.brakeRatio = brakeRatio;
      this.maxRatio = maxRatio;
    }
    
    public AdaptiveSpeedController Slew(double rate) {
      this.slewRate = rate;
      return this;
    }

    public AdaptiveSpeedController RotationSlew(double rate) {
      this.rotateRate = rate;
      return this;
    }

    public AdaptiveSpeedController Boost(double rate) {
      this.boostRatio = rate;
      return this;
    }

    public AdaptiveSpeedController Brake(double rate) {
      this.brakeRatio = rate;
      return this;
    }

    private void VerifyLimiters() {
      if (fwdspeedlimiter == null) {
        fwdspeedlimiter = new AdaptiveSlewRateLimiter(slewRate);
        strafespeedlimiter = new AdaptiveSlewRateLimiter(slewRate);
        turnrateLimiter = new AdaptiveSlewRateLimiter(rotateRate);
      }
    }

    public SpeedCommand GetSpeedCommand(double forward, double strafe, double rotation, Boolean brake, Boolean boost)
    {
      VerifyLimiters(); //  make sure limiters are initialized;
      var ratio = maxRatio;
      if (brake) {
        ratio *= brakeRatio;
      }
      
      // don't boost rotation
      var rot = turnrateLimiter.calculate(rotation) * ratio;
     
      if (boost) {
        ratio *= boostRatio;
      }
      var fwdSpeed = fwdspeedlimiter.calculate(forward) * ratio;
      var strafeSpeed = strafespeedlimiter.calculate(strafe) * ratio;
      
      CheckBrownouts();

      return new SpeedCommand(fwdSpeed, strafeSpeed, rot);
    }

    void CheckBrownouts() {
      if (brownOutDetector.HasBrownout()) {
        if (slewRate > SLEW_CONSTANTS.SLEW_ROTATION_RATE_MIN) {
          ChangeRotationRate(rotateRate -= SLEW_CONSTANTS.SLEW_ROTATION_REDUCTION);
        }
        if (slewRate > SLEW_CONSTANTS.SLEW_RATE_MIN) {
          ChangeSlewRate(slewRate -= SLEW_CONSTANTS.SLEW_RATE_REDUCTION);
        } else if (maxRatio > SLEW_CONSTANTS.RATE_MIN) {
          maxRatio -= SLEW_CONSTANTS.RATE_REDUCTION;
        }
      }

      SmartDashboard.putNumber("Slew Rate", slewRate);
      SmartDashboard.putNumber("Rotate Rate", rotateRate);
      SmartDashboard.putNumber("Max Ratio", maxRatio);
    }

    void ChangeSlewRate(double rate) {
      fwdspeedlimiter.ChangeRate(rate);
      strafespeedlimiter.ChangeRate(rate);
    }
    void ChangeRotationRate(double rate) {
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
    addRequirements(drive);

    this.speedController = new AdaptiveSpeedController(brownOutDetector)
      .Slew(SLEW_CONSTANTS.SLEW_RATE)
      .RotationSlew(SLEW_CONSTANTS.SLEW_ROTATION_RATE) // different slew rate for rotations to ease control
      .Boost(SLEW_CONSTANTS.BOOST)
      .Brake(SLEW_CONSTANTS.BRAKE);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
    var speedCommand = speedController.GetSpeedCommand(
      Joysticks.DRIVE_CONTROLLER.getLeftY(), // Forward
      Joysticks.DRIVE_CONTROLLER.getLeftX(), // Strafe
      Joysticks.DRIVE_CONTROLLER.getRightX(), // Rotate
      Constants.XboxButtons.DRIVER_RIGHT_BUMPER.getAsBoolean(), // brake
      Constants.XboxButtons.DRIVER_BUTTON_B.getAsBoolean()); // boost
  
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
