// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmAngleSubsystem;
import frc.robot.subsystems.ArmLengthSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArmCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ArmAngleSubsystem m_ArmAngleSubsystem;
  private final ArmLengthSubsystem m_ArmLengthSubsystem;
  private final GripperSubsystem m_GripperSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmCommand(ArmAngleSubsystem subsystem_1 , ArmLengthSubsystem subsystem_2 , GripperSubsystem subsystem_3) {
    m_ArmAngleSubsystem = subsystem_1;
    m_ArmLengthSubsystem = subsystem_2;
    m_GripperSubsystem = subsystem_3;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem_1);
    addRequirements(subsystem_2);
    addRequirements(subsystem_3);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
