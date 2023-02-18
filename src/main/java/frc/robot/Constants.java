package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public final class Constants {

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
    public static final CANSparkMax INTAKE_MOTOR = new CANSparkMax(18, CANSparkMaxLowLevel.MotorType.kBrushless);

    //arm
    public static final CANSparkMax RIGHT_ARM_ANGLE = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax LEFT_ARM_ANGLE = new CANSparkMax(17, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax ARM_EXTENDER = new CANSparkMax(18, CANSparkMaxLowLevel.MotorType.kBrushless);
}

  public static final class Joysticks {
    public static final XboxController OPERATOR_CONTROLLER = new XboxController(0);
    public static final XboxController DRIVE_CONTROLLER = new XboxController(1);
  }

  public static final class XboxButtons {
    public static final JoystickButton LEFT_BUMPER = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kLeftBumper.value); 
    public static final JoystickButton RIGHT_BUMPER = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kRightBumper.value); 
    public static final JoystickButton BUTTON_Y = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kY.value); 
    public static final JoystickButton BUTTON_A = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kA.value); 
    public static final JoystickButton BUTTON_X = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kX.value); 
    public static final JoystickButton BUTTON_B = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kB.value); 
    public static final JoystickButton BACK = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 7);
    public static final JoystickButton START = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 8);
    public static final JoystickButton LEFT_JOYSTICK = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 12);
    public static final JoystickButton RIGHT_JOYSTICK = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 13);
    public static final JoystickButton LEFT_STICK = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 14);
    public static final JoystickButton RIGHT_STICK = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, 15);

    //Driver Buttons
    public static final JoystickButton DRIVER_RIGHT_BUMPER = new JoystickButton(Joysticks.DRIVE_CONTROLLER, XboxController.Button.kRightBumper.value); 
    public static final JoystickButton DRIVER_LEFT_BUMPER = new JoystickButton(Joysticks.DRIVE_CONTROLLER, XboxController.Button.kLeftBumper.value); 
  }

  public static final class IntakeConstants {
    public static final int FORWARD_CHANNEL = 1;
    public static final int REVERSE_CHANNEL = 0;
    public final static int INTAKE_DEVICE_ID = 18; // TODO: TBD   
  }

  public static class PneumaticsConstants{
    // Forward piston solenoid value
    public static final int kreversechannel = 0;
    public static final int kforwardchannel = 15;
  }
}