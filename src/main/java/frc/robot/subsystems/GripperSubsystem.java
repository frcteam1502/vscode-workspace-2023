package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class GripperSubsystem extends SubsystemBase {
  private final CANSparkMax gripper;
  private final CANSparkMax rotate;

  private final RelativeEncoder rotateEncoder;
  private final RelativeEncoder gripperEncoder;

  private boolean Out = true;

  public GripperSubsystem() {
    gripper = Constants.Motors.GRIPPER;
    rotate = Constants.Motors.GRIPPER_ROTATE;

    rotateEncoder = rotate.getEncoder();
    gripperEncoder = gripper.getEncoder();

    rotateEncoder.setPosition(0);
    gripperEncoder.setPosition(0);

    //Ticks (42 per revolution) to degrees (360 per revolution... idiot)
    rotateEncoder.setPositionConversionFactor(360/42);
  }

  public void OpenGripper() {
    // This method will open gripper
  }

  public void CloseGripper() {
    // This method will close gripper
  }

  public void toggleRotate() {

  }
 }
