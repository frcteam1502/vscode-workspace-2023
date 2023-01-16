// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.DriveTrain;

public class DriveByController extends CommandBase {
  private final DriveTrain drive;

  public DriveByController(DriveTrain drive) {
    this.drive = drive;
    addRequirements(drive);
    
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    drive.drive(
      Joysticks.DRIVE_CONTROLLER.getLeftY(), 
      Joysticks.DRIVE_CONTROLLER.getLeftX(), 
      0/*Joysticks.DRIVE_CONTROLLER.getRightX()*/, 
      true);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
