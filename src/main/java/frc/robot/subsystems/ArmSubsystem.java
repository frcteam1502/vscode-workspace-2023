package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final CANSparkMax rotate;
  private final CANSparkMax rotateFollower;
  private final CANSparkMax extend;

  private final SparkMaxPIDController rotatePID;
  private final SparkMaxPIDController extendPID;

  private final RelativeEncoder rotateEncoder;
  private final RelativeEncoder extendEncoder;

  private final DigitalInput limit = new DigitalInput(21);

  private double goalExtend = 0;
  private double goalRotate = 0;

  private final double MAX_ROTATE = 91;
  private final double MIN_ROTATE = -30;
  private final double DEGREES_PER_ROTATION = 360 / 12;
  private final double MAX_ROTATE_FEEDFORWARD = 0; //TODO: get actual value
  private final double ROTATE_CHANGE = .1; //TODO: Too high/low
  private final double EXTEND_CHANGE = .1;

  //Test points in order of {Angle position, extend position}
  private final double[][] positionTable = 
  {
    {0, 0}, //Stow position
    {1, 0}, //Enter "To limit switch" section
    {2, 0}, //Straight down position
    {3, 0}, //Leave "To limit switch" section
    {4, 0}, //Ground score
    {5, 0}, //Medium score
    {6, 0}  //High score
  };
  
  public ArmSubsystem() {
    rotate = Constants.Motors.ARM_LEAD;
    rotateFollower = Constants.Motors.ARM_FOLLOW;
    extend = Constants.Motors.EXTEND;

    rotateFollower.follow(rotate, true);

    rotateEncoder = rotate.getEncoder();
    extendEncoder = extend.getEncoder();

    rotateEncoder.setPositionConversionFactor(DEGREES_PER_ROTATION);

    rotatePID = rotate.getPIDController();
    extendPID = extend.getPIDController();

    rotatePID.setFeedbackDevice(rotateEncoder);
    extendPID.setFeedbackDevice(extendEncoder);

    rotatePID.setP(0.1); //TODO: Get PID values
    rotatePID.setI(0);
    rotatePID.setD(0);
    rotatePID.setFF(0);

    extendPID.setP(0.1);
    extendPID.setI(0);
    extendPID.setD(0);
    extendPID.setFF(0);
  }

  public void rotateArm(double Pose) {
    goalRotate = Pose;
    calculateGoalExtend(rotateEncoder.getPosition());
  }

  public void rotateToStow() {
    rotateArm(positionTable[0][0]);
  }

  public void rotateToLow() {
    rotateArm(positionTable[4][0]);
  }

  public void rotateToMid() {
    rotateArm(positionTable[5][0]);
  }

  public void rotateToHigh() {
    rotateArm(positionTable[6][0]);
  }

  public void rotateManually(double input) {
    double change = Math.signum(input) * ROTATE_CHANGE;
    if(input > .8) change *= 2;
    rotateArm(goalRotate + change);
  }

  public void checkAngle() {
    if(rotateEncoder.getPosition() > MAX_ROTATE) goalRotate -= ROTATE_CHANGE;
    else if(rotateEncoder.getPosition() < MIN_ROTATE) goalRotate += ROTATE_CHANGE;
  }

  //BELOW THIS IS ALL RELATED TO EXTENSION (And there a periodic function)

  /**
   * Uses the positionTable to calcuate the
   * GOAL position of the extension based on 
   * the current angle of the arm
   * 
   * checks in this order:
   * between stow and enter - calc
   * enter to 0 - limit
   * 0 to exit - limit
   * exit to low - calc
   * low to mid - calc
   * mid to high - calc
   */
  public void calculateGoalExtend(double currentPose) {
    if(isBetweenPoints(positionTable[0], positionTable[1], currentPose)) {
      goalExtend = calcBetweenPoints(positionTable[0], positionTable[1], currentPose);
    } 
    else if(isBetweenPoints(positionTable[1], positionTable[2], currentPose)) {
      toLimitSwitch();
    } 
    else if(isBetweenPoints(positionTable[2], positionTable[3], currentPose)) {
      toLimitSwitch();
    } 
    else if(isBetweenPoints(positionTable[3], positionTable[4], currentPose)) {
      goalExtend = calcBetweenPoints(positionTable[3], positionTable[4], currentPose);
    } 
    else if(isBetweenPoints(positionTable[4], positionTable[5], currentPose)) {
      goalExtend = calcBetweenPoints(positionTable[4], positionTable[5], currentPose);
    } 
    else if(isBetweenPoints(positionTable[5], positionTable[6], currentPose)) {
      goalExtend = calcBetweenPoints(positionTable[5], positionTable[6], currentPose);
    } 
  }

  /**
   * 
   * Takes in the coordinates from the positionTable
   * and calculates linear function between the 2 points
   * starting with slope then finding the constant
   * then using these to calculate the final goalExtend
   *  
   * @param position1
   * @param position2
   * @param currentPose
   * @return goalExtend for the extension motor 
   */
  public double calcBetweenPoints(double[] position1, double[] position2, double currentPose) {
    double slope = (position2[1] - position1[1])/(position2[0] - position1[0]);
    double constant = position1[1] - slope * position1[0];
    return slope * currentPose + constant;
  }

  public void toLimitSwitch() {
    if(!limit.get()) goalExtend -= EXTEND_CHANGE;
    else extendEncoder.setPosition(0);
  }

  public boolean isBetweenPoints(double[] position1, double[] position2, double currentPose) {
    return (position1[0] <= currentPose && currentPose <= position2[0]);
  }

  public double dynamicFeedForward(double angle) {
    return MAX_ROTATE_FEEDFORWARD * Math.cos(angle);
  }

  // @Override
  // public void periodic() {
  //   checkAngle();
  //   rotatePID.setFF(dynamicFeedForward(rotateEncoder.getPosition()));
  //   extendPID.setReference(goalExtend, ControlType.kPosition);
  //   rotatePID.setReference(goalRotate, ControlType.kPosition);
  // }
}