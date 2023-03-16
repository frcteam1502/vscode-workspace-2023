package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
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

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
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
}