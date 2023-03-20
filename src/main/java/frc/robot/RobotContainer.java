package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.ArmByController;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.SimpleBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripperSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();
  private final DriveTrain driveSubsystem = new DriveTrain();

  //Autonomous Commands
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();
  private final SimpleBalance simpleBalance = new SimpleBalance(driveSubsystem);
  private final HashMap<String, Command> A1 = new HashMap<>();
  private final HashMap<String, Command> Temp = new HashMap<>();

  
  public RobotContainer() {
    configureBindings();
    setUpSendableChooser();
  }

  private void configureBindings() {

    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem)); //USES THE LEFT BUMPER TO SLOW DOWN
    Constants.XboxButtons.DRIVER_BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
    
    //Arm
    armSubsystem.setDefaultCommand(new ArmByController(armSubsystem));
    XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::rotateToHigh));
    XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::rotateToMid));
    XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::rotateToLow));
    XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::rotateToStow));
    
    //Gripper
    XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::turnOn))
                            .onFalse(new InstantCommand(gripperSubsystem::turnOff));
  }

  private void setUpSendableChooser() {
    createAutoHashMaps();
    sendableChooser.setDefaultOption("Balance", simpleBalance);
    sendableChooser.addOption("1A", driveSubsystem.buildAuto(A1, "1A"));
    sendableChooser.addOption("Test", driveSubsystem.buildAuto(Temp, "Temp"));
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {
    //1A
    //sequential
    A1.put("Close Gripper", new InstantCommand(gripperSubsystem::turnOn).andThen(new WaitCommand(.1))); //Close the gripper
    A1.put("To Top", new InstantCommand(armSubsystem::rotateToHigh).andThen(new WaitCommand(1.5))); //Move arm to high
    A1.put("Open Gripper", new InstantCommand(gripperSubsystem::turnOff).andThen(new WaitCommand(.2))); //Open the gripper and drop cone

    //Marker
    A1.put("To Low", new InstantCommand(armSubsystem::rotateToLow)); //rotate arm to low

    //sequential
    A1.put("Wait", new WaitCommand(.1)); //Wait before closing gripper
    A1.put("Close Gripper", new InstantCommand(gripperSubsystem::turnOn).andThen(new WaitCommand(1))); //Close the gripper and grab cube

    //Marker
    A1.put("To High", new InstantCommand(armSubsystem::rotateToHigh)); //rotate arm to High

    //Sequential
    A1.put("Open Gripper", new InstantCommand(gripperSubsystem::turnOff).andThen(new WaitCommand(.2))); //Open the gripper and drop cube
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}