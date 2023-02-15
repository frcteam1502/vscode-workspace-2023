// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArmCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final ArmSubsystem m_ArmSubsystem;
  private final GripperSubsystem m_GripperSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmCommand(ArmSubsystem subsystem_1, GripperSubsystem subsystem_2) {
    m_ArmSubsystem = subsystem_1;
    m_GripperSubsystem = subsystem_2;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem_1);
    addRequirements(subsystem_2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double armFine = -Joysticks.OPERATOR_CONTROLLER.getLeftY();
    if (Math.abs(armFine) > 0.1) {
      m_ArmSubsystem.FineTuneAngle(Math.signum(armFine));
    }

    double extendFine = -Joysticks.OPERATOR_CONTROLLER.getRightY();
    if (Math.abs(extendFine) > 0.1) {
      m_ArmSubsystem.FineTuneExtend(Math.signum(extendFine));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
