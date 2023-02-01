package frc.robot;

import frc.robot.commands.DriveByController;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.pneumaticCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.pneumaticSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.Auto.EmptyAutoCommand;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.XboxButtons;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
public class RobotContainer {
  //Subsystems
  private final DriveTrain driveSubsystem = new DriveTrain();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(frc.robot.Constants.Motors.INTAKE_MOTOR);
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  //Adding a pneumatic subsystem
  private final pneumaticSubsystem pneumaticSubsystem = new pneumaticSubsystem();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
      private final pneumaticCommand pneumaticCommand = new pneumaticCommand(pneumaticSubsystem);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    XboxButtons.BUTTON_A.whileTrue(intakeSubsystem.runIntakeCommand()); // TODO: assign trigger
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //new Trigger(m_exampleSubsystem::exampleCondition)
        //.onTrue(new ExampleCommand(m_exampleSubsystem));
       
       
    XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveOut));
    XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(pneumaticCommand::MoveIn));
       
    
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
   // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  // TODO: implement SendableChooser
  public Command getAutonomousCommand() {
    return new EmptyAutoCommand();
  }
}