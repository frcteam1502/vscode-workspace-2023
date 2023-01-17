package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.Motors;

public class RobotContainer {
  private final SwerveModule frontLeft = new SwerveModule(
    Motors.DRIVE_FRONT_LEFT, Motors.ANGLE_FRONT_LEFT, 
    Constants.CANCoders.FRONT_LEFT_CAN_CODER, Constants.CanConstants.FRONT_LEFT_MODULE_STEER_OFFSET);
  private final SwerveModule frontRight = new SwerveModule(
    Motors.DRIVE_FRONT_RIGHT, Motors.ANGLE_FRONT_RIGHT, 
    Constants.CANCoders.FRONT_RIGHT_CAN_CODER, Constants.CanConstants.FRONT_RIGHT_MODULE_STEER_OFFSET);
  private final SwerveModule backLeft = new SwerveModule(
    Motors.DRIVE_BACK_LEFT, Motors.ANGLE_BACK_LEFT, 
    Constants.CANCoders.BACK_LEFT_CAN_CODER, Constants.CanConstants.BACK_LEFT_MODULE_STEER_OFFSET);
  private final SwerveModule backRight = new SwerveModule(
    Motors.DRIVE_BACK_RIGHT, Motors.ANGLE_BACK_RIGHT, 
    Constants.CANCoders.BACK_RIGHT_CAN_CODER, Constants.CanConstants.BACK_RIGHT_MODULE_STEER_OFFSET);

  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain(frontLeft, frontRight, backLeft, backRight);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
  }

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}