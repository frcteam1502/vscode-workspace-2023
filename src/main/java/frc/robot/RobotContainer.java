package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.Selection.*;
import frc.robot.commands.Autonomous.Simple.AutoBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer { 
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();

  //Subsystems
  public final DriveTrain driveSubsystem = new DriveTrain();

  public final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  public final ArmSubsystem armAngleSubsystem = new ArmSubsystem();

  public final GripperSubsystem gripperSubsystem = new GripperSubsystem();

  //Commands

  //Autonomous Commands
  private final SimpleBalance simpleBalance = new SimpleBalance(driveSubsystem);

  private final HashMap<String, Command> twoCone = new HashMap<>();

  private final HashMap<String, Command> straightAndTurn = new HashMap<>();


  public RobotContainer() {
    configureBindings();
    setUpSendableChooser();
  }


  private void configureBindings() {
    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    Constants.XboxButtons.BUTTON_Y.whileTrue(new AutoBalance(driveSubsystem));
    Constants.XboxButtons.BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
    Constants.XboxButtons.BUTTON_A.whileTrue(driveSubsystem.moveToImage()); //on false?

    //Intake
    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(intakeSubsystem::OnPressed));
    Constants.XboxButtons.LEFT_BUMPER.onFalse(new InstantCommand(intakeSubsystem::OnReleased));

    //Arm
    armAngleSubsystem.setDefaultCommand(new ArmCommand(armAngleSubsystem));

    //Gripper
    Constants.XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::toggleRotate));
    Constants.XboxButtons.BUTTON_X.onTrue(new InstantCommand(gripperSubsystem::CloseGripper));
    Constants.XboxButtons.BUTTON_X.onFalse(new InstantCommand(gripperSubsystem::OpenGripper));

  }


  private void setUpSendableChooser() {
    createAutoHashMaps();
    sendableChooser.setDefaultOption("Balance", simpleBalance);
    sendableChooser.addOption("2 Cone", driveSubsystem.buildAuto(twoCone, "twoCone"));
    sendableChooser.addOption("Straight and Turn", driveSubsystem.buildAuto(straightAndTurn, "straightAndTurn"));
    SmartDashboard.putData(sendableChooser);
  }
  

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }

  
  public void createAutoHashMaps() {
    //twoCone
    //twoCone.put(null, null);

    //straightAndTurn
    straightAndTurn.put("Stop", new WaitCommand(2));

  }
}