package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final CANSparkMax rotate;
  private final CANSparkMax rotateFollower;
  private final CANSparkMax extend;

  private final SparkMaxPIDController rotatePID;
  private final SparkMaxPIDController extendPID;

  public final RelativeEncoder rotateEncoder;
  private final RelativeEncoder extendEncoder;

  private double goalExtend = 0;
  private double goalRotate = 0;

  //Rotation bounds
  private final double MAX_ROTATE = 120;
  private final double MIN_ROTATE = -7;

  private final double MAX_EXTEND = 48.8;

  private final double DEGREES_PER_ROTATION = 360 / 28.5; 
  private final double MAX_ROTATE_FEEDFORWARD = .06; //TODO: increase?
  
  private final double ROTATE_CHANGE = .3; 
  private final double EXTEND_CHANGE = .1;

  private final double MAX_ROTATION_SPEED = .3;

  //Test points in order of {Angle position, extend position}
  private final double[][] positionTable = 
  {
    {0, 0}, //Straight down position
    {18, 0}, //Enter "To limit switch" section
    {20.1, 34.5}, //Ground score
    {90, 0}, //Medium score
    {110, 48.7}  //High score
  };
  
  public ArmSubsystem() {
    rotate = Constants.Motors.ARM_LEAD;
    rotateFollower = Constants.Motors.ARM_FOLLOW;
    extend = Constants.Motors.EXTEND;

    rotateFollower.follow(rotate, true);

    rotate.setSmartCurrentLimit(40);
    rotateFollower.setSmartCurrentLimit(40);
    extend.setSmartCurrentLimit(40);

    rotateEncoder = rotate.getEncoder();
    extendEncoder = extend.getEncoder();

    rotateEncoder.setPositionConversionFactor(DEGREES_PER_ROTATION);
    rotateEncoder.setPosition(0);
    extendEncoder.setPosition(0);

    rotatePID = rotate.getPIDController();
    extendPID = extend.getPIDController();

    rotatePID.setFeedbackDevice(rotateEncoder);
    extendPID.setFeedbackDevice(extendEncoder);

    rotatePID.setP(.7); //TODO: Get PID values
    rotatePID.setI(0);
    rotatePID.setD(.9);
    //rotatePID.setFF(MAX_ROTATE_FEEDFORWARD);
    rotatePID.setOutputRange(-MAX_ROTATION_SPEED / 4, MAX_ROTATION_SPEED);

    extendPID.setP(0.23);
    extendPID.setI(0);
    extendPID.setD(0);
  }

   // For Testing
   public void UpdateInformation(){ 
    // read PID coefficients from SmartDashboard
    double p =         SmartDashboard.getNumber("ANGLE P Gain", 0);
    double i =         SmartDashboard.getNumber("ANGLE I Gain", 0);
    double d =         SmartDashboard.getNumber("ANGLE D Gain", 0);
    double iz =        SmartDashboard.getNumber("ANGLE I Zone", 0);
    double ff =        SmartDashboard.getNumber("ANGLE Feed Forward", 0);
    double max =       SmartDashboard.getNumber("ANGLE Max Output", 0);
    double min =       SmartDashboard.getNumber("ANGLE Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != rotatePID.getP())) { rotatePID.setP(p); }
    if((i != rotatePID.getI())) { rotatePID.setI(i); }
    if((d != rotatePID.getD())) { rotatePID.setD(d); }
    if((iz != rotatePID.getIZone())) { rotatePID.setIZone(iz); }
    if((ff != rotatePID.getFF())) { rotatePID.setFF(ff); }
    if((max != rotatePID.getOutputMax()) || (min != rotatePID.getOutputMin())) { 
      rotatePID.setOutputRange(min, max); 
    }
    
  }

  public void rotateArm(double Pose) {
    goalRotate = Pose;
    calculateGoalExtend(rotateEncoder.getPosition());
  }

  public void rotateToStow() {
    rotateArm(positionTable[0][0]);
  }

  public void rotateToLow() {
    rotateArm(positionTable[2][0]);
  }

  public void rotateToMid() {
    rotateArm(positionTable[3][0]);
  }

  public void rotateToHigh() {
    rotateArm(positionTable[4][0]);
  }

  public void rotateToHumanPlayer() {
    goalRotate = 84.5;
    goalExtend = 0;
  }

  public void rotateManually(double input) {
    double change = Math.signum(input) * ROTATE_CHANGE;
    if(input > .8) change *= 2;
    rotateArm(goalRotate + change);
  }

  public void checkMaxAndMin() {
    if(rotateEncoder.getPosition() > MAX_ROTATE) goalRotate -= ROTATE_CHANGE * 5;
    else if(rotateEncoder.getPosition() < MIN_ROTATE) goalRotate += ROTATE_CHANGE * 5;
    if(extendEncoder.getPosition() > MAX_EXTEND) goalExtend -= EXTEND_CHANGE * 5;
  }

  //BELOW THIS IS ALL RELATED TO EXTENSION (And there a periodic function)

  /**
   * Uses the positionTable to calcuate the
   * GOAL position of the extension based on 
   * the current angle of the arm
   * 
   * checks in this order:
   * between stow and enter,
   * enter to low,
   * low to mid,
   * mid to high
   * 
   * @param currentAngle The current angle returned by the rotation encoder
   */
  public void calculateGoalExtend(double currentAngle) {
    if(isBetweenPoints(positionTable[0], positionTable[1], currentAngle)) {
      goalExtend = 0;
      if(isWithinExtendRange(rotateEncoder.getPosition(), 15)) 
      goalExtend = calcBetweenPoints(positionTable[0], positionTable[1], currentAngle);
    } 
    else if(isBetweenPoints(positionTable[1], positionTable[2], currentAngle)) { //TODO: Maybe change the "1" in this position table to a "0"
      goalExtend = 0;
      if(isWithinExtendRange(rotateEncoder.getPosition(), 1)) 
      goalExtend = calcBetweenPoints(positionTable[1], positionTable[2], currentAngle);
    } 
    else if(isBetweenPoints(positionTable[2], positionTable[3], currentAngle)) {
      goalExtend = 0;
      if(isWithinExtendRange(rotateEncoder.getPosition(), 15)) 
      goalExtend = calcBetweenPoints(positionTable[2], positionTable[3], currentAngle);
    } 
    else if(isBetweenPoints(positionTable[3], positionTable[4], currentAngle)) {
      goalExtend = 0;
      if(isWithinExtendRange(rotateEncoder.getPosition(), 15)) 
      goalExtend = calcBetweenPoints(positionTable[3], positionTable[4], currentAngle);
    } 
  }

  /**
   * Takes in the coordinates from the positionTable
   * and calculates linear function between the 2 points
   * starting with slope then finding the constant
   * then using these to calculate the final goalExtend
   *  
   * @param position1 First position in the position array
   * 
   * @param position2 Second position in the position array
   * 
   * @param currentAngle The current angle returned by the rotation encoder
   * 
   * @return goalExtend for the extension motor 
   */
  public double calcBetweenPoints(double[] position1, double[] position2, double currentAngle) {
    double slope = (position2[1] - position1[1])/(position2[0] - position1[0]);
    double constant = position1[1] - slope * position1[0];
    return slope * currentAngle + constant;
  }

  /**
   * Based upon the position array this method
   * decides wether the current angle is within
   * the two points in the array
   * 
   * @param position1 First position in the position array
   * 
   * @param position2 Second position in the position array
   * 
   * @param currentAngle The current angle returned by the rotation encoder
   * 
   * @return Wether or not the current angle is within the two points on the array
   */
  public boolean isBetweenPoints(double[] position1, double[] position2, double currentAngle) {
    return (position1[0] <= currentAngle && currentAngle <= position2[0]);
  }

  /**
   * Takes in the current angle to be used to calculate
   * the necesary feedforward based on the maximum
   * feedforward to hold the arm ar 90 degrees
   * 
   * @param currentAngle the current angle returned by the rotation encoder
   * 
   * @return the feedforward value to use to hold up the arm at the given angle
   */
  public double dynamicFeedForward(double currentAngle) {
    return MAX_ROTATE_FEEDFORWARD * Math.cos(currentAngle);
  }

  /**
   * Takes in the current angle of the angle motor
   * and based upon that it determines if the
   * current angle is within the extend range 
   * provided by the constant: 
   * 
   * {@value range}
   * 
   * @param currentAngle the current angle returned by the rotation encoder
   * 
   * @return whether or not the current angle is within our alloted range to begin extending
   */
  public boolean isWithinExtendRange(double currentAngle, double range) {
    return (currentAngle >= goalRotate - range && currentAngle <= goalRotate + range);
  }

  @Override
  public void periodic() {
    checkMaxAndMin();
    //rotatePID.setFF(dynamicFeedForward(rotateEncoder.getPosition()));
    extendPID.setReference(goalExtend, ControlType.kPosition);
    rotatePID.setReference(goalRotate, ControlType.kPosition);
  }
}