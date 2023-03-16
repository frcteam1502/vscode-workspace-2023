package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PneumaticsConstants;

public class GripperSubsystem extends SubsystemBase {
  public Solenoid solenoid = new Solenoid(20, PneumaticsModuleType.REVPH, PneumaticsConstants.channel);
 //TODO: Get CanID Change

  public GripperSubsystem() {
    solenoid.set(false);
  }

  public void turnOn() {
    solenoid.set(true);
  }

  public void turnOff() {
    solenoid.set(false);
  }
}