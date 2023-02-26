package frc.robot.commands;

import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final ArmSubsystem m_ArmSubsystem;

  public ArmCommand(ArmSubsystem subsystem_1) {
    m_ArmSubsystem = subsystem_1;

    addRequirements(subsystem_1);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double armFine = -Joysticks.OPERATOR_CONTROLLER.getLeftY();
    if (Math.abs(armFine) > 0.1) {
      m_ArmSubsystem.FineTune(Math.signum(armFine));
    }

    double extendFine = -Joysticks.OPERATOR_CONTROLLER.getRightY();
    if (Math.abs(extendFine) > 0.1) {
      m_ArmSubsystem.FineTune(Math.signum(extendFine));
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
