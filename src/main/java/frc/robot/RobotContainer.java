package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.ArmByController;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.Selection.SimpleBalance;
import frc.robot.commands.Autonomous.Simple.AutoBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
// import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem(ArmConstants.LEAD_DEVICE_ID, ArmConstants.FOLOW_DEVICE_ID, ArmConstants.EXTEND_DEVICE_ID);
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  // private final GripperSubsystem gripperSubsystem = new GripperSubsystem();
  private final DriveTrain driveSubsystem = new DriveTrain();

  //Autonomous Commands
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();
  private final SimpleBalance simpleBalance = new SimpleBalance(driveSubsystem);
  private final HashMap<String, Command> twoCone = new HashMap<>();
  private final HashMap<String, Command> straightAndTurn = new HashMap<>();

  
  public RobotContainer() {
    configureBindings();
    setUpSendableChooser();
  }

  private void configureBindings() {
    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem)); //USES THE LEFT BUMPER TO SLOW DOWN
    Constants.XboxButtons.DRIVER_BUTTON_Y.whileTrue(new AutoBalance(driveSubsystem));
    Constants.XboxButtons.DRIVER_BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
  
    // //Intake
    // XboxButtons.DRIVER_RIGHT_BUMPER.onTrue(new InstantCommand(intakeSubsystem::OnPressed).andThen(new WaitCommand(.25)));
    // XboxButtons.DRIVER_RIGHT_BUMPER.onFalse(new InstantCommand(intakeSubsystem::OnReleased).andThen(new WaitCommand(.25)));

    // //Arm
    // armSubsystem.setDefaultCommand(new ArmByController(armSubsystem));
    // XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::GoToTop));
    // XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::GoToMiddle));
    // XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::GoToFloor));
    // XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::GoToStow));

    // //Gripper
    // XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::toggleGripper));
    // XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::toggleRotate));
  }

  private void setUpSendableChooser() {
    createAutoHashMaps();
    sendableChooser.setDefaultOption("Balance", simpleBalance);
    // sendableChooser.addOption("2 Cone", driveSubsystem.buildAuto(twoCone, "twoCone"));
    // sendableChooser.addOption("Straight and Turn", driveSubsystem.buildAuto(straightAndTurn, "straightAndTurn"));
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {
    //twoCone
    //twoCone.put(null, null);

    //straightAndTurn
    straightAndTurn.put("Stop", new WaitCommand(2));
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}