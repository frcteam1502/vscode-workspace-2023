package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.XboxButtons;
import frc.robot.commands.DriveByController;
import frc.robot.commands.Autonomous.Simple.AutoBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem(ArmConstants.LEAD_DEVICE_ID, ArmConstants.FOLOW_DEVICE_ID, ArmConstants.EXTEND_DEVICE_ID);
<<<<<<< HEAD
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();
  private final DriveTrain driveSubsystem = new DriveTrain();
=======
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(IntakeSubsystem.INTAKE_CONSTANTS.INTAKE_DEVICE_ID);
>>>>>>> 72c65a52494c1eabe4ecb90afd5428a5c5de8bba
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem));
    Constants.XboxButtons.BUTTON_Y.whileTrue(new AutoBalance(driveSubsystem)); //.repeatedly?
    Constants.XboxButtons.BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());

    //Arm Angle
    XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::GoToTop));
    XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::GoToMiddle));
    XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::GoToFloor));
    XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::GoToStow));
<<<<<<< HEAD

    //Intake
    XboxButtons.DRIVER_RIGHT_BUMPER.onTrue(new InstantCommand(intakeSubsystem::OnPressed).andThen(new WaitCommand(.25)));
    XboxButtons.DRIVER_RIGHT_BUMPER.onFalse(new InstantCommand(intakeSubsystem::OnReleased).andThen(new WaitCommand(.25)));

    //Gripper
    XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::toggleGripper));
    XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::toggleRotate));
=======
    Constants.XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(intakeSubsystem::OnPressed));
    Constants.XboxButtons.LEFT_BUMPER.onFalse(new InstantCommand(intakeSubsystem::OnReleased));
>>>>>>> 72c65a52494c1eabe4ecb90afd5428a5c5de8bba
  }
}