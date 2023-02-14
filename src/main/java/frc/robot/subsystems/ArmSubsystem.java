// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

  // no need to be global/public if not used outside of this subsystemstatic class xboxcontroller.axis 
 static class xboxcontroller {
  //work in progress found the right buttons just need to put the values.

  public final static double kLeftX = 0;
  public final static double kLeftY = 0;


 }
 
 
  final static class ARM_CONSTANTS {

    private static final double GEAR_BOX_RATIO = 12.0;

    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_ANGLE = 0;
    private static final double FLOOR_ANGLE = 0.1; // 
    private static final double MIDDLE_ANGLE = 0.25; // 
    private static final double TOP_ANGLE = 0.4; // 
    
    private static final double MIN_ANGLE = 0.0;
    private static final double MAX_ANGLE = 0.4; // don't hit top crossbar
    
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
    private static final double GEAR_BOX_RATIO = 12.0;

    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_EXTENSION = 0;
    private static final double FLOOR_EXTENSION = 1.0;
    private static final double MIDDLE_EXTENSION = 2.0;
    private static final double TOP_EXTENSION = 4.0;

    private static final double MIN_EXTENSION = 0.0;
    private static final double MAX_EXTENSION = 4.0;
   
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
    private double m_gearBoxRatio = ARM_CONSTANTS.GEAR_BOX_RATIO;

    public DualMotor(int leadDeviceID, int followDeviceID) {
      m_leadMotor = new CANSparkMax(leadDeviceID, MotorType.kBrushless);
      m_followMotor = new CANSparkMax(followDeviceID, MotorType.kBrushless);

      //SWAP FOR TEST
      var motor = m_leadMotor;
      m_leadMotor = m_followMotor ;
      m_followMotor = motor;

      m_leadMotor.restoreFactoryDefaults();
      m_followMotor.restoreFactoryDefaults();                                                                            

      //m_followMotor.follow(m_leadMotor, /*invert*/ true);

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
      m_targetPosition = rotations * m_gearBoxRatio;
      m_pidControllerAngle.setReference(m_targetPosition, CANSparkMax.ControlType.kPosition);
    }
    
    // For Testing
    public void UpdateInformation(){ 
      // read PID coefficients from SmartDashboard
      double p =         SmartDashboard.getNumber("ANGLE P Gain", 0);
      double i =         SmartDashboard.getNumber("ANGLE I Gain", 0);
      double d =         SmartDashboard.getNumber("ANGLE D Gain", 0);
      double iz =        SmartDashboard.getNumber("ANGLE I Zone", 0);
      double ff =        SmartDashboard.getNumber("ANGLE Feed Forward", 0);
      double max =       SmartDashboard.getNumber("ANGLE Max Output", 0);
      double min =       SmartDashboard.getNumber("ANGLE Min Output", 0);
      double rotations = SmartDashboard.getNumber("ANGLE Set Position", 0);
      double m_gearBoxRatio = SmartDashboard.getNumber("ANGLE GearBox Ratio", ARM_CONSTANTS.GEAR_BOX_RATIO);
  
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
      SmartDashboard.putNumber("ANGLE P Gain", m_pidControllerAngle.getP());
      SmartDashboard.putNumber("ANGLE I Gain", m_pidControllerAngle.getI());
      SmartDashboard.putNumber("ANGLE D Gain", m_pidControllerAngle.getD());
      SmartDashboard.putNumber("ANGLE I Zone", m_pidControllerAngle.getIZone());
      SmartDashboard.putNumber("ANGLE Feed Forward", m_pidControllerAngle.getFF());
      SmartDashboard.putNumber("ANGLE Max Output", m_pidControllerAngle.getOutputMax());
      SmartDashboard.putNumber("ANGLE Min Output", m_pidControllerAngle.getOutputMin());

      SmartDashboard.putNumber("ANGLE GearBox Ratio", ARM_CONSTANTS.GEAR_BOX_RATIO);
      SmartDashboard.putNumber("ANGLE Set Position", 0);

    }
    
    public void DisplayPosition()
    {
      SmartDashboard.putNumber("ANGLE Arm Target Position", m_targetPosition / m_gearBoxRatio);
      SmartDashboard.putNumber("ANGLE Arm Current Position", m_encoderangle.getPosition() / m_gearBoxRatio);
    }

    // TODO: Cancel previous target position from button? Some sort of adaptive fine-tune
    public void FineTune(double signum) {
      double targetPosition = m_targetPosition + signum * 0.1 * m_gearBoxRatio;
      if (ARM_CONSTANTS.MIN_ANGLE < targetPosition && targetPosition < ARM_CONSTANTS.MAX_ANGLE) {
        SetElevation(targetPosition);
      }
    }

  }
  
  final class ExtendMotor {
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

    // For Testing
    public void UpdateInformation(){ 
      // read PID coefficients from SmartDashboard
      double p = SmartDashboard.getNumber(  "EXTEND P Gain", 0);
      double i = SmartDashboard.getNumber ( "EXTEND I Gain", 0);
      double d = SmartDashboard.getNumber ( "EXTEND D Gain", 0);
      double iz = SmartDashboard.getNumber( "EXTEND I Zone", 0);
      double ff = SmartDashboard.getNumber( "EXTEND Feed Forward", 0);
      double max = SmartDashboard.getNumber("EXTEND Max Output", 0);
      double min = SmartDashboard.getNumber("EXTEND Min Output", 0);
      double rotations = SmartDashboard.getNumber("EXTEND Set Position", 0);
  
      // if PID coefficients on SmartDashboard have changed, write new values to controller
      if((p != m_pidControllerExtension.getP())) { m_pidControllerExtension.setP(p); }
      if((i != m_pidControllerExtension.getI())) { m_pidControllerExtension.setI(i); }
      if((d != m_pidControllerExtension.getD())) { m_pidControllerExtension.setD(d); }
      if((iz != m_pidControllerExtension.getIZone())) { m_pidControllerExtension.setIZone(iz); }
      if((ff != m_pidControllerExtension.getFF())) { m_pidControllerExtension.setFF(ff); }
      if((max != m_pidControllerExtension.getOutputMax()) || (min != m_pidControllerExtension.getOutputMin())) { 
        m_pidControllerExtension.setOutputRange(min, max); 
      }
      
      SetExtension(rotations);
    }

    // For Testing
    public void DisplayInformation() {
      // display PID coefficients on SmartDashboard
      SmartDashboard.putNumber("EXTEND P Gain", m_pidControllerExtension.getP());
      SmartDashboard.putNumber("EXTEND I Gain", m_pidControllerExtension.getI());
      SmartDashboard.putNumber("EXTEND D Gain", m_pidControllerExtension.getD());
      SmartDashboard.putNumber("EXTEND I Zone", m_pidControllerExtension.getIZone());
      SmartDashboard.putNumber("EXTEND Feed Forward", m_pidControllerExtension.getFF());
      SmartDashboard.putNumber("EXTEND Max Output", m_pidControllerExtension.getOutputMax());
      SmartDashboard.putNumber("EXTEND Min Output", m_pidControllerExtension.getOutputMin());

      SmartDashboard.putNumber("EXTEND Set Position", 0);
    }
    public void DisplayPosition()
    {
      SmartDashboard.putNumber("EXTEND Arm Target Extension", m_targetExtension);
      SmartDashboard.putNumber("EXTEND Arm Current Position", m_encoderextension.getPosition());
    }

    public void FineTune(double signum) {
      double targetPosition = m_targetExtension + signum * 0.05;
      if (EXTEND_CONSTANTS.MIN_EXTENSION < targetPosition && targetPosition < EXTEND_CONSTANTS.MAX_EXTENSION) {
        SetExtension(targetPosition);
      }
    }
  }

  private DualMotor m_elevationMotor;
  private ExtendMotor m_extenderMotor;

  /* TODO: provide LEAD_DEVICE_ID, etc. */
  public ArmSubsystem(int leadDeviceID, int followDeviceID, int extendDeviceID) {
    m_elevationMotor = new DualMotor(leadDeviceID, followDeviceID);
    m_extenderMotor = new ExtendMotor(extendDeviceID);
    
    SmartDashboard.putBoolean("Toggle ARM Update", m_UpdateArmDiagnostics);
    SmartDashboard.putData("Toggle ARM Diagnostics", new InstantCommand(this::ToggleArmDiagnostics));
    m_elevationMotor.DisplayInformation();
    SmartDashboard.putData("Toggle EXTEND Update", new InstantCommand(this::ToggleExtendUpdate));
    SmartDashboard.putData("Toggle EXTEND Diagnostics", new InstantCommand(this::ToggleExtendDiagnostics));
    m_extenderMotor.DisplayInformation();

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
    m_extenderMotor.SetExtension(extend);
  }

  public void Extension() {
    // Extend the arm
  }

  public void Retraction() {
    // Retract the arm
  }

  private boolean m_DisplayArmDiagnostics = true;
  private boolean m_UpdateArmDiagnostics = false;
  private boolean m_DisplayExtendDiagnostics = true;
  private boolean m_UpdateExtendDiagnostics = false;

  private void ToggleArmUpdate() {
    m_UpdateArmDiagnostics = !m_UpdateArmDiagnostics;
  }

  private void ToggleArmDiagnostics() {
    m_DisplayArmDiagnostics = !m_DisplayArmDiagnostics;
  }

  private void ToggleExtendUpdate() {
    m_UpdateExtendDiagnostics = !m_UpdateExtendDiagnostics;
  }

  private void ToggleExtendDiagnostics() {
    m_DisplayExtendDiagnostics = !m_DisplayExtendDiagnostics;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (m_UpdateArmDiagnostics) {
      m_elevationMotor.UpdateInformation(); // for PID testing/tuning (also see test-mode)
    }
    if (m_DisplayArmDiagnostics) {
      m_elevationMotor.DisplayPosition(); // for basic arm info
    }
    if (m_UpdateExtendDiagnostics) {
      m_extenderMotor.UpdateInformation(); // for PID testing/tuning (also see test-mode)
    }
    if (m_DisplayExtendDiagnostics) {
      m_extenderMotor.DisplayPosition(); // for basic arm info
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void FineTune(double signum) {
    m_elevationMotor.FineTune(signum);
  }
   
 }