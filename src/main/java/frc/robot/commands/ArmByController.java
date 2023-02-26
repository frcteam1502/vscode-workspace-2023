package frc.robot.commands;

import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmByController extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final ArmSubsystem arm;

  public ArmByController(ArmSubsystem arm) {
    this.arm = arm;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    
    double armFine = -Joysticks.OPERATOR_CONTROLLER.getLeftY();
    if (Math.abs(armFine) > 0.1) {
      arm.FineTuneAngle(Math.signum(armFine));
    }

    double extendFine = -Joysticks.OPERATOR_CONTROLLER.getRightY();
    if (Math.abs(extendFine) > 0.1) {
      arm.FineTuneExtend(Math.signum(extendFine));
    }

    // arm.checkZeroAngle();
    // arm.checkZeroExtend();
    arm.updateSmartDashboard();

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}