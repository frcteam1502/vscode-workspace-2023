package frc.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public class SwerveConstants {
    
//needed_for_swerve
public static final Pigeon2 gyro = new Pigeon2(14);

//needed_for_swerve
public static final class CANCoders {
  //Front Left CANCoder
  public static final CANCoder FRONT_LEFT_CAN_CODER = new CANCoder(12);
  public static final boolean FRONT_LEFT_CAN_CODER_DIRECTION = false;
  public static final double FRONT_LEFT_CAN_CODER_OFFSET = 111.4;

  //Front Right CANCoder
  public static final CANCoder FRONT_RIGHT_CAN_CODER = new CANCoder(13);
  public static final boolean FRONT_RIGHT_CAN_CODER_DIRECTION = false;
  public static final double FRONT_RIGHT_CAN_CODER_OFFSET = 104.0;

  //Back Left CANCoder
  public static final CANCoder BACK_LEFT_CAN_CODER = new CANCoder(11);
  public static final boolean BACK_LEFT_CAN_CODER_DIRECTION = false;
  public static final double BACK_LEFT_CAN_CODER_OFFSET = 15.0;

  //Back Right CANCoder
  public static final CANCoder BACK_RIGHT_CAN_CODER = new CANCoder(10);
  public static final boolean BACK_RIGHT_CAN_CODER_DIRECTION = false;
  public static final double BACK_RIGHT_CAN_CODER_OFFSET = 91.4; 
}

//needed_for_swerve
public static final class DriveConstants {
  
  //Turning Motors
  public static final boolean FrontLeftTurningMotorReversed = true;
  public static final boolean BackLeftTurningMotorReversed = true;
  public static final boolean FrontRightTurningMotorReversed = true;
  public static final boolean BackRightTurningMotorReversed = true;

  //Drive Motors
  public static final boolean FrontLeftDriveMotorReversed = false;
  public static final boolean BackLeftDriveMotorReversed = false;
  public static final boolean FrontRightDriveMotorReversed = false;
  public static final boolean BackRightDriveMotorReversed = false;

  //Wheel Base
  public static final double WHEEL_BASE_WIDTH = Units.inchesToMeters(25);
  public static final double WHEEL_BASE_LENGTH = Units.inchesToMeters(32);

  public static final boolean GYRO_REVERSED = true;

  public static final Translation2d FRONT_LEFT_MODULE = new Translation2d(WHEEL_BASE_LENGTH/2, WHEEL_BASE_WIDTH/2);
  public static final Translation2d FRONT_RIGHT_MODULE = new Translation2d(WHEEL_BASE_LENGTH/2, -WHEEL_BASE_WIDTH/2);
  public static final Translation2d BACK_LEFT_MODULE = new Translation2d(-WHEEL_BASE_LENGTH/2, WHEEL_BASE_WIDTH/2);
  public static final Translation2d BACK_RIGHT_MODULE = new Translation2d(-WHEEL_BASE_LENGTH/2, -WHEEL_BASE_WIDTH/2);

  public static final SwerveDriveKinematics KINEMATICS =
    new SwerveDriveKinematics(
        FRONT_LEFT_MODULE, 
        FRONT_RIGHT_MODULE, 
        BACK_LEFT_MODULE, 
        BACK_RIGHT_MODULE
      );

  public static final double MAX_SPEED_METERS_PER_SECOND = 1.2; //IF YOU UP THE SPEED CHANGE ACCELERATION

  public static final double MAX_ROTATION_RADIANS_PER_SECOND = (Math.PI/2);
  public static final double MAX_ROTATION_RADIANS_PER_SECOND_PER_SECOND = Math.PI;

  public static final double GO_STRAIGHT_GAIN = 0.02;
}

//needed_for_swerve
public static final class ModuleConstants {
  
  public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);
  public static final double DRIVE_GEAR_RATIO = 1 / ((14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0));
  public static final double STEER_GEAR_RATIO = 1 / ((14.0 / 50.0) * (10.0 / 60.0));

  public static final double DRIVE_METERS_PER_ENCODER_REV = (WHEEL_DIAMETER_METERS * Math.PI) / DRIVE_GEAR_RATIO;

  public static final double DRIVE_ENCODER_MPS_PER_REV = DRIVE_METERS_PER_ENCODER_REV / 60; 

  public static final double MAX_METERS_PER_SECOND = 4.4; //5600 * DRIVE_ENCODER_MPS_PER_REV;

  public static final double TURNING_DEGREES_PER_ENCODER_REV = 360 / STEER_GEAR_RATIO;

  public static final double RADIANS_PER_ENCODER_REV = TURNING_DEGREES_PER_ENCODER_REV * (Math.PI/180);

  // max turn speed = (5400/ 21.43) revs per min 240 revs per min 4250 deg per min
  public static final double MODULE_TURN_PID_CONTROLLER_P = 1.5;

  public static final double MODULE_DRIVE_PID_CONTROLLER_P = 1.0;

  public static final double MAX_MODULE_ROTATION_RADIANS_PER_SECOND = Math.PI/2;

  public static final double MAX_MODULE_ROTATION_RADIANS_PER_SECOND_PER_SECOND = Math.PI;
}

//needed_for_swerve
public static final class Motors {
  //drive
  public static final CANSparkMax DRIVE_FRONT_LEFT = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax DRIVE_FRONT_RIGHT = new CANSparkMax(8, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax DRIVE_BACK_LEFT = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax DRIVE_BACK_RIGHT = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
  
  //turn
  public static final CANSparkMax ANGLE_FRONT_LEFT = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax ANGLE_FRONT_RIGHT = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax ANGLE_BACK_LEFT = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax ANGLE_BACK_RIGHT = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
}

}
