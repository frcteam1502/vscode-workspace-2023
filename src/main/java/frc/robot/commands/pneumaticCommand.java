// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.pneumaticSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An pneumatic command that uses an pneumatic subsystem. */
public class pneumaticCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final pneumaticSubsystem DoublePHSubsystem;

  /**
   * Creates a new pneumaticCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public pneumaticCommand(pneumaticSubsystem subsystem) {
    DoublePHSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  
  public void MoveIn() {
    DoublePHSubsystem.setIn();
  }
  public void MoveOut() {
    DoublePHSubsystem.setOut();
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
