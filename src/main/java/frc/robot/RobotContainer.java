package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.SimpleBalance;
import frc.robot.commands.Autonomous.AutoBalance;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PdpSubsystem;

public class RobotContainer {
  //Subsystems
  public final DriveTrain driveSubsystem = new DriveTrain();
  private final PdpSubsystem pdpSubsystem = new PdpSubsystem();

  //Autonomous Commands
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();
  
  public RobotContainer() {
    configureBindings();
    setUpSendableChooser();
  }

  private void configureBindings() {

    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem, pdpSubsystem)); //USES THE LEFT BUMPER TO SLOW DOWN
    Constants.XboxButtons.DRIVER_BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
  
  }

  private void setUpSendableChooser() {
    createAutoHashMaps();
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {
    
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}