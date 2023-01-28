// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.GamePiece;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import static frc.robot.Constants.IntakeConstants.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  DoubleSolenoid m_intakeDeploy = new DoubleSolenoid(1, PneumaticsModuleType.REVPH, FORWARD_CHANNEL, REVERSE_CHANNEL);
  private final CANSparkMax m_intakeMotor;





  public IntakeSubsystem(CANSparkMax intakeMotor) {
  this.m_intakeMotor  =  intakeMotor;
  }
//below are the functions for the intake system
//intake uses pneumatics to extend and retract

public void RetractIntake() {
  // the intake pneumatic program is below
  m_intakeDeploy.set(Value.kReverse);
  
}
public void DeployIntake() {
  // the intake pneumatic program is below
  m_intakeDeploy.set(Value.kForward);
  
}
public void TurnOnIntake(GamePiece gamepiece) {
  // the intake pneumatic program below
  // adjust speed- acording to game piece color
  m_intakeMotor.set(0.2);
}

public void TurnOffIntake() {
  // turn off intake
  m_intakeMotor.set(0);
  
  
}
public void ReverseIntake() {
  // spit out game piece
  m_intakeMotor.set(-0.2);

  
  
}

public Command runIntakeCommand () {
  return new StartEndCommand(()->this.TurnOnIntake(GamePiece.cone), this::TurnOffIntake, this);
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
