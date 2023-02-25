package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Speed", Constants.Motors.TEST.get());
    SmartDashboard.putNumber("Position", Constants.Motors.TEST.getEncoder().getPosition());
  }

  @Override
  public void teleopPeriodic() {}
}
