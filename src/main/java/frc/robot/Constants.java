package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public final class Constants {

  public static final class Motors {
    public static final CANSparkMax TEST = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static final CANSparkMax TEST_FOLLOWER = new CANSparkMax(17, CANSparkMaxLowLevel.MotorType.kBrushless);
  }

  public static final class Joysticks {
    public static final XboxController OPERATOR_CONTROLLER = new XboxController(0);
  }

  public static final class XboxButtons {
    public static final JoystickButton LEFT_BUMPER = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kLeftBumper.value); 
    public static final JoystickButton RIGHT_BUMPER = new JoystickButton(Joysticks.OPERATOR_CONTROLLER, XboxController.Button.kRightBumper.value); 
  }
}
