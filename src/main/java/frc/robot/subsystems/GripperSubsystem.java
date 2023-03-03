package frc.robot.subsystems;


import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.Logger;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class GripperSubsystem extends SubsystemBase {

    private static final double MAX_GRIPPER_OPEN = 1.83333;

  private final CANSparkMax gripper;
  private final CANSparkMax rotate;

  private final RelativeEncoder gripperEncoder;
  private final RelativeEncoder rotateEncoder;

  private final SparkMaxPIDController gripperPID;
  private final SparkMaxPIDController rotatePID;

  private boolean turn = true;
  private double goalRotate = 0;
  private boolean open = true;
  private double goalOpen = 0;

  public GripperSubsystem() {
    //Motors
    gripper = Constants.Motors.GRIPPER;
    rotate = Constants.Motors.GRIPPER_ROTATE;

    //Encoders
    rotateEncoder = rotate.getEncoder();
    gripperEncoder = gripper.getEncoder();

    rotateEncoder.setPosition(0);
    gripperEncoder.setPosition(0);
    
    //PIDs
    gripperPID = gripper.getPIDController();
    gripperPID.setP(0.1);
    rotatePID = rotate.getPIDController();
    rotatePID.setP(0.2);
    rotatePID.setI(0.005);
    rotatePID.setD(0.01);
    rotatePID.setOutputRange(-0.1, 0.1);
    gripperPID.setFeedbackDevice(gripperEncoder);
    rotatePID.setFeedbackDevice(rotateEncoder);
  }

  public void toggleGripper() {
    if(open) goalOpen = 0;
    else goalOpen = MAX_GRIPPER_OPEN; //Fully Open
    open = !open;
    gripperPID.setReference(goalOpen, ControlType.kPosition);
  }

  public void toggleRotate() {
    if(turn) goalRotate = 0;
    else goalRotate = 0.5; //ARBITRATY VAUE
    turn = !turn;
    rotatePID.setReference(goalRotate, ControlType.kPosition);
  }

  public void DisplayInfo()
  {
    //Logger.log(ErrorCode.OK, getName())
    System.out.print("WRIST");
    SmartDashboard.putNumber("GRIPPER Position", gripperEncoder.getPosition());
    SmartDashboard.putNumber("GRIPPER PID", gripperPID.getP());
    SmartDashboard.putBoolean("GRIPPER Open", open);
    SmartDashboard.putNumber("WRIST Position", rotateEncoder.getPosition());
    SmartDashboard.putBoolean("Wrist Turn", turn);
  }

}