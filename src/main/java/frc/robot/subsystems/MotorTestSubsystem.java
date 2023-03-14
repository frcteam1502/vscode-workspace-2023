package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorTestSubsystem extends SubsystemBase {
  private final CANSparkMax TEST;
  private final CANSparkMax TEST_FOLLOWER;

  public MotorTestSubsystem() {
    TEST = Constants.Motors.TEST;
    TEST_FOLLOWER = Constants.Motors.TEST_FOLLOWER;
    TEST_FOLLOWER.follow(TEST, true);

    TEST.setSmartCurrentLimit(40);
    TEST_FOLLOWER.setSmartCurrentLimit(40);
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
