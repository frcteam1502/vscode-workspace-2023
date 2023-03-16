package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
  //private final HashMap<String, Command> twoCone = new HashMap<>();

  
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
    //sendableChooser.addOption("2 Cone", driveSubsystem.buildAuto(twoCone, "twoCone"));
    //sendableChooser.addOption("Straight and Turn", driveSubsystem.buildAuto(straightAndTurn, "straightAndTurn"));
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {
    //twoCone
    //twoCone.put(null, null);
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}