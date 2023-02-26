package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase{

  // no need to be global/public if not used outside of this subsystemstatic class xboxcontroller.axis 
 static class xboxcontroller {
  //work in progress found the right buttons just need to put the values
  public final static double kLeftX = 0;
  public final static double kLeftY = 0;
 }
 
 
  final static class ARM_CONSTANTS {

    private static final double GEAR_BOX_RATIO = 12.0;

    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_ANGLE = 0;
    private static final double FLOOR_ANGLE = 2;
    private static final double MIDDLE_ANGLE = 3;
    private static final double TOP_ANGLE = 5;
    
    private static final double MIN_ANGLE = 0.0;
    private static final double MAX_ANGLE = 5;
    
    // PID coefficients (sample values, TBD)
    public final static double kP = 0.1;
    public final static double kI = 1e-4;
    public final static double kD = 1;
    public final static double kIz = 0;
    public final static double kFF = 0;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
  }

  final static class EXTEND_CONSTANTS {

    // ARM POSITION/ELEVATION (rotations)
    private static final double STOW_EXTENSION = 0;
    private static final double FLOOR_EXTENSION = 0.1;
    private static final double MIDDLE_EXTENSION = 0.25;
    private static final double TOP_EXTENSION = 0.5;

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
    private SparkMaxLimitSwitch m_angleFwdLimitSwitch;
    private SparkMaxLimitSwitch m_angleRwdLimitSwitch;
    private RelativeEncoder m_encoderangle;
    private double m_targetPosition = 0;

    private boolean angleLimitPressed = false;

    public DualMotor(int leadDeviceID, int followDeviceID) {
      m_leadMotor = new CANSparkMax(leadDeviceID, MotorType.kBrushless);
      m_followMotor = new CANSparkMax(followDeviceID, MotorType.kBrushless);

      m_leadMotor.restoreFactoryDefaults();
      m_followMotor.restoreFactoryDefaults();                                                                            

      m_followMotor.follow(m_leadMotor, /*invert*/ true);

      m_pidControllerAngle = m_leadMotor.getPIDController();

      // Encoder object created to display position values
      m_encoderangle = m_leadMotor.getEncoder();
      m_encoderangle.setPosition(0);

      m_angleFwdLimitSwitch =  m_leadMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
      m_angleRwdLimitSwitch =  m_leadMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);

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
  
      // if PID coefficients on SmartDashboard have changed, write new values to controller
      if((p != m_pidControllerAngle.getP())) { m_pidControllerAngle.setP(p); }
      if((i != m_pidControllerAngle.getI())) { m_pidControllerAngle.setI(i); }
      if((d != m_pidControllerAngle.getD())) { m_pidControllerAngle.setD(d); }
      if((iz != m_pidControllerAngle.getIZone())) { m_pidControllerAngle.setIZone(iz); }
      if((ff != m_pidControllerAngle.getFF())) { m_pidControllerAngle.setFF(ff); }
      if((max != m_pidControllerAngle.getOutputMax()) || (min != m_pidControllerAngle.getOutputMin())) { 
        m_pidControllerAngle.setOutputRange(min, max); 
      }
      
      SetElevation(rotations / ARM_CONSTANTS.GEAR_BOX_RATIO);
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

      SmartDashboard.putNumber("ANGLE Set Position", 0);

    }
    
    public void DisplayPosition() {
      SmartDashboard.putNumber("ANGLE Arm Target Position", m_targetPosition);
      SmartDashboard.putNumber("ANGLE Arm Current Position", m_encoderangle.getPosition());
      SmartDashboard.putNumber("Elevation Target Position", elevationFineTune);
      SmartDashboard.putBoolean("ANGLE Fwd Limit Switch", m_angleFwdLimitSwitch.isPressed());
      SmartDashboard.putBoolean("ANGLE Rwd Limit Switch", m_angleRwdLimitSwitch.isPressed());
    }
    public double elevationFineTune;

    // TODO: Cancel previous target position from button? Some sort of adaptive fine-tune
    public void FineTune(double signum) {
      double targetElevation = m_targetPosition + signum * 0.05;

      elevationFineTune = targetElevation;

      if (ARM_CONSTANTS.MIN_ANGLE < targetElevation && targetElevation < ARM_CONSTANTS.MAX_ANGLE) {
        SetElevation(targetElevation);
      }
    }

    // public void checkZeroPosition(){
    //   if(m_angleRwdLimitSwitch.isPressed()){
    //     if(!angleLimitPressed){
    //       angleLimitPressed = true;
    //       m_leadMotor.set(0);
    //       m_encoderangle.setPosition(ARM_CONSTANTS.MIN_ANGLE);
    //       SetElevation(ARM_CONSTANTS.MIN_ANGLE);
    //     }
    //   }
    //   else{
    //       angleLimitPressed = false;
    //   }
    // }
  }
  
  final class ExtendMotor {
    private CANSparkMax m_extendMotor;
    private SparkMaxLimitSwitch m_extendRwdLimitSwitch;
    private RelativeEncoder m_encoderextension;
    private SparkMaxPIDController m_pidControllerExtension;
    private double m_targetExtension = 0;

    private boolean extendLimitPressed = false;

    public ExtendMotor(int extendDeviceID) {
      m_extendMotor = new CANSparkMax(extendDeviceID, MotorType.kBrushless);

      m_extendRwdLimitSwitch = m_extendMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);

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

    public void DisplayPosition() {
      SmartDashboard.putNumber("EXTEND Arm Target Extension", m_targetExtension);
      SmartDashboard.putNumber("EXTEND Arm Current Position", m_encoderextension.getPosition());
      SmartDashboard.putBoolean("EXTEND Arm Rearward Limit Pressed", m_extendRwdLimitSwitch.isPressed());
    }

    public void FineTune(double signum) {
      double targetPosition = m_targetExtension + signum * 0.05;
      if (EXTEND_CONSTANTS.MIN_EXTENSION < targetPosition && targetPosition < EXTEND_CONSTANTS.MAX_EXTENSION) {
        SetExtension(targetPosition);
      }
    }

    // public void checkZeroPosition(){
    //   //Check if Rwd Limit Pressed
    //   if(m_extendRwdLimitSwitch.isPressed()){
    //     if(!extendLimitPressed){
    //       extendLimitPressed = true;
    //       m_extendMotor.set(0);
    //       m_encoderextension.setPosition(EXTEND_CONSTANTS.MIN_EXTENSION);
    //       SetExtension(EXTEND_CONSTANTS.MIN_EXTENSION);
    //     }
    //   }
    //   else{
    //     extendLimitPressed = false;
    //   }
    // }
  }

  private DualMotor m_elevationMotor;
  private ExtendMotor m_extenderMotor;

  /* TODO: provide LEAD_DEVICE_ID, etc. */
  public ArmSubsystem(int leadDeviceID, int followDeviceID, int extendDeviceID) {
    m_elevationMotor = new DualMotor(leadDeviceID, followDeviceID);
    m_extenderMotor = new ExtendMotor(extendDeviceID);
  
    //Populate Network Tables 
    m_elevationMotor.DisplayInformation();
    m_elevationMotor.DisplayPosition();
    m_extenderMotor.DisplayPosition();

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

  public void FineTuneAngle(double signum) {
    m_elevationMotor.FineTune(signum);
  }

  // public void checkZeroAngle(){
  //   m_elevationMotor.checkZeroPosition();
  // }

  // public void checkZeroExtend(){
  //   m_extenderMotor.checkZeroPosition();
  // }

  public void FineTuneExtend(double signum) {
    m_extenderMotor.FineTune(signum);
  }

  public void updateSmartDashboard(){
    m_elevationMotor.DisplayPosition();
    m_extenderMotor.DisplayPosition();
  }


 }