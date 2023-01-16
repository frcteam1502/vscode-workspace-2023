package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.Constants;

public class SwerveModule {
  private final CANSparkMax driveMotor;
  private final CANSparkMax turningMotor;

  private final RelativeEncoder driveEncoder;
  private final RelativeEncoder turningEncoder;

  private final PIDController drivePIDController = new PIDController(Constants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_P, 0, 0);

  private final ProfiledPIDController turningPIDController =
      new ProfiledPIDController(
          Constants.ModuleConstants.MODULE_TURN_PID_CONTROLLER_P,
          0,
          0,
          new TrapezoidProfile.Constraints(
            Constants.ModuleConstants.MAX_MODULE_ROTATION_DEGREES_PER_SECOND, 
            Constants.ModuleConstants.MAX_MODULE_ROTATION_DEGREES_PER_SECOND_PER_SECOND));

  //FIXME: Get actual values
  private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(0, 0);
  private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(0, 0);

  // private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(1, 3);
  // private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turnMotor) {
    this.driveMotor = driveMotor;
    this.turningMotor = turnMotor;

    driveEncoder = driveMotor.getEncoder();
    turningEncoder = turnMotor.getEncoder();

    /**Set the distance per pulse for the drive encoder. We can simply use the
    distance traveled for one rotation of the wheel divided by the encoder
    resolution.*/
    driveEncoder.setPositionConversionFactor(Constants.ModuleConstants.DRIVE_METERS_PER_ENCODER_REV);

    driveEncoder.setVelocityConversionFactor(Constants.ModuleConstants.DRIVE_ENCODER_MPS_PER_REV);

    /**Set the distance (in this case, angle) in radians per pulse for the turning encoder.
    This is the the angle through an entire rotation (2 * pi) divided by the
    encoder resolution.*/
    turningEncoder.setPositionConversionFactor(Constants.ModuleConstants.RADIANS_PER_ENCODER_REV);

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turningEncoder.getPosition()));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(driveEncoder.getPosition(), new Rotation2d(turningEncoder.getPosition()));
  }

  public void resetEncoders() {
    driveEncoder.setPosition(0);
    turningEncoder.setPosition(0);
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(turningEncoder.getPosition()));

    // Calculate the drive output from the drive PID controller.
    final double driveOutput = drivePIDController.calculate(driveEncoder.getVelocity(), state.speedMetersPerSecond);

    final double driveFeedforward = this.driveFeedforward.calculate(state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput = turningPIDController.calculate(turningEncoder.getPosition(), state.angle.getRadians());

    final double turnFeedforward = this.turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);

    driveMotor.setVoltage(driveOutput + driveFeedforward);
    turningMotor.setVoltage(turnOutput + turnFeedforward);
  }
}