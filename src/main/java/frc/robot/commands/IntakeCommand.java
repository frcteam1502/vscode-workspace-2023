// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final IntakeSubsystem m_Intakesubsystem;

  public IntakeCommand(IntakeSubsystem subsystem) {
    m_Intakesubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }
 // Called when the command is initially scheduled.
 
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {
  if (Joysticks.OPERATOR_CONTROLLER.getLeftBumper())
  {m_Intakesubsystem.TurnOnIntake();}
  else 
  {m_Intakesubsystem.TurnOffIntake();}
   }
  // Called once the command ends or is interrupted.
  
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
