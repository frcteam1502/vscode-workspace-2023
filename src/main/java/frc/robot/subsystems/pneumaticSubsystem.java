// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants.PneumaticsConstants;
public class pneumaticSubsystem extends SubsystemBase {
  public DoubleSolenoid DoublePH = new DoubleSolenoid(1, PneumaticsModuleType.REVPH,frc.robot.Constants.PneumaticsConstants.kforwardchannel,frc.robot.Constants.PneumaticsConstants.kreversechannel);
  public Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

  public pneumaticSubsystem() {}
    
  public void Toggle() {
    DoublePH.toggle();
  }

  public void setIn() {
    DoublePH.set(Value.kReverse);
  }  
  
  public void setOut() {
      DoublePH.set(Value.kForward);
  }
  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *  boolean enabled = pcmCompressor.enabled();
boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();

   * @return value of some boolean subsystem state, such as a digital sensor.
  
   boolean enabled = phCompressor.enabled();
  boolean pressureSwitch = phCompressor.getPressureSwitchValue();
  */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    //DoublePH.set(kOff);
    //DoublePH.set(kForward);
    //DoublePH.set(kReverse);
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
  