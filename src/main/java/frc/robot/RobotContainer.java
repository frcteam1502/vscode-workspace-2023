package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Motors;

public class RobotContainer {
  private final SwerveModule frontLeft = new SwerveModule(Motors.DRIVE_FRONT_LEFT, Motors.ANGLE_FRONT_LEFT);
  private final SwerveModule frontRight = new SwerveModule(Motors.DRIVE_FRONT_RIGHT, Motors.ANGLE_FRONT_RIGHT);
  private final SwerveModule backLeft = new SwerveModule(Motors.DRIVE_BACK_LEFT, Motors.ANGLE_BACK_LEFT);
  private final SwerveModule backRight = new SwerveModule(Motors.DRIVE_BACK_RIGHT, Motors.ANGLE_BACK_RIGHT);

  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain(frontLeft, frontRight, backLeft, backRight);

  //Commands
  private final DriveByController drive = new DriveByController(driveSubsystem);

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  // public Command getAutonomousCommand() {
  //   return Autos.exampleAuto(m_exampleSubsystem);
  // }
}