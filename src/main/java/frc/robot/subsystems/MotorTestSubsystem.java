package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorTestSubsystem extends SubsystemBase {
  private final CANSparkMax TEST;

  public MotorTestSubsystem() {
    TEST = Constants.Motors.TEST;
  }

  public void FORWARD() {
    TEST.set(.1);
  }

  public void BACKWARD() {
    TEST.set(-.1);
  }

  public void STOP() {
    TEST.set(0);
  }

  public void MOVE(double SPEED) {
    TEST.set(SPEED / 10);
  }
 }
