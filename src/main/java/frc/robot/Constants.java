// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
hello
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class CanConstants {
    //FRONT LEFT MODULE
    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 5; //FIXME: CAN ID of front left drive motor
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 9; //FIXME: CAN ID of front left steer motor
    public static final int FRONT_LEFT_MODULE_STEER_CANCODER = 1; //FIXME: CAN ID of front left CANCoder
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = 313.09; /*FIXME: reading of front left CANCoder (in degrees) 
    after manually setting wheel to forward (axle bolt head to the right side of the robot)*/
  }

  public static final class DriveConstants {
    
    //Turning Motors
    public static final boolean FrontLeftTurningMotorReversed = true;
    public static final boolean BackLeftTurningMotorReversed = true;
    public static final boolean FrontRightTurningMotorReversed = true;
    public static final boolean BackRightTurningMotorReversed = true;

    //Drive Motors
    public static final boolean FrontLeftDriveMotorReversed = true;
    public static final boolean BackLeftDriveMotorReversed = true;
    public static final boolean FrontRightDriveMotorReversed = true;
    public static final boolean BackRightDriveMotorReversed = true;

    //Wheel Base
    public static final double wheelBaseWidth = Units.inchesToMeters(29.5); //FIXME: Change actual values
    public static final double wheelBaseLength = Units.inchesToMeters(29.5); //FIXME: Change actual values

    public static final boolean GyroReversed = true;

    //FIXME: fix values with a finished robot

    public static final double ksVolts = 1;
    public static final double kvVoltSecondsPerMeter = 0.8;
    public static final double kaVoltSecondsSquaredPerMeter = 0.15;

    public static final double kMaxSpeedMetersPerSecond = 5;

    public static final double kMaxRotationRadiansPerSecond = Math.PI * 2;
    public static final double kMaxRotationRadiansPerSecondSquared = Math.PI;

    public static final double kP_X = 0.2;
    public static final double kD_X = 0;
    public static final double kP_Y = 0.2;
    public static final double kD_Y = 0;
    public static final double kP_Theta = 8;
    public static final double kD_Theta = 0;
    public static double kTranslationSlew = 1.55;
    public static double kRotationSlew = 3.00;
    public static double kControllerDeadband = .05;
    public static double kControllerRotDeadband = .1;
    public static double kVoltCompensation = 12.6;
  }

  public static final class ModuleConstants {
    
    public static final double kWheelDiameterMeters = Units.inchesToMeters(4); //FIXME: Get inches of wheels

    public static double driveGearRatio = 1 / ((14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0)); //FIXME: Builders?

    public static double steerGearRatio = 1 / ((14.0 / 50.0) * (10.0 / 60.0)); //FIXME: Maybe. I'm not sure yet. Consult the builders

    public static final double kDriveMetersPerEncRev = (kWheelDiameterMeters * Math.PI) / driveGearRatio;

    public static final double kDriveEncRPMperMPS = kDriveMetersPerEncRev / 60;

    public static double kEncoderRevsPerMeter = 1 / kDriveMetersPerEncRev;

    public static double kFreeMetersPerSecond = 5600 * kDriveEncRPMperMPS;
  
    public static final double kTurningDegreesPerEncRev = 360 / steerGearRatio;

    // max turn speed = (5400/ 21.43) revs per min 240 revs per min 4250 deg per min
    public static final double kPModuleTurningController = .025;

    public static final double kPModuleDriveController = .2;

    //FIXME: use sysid on robot
    public static double ksVolts = .055;
    public static double kvVoltSecondsPerMeter = .2;
    public static double kaVoltSecondsSquaredPerMeter = .02;

    public static double kPModuleTurnController; //FIXME: ???

    public static double kSMmaxAccel = 90;//deg per sec per sec

    public static double maxVel= 90; // deg per sec

    public static double allowedErr = .05;//deg

    // sysid on module?
    public static final double ksDriveVoltSecondsPerMeter = 0.667 / 12;
    public static final double kvDriveVoltSecondsSquaredPerMeter = 2.44 / 12;
    public static final double kaDriveVoltSecondsSquaredPerMeter = 0.27 / 12;
    // sysid on module?
    public static final double kvTurnVoltSecondsPerRadian = 1.47; // originally 1.5
    public static final double kaTurnVoltSecondsSquaredPerRadian = 0.348; // originally 0.3

    
    public static double kMaxModuleAngularSpeedDegPerSec = 90;

    public static final double kMaxModuleAngularAccelerationDegreesPerSecondSquared = 90;

  }

  public static final class Motors {
    //drive
    public static final CANSparkMax DRIVE_FRONT_LEFT = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax DRIVE_FRONT_RIGHT = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax DRIVE_BACK_LEFT = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax DRIVE_BACK_RIGHT = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    

    //turn
    public static final CANSparkMax ANGLE_FRONT_LEFT = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax ANGLE_FRONT_RIGHT = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax ANGLE_BACK_LEFT = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax ANGLE_BACK_RIGHT = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
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
}
}
