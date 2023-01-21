// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
  private CANSparkMax motor;

  public ExampleSubsystem(CANSparkMax motor) {
    this.motor = motor;
  }

  public void moveMotor(double motorValue) {
    motor.set(motorValue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
