package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

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
  private Command autonomousCommand;
  private RobotContainer m_robotContainer;


  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    Constants.Motors.DRIVE_FRONT_LEFT.setIdleMode(IdleMode.kBrake);
    Constants.Motors.DRIVE_FRONT_RIGHT.setIdleMode(IdleMode.kBrake);
    Constants.Motors.DRIVE_BACK_LEFT.setIdleMode(IdleMode.kBrake);
    Constants.Motors.DRIVE_BACK_RIGHT.setIdleMode(IdleMode.kBrake);

    Constants.Motors.ANGLE_FRONT_LEFT.setIdleMode(IdleMode.kCoast);
    Constants.Motors.ANGLE_FRONT_RIGHT.setIdleMode(IdleMode.kCoast);
    Constants.Motors.ANGLE_BACK_LEFT.setIdleMode(IdleMode.kCoast);
    Constants.Motors.ANGLE_BACK_RIGHT.setIdleMode(IdleMode.kCoast);
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
    SmartDashboard.putNumber("FL Encoder Pos", Constants.Motors.DRIVE_FRONT_LEFT.getEncoder().getPosition());
    SmartDashboard.putNumber("FR Encoder Pos", Constants.Motors.DRIVE_FRONT_RIGHT.getEncoder().getPosition());
    SmartDashboard.putNumber("RL Encoder Pos", Constants.Motors.DRIVE_BACK_LEFT.getEncoder().getPosition());
    SmartDashboard.putNumber("RR Encoder Pos", Constants.Motors.DRIVE_BACK_RIGHT.getEncoder().getPosition());

    SmartDashboard.putNumber("FL Encoder Vel", Constants.Motors.DRIVE_FRONT_LEFT.getEncoder().getVelocity());
    SmartDashboard.putNumber("FR Encoder Vel", Constants.Motors.DRIVE_FRONT_RIGHT.getEncoder().getVelocity());
    SmartDashboard.putNumber("RL Encoder Vel", Constants.Motors.DRIVE_BACK_LEFT.getEncoder().getVelocity());
    SmartDashboard.putNumber("RR Encoder Vel", Constants.Motors.DRIVE_BACK_RIGHT.getEncoder().getVelocity());

    //Drive Motor Volts
    SmartDashboard.putNumber("FrontLeft Volt", Constants.Motors.DRIVE_FRONT_LEFT.getAppliedOutput());
    SmartDashboard.putNumber("FrontRight Volt", Constants.Motors.DRIVE_FRONT_RIGHT.getAppliedOutput());
    SmartDashboard.putNumber("BackLeft Volt", Constants.Motors.DRIVE_BACK_LEFT.getAppliedOutput());
    SmartDashboard.putNumber("BackRight Volt", Constants.Motors.DRIVE_BACK_RIGHT.getAppliedOutput());

    //Turn Motor Angles
    SmartDashboard.putNumber("FrontLeft Angle", Constants.CANCoders.FRONT_LEFT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("FrontRight Angle", Constants.CANCoders.FRONT_RIGHT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("BackLeft Angle", Constants.CANCoders.BACK_LEFT_CAN_CODER.getAbsolutePosition());
    SmartDashboard.putNumber("BackRight Angle", Constants.CANCoders.BACK_RIGHT_CAN_CODER.getAbsolutePosition());

    SmartDashboard.putNumber("BackRight Angle Velocity", Constants.CANCoders.BACK_RIGHT_CAN_CODER.getVelocity());


    //Turn Angle Commanded
    SmartDashboard.putNumber("FL Angle Cmd", DriveTrain.fl_angle);
    SmartDashboard.putNumber("FR Angle Cmd", DriveTrain.fr_angle);
    SmartDashboard.putNumber("BL Angle Cmd", DriveTrain.bl_angle);
    SmartDashboard.putNumber("BR Angle Cmd", DriveTrain.br_angle);

    //Turn Motor Volts
    SmartDashboard.putNumber("TurnFrontLeft Volt", Constants.Motors.ANGLE_FRONT_LEFT.getAppliedOutput());
    SmartDashboard.putNumber("TurnFrontRight Volt", Constants.Motors.ANGLE_FRONT_RIGHT.getAppliedOutput());
    SmartDashboard.putNumber("TurnBackLeft Volt", Constants.Motors.ANGLE_BACK_LEFT.getAppliedOutput());
    SmartDashboard.putNumber("TurnBackRight Volt", Constants.Motors.ANGLE_BACK_RIGHT.getAppliedOutput());

    //Joysticks
    SmartDashboard.putNumber("joystick Throttle Strafe", Constants.Joysticks.DRIVE_CONTROLLER.getLeftX());
    SmartDashboard.putNumber("joystick Throttle Fwd", Constants.Joysticks.DRIVE_CONTROLLER.getLeftY());
    SmartDashboard.putNumber("joystick Turn", Constants.Joysticks.DRIVE_CONTROLLER.getRightX());

    //Gyro
    SmartDashboard.putNumber("Angle", Constants.gyro.getYaw());

    //Calc Power
    SmartDashboard.putNumber("Foward Speed Cmd", DriveTrain.fwdSpeedCmd);
    SmartDashboard.putNumber("Strafe Speed Cmd", DriveTrain.strafeSpeedCmd);
    SmartDashboard.putNumber("Turn Speed Command", DriveTrain.turnSpeedCmd);

    SmartDashboard.putNumber("FL Module Speed Cmd", DriveTrain.fl_speed);
    SmartDashboard.putNumber("FR Module Speed Cmd", DriveTrain.fr_speed);
    SmartDashboard.putNumber("RL Module Speed Cmd", DriveTrain.rl_speed);
    SmartDashboard.putNumber("RR Module Speed Cmd", DriveTrain.rr_speed);


  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
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
