package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {

  /** TODO:
   *
   * Configure CANCoders: Direction
   * 
   * Configure Motor Direction
   * 
   * Configure PIDs: SysID for Drive PIDs, Trial and error for turn PIDs
   */

  public static final Pigeon2 gyro = new Pigeon2(14);

  public static final class CanConstants {
    //FRONT LEFT MODULE
    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 6; 
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 5; 
    public static final int FRONT_LEFT_MODULE_STEER_CANCODER = 12;
    

    //FRONT Right MODULE
    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 8; 
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 7; 
    public static final int FRONT_RIGHT_MODULE_STEER_CANCODER = 13;
    

    //BACK LEFT MODULE
    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 2; 
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 1; 
    public static final int BACK_LEFT_MODULE_STEER_CANCODER = 11;
    

    //BACK RIGHT MODULE
    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 4; 
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 3; 
    public static final int BACK_RIGHT_MODULE_STEER_CANCODER = 10;
    
  }

  public static final class CANCoders {
    //Front Left CANCoder
    public static final CANCoder FRONT_LEFT_CAN_CODER = new CANCoder(CanConstants.FRONT_LEFT_MODULE_STEER_CANCODER);
    public static final boolean FRONT_LEFT_CAN_CODER_DIRECTION = false;
    public static final double FRONT_LEFT_CAN_CODER_OFFSET = 3.4;

    //Front Right CANCoder
    public static final CANCoder FRONT_RIGHT_CAN_CODER = new CANCoder(CanConstants.FRONT_RIGHT_MODULE_STEER_CANCODER);
    public static final boolean FRONT_RIGHT_CAN_CODER_DIRECTION = false;
    public static final double FRONT_RIGHT_CAN_CODER_OFFSET = 152.4;

    //Back Left CANCoder
    public static final CANCoder BACK_LEFT_CAN_CODER = new CANCoder(CanConstants.BACK_LEFT_MODULE_STEER_CANCODER);
    public static final boolean BACK_LEFT_CAN_CODER_DIRECTION = false;
    public static final double BACK_LEFT_CAN_CODER_OFFSET = 211.46;

    //Back Right CANCoder
    public static final CANCoder BACK_RIGHT_CAN_CODER = new CANCoder(CanConstants.BACK_RIGHT_MODULE_STEER_CANCODER);
    public static final boolean BACK_RIGHT_CAN_CODER_DIRECTION = false;
    public static final double BACK_RIGHT_CAN_CODER_OFFSET = 96.5; 
  }

  public static final class DriveConstants {
    
    //Turning Motors
    public static final boolean Front_Left_Turning_Motor_Reversed = true;
    public static final boolean Back_Left_Turning_Motor_Reversed = true;
    public static final boolean Front_Right_Turning_Motor_Reversed = true;
    public static final boolean Back_Right_Turning_Motor_Reversed = true;

    //Drive Motors
    public static final boolean Front_Left_Drive_Motor_Reversed = false;
    public static final boolean Back_Left_Drive_Motor_Reversed = false;
    public static final boolean Front_Right_Drive_Motor_Reversed = false;
    public static final boolean Back_Right_Drive_Motor_Reversed = false;

    //Wheel Base
    public static final double WHEEL_BASE_WIDTH = Units.inchesToMeters(23); //FIXME: Change actual values
    public static final double WHEEL_BASE_LENGTH = Units.inchesToMeters(26.5); //FIXME: Change actual values

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

    public static final double MAX_SPEED_METERS_PER_SECOND = 1; //IF YOU UP THE SPEED CHANGE ACCELERATION

    public static final double MAX_ROTATION_RADIANS_PER_SECOND = (Math.PI / 2);
    public static final double MAX_ROTATION_RADIANS_PER_SECOND_PER_SECOND = Math.PI;

    // public static final double ksVolts = 1;
    // public static final double kvVoltSecondsPerMeter = 0.8;
    // public static final double kaVoltSecondsSquaredPerMeter = 0.15;

    // public static final double kP_X = 0.2;
    // public static final double kD_X = 0;
    // public static final double kP_Y = 0.2;
    // public static final double kD_Y = 0;
    // public static final double kP_Theta = 8;
    // public static final double kD_Theta = 0;
    // public static double kTranslationSlew = 1.55;
    // public static double kRotationSlew = 3.00;
    // public static double kControllerDeadband = .05;
    // public static double kControllerRotDeadband = .1;
    // public static double kVoltCompensation = 12.6;
  }

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

    // //FIXME: use sysid on robot
    // public static double ksVolts = .055;
    // public static double kvVoltSecondsPerMeter = .2;
    // public static double kaVoltSecondsSquaredPerMeter = .02;

    public static final double MAX_MODULE_ROTATION_RADIANS_PER_SECOND = Math.PI;

    public static final double MAX_MODULE_ROTATION_RADIANS_PER_SECOND_PER_SECOND = Math.PI;
  }

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

    //intake
    public static final CANSparkMax INTAKE_MOTOR = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);

    //arm
    public static final CANSparkMax RIGHT_ARM_ANGLE = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax LEFT_ARM_ANGLE = new CANSparkMax(17, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax ARM_EXTENDER = new CANSparkMax(18, CANSparkMaxLowLevel.MotorType.kBrushless);
}

  public static final class Joysticks {
    public static final XboxController MANIP_CONTROLLER = new XboxController(0);
    public static final XboxController DRIVE_CONTROLLER = new XboxController(1);
  }

  public static final class XboxButtons {
    public static final JoystickButton LEFT_BUMPER = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kLeftBumper.value); 
    public static final JoystickButton RIGHT_BUMPER = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kRightBumper.value); 
    public static final JoystickButton BUTTON_Y = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kY.value); 
    public static final JoystickButton BUTTON_A = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kA.value); 
    public static final JoystickButton BUTTON_X = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kX.value); 
    public static final JoystickButton BUTTON_B = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kB.value); 
    public static final JoystickButton BACK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 7);
    public static final JoystickButton START = new JoystickButton(Joysticks.MANIP_CONTROLLER, 8);
    public static final JoystickButton LEFT_JOYSTICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 12);
    public static final JoystickButton RIGHT_JOYSTICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 13);
    public static final JoystickButton LEFT_STICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 14);
    public static final JoystickButton RIGHT_STICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 15);

    //Driver Buttons
    public static final JoystickButton DRIVER_RIGHT_BUMPER = new JoystickButton(Joysticks.DRIVE_CONTROLLER, XboxController.Button.kRightBumper.value); 
  }

  public static final class IntakeConstants {
    public static final int FORWARD_CHANNEL = 1;
    public static final int REVERSE_CHANNEL = 0;
  }

  public static class PneumaticsConstants{
    // Forward piston solenoid value
        public static final int kreversechannel = 0;
        public static final int kforwardchannel = 15;
  }

  public static class ArmConstants {
    public final static int LEAD_DEVICE_ID = 16; // TODO: TBD (get from global device map?)
    public final static int FOLOW_DEVICE_ID = 17; // TODO: TBD
    public final static int EXTEND_DEVICE_ID = 18; // TODO: TBD    
  }
}