// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  // no need to be global/public if not used outside of this subsystemstatic class xboxcontroller.axis 
 static class xboxcontroller {
  //work in progress found the right buttons just need to put the values.

 }
 
 
  final static class ARM_CONSTANTS {
    // Device IDs
    public final static int LEAD_DEVICE_ID = 16; // TODO: TBD (get from global device map?)
    public final static int FOLOW_DEVICE_ID = 17; // TODO: TBD
    public final static int EXTEND_DEVICE_ID = 18; // TODO: TBD
    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_ANGLE = 0;
    private static final double FLOOR_ANGLE = 0.1;
    private static final double MIDDLE_ANGLE = 0.2;
    private static final double TOP_ANGLE = 0.3;
    
    // PID coefficients (sample values, TBD)
    public final static double kP = 0.1;
    public final static double kI = 1e-4;
    public final static double kD = 1;
    public final static double kIz = 0;
    public final static double kFF = 0;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
  } 
  // no need to be global/public if not used outside of this subsystem
  final static class EXTEND_CONSTANTS {
    // Device IDs
    public final static int LEAD_DEVICE_ID = 16; // TODO: TBD (get from global device map?)
    public final static int FOLOW_DEVICE_ID = 17; // TODO: TBD

    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_EXTENSION = 0;
    private static final double FLOOR_EXTENSION = 0.1;
    private static final double MIDDLE_EXTENSION = 0.2;
    private static final double TOP_EXTENSION = 0.3;

    // PID coefficients (sample values, TBD)
    public final static double kP = 0.1;
    public final static double kI = 1e-4;
    public final static double kD = 1;
    public final static double kIz = 0;
    public final static double kFF = 0;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
  } 
  final class DualMotor {
    private CANSparkMax m_leadMotor; // this one needs to match the rotations (CCW)
    private CANSparkMax m_followMotor;
    private SparkMaxPIDController m_pidControllerAngle;
    private RelativeEncoder m_encoderangle;
    private double m_targetPosition = 0;

    public DualMotor(int leadDeviceID, int followDeviceID) {
      m_leadMotor = new CANSparkMax(leadDeviceID, MotorType.kBrushless);
      m_followMotor = new CANSparkMax(followDeviceID, MotorType.kBrushless);
    
      
      m_leadMotor.restoreFactoryDefaults();
      m_followMotor.restoreFactoryDefaults();
  

      m_followMotor.follow(m_leadMotor, /*invert*/ true);

      m_pidControllerAngle = m_leadMotor.getPIDController();

      // Encoder object created to display position values
      m_encoderangle = m_leadMotor.getEncoder();

      // set PID coefficients
      m_pidControllerAngle.setP(ARM_CONSTANTS.kP);
      m_pidControllerAngle.setI(ARM_CONSTANTS.kI);
      m_pidControllerAngle.setD(ARM_CONSTANTS.kD);
      m_pidControllerAngle.setIZone(ARM_CONSTANTS.kIz);
      m_pidControllerAngle.setFF(ARM_CONSTANTS.kFF);
      m_pidControllerAngle.setOutputRange(ARM_CONSTANTS.kMinOutput, ARM_CONSTANTS.kMaxOutput);
    }
 
    public void SetElevation(double rotations) {
      m_targetPosition = rotations;
      m_pidControllerAngle.setReference(rotations, CANSparkMax.ControlType.kPosition);
    }
    
    // For Testing
    public void UpdateInformation(){ 
      // read PID coefficients from SmartDashboard
      double p = SmartDashboard.getNumber("P Gain", 0);
      double i = SmartDashboard.getNumber("I Gain", 0);
      double d = SmartDashboard.getNumber("D Gain", 0);
      double iz = SmartDashboard.getNumber("I Zone", 0);
      double ff = SmartDashboard.getNumber("Feed Forward", 0);
      double max = SmartDashboard.getNumber("Max Output", 0);
      double min = SmartDashboard.getNumber("Min Output", 0);
      double rotations = SmartDashboard.getNumber("Set Position", 0);
  
      // if PID coefficients on SmartDashboard have changed, write new values to controller
      if((p != m_pidControllerAngle.getP())) { m_pidControllerAngle.setP(p); }
      if((i != m_pidControllerAngle.getI())) { m_pidControllerAngle.setI(i); }
      if((d != m_pidControllerAngle.getD())) { m_pidControllerAngle.setD(d); }
      if((iz != m_pidControllerAngle.getIZone())) { m_pidControllerAngle.setIZone(iz); }
      if((ff != m_pidControllerAngle.getFF())) { m_pidControllerAngle.setFF(ff); }
      if((max != m_pidControllerAngle.getOutputMax()) || (min != m_pidControllerAngle.getOutputMin())) { 
        m_pidControllerAngle.setOutputRange(min, max); 
      }

      SetElevation(rotations);
    }
    
    // For Testing
    public void DisplayInformation(){
      // display PID coefficients on SmartDashboard
      SmartDashboard.putNumber("P Gain", m_pidControllerAngle.getP());
      SmartDashboard.putNumber("I Gain", m_pidControllerAngle.getI());
      SmartDashboard.putNumber("D Gain", m_pidControllerAngle.getD());
      SmartDashboard.putNumber("I Zone", m_pidControllerAngle.getIZone());
      SmartDashboard.putNumber("Feed Forward", m_pidControllerAngle.getFF());
      SmartDashboard.putNumber("Max Output", m_pidControllerAngle.getOutputMax());
      SmartDashboard.putNumber("Min Output", m_pidControllerAngle.getOutputMin());

      SmartDashboard.putNumber("Set Position", 0);

    }
    
    public void DisplayPosition()
    {
      SmartDashboard.putNumber("Arm Target Position", m_targetPosition);
      SmartDashboard.putNumber("Arm Current Position", m_encoderangle.getPosition());
    }

  }
  final class ExtendMotor{
    private CANSparkMax m_extendMotor;
    private RelativeEncoder m_encoderextension;
    private SparkMaxPIDController m_pidControllerExtension;
    private double m_targetExtension = 0;

    public ExtendMotor(int extendDeviceID) {
      m_extendMotor = new CANSparkMax(extendDeviceID, MotorType.kBrushless);

      m_extendMotor.restoreFactoryDefaults();

      m_pidControllerExtension = m_extendMotor.getPIDController();

    // Encoder object created to display position values
      m_encoderextension = m_extendMotor.getEncoder();

    // set PID coefficients
    m_pidControllerExtension.setP(EXTEND_CONSTANTS.kP);
    m_pidControllerExtension.setI(EXTEND_CONSTANTS.kI);
    m_pidControllerExtension.setD(EXTEND_CONSTANTS.kD);
    m_pidControllerExtension.setIZone(EXTEND_CONSTANTS.kIz);
    m_pidControllerExtension.setFF(EXTEND_CONSTANTS.kFF);
    m_pidControllerExtension.setOutputRange(EXTEND_CONSTANTS.kMinOutput, EXTEND_CONSTANTS.kMaxOutput);
    }
   public void SetExtension(double rotations) {
    m_targetExtension = rotations;
    m_pidControllerExtension.setReference(rotations, CANSparkMax.ControlType.kPosition);
    }
    
}
  private DualMotor m_elevationMotor;
  private ExtendMotor m_extenderMotor;

  /* TODO: provide LEAD_DEVICE_ID, etc. */
  public ArmSubsystem(int leadDeviceID, int followDeviceID, int extendDeviceID) {
    m_elevationMotor = new DualMotor(leadDeviceID, followDeviceID);
    m_elevationMotor.DisplayInformation();
    
    m_extenderMotor = new ExtendMotor(extendDeviceID);
  }
  public void GoToStow() { // Change arm angle to floor
    SetPosition(ARM_CONSTANTS.STOW_ANGLE, EXTEND_CONSTANTS.STOW_EXTENSION);
  }
  public void GoToFloor() { // Change arm angle to floor
    SetPosition(ARM_CONSTANTS.FLOOR_ANGLE, EXTEND_CONSTANTS.FLOOR_EXTENSION);
  }
  public void GoToMiddle() { // Change arm angle to middle
    SetPosition(ARM_CONSTANTS.MIDDLE_ANGLE, EXTEND_CONSTANTS.MIDDLE_EXTENSION);
  }
  public void GoToTop() { // Change arm angle to top
    SetPosition(ARM_CONSTANTS.TOP_ANGLE, EXTEND_CONSTANTS.TOP_EXTENSION);
  }

  public void SetPosition(double angle, double extend) {
    m_elevationMotor.SetElevation(angle);
  }

  public void Extension() {
    // Extend the arm
  }
  public void Retraction() {
    // Retract the arm
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_elevationMotor.UpdateInformation(); // for PID testing/tuning (also see test-mode)
    m_elevationMotor.DisplayPosition(); // for basic arm info
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
   
 }