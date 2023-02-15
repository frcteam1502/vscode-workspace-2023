// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.GamePiece;
import frc.robot.Constants;
public class IntakeSubsystem extends SubsystemBase {
  DoubleSolenoid m_intakeDeploy = new DoubleSolenoid(1, PneumaticsModuleType.REVPH, 0, 15);
  
  
  
  
  final static class INTAKE_CONSTANTS {
    // Device IDs
    
    // PID coefficients (sample values, TBD)
    public final static double kP = 0.1;
    public final static double kI = 1e-4;
    public final static double kD = 1;
    public final static double kIz = 0;
    public final static double kFF = 0;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
  }
  

  final class IntakeMotor {
    private CANSparkMax m_intakeMotor;
    private RelativeEncoder m_encoderintake;
    private SparkMaxPIDController m_pidControllerIntake;
    private double m_targetIntake = 0;

    public IntakeMotor(int intakeDeviceID) {
      m_intakeMotor = new CANSparkMax(intakeDeviceID, MotorType.kBrushless);

      m_intakeMotor.restoreFactoryDefaults();

      m_pidControllerIntake = m_intakeMotor.getPIDController();

      // Encoder object created to display position values
      m_encoderintake = m_intakeMotor.getEncoder();

      // set PID coefficients
      m_pidControllerIntake.setP(INTAKE_CONSTANTS.kP);
      m_pidControllerIntake.setI(INTAKE_CONSTANTS.kI);
      m_pidControllerIntake.setD(INTAKE_CONSTANTS.kD);
      m_pidControllerIntake.setIZone(INTAKE_CONSTANTS.kIz);
      m_pidControllerIntake.setFF(INTAKE_CONSTANTS.kFF);
      m_pidControllerIntake.setOutputRange(INTAKE_CONSTANTS.kMinOutput, INTAKE_CONSTANTS.kMaxOutput);
    }

    public void SetIntake(double rotations) {
      m_targetIntake = rotations;
      m_pidControllerIntake.setReference(rotations, CANSparkMax.ControlType.kPosition);
    }
  
  
  
  // below are the functions for the intake system
  // intake uses pneumatics to extend and retract

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
}

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
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
