package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;


  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    //Drive Motor Encoders
    SmartDashboard.putNumber("FrontLeft Encoder", Constants.Motors.DRIVE_FRONT_LEFT.getEncoder().getPosition());
    SmartDashboard.putNumber("FrontRight Encoder", Constants.Motors.DRIVE_FRONT_RIGHT.getEncoder().getPosition());
    SmartDashboard.putNumber("BackLeft Encoder", Constants.Motors.DRIVE_BACK_LEFT.getEncoder().getPosition());
    SmartDashboard.putNumber("BackRight Encoder", Constants.Motors.DRIVE_BACK_RIGHT.getEncoder().getPosition());

    //Drive Motor Volts
    SmartDashboard.putNumber("FrontLeft Volt", Constants.Motors.DRIVE_FRONT_LEFT.getBusVoltage());
    SmartDashboard.putNumber("FrontRight Volt", Constants.Motors.DRIVE_FRONT_RIGHT.getBusVoltage());
    SmartDashboard.putNumber("BackLeft Volt", Constants.Motors.DRIVE_BACK_LEFT.getBusVoltage());
    SmartDashboard.putNumber("BackRight Volt", Constants.Motors.DRIVE_BACK_RIGHT.getBusVoltage());

    //Turn Motor Angles
    SmartDashboard.putNumber("FrontLeft Angle", Constants.CANCoders.FRONT_LEFT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("FrontRight Angle", Constants.CANCoders.FRONT_RIGHT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("BackLeft Angle", Constants.CANCoders.BACK_LEFT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("BackRight Angle", Constants.CANCoders.BACK_RIGHT_CAN_CODER.getAbsolutePosition());

    //Turn Motor Volts
    SmartDashboard.putNumber("TurnFrontLeft Volt", Constants.Motors.ANGLE_FRONT_LEFT.getBusVoltage());
    SmartDashboard.putNumber("TurnFrontRight Volt", Constants.Motors.ANGLE_FRONT_RIGHT.getBusVoltage());
    SmartDashboard.putNumber("TurnBackLeft Volt", Constants.Motors.ANGLE_BACK_LEFT.getBusVoltage());
    SmartDashboard.putNumber("TurnBackRight Volt", Constants.Motors.ANGLE_BACK_RIGHT.getBusVoltage());

    //Joysticks
    SmartDashboard.putNumber("joystick Throttle Y", Constants.Joysticks.DRIVE_CONTROLLER.getLeftX());
    SmartDashboard.putNumber("joystick Throttle X", Constants.Joysticks.DRIVE_CONTROLLER.getLeftY());
    SmartDashboard.putNumber("joystick Turn", Constants.Joysticks.DRIVE_CONTROLLER.getRightX());

    //Gyro
    SmartDashboard.putNumber("Angle", Constants.gyro.getYaw());

    //Calc Power
    SmartDashboard.putNumber("Drive X Power", DriveTrain.xSpeed);
    SmartDashboard.putNumber("Drive Y Power", DriveTrain.ySpeed);
    SmartDashboard.putNumber("Turn X Power", DriveTrain.turnSpeed);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      //m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
