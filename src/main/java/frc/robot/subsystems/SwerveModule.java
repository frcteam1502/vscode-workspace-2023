package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import frc.robot.SwerveConstants;

public class SwerveModule {
  private final CANSparkMax driveMotor;
  private final CANSparkMax turningMotor;

  private final RelativeEncoder driveEncoder;

  private final CANCoder absEncoder;

  private final SparkMaxPIDController drivePIDController;
  private final PIDController turningPIDController = new PIDController(SwerveConstants.ModuleConstants.MODULE_TURN_PID_CONTROLLER_P, SwerveConstants.ModuleConstants.MODULE_TURN_PID_CONTROLLER_I, SwerveConstants.ModuleConstants.MODULE_TURN_PID_CONTROLLER_D);

  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turnMotor, CANCoder absEncoder, double absOffset, boolean CANCoderDirection) {
    this.driveMotor = driveMotor;
    this.turningMotor = turnMotor;
    this.absEncoder = absEncoder;

    driveMotor.setClosedLoopRampRate(SwerveConstants.ModuleConstants.CLOSED_LOOP_RAMP_RATE);
    driveMotor.setSmartCurrentLimit(SwerveConstants.ModuleConstants.SMART_CURRENT_LIMIT);

    driveEncoder = driveMotor.getEncoder();

    // Set the distance per pulse for the drive encoder. 
    driveEncoder.setPositionConversionFactor(SwerveConstants.ModuleConstants.DRIVE_METERS_PER_ENCODER_REV);

    // Set the velocity per pulse for the drive encoder
    driveEncoder.setVelocityConversionFactor(SwerveConstants.ModuleConstants.DRIVE_ENCODER_MPS_PER_REV);

    // Set the angle in radians per pulse for the turning encoder.
    //turningEncoder.setPositionConversionFactor(Constants.ModuleConstants.RADIANS_PER_ENCODER_REV);
    this.absEncoder.configSensorDirection(CANCoderDirection);
    this.absEncoder.configMagnetOffset(-absOffset);

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    this.turningPIDController.enableContinuousInput(-Math.PI, Math.PI);

    this.drivePIDController = this.driveMotor.getPIDController();
    this.drivePIDController.setP(SwerveConstants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_P);
    this.drivePIDController.setI(SwerveConstants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_I);
    this.drivePIDController.setD(SwerveConstants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_D);
    this.drivePIDController.setFF(SwerveConstants.ModuleConstants.MODULE_DRIVE_PID_CONTROLLER_F);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(getAbsPositionZeroed(true)));
  }

  public double getVelocity() {
    return driveEncoder.getVelocity();
  }

  public Rotation2d geRotation2d() {
    return new Rotation2d(Units.degreesToRadians(absEncoder.getAbsolutePosition()));
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
    return Units.degreesToRadians(absEncoder.getAbsolutePosition());
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(getAbsPositionZeroed(true)));

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput = turningPIDController.calculate(getAbsPositionZeroed(true), state.angle.getRadians());

    //driveMotor.setVoltage(driveOutput + driveFeedforward);
    drivePIDController.setReference(state.speedMetersPerSecond, ControlType.kVelocity);
    turningMotor.setVoltage(turnOutput);
  }
}