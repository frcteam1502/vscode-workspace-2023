package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.MotorTestSubsystem;

public class MotorTestCommand extends CommandBase {
  private MotorTestSubsystem motorTestSubsystem;

  public MotorTestCommand(MotorTestSubsystem motorTestSubsystem) {
    this.motorTestSubsystem = motorTestSubsystem;

    addRequirements(motorTestSubsystem);
  }

  @Override
  public void initialize() {
    motorTestSubsystem.STOP();
  }

  @Override
  public void execute() {
    motorTestSubsystem.MOVE(MathUtil.applyDeadband(Constants.Joysticks.OPERATOR_CONTROLLER.getRightY(), .1));
  }
}
