// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.Joysticks;
import frc.robot.subsystems.DriveTrain;

public class DriveByController extends CommandBase {
  private final DriveTrain drive;
  private final double maxTurnRadiansPerSecond = 
    Units.degreesToRadians(Constants.ModuleConstants.MAX_MODULE_ROTATION_DEGREES_PER_SECOND);
  private final double maxSpeedMetersPerSecond = 
    Constants.ModuleConstants.MAX_METERS_PER_SECOND; 
  
  private final SlewRateLimiter throttle = new SlewRateLimiter(2);
  private final SlewRateLimiter throttleRad = new SlewRateLimiter(Math.PI);

  public DriveByController(DriveTrain drive) {
    this.drive = drive;
    addRequirements(drive);
    
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    drive.drive(
      controlSupplier(Joysticks.DRIVE_CONTROLLER.getLeftY(), maxSpeedMetersPerSecond), 
      controlSupplier(Joysticks.DRIVE_CONTROLLER.getLeftX(), maxSpeedMetersPerSecond), 
      controlSupplier(Joysticks.DRIVE_CONTROLLER.getRightX(), -maxTurnRadiansPerSecond), 
      true);
  }

  @Override
  public void end(boolean interrupted) {}

  public double controlSupplier(double controllerValue, double speed) {
    return (controllerValue * speed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
