package frc.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.Motors;
import frc.robot.Constants.CANCoders;
import static frc.robot.Constants.DriveConstants.*;

public class DriveTrain extends SubsystemBase{
  //Debug variables - CL
  public static double fwdSpeedCmd    = 0;
  public static double strafeSpeedCmd = 0;
  public static double turnSpeedCmd   = 0;

  public static double fl_speed = 0;
  public static double fr_speed = 0;
  public static double rl_speed = 0;
  public static double rr_speed = 0;

  public static double fl_angle = 0;
  public static double fr_angle = 0;
  public static double bl_angle = 0;
  public static double br_angle = 0;

  public static double fl_encoder_speed = 0;
  public static double fr_encoder_speed = 0;
  public static double rl_encoder_speed = 0;
  public static double rr_encoder_speed = 0;

  private final SwerveModule frontLeft = new SwerveModule(
    Motors.DRIVE_FRONT_LEFT, Motors.ANGLE_FRONT_LEFT, 
    CANCoders.FRONT_LEFT_CAN_CODER, 
    CANCoders.FRONT_LEFT_CAN_CODER_OFFSET,
    CANCoders.FRONT_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule frontRight = new SwerveModule(
    Motors.DRIVE_FRONT_RIGHT, Motors.ANGLE_FRONT_RIGHT, 
    CANCoders.FRONT_RIGHT_CAN_CODER, 
    CANCoders.FRONT_RIGHT_CAN_CODER_OFFSET,
    CANCoders.FRONT_RIGHT_CAN_CODER_DIRECTION);

  private final SwerveModule backLeft = new SwerveModule(
    Motors.DRIVE_BACK_LEFT, Motors.ANGLE_BACK_LEFT, 
    CANCoders.BACK_LEFT_CAN_CODER, 
    CANCoders.BACK_LEFT_CAN_CODER_OFFSET,
    CANCoders.BACK_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule backRight = new SwerveModule(
    Motors.DRIVE_BACK_RIGHT, Motors.ANGLE_BACK_RIGHT, 
    CANCoders.BACK_RIGHT_CAN_CODER, 
    CANCoders.BACK_RIGHT_CAN_CODER_OFFSET,
    CANCoders.BACK_RIGHT_CAN_CODER_DIRECTION);

  private final Pigeon2 gyro = Constants.gyro;
  private final AnalogGyro analogGyro = new AnalogGyro(0);


  private final SwerveDriveKinematics kinematics = KINEMATICS;

  private final SwerveDriveOdometry odometry;

  public DriveTrain() {
    this.odometry = new SwerveDriveOdometry(
      kinematics,
      getGyroRotation2d(),
      new SwerveModulePosition[] {
        frontLeft.getPosition(),
        frontRight.getPosition(),
        backLeft.getPosition(),
        backRight.getPosition()
      });

      reset();

      ConfigMotorDirections();
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    
    //Log commanded speed inputs
    fwdSpeedCmd = xSpeed;
    strafeSpeedCmd = ySpeed;
    turnSpeedCmd = rot;

    var swerveModuleStates =
        kinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getGyroRotation2d())
                : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, MAX_SPEED_METERS_PER_SECOND);
    
    fl_speed = swerveModuleStates[0].speedMetersPerSecond;
    fr_speed = swerveModuleStates[1].speedMetersPerSecond;
    rl_speed = swerveModuleStates[2].speedMetersPerSecond;
    rr_speed = swerveModuleStates[3].speedMetersPerSecond;

    fl_angle = swerveModuleStates[0].angle.getDegrees();
    fr_angle = swerveModuleStates[1].angle.getDegrees();
    bl_angle = swerveModuleStates[2].angle.getDegrees();
    br_angle = swerveModuleStates[3].angle.getDegrees();
  
    frontLeft.setDesiredState(swerveModuleStates[0]);
    frontRight.setDesiredState(swerveModuleStates[1]);
    backLeft.setDesiredState(swerveModuleStates[2]);
    backRight.setDesiredState(swerveModuleStates[3]);

  }

  public void updateOdometry() {
    odometry.update(
        getGyroRotation2d(),
        new SwerveModulePosition[] {
          frontLeft.getPosition(),
          frontRight.getPosition(),
          backLeft.getPosition(),
          backRight.getPosition()
        });
  }

  //Make individual states for each swerve module to be set to break
  public SwerveModuleState[] makeSwerveModuleState(double[] speeds, double[] angles) {
    SwerveModuleState[] moduleStates = new SwerveModuleState[angles.length];
    for(int i = 0; i <= angles.length; i++) moduleStates[i] = new SwerveModuleState(speeds[i], new Rotation2d(Units.degreesToRadians(angles[i])));
    return moduleStates;
  }

  public void setToBreak() {
    resetModules();
    double[] speeds = {.1, .1, .1, .1};
    double[] angles = {-135, 135, -45, 45};
    SwerveModuleState[] moduleStates = makeSwerveModuleState(speeds, angles);
    frontLeft.setDesiredState(moduleStates[0]);
    frontRight.setDesiredState(moduleStates[1]);
    backLeft.setDesiredState(moduleStates[2]);
    backRight.setDesiredState(moduleStates[3]);
  }

  public void setWheelsForward() {
    resetModules();
    double[] speeds = {.001, .001, .001, .001};
    double[] angles = {0, 0, 0, 0};
    SwerveModuleState[] moduleStates = makeSwerveModuleState(speeds, angles);
    frontLeft.setDesiredState(moduleStates[0]);
    frontRight.setDesiredState(moduleStates[1]);
    backLeft.setDesiredState(moduleStates[2]);
    backRight.setDesiredState(moduleStates[3]);
  }

  //Get Rotation2d from the Pigeon
  public Rotation2d getGyroRotation2d() {
    return new Rotation2d(Units.degreesToRadians(gyro.getYaw()));
  }

  public void resetGyro() {
    gyro.setYaw(0);
  }

  public void resetModules() {
    frontLeft.zeroModule();
    frontRight.zeroModule();
    backLeft.zeroModule();
    backRight.zeroModule();
  }

  public void reset() {
    resetGyro();
    resetModules();
  }  

  public void ConfigMotorDirections() {
    Motors.ANGLE_FRONT_LEFT.setInverted(DriveConstants.Front_Left_Turning_Motor_Reversed);
    Motors.ANGLE_FRONT_RIGHT.setInverted(DriveConstants.Front_Right_Turning_Motor_Reversed);
    Motors.ANGLE_BACK_LEFT.setInverted(DriveConstants.Back_Left_Turning_Motor_Reversed);
    Motors.ANGLE_BACK_RIGHT.setInverted(DriveConstants.Back_Right_Turning_Motor_Reversed);
    Motors.DRIVE_FRONT_LEFT.setInverted(DriveConstants.Front_Left_Drive_Motor_Reversed);
    Motors.DRIVE_FRONT_RIGHT.setInverted(DriveConstants.Front_Right_Drive_Motor_Reversed);
    Motors.DRIVE_BACK_LEFT.setInverted(DriveConstants.Back_Left_Drive_Motor_Reversed);
    Motors.DRIVE_BACK_RIGHT.setInverted(DriveConstants.Back_Right_Drive_Motor_Reversed);
  }
}
