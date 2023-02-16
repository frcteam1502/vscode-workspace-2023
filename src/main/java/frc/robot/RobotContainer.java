package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.Selection.*;
import frc.robot.commands.Autonomous.Simple.AutoBalance;
import frc.robot.subsystems.DriveTrain;

public class RobotContainer { 
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();

  //Subsystems
  public final DriveTrain driveSubsystem = new DriveTrain();

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
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));

    Constants.XboxButtons.BUTTON_Y.whileTrue(new AutoBalance(driveSubsystem));
    Constants.XboxButtons.BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
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