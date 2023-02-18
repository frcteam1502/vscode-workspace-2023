package frc.robot.commands;

import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final IntakeSubsystem m_Intakesubsystem;

  public IntakeCommand(IntakeSubsystem subsystem) {
    m_Intakesubsystem = subsystem;

    addRequirements(subsystem);
  }
 
  @Override
  public void initialize() {}

  @Override
  public void execute() {
  if (Joysticks.DRIVE_CONTROLLER.getLeftBumper()) m_Intakesubsystem.TurnOnIntake();
  else m_Intakesubsystem.TurnOffIntake();
  }
  
  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
