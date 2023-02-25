package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.MotorTestCommand;
import frc.robot.subsystems.MotorTestSubsystem;

public class RobotContainer {
  //Subsystems
  private MotorTestSubsystem motorTestSubsystem = new MotorTestSubsystem();
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    motorTestSubsystem.setDefaultCommand(new MotorTestCommand(motorTestSubsystem));

    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(motorTestSubsystem::BACKWARD)).onFalse(new InstantCommand(motorTestSubsystem::STOP));
    Constants.XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(motorTestSubsystem::FORWARD)).onFalse(new InstantCommand(motorTestSubsystem::STOP));
  }
}