package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class SwerveModule {
  private final CANSparkMax driveMotor;
  private final CANSparkMax turningMotor;

  private final RelativeEncoder driveEncoder;

  private final CANCoder absEncoder;
  private final double AbsOffset;

  private final PIDController drivePIDController = new PIDController(Constants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_P, 0, 0);
  private final ProfiledPIDController turningPIDController =
      new ProfiledPIDController(
          Constants.ModuleConstants.MODULE_TURN_PID_CONTROLLER_P,
          0,
          0,
          new TrapezoidProfile.Constraints(
            Units.degreesToRadians(Constants.ModuleConstants.MAX_MODULE_ROTATION_DEGREES_PER_SECOND), 
            Units.degreesToRadians(Constants.ModuleConstants.MAX_MODULE_ROTATION_DEGREES_PER_SECOND_PER_SECOND)));

  //FIXME: Get actual values
  //private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(0, 0);
  //private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(0, 0);

  private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(1, 3);
  private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turnMotor, CANCoder absEncoder, double absOffset, boolean CANCoderDirection) {
    this.driveMotor = driveMotor;
    this.turningMotor = turnMotor;
    this.absEncoder = absEncoder;
    this.AbsOffset = absOffset;

    driveEncoder = driveMotor.getEncoder();

    // Set the distance per pulse for the drive encoder. 
    driveEncoder.setPositionConversionFactor(Constants.ModuleConstants.DRIVE_METERS_PER_ENCODER_REV);

    // Set the velocity per pulse for the drive encoder
    driveEncoder.setVelocityConversionFactor(Constants.ModuleConstants.DRIVE_ENCODER_MPS_PER_REV);

    // Set the angle in radians per pulse for the turning encoder.
    //turningEncoder.setPositionConversionFactor(Constants.ModuleConstants.RADIANS_PER_ENCODER_REV);
    this.absEncoder.configSensorDirection(CANCoderDirection);

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
    return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(getAbsPositionZeroed(true)));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(driveEncoder.getPosition(), new Rotation2d(getAbsPositionZeroed(true)));
  }

  public void zeroModule() {
    driveEncoder.setPosition(0);
  }

  public double getAbsPositionZeroed(boolean inRadians) {
    if (inRadians) {
      double radians = Units.degreesToRadians(getAbsPositionZeroed(false));// is this the right API???
      if(radians <= Math.PI) return radians;
      else return radians - (2 * Math.PI);
    } 
    else return absEncoder.getAbsolutePosition() - AbsOffset;
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(getAbsPositionZeroed(true)));

    // Calculate the drive output from the drive PID controller.

    final double driveOutput = drivePIDController.calculate(driveEncoder.getVelocity(), state.speedMetersPerSecond);

    final double driveFeedforward = this.driveFeedforward.calculate(state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput = turningPIDController.calculate(getAbsPositionZeroed(true), state.angle.getRadians());

    final double turnFeedforward = this.turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);

    driveMotor.setVoltage(driveOutput + driveFeedforward);
    turningMotor.setVoltage(turnOutput + turnFeedforward);
  }
}