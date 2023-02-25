package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

public class ArmCommand extends CommandBase {
  private ArmSubsystem armAngleSubsystem;

  public ArmCommand(ArmSubsystem armAngleSubsystem) {
    this.armAngleSubsystem = armAngleSubsystem;

    addRequirements(armAngleSubsystem);
  }

  @Override
  public void execute() {
    armAngleSubsystem.angleMove(Constants.Joysticks.MANIP_CONTROLLER.getLeftY());

    armAngleSubsystem.extendMove(MathUtil.applyDeadband(Constants.Joysticks.MANIP_CONTROLLER.getRightY(), .1));
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
