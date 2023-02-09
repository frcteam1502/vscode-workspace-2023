package frc.robot.subsystems;

import java.util.HashMap;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Motors;

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
    Constants.CANCoders.FRONT_LEFT_CAN_CODER, 
    Constants.CANCoders.FRONT_LEFT_CAN_CODER_OFFSET,
    Constants.CANCoders.FRONT_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule frontRight = new SwerveModule(
    Motors.DRIVE_FRONT_RIGHT, Motors.ANGLE_FRONT_RIGHT, 
    Constants.CANCoders.FRONT_RIGHT_CAN_CODER, 
    Constants.CANCoders.FRONT_RIGHT_CAN_CODER_OFFSET,
    Constants.CANCoders.FRONT_RIGHT_CAN_CODER_DIRECTION);

  private final SwerveModule backLeft = new SwerveModule(
    Motors.DRIVE_BACK_LEFT, Motors.ANGLE_BACK_LEFT, 
    Constants.CANCoders.BACK_LEFT_CAN_CODER, 
    Constants.CANCoders.BACK_LEFT_CAN_CODER_OFFSET,
    Constants.CANCoders.BACK_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule backRight = new SwerveModule(
    Motors.DRIVE_BACK_RIGHT, Motors.ANGLE_BACK_RIGHT, 
    Constants.CANCoders.BACK_RIGHT_CAN_CODER, 
    Constants.CANCoders.BACK_RIGHT_CAN_CODER_OFFSET,
    Constants.CANCoders.BACK_RIGHT_CAN_CODER_DIRECTION);

  private final Pigeon2 gyro = Constants.gyro;

  private final SwerveDriveKinematics kinematics = Constants.DriveConstants.KINEMATICS;

  public final SwerveDrivePoseEstimator odometry = new SwerveDrivePoseEstimator(kinematics, getGyroRotation2d(), getModulePositions(), getPose2d());

  public DriveTrain() {
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
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND);
    
    fl_speed = swerveModuleStates[0].speedMetersPerSecond;
    fr_speed = swerveModuleStates[1].speedMetersPerSecond;
    rl_speed = swerveModuleStates[2].speedMetersPerSecond;
    rr_speed = swerveModuleStates[3].speedMetersPerSecond;

    fl_angle = swerveModuleStates[0].angle.getDegrees();
    fr_angle = swerveModuleStates[1].angle.getDegrees();
    bl_angle = swerveModuleStates[2].angle.getDegrees();
    br_angle = swerveModuleStates[3].angle.getDegrees();
  
    setDesiredState(swerveModuleStates); //TODO: Does this work???
  }

  public void setDesiredState(SwerveModuleState[] swerveModuleStates) {
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

  public void resetOdometry(Pose2d pose) {
    odometry.resetPosition(getGyroRotation2d(), getModulePositions(), pose);
  }
  
  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
      frontLeft.getPosition(),
      frontRight.getPosition(),
      backLeft.getPosition(),
      backRight.getPosition()
    };
  }

  public SwerveModuleState[] getModuleStates() {
    return new SwerveModuleState[] {
      new SwerveModuleState(frontLeft.getVelocity(), frontLeft.geRotation2d()),
      new SwerveModuleState(frontRight.getVelocity(), frontRight.geRotation2d()),
      new SwerveModuleState(backLeft.getVelocity(), backLeft.geRotation2d()),
      new SwerveModuleState(backRight.getVelocity(), backRight.geRotation2d())
    };
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
    setDesiredState(moduleStates);
  }

  public Rotation2d getGyroRotation2d() {
    return new Rotation2d(Units.degreesToRadians(gyro.getYaw()));
  }

  public Pose2d getPose2d() {
    return odometry.getEstimatedPosition();
  }

  public double getPitch() {
    return gyro.getPitch();
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
    Motors.ANGLE_FRONT_LEFT.setInverted(Constants.DriveConstants.FrontLeftTurningMotorReversed);
    Motors.ANGLE_FRONT_RIGHT.setInverted(Constants.DriveConstants.FrontRightTurningMotorReversed);
    Motors.ANGLE_BACK_LEFT.setInverted(Constants.DriveConstants.BackLeftTurningMotorReversed);
    Motors.ANGLE_BACK_RIGHT.setInverted(Constants.DriveConstants.BackRightTurningMotorReversed);
    Motors.DRIVE_FRONT_LEFT.setInverted(Constants.DriveConstants.FrontLeftDriveMotorReversed);
    Motors.DRIVE_FRONT_RIGHT.setInverted(Constants.DriveConstants.FrontRightDriveMotorReversed);
    Motors.DRIVE_BACK_LEFT.setInverted(Constants.DriveConstants.BackLeftDriveMotorReversed);
    Motors.DRIVE_BACK_RIGHT.setInverted(Constants.DriveConstants.BackRightDriveMotorReversed);
  }

  public Command getPathFollowingCommand(String pathName) {
    PathPlannerTrajectory trajectory = PathPlanner.loadPath(
      pathName, 
      new PathConstraints(Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND, 1) //TODO: Find MAX ACCEL
      );
    return new PPSwerveControllerCommand(
      trajectory, 
      this::getPose2d, // Pose supplier
      Constants.DriveConstants.KINEMATICS, // SwerveDriveKinematics
      new PIDController(0, 0, 0), // X controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
      new PIDController(0, 0, 0), // Y controller (usually the same values as X controller)
      new PIDController(3, 0, 0), // Rotation controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
      this::setDesiredState, // Module states consumer
      true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
      this // Requires this drive subsystem
    ); //TODO: Fix these PIDControllers
  }

  public Command buildAuto(HashMap<String, Command> eventMap, String pathName) {
    List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(
      pathName, 
      new PathConstraints(Constants.DriveConstants.MAX_SPEED_METERS_PER_SECOND, 1)); //TODO: Find MAX ACCEL

    SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
      this::getPose2d, // Pose2d supplier
      this::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
      Constants.DriveConstants.KINEMATICS, // SwerveDriveKinematics
      new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X and Y PID controllers)
      new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the rotation controller)
      this::setDesiredState, // Module states consumer used to output to the drive subsystem
      eventMap,
      true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
      this // The drive subsystem. Used to properly set the requirements of path following commands
    ); //TODO: Check PIDs
    
    return autoBuilder.fullAuto(pathGroup);
  }
}
