// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystemOrigin extends SubsystemBase {
  private static final double STOW_ANGLE = 0;
  private static final double FLOOR_ANGLE = 45;
  private static final double MIDDLE_ANGLE = 90;
  private static final double TOP_ANGLE = 120;
  private static final double STOW_EXTENSION = 0;
  private static final double FLOOR_EXTENSION = 45;
  private static final double MIDDLE_EXTENSION = 90;
  private static final double TOP_EXTENSION = 120;
  private CANSparkMax m_LeftArmAngle;
  private CANSparkMax m_RightArmAngle;
  private CANSparkMax m_ArmExtend;
  private static final int leadDeviceID = 1;
  private static final int followDeviceID = 2;

  /** Creates a new ExampleSubsystem. */
  public ArmSubsystemOrigin(CANSparkMax LEFT_ARM_ANGLE , CANSparkMax RIGHT_ARM_ANGLE , CANSparkMax ARM_EXTENDER) {
  this.m_LeftArmAngle = LEFT_ARM_ANGLE;
  this.m_RightArmAngle = RIGHT_ARM_ANGLE;
  this.m_ArmExtend = ARM_EXTENDER;

  m_RightArmAngle = new CANSparkMax(leadDeviceID, MotorType.kBrushless);
  m_LeftArmAngle = new CANSparkMax(followDeviceID, MotorType.kBrushless);
  m_LeftArmAngle.follow(m_RightArmAngle);
  }
  public void GoToFloor() {
    // Change arm angle to floor
    //get current encoder value
    double current_arm_value = m_LeftArmAngle.getEncoder().getPosition();
    if (current_arm_value > FLOOR_ANGLE) {m_LeftArmAngle.set(0.25);}
    if (current_arm_value == FLOOR_ANGLE) {m_LeftArmAngle.set(0);}
    if (current_arm_value < FLOOR_ANGLE) {m_LeftArmAngle.set(-0.25);}
  }
  public void GoToMiddle() {
    // Change arm angle to middle
    double current_arm_value = m_LeftArmAngle.getEncoder().getPosition();
    if (current_arm_value > MIDDLE_ANGLE) {m_LeftArmAngle.set(0.25);}
    if (current_arm_value == MIDDLE_ANGLE) {m_LeftArmAngle.set(0);}
    if (current_arm_value < MIDDLE_ANGLE) {m_LeftArmAngle.set(-0.25);}
  }
  public void GoToTop() {
    // Change arm angle to top
    double current_arm_value = m_LeftArmAngle.getEncoder().getPosition();
    if (current_arm_value > TOP_ANGLE) {m_LeftArmAngle.set(0.25);}
    if (current_arm_value == TOP_ANGLE) {m_LeftArmAngle.set(0);}
    if (current_arm_value < TOP_ANGLE) {m_LeftArmAngle.set(-0.25);}
  }
  public void GoToStow(){
//change arm angle to stow
double current_arm_value = m_LeftArmAngle.getEncoder().getPosition();
    if (current_arm_value > STOW_ANGLE) {m_LeftArmAngle.set(0.25);}
    if (current_arm_value == STOW_ANGLE) {m_LeftArmAngle.set(0);}
    if (current_arm_value < STOW_ANGLE) {m_LeftArmAngle.set(-0.25);}

  }

  

  public void Extension() {
    // Extend the arm
  }
  public void Retraction() {
    // Retract the arm
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
