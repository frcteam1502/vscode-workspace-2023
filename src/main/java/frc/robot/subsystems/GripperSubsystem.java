package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PneumaticsConstants;

public class GripperSubsystem extends SubsystemBase {
  public DoubleSolenoid DoublePH = new DoubleSolenoid(15, PneumaticsModuleType.REVPH, PneumaticsConstants.kforwardchannel, PneumaticsConstants.kreversechannel);
 //TODO: Get CanID Change
  public GripperSubsystem() {
    DoublePH.set(Value.kForward);
  }

  public void toggleGripper() {
    DoublePH.toggle();
  }
}