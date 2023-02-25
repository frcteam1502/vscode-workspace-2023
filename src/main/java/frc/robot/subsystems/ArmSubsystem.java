package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  public CANSparkMax armAngle;
  public CANSparkMax armExtend;

  public ArmSubsystem() {
    armAngle = Constants.Motors.ARM_ANGLE;
    armExtend = Constants.Motors.ARM_EXTEND;
  }

  public void angleMove(double speed) {
    armAngle.set(speed/10);
  }

  public void extendMove(double speed) {
    if(speed > 0) armExtend.set(speed/10);
    else armExtend.set(0);
  }
}
