package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArm extends CommandBase {
  ArmSubsystem armSubsystem;
  String pose;

  public MoveArm(ArmSubsystem armSubsystem, String pose) {
    this.armSubsystem = armSubsystem;
    this.pose = pose;
    addRequirements(armSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(pose.equals("High")) armSubsystem.rotateToHigh();
    else if(pose.equals("Middle")) armSubsystem.rotateToMid();
    else if(pose.equals("Low")) armSubsystem.rotateToLow();
    else armSubsystem.rotateToStow();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return armSubsystem.isWithinExtendRange(armSubsystem.rotateEncoder.getPosition(), 2);
  }
}