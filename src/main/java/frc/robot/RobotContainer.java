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
import frc.robot.commands.Autonomous.Selection.SimpleBalance;
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
  private final HashMap<String, Command> twoElement = new HashMap<>();

  
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
    //sendableChooser.addOption("2 Element", driveSubsystem.buildAuto(twoElement, "twoElement"));
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {
    //twoElement
    // //sequential
    // twoElement.put("Close Gripper", new InstantCommand(gripperSubsystem::turnOn)); //Close the gripper
    // twoElement.put("Wait", new WaitCommand(.5)); //Let the gripper close
    // twoElement.put("To Top", new InstantCommand(armSubsystem::rotateToHigh)); //Move arm to high

    // //sequential
    // twoElement.put("Wait", new WaitCommand(.5)); //Let arm get to high

    // //parallel deadline
    // twoElement.put("Wait", new WaitCommand(1)); //Wait while gripper places
    // twoElement.put("Open Gripper", new InstantCommand(gripperSubsystem::turnOff)); //Open the gripper and drop cone

    // //sequential
    // twoElement.put("To Low", new InstantCommand(armSubsystem::rotateToLow)); //rotate arm to low

    // //parallel deadline
    // twoElement.put("Close Gripper", new InstantCommand(gripperSubsystem::turnOn)); //Close the gripper and grab cube
    // twoElement.put("Wait", new WaitCommand(1)); //Wait while gripper grabs piece
    
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}