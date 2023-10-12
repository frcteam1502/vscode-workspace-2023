package frc.robot.subsystems;

import java.util.HashMap;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.libraries.LimelightHelpers;
import frc.robot.SwerveConstants;
import frc.robot.SwerveConstants.Motors;

public class DriveTrain extends SubsystemBase{
  
  public static boolean isTeleOp = false;

  public boolean isTurning = false;
  public double targetAngle = 0.0;
  public double turnCommand = 0.0;
  public double forwardCommand = 0;
  public double strafeCommand = 0;

  private final SwerveModule frontLeft = new SwerveModule(
    Motors.DRIVE_FRONT_LEFT, Motors.ANGLE_FRONT_LEFT, 
    SwerveConstants.CANCoders.FRONT_LEFT_CAN_CODER, 
    SwerveConstants.CANCoders.FRONT_LEFT_CAN_CODER_OFFSET,
    SwerveConstants.CANCoders.FRONT_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule frontRight = new SwerveModule(
    Motors.DRIVE_FRONT_RIGHT, Motors.ANGLE_FRONT_RIGHT, 
    SwerveConstants.CANCoders.FRONT_RIGHT_CAN_CODER, 
    SwerveConstants.CANCoders.FRONT_RIGHT_CAN_CODER_OFFSET,
    SwerveConstants.CANCoders.FRONT_RIGHT_CAN_CODER_DIRECTION);

  private final SwerveModule backLeft = new SwerveModule(
    Motors.DRIVE_BACK_LEFT, Motors.ANGLE_BACK_LEFT, 
    SwerveConstants.CANCoders.BACK_LEFT_CAN_CODER, 
    SwerveConstants.CANCoders.BACK_LEFT_CAN_CODER_OFFSET,
    SwerveConstants.CANCoders.BACK_LEFT_CAN_CODER_DIRECTION);

  private final SwerveModule backRight = new SwerveModule(
    Motors.DRIVE_BACK_RIGHT, Motors.ANGLE_BACK_RIGHT, 
    SwerveConstants.CANCoders.BACK_RIGHT_CAN_CODER, 
    SwerveConstants.CANCoders.BACK_RIGHT_CAN_CODER_OFFSET,
    SwerveConstants.CANCoders.BACK_RIGHT_CAN_CODER_DIRECTION);

  private final Pigeon2 gyro = SwerveConstants.gyro;

  private final SwerveDriveKinematics kinematics = SwerveConstants.DriveConstants.KINEMATICS;

  public final SwerveDrivePoseEstimator odometry;

  private Pose2d pose = new Pose2d();
  
  private double pitchOffset;

  public DriveTrain() {
    pitchOffset = 0;

    this.odometry = new SwerveDrivePoseEstimator(kinematics, getGyroRotation2d(), getModulePositions(), pose);

    pitchOffset = gyro.getRoll();

    reset();

    ConfigMotorDirections();
  }

  @Override
  public void periodic() {
    updateOdometry();
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    if(isTeleOp) {
      if(Math.abs(rot) > 0){
        isTurning = true;
        targetAngle = gyro.getYaw();
      } 
      else if(rot == 0 && isTurning) isTurning = false;

      if(isTurning) turnCommand = rot;
      else turnCommand = (targetAngle - gyro.getYaw()) * SwerveConstants.DriveConstants.GO_STRAIGHT_GAIN;

      forwardCommand = xSpeed;
      strafeCommand = ySpeed;
    }
    

    var swerveModuleStates =
        kinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, turnCommand, getGyroRotation2d())
                : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.DriveConstants.MAX_SPEED_METERS_PER_SECOND);
  
    setDesiredState(swerveModuleStates);
  }

  public void teleopInit() {
    targetAngle = gyro.getYaw();
  }

  public void setDesiredState(SwerveModuleState[] swerveModuleStates) {
    frontLeft.setDesiredState(swerveModuleStates[0]);
    frontRight.setDesiredState(swerveModuleStates[1]);
    backLeft.setDesiredState(swerveModuleStates[2]);
    backRight.setDesiredState(swerveModuleStates[3]);
  }

  public void updateOdometry() {
    pose = odometry.update(
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
  
  public SwerveModuleState[] makeSwerveModuleState(double[] speeds, double[] angles) {
    SwerveModuleState[] moduleStates = new SwerveModuleState[angles.length];
    for(int i = 0; i < angles.length; i++) moduleStates[i] = new SwerveModuleState(speeds[i], new Rotation2d(Units.degreesToRadians(angles[i])));
    return moduleStates;
  }

  public void setToBreak() {
    resetModules();
    double[] speeds = {0, 0, 0, 0};
    double[] angles = {90, 90, 90, 90};
    SwerveModuleState[] moduleStates = makeSwerveModuleState(speeds, angles);
    setDesiredState(moduleStates);
  }

  public Rotation2d getGyroRotation2d() {
    return new Rotation2d(Units.degreesToRadians(gyro.getYaw()));
  }

  public Pose2d getPose2d() {
    return odometry.getEstimatedPosition();
  }

  public double getVelocity() {
    return Math.sqrt(
      Math.pow(ChassisSpeeds.fromFieldRelativeSpeeds(forwardCommand, strafeCommand, turnCommand, getGyroRotation2d()).vxMetersPerSecond, 2) + 
      Math.pow(ChassisSpeeds.fromFieldRelativeSpeeds(forwardCommand, strafeCommand, turnCommand, getGyroRotation2d()).vyMetersPerSecond, 2)
    );
  }

  public Rotation2d getHeading() {
    return new Rotation2d(
      Math.atan2(
        Math.pow(ChassisSpeeds.fromFieldRelativeSpeeds(forwardCommand, strafeCommand, turnCommand, getGyroRotation2d()).vyMetersPerSecond, 2), 
        Math.pow(ChassisSpeeds.fromFieldRelativeSpeeds(forwardCommand, strafeCommand, turnCommand, getGyroRotation2d()).vxMetersPerSecond, 2)
      )
    );
  }

  public double getRoll() {
    return gyro.getRoll() - pitchOffset;
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
    resetOdometry(pose);
  }  

  public void ConfigMotorDirections() {
    Motors.ANGLE_FRONT_LEFT.setInverted(SwerveConstants.DriveConstants.FrontLeftTurningMotorReversed);
    Motors.ANGLE_FRONT_RIGHT.setInverted(SwerveConstants.DriveConstants.FrontRightTurningMotorReversed);
    Motors.ANGLE_BACK_LEFT.setInverted(SwerveConstants.DriveConstants.BackLeftTurningMotorReversed);
    Motors.ANGLE_BACK_RIGHT.setInverted(SwerveConstants.DriveConstants.BackRightTurningMotorReversed);
    Motors.DRIVE_FRONT_LEFT.setInverted(SwerveConstants.DriveConstants.FrontLeftDriveMotorReversed);
    Motors.DRIVE_FRONT_RIGHT.setInverted(SwerveConstants.DriveConstants.FrontRightDriveMotorReversed);
    Motors.DRIVE_BACK_LEFT.setInverted(SwerveConstants.DriveConstants.BackLeftDriveMotorReversed);
    Motors.DRIVE_BACK_RIGHT.setInverted(SwerveConstants.DriveConstants.BackRightDriveMotorReversed);

    SwerveConstants.Motors.DRIVE_FRONT_LEFT.setIdleMode(IdleMode.kBrake);
    SwerveConstants.Motors.DRIVE_FRONT_RIGHT.setIdleMode(IdleMode.kBrake);
    SwerveConstants.Motors.DRIVE_BACK_LEFT.setIdleMode(IdleMode.kBrake);
    SwerveConstants.Motors.DRIVE_BACK_RIGHT.setIdleMode(IdleMode.kBrake);

    SwerveConstants.Motors.ANGLE_FRONT_LEFT.setIdleMode(IdleMode.kCoast);
    SwerveConstants.Motors.ANGLE_FRONT_RIGHT.setIdleMode(IdleMode.kCoast);
    SwerveConstants.Motors.ANGLE_BACK_LEFT.setIdleMode(IdleMode.kCoast);
    SwerveConstants.Motors.ANGLE_BACK_RIGHT.setIdleMode(IdleMode.kCoast);
  }

  public Command moveToImage() {
    PathPlannerTrajectory toImage = PathPlanner.generatePath(
      new PathConstraints(SwerveConstants.DriveConstants.MAX_SPEED_METERS_PER_SECOND * 3, 1), 
      new PathPoint(new Translation2d(0, 0), getHeading(), getGyroRotation2d(), getVelocity()),
      pointFromLimelight(new Translation2d(Limelight.getTargetPose()[0], Limelight.getTargetPose()[1])) 
    );
    
    return new PPSwerveControllerCommand(
      toImage, 
      this::getPose2d, // Pose supplier
      SwerveConstants.DriveConstants.KINEMATICS, // SwerveDriveKinematics
      new PIDController(4.35, 0.0, 0.09), // X controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
      new PIDController(4.35, 0.0, 0.09), // Y controller (usually the same values as X controller)
      new PIDController(1.3, 0, 0), // Rotation controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
      this::setDesiredState, // Module states consumer
      true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
      this // Requires this drive subsystem
    );
  }

  public PathPoint pointFromLimelight(Translation2d imagePose) {
    double xDistanceFromCenter = 0;
    double yDistanceFromCenter = 0;
    double desiredDistanceFromImage = 0;

    if(Limelight.getPipeline() == 0) desiredDistanceFromImage = 1;
    else if(Limelight.getPipeline() == 1) desiredDistanceFromImage = 2;

    Translation2d goalTranslation = new Translation2d(
      imagePose.getX() - xDistanceFromCenter, 
      imagePose.getY() - yDistanceFromCenter - desiredDistanceFromImage);
    return new PathPoint(goalTranslation, Rotation2d.fromDegrees(0), Rotation2d.fromDegrees(0), 0);
  }

  public void strafe(boolean right) {
    if(!LimelightHelpers.getTV("Limelight")) {
      double strafe = .1;
      if(!right) strafe *= -1;
      drive(0, strafe, 0, true);
    }
    else moveToImage();
  }

  public void strafeRight() {
    strafe(true);
  }

  public void strafeLeft() {
    strafe(false);
  }

  public Command buildAuto(HashMap<String, Command> eventMap, String pathName) {
    List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(
      pathName, 
      new PathConstraints(SwerveConstants.DriveConstants.MAX_SPEED_METERS_PER_SECOND / 1.1, 1));

    SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
      this::getPose2d, // Pose2d supplier
      this::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
      SwerveConstants.DriveConstants.KINEMATICS, // SwerveDriveKinematics
      new PIDConstants(4.35, 0.0, 0.09), // PID constants to correct for translation error (used to create the X and Y PID controllers)
      new PIDConstants(2.55, 0.0, 0.15), // PID constants to correct for rotation error (used to create the rotation controller)
      this::setDesiredState, // Module states consumer used to output to the drive subsystem
      eventMap,
      true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
      this // The drive subsystem. Used to properly set the requirements of path following commands
    );
    
    return autoBuilder.fullAuto(pathGroup);
  }
}