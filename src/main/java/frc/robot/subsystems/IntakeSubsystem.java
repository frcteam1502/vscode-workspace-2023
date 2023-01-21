// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.GamePiece;


public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public IntakeSubsystem() {}
//below are the functions for the intake system
//intake uses pneumatics to extend and retract

public void RetractIntake() {
  // the intake pneumatic program is below
  
  
}
public void DeployIntake() {
  // the intake pneumatic program is below
  
  
}
public void TurnOnIntake(GamePiece gamepiece) {
  // the intake pneumatic program below
  // adjust speed- acording to game piece color
  
}
public void TurnOffIntake() {
  // turn off intake
  
  
}
public void ReverseIntake() {
  // spit out game piece
  
  
}









  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
