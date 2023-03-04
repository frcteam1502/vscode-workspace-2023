package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.ArmByController;
import frc.robot.commands.DriveByController;
import frc.robot.commands.GripperByController;
import frc.robot.commands.Autonomous.Chooser;
import frc.robot.commands.Autonomous.Simple.AutoBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
  
  private static final SystemMap systemMap = Constants.SystemMap;

  // would like to be able to set the "target" during the Deploy (e.g., deploy Comp or deploy Test)
  public static SystemMap ConfigureSystem(SystemMap map) { // sample config for drive train testing
    return ConfigureForCompetition(map, 1);
    //return ConfigureForDriveTest(map, 1);
    //return ConfigureForArmTest(map, 1);
  }

  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();
  private final DriveTrain driveSubsystem = new DriveTrain();

  //Autonomous Command Chooser
  private final Chooser chooser = new Chooser();
  
  public RobotContainer() {
    configureBindings();
    chooser.setUpSendableChooser(driveSubsystem);
  }

  private static SystemMap ConfigureForCompetition(SystemMap map, int driveDiagnostics) {
    map.DriveSubsystem.Enable();
  
    // disable these until working
    map.ArmSubsystem.Disable();
    map.GripperSubsystem.Disable();
    map.IntakeSubsystem.Disable();
  
    return map;
  }

  private static SystemMap ConfigureForDriveTest(SystemMap map, int driveDiagnostics) {
    map.DriveSubsystem.Enable().EnableDiagnostics(driveDiagnostics);
  
    // disable these
    map.ArmSubsystem.Disable();
    map.IntakeSubsystem.Disable();
    map.GripperSubsystem.Disable();
  
    return map;
  }  
  private static SystemMap ConfigureForArmTest(SystemMap map, int armDiagnostics) {
    map.ArmSubsystem.Enable().EnableDiagnostics(armDiagnostics);
  
    // disable these
    map.DriveSubsystem.Disable();
    map.IntakeSubsystem.Disable();
    map.GripperSubsystem.Disable();
  
    return map;
  }


  private void configureBindings() {

    //Drive
    if (systemMap.DriveSubsystem.IsEnabled) {
      driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem)); //USES THE LEFT BUMPER TO SLOW DOWN
      Constants.XboxButtons.DRIVER_BUTTON_Y.whileTrue(new AutoBalance(driveSubsystem));
      Constants.XboxButtons.DRIVER_BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
    }

    // //Intake
    if (systemMap.IntakeSubsystem.IsEnabled) {
      XboxButtons.DRIVER_BUTTON_A.toggleOnTrue(new InstantCommand(intakeSubsystem::Toggle));
      // XboxButtons.DRIVER_RIGHT_BUMPER.onTrue(new InstantCommand(intakeSubsystem::OnPressed).andThen(new WaitCommand(.25)));
      // XboxButtons.DRIVER_RIGHT_BUMPER.onFalse(new InstantCommand(intakeSubsystem::OnReleased).andThen(new WaitCommand(.25)));
    }
    
    // //Arm
    if (systemMap.ArmSubsystem.IsEnabled) {
      armSubsystem.setDefaultCommand(new ArmByController(armSubsystem));
      XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::GoToTop));
      XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::GoToMiddle));
      XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::GoToFloor));
      XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::GoToStow));
    }
    
    //Gripper
    if (systemMap.GripperSubsystem.IsEnabled) {
      gripperSubsystem.setDefaultCommand(new GripperByController(gripperSubsystem));
      XboxButtons.RIGHT_BUMPER.toggleOnTrue(new InstantCommand(gripperSubsystem::toggleGripper));
      XboxButtons.LEFT_BUMPER.toggleOnTrue(new InstantCommand(gripperSubsystem::toggleRotate));
    }
  }

  public Command getAutonomousCommand() {
    return chooser.getAutonomousCommand();
  }   

}