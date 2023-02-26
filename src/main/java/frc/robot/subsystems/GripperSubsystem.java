// package frc.robot.subsystems;


// import com.revrobotics.CANSparkMax;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.SparkMaxPIDController;
// import com.revrobotics.CANSparkMax.ControlType;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class GripperSubsystem extends SubsystemBase {
//   private final CANSparkMax gripper;
//   private final CANSparkMax rotate;

//   private final RelativeEncoder gripperEncoder;
//   private final RelativeEncoder rotateEncoder;

//   private final SparkMaxPIDController gripperPID;
//   private final SparkMaxPIDController rotatePID;

//   private boolean turn = true;
//   private double goalRotate = 0;
//   private boolean open = true;
//   private double goalOpen = 0;

//   public GripperSubsystem() {
//     //Motors
//     gripper = Constants.Motors.GRIPPER;
//     rotate = Constants.Motors.GRIPPER_ROTATE;

//     //Encoders
//     rotateEncoder = rotate.getEncoder();
//     gripperEncoder = gripper.getEncoder();

//     rotateEncoder.setPosition(0);
//     gripperEncoder.setPosition(0);
    
//     //PIDs
//     gripperPID = gripper.getPIDController();
//     rotatePID = rotate.getPIDController();

//     gripperPID.setFeedbackDevice(gripperEncoder);
//     rotatePID.setFeedbackDevice(rotateEncoder);
//   }

//   public void toggleGripper() {
//     if(open) goalOpen = 0;
//     else goalOpen = 10; //ARBITRATY VAUE
//     turn = !turn;
//     rotatePID.setReference(goalOpen, ControlType.kPosition);
//   }

//   public void toggleRotate() {
//     if(turn) goalRotate = 0;
//     else goalRotate = 10; //ARBITRATY VAUE
//     turn = !turn;
//     rotatePID.setReference(goalRotate, ControlType.kPosition);
//   }
// }