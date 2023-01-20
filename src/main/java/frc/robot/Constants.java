// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  public final static class Joysticks {
    public static final XboxController MANIP_CONTROLLER = new XboxController(0);
    public static final XboxController DRIVE_CONTROLLER = new XboxController(1);
}

public static final class XboxButtons {        
    public static final JoystickButton MODE_BUTTON = new JoystickButton(Joysticks.MANIP_CONTROLLER, 11); // no clue which number is mode button

    public static final JoystickButton LEFT_BUMPER = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kLeftBumper.value); // Extend
    public static final JoystickButton RIGHT_BUMPER = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kRightBumper.value); // Contract
    public static final JoystickButton BUTTON_Y = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kY.value); // Rotate Clockwise
    public static final JoystickButton BUTTON_A = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kA.value); // Rotate Counter Clockwise
    public static final JoystickButton BUTTON_X = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kX.value); // Rotate babies Clockwise
    public static final JoystickButton BUTTON_B = new JoystickButton(Joysticks.MANIP_CONTROLLER, XboxController.Button.kB.value); // Rotate babies Counter Clockwise
    public static final JoystickButton BACK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 7);
    public static final JoystickButton START = new JoystickButton(Joysticks.MANIP_CONTROLLER, 8);
    // I dont know the values for these
    public static final JoystickButton LEFT_JOYSTICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 12);
    public static final JoystickButton RIGHT_JOYSTICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 13);
    public static final JoystickButton LEFT_STICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 14);
    public static final JoystickButton RIGHT_STICK = new JoystickButton(Joysticks.MANIP_CONTROLLER, 15);
}

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class PneumaticsConstants{
// Forward piston solenoid value
    public static final int kIn = 0;
    public static final int kOut = 15;
  } }
  
    
  

