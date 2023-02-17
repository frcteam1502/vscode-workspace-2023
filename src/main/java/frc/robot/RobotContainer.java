package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.commands.Auto.EmptyAutoCommand;
import frc.robot.commands.ArmByController;
import frc.robot.commands.DriveByController;
import frc.robot.commands.pneumaticCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.Motors;
import frc.robot.Constants.XboxButtons;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.pneumaticSubsystem;

public class RobotContainer {
  //Subsystems
  // The robot's subsystems and commands are defined here...
  
  // private final DriveTrain driveSubsystem; // = new DriveTrain();
  
  // private final IntakeSubsystem intakeSubsystem;// = new IntakeSubsystem(Motors.INTAKE_MOTOR);
  
  // private final pneumaticSubsystem pneumaticSubsystem;// = new pneumaticSubsystem();
  // private final pneumaticCommand pneumaticCommand;// = new pneumaticCommand(pneumaticSubsystem);
  private final ArmSubsystem armSubsystem;
  
 private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem(); //Motors.INTAKE_MOTOR);
  
  public RobotContainer() {
  
    // driveSubsystem = new DriveTrain();
    armSubsystem = new ArmSubsystem(ArmConstants.LEAD_DEVICE_ID,ArmConstants.FOLOW_DEVICE_ID, ArmConstants.EXTEND_DEVICE_ID);
    // intakeSubsystem = new IntakeSubsystem(Motors.INTAKE_MOTOR);
    // pneumaticSubsystem = new pneumaticSubsystem();
    // pneumaticCommand = new pneumaticCommand(pneumaticSubsystem);
    configureBindings();
  }

  private void configureBindings() {
    //driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    armSubsystem.setDefaultCommand(new ArmByController(armSubsystem));
    //XboxButtons.BUTTON_A.whileTrue(intakeSubsystem.runIntakeCommand()); // TODO: assign trigger
  
    //XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveOut));
    //XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveIn));
    XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::GoToTop));
    XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::GoToMiddle));
    XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::GoToFloor));
    XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::GoToStow));
    //XboxButtons.LEFT_JOYSTICK.onTrue(new InstantCommand(ArmSubsystem::Extension));
  }
  


  // TODO: implement SendableChooser
  public Command getAutonomousCommand() {
    return new EmptyAutoCommand(m_exampleSubsystem);
  }
}