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

    Constants.Motors.ARM_ANGLE_FOLLOWER.follow(armAngle, true);
  }

  public void angleMove(double speed) {
    armAngle.set(speed/10);
  }

  public void extendMove(double speed) {
    armExtend.set(speed/10);
  }
}
