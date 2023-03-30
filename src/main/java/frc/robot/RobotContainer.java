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
import frc.robot.commands.Autonomous.SimpleBalance;
import frc.robot.commands.Autonomous.AutoBalance;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.PdpSubsystem;
import frc.robot.subsystems.LedSubsystem;

public class RobotContainer {
  //Subsystems
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();
  public final DriveTrain driveSubsystem = new DriveTrain();
  private final PdpSubsystem pdpSubsystem = new PdpSubsystem();
  private final LedSubsystem ledSubsystem = new LedSubsystem();

  //Autonomous Commands
  private final SendableChooser<Command> sendableChooser = new SendableChooser<>();
  private final AutoBalance autobalance = new AutoBalance(driveSubsystem);
  private final SimpleBalance simpleBalance = new SimpleBalance(driveSubsystem);
  private final HashMap<String, Command> Cone1High = new HashMap<>();
  private final HashMap<String, Command> Cube2High = new HashMap<>();
  private final HashMap<String, Command> Cone9High = new HashMap<>();
  private final HashMap<String, Command> Cone6Balance = new HashMap<>();


  
  public RobotContainer() {
    configureBindings();
    setUpSendableChooser();
  }

  private void configureBindings() {

    //Drive
    driveSubsystem.setDefaultCommand(new DriveByController(driveSubsystem, pdpSubsystem)); //USES THE LEFT BUMPER TO SLOW DOWN
    Constants.XboxButtons.DRIVER_BUTTON_B.whileTrue(new InstantCommand(driveSubsystem::setToBreak).repeatedly());
    
    //LEDs
    Constants.XboxButtons.DRIVER_BUTTON_X.onTrue(new InstantCommand(ledSubsystem::setViolet));
    Constants.XboxButtons.DRIVER_BUTTON_Y.onTrue(new InstantCommand(ledSubsystem::setYellow));

    //Arm
    armSubsystem.setDefaultCommand(new ArmByController(armSubsystem));
    XboxButtons.BUTTON_Y.onTrue(new InstantCommand(armSubsystem::rotateToHigh));
    XboxButtons.BUTTON_B.onTrue(new InstantCommand(armSubsystem::rotateToMid));
    XboxButtons.BUTTON_A.onTrue(new InstantCommand(armSubsystem::rotateToLow));
    XboxButtons.BUTTON_X.onTrue(new InstantCommand(armSubsystem::rotateToStow));
    XboxButtons.LEFT_BUMPER.onTrue(new InstantCommand(armSubsystem::rotateToHumanPlayer));
    
    //Gripper
    XboxButtons.RIGHT_BUMPER.onTrue(new InstantCommand(gripperSubsystem::turnOn))
                            .onFalse(new InstantCommand(gripperSubsystem::turnOff));
  }

  private void setUpSendableChooser() {
    createAutoHashMaps();
    sendableChooser.setDefaultOption("Balance", simpleBalance);
    sendableChooser.addOption("Cone 1 High", driveSubsystem.buildAuto(Cone1High, "Cone1High"));
    sendableChooser.addOption("Cube 2 High", driveSubsystem.buildAuto(Cube2High, "Cube2High"));
    sendableChooser.addOption("Cone 9 High", driveSubsystem.buildAuto(Cone9High, "Cone9High"));
    //sendableChooser.addOption("Cone 6 Balance", driveSubsystem.buildAuto(Cone6Balance, "Cone6Balance"));
    SmartDashboard.putData(sendableChooser);
  }

  private void createAutoHashMaps() {

    /* Cone 1 High */
    //sequential
    Cone1High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));//Move arm to stow to reset from backdrive pre-match
    Cone1High.put("Wait1", new WaitCommand(.5));//Wait .5 sec for stow
    Cone1High.put("GoToHigh", new InstantCommand(armSubsystem::rotateToHigh));//Move arm to stow to high
    Cone1High.put("Wait2", new WaitCommand(.5));

    //sequential
    Cone1High.put("OpenGripper", new InstantCommand(gripperSubsystem::turnOn)); //Open the gripper and drop Cone
    Cone1High.put("Wait3", new WaitCommand(2));

    //sequential
    Cone1High.put("CloseGripper", new InstantCommand(gripperSubsystem::turnOff)); //Close gripper and grab cube
    Cone1High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));
    Cone1High.put("Wait4", new WaitCommand(2));
    
    /* Cube 2 High */
    //sequential
    Cube2High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));//Move arm to stow to reset from backdrive pre-match
    Cube2High.put("Wait1", new WaitCommand(.5));//Wait .5 sec for stow
    Cube2High.put("GoToHigh", new InstantCommand(armSubsystem::rotateToHigh));//Move arm to stow to high
    Cube2High.put("Wait2", new WaitCommand(.5));

    //sequential
    Cube2High.put("OpenGripper", new InstantCommand(gripperSubsystem::turnOn)); //Open the gripper and drop Cone
    Cube2High.put("Wait3", new WaitCommand(2));

    //Sequential
    Cube2High.put("CloseGripper", new InstantCommand(gripperSubsystem::turnOff)); //Close gripper and grab cube
    Cube2High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));
    Cube2High.put("Wait4", new WaitCommand(2));
    
    /* Cone 9 High */
    //sequential
    Cone9High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));//Move arm to stow to reset from backdrive pre-match
    Cone9High.put("Wait1", new WaitCommand(.5));//Wait .5 sec for stow
    Cone9High.put("GoToHigh", new InstantCommand(armSubsystem::rotateToHigh));//Move arm to stow to high
    Cone9High.put("Wait2", new WaitCommand(.5));

    //sequential
    Cone9High.put("OpenGripper", new InstantCommand(gripperSubsystem::turnOn)); //Open the gripper and drop Cone
    Cone9High.put("Wait3", new WaitCommand(2));

    //Parallel
    Cone9High.put("CloseGripper", new InstantCommand(gripperSubsystem::turnOff)); //Close gripper and grab cube
    Cone9High.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow)); //rotate arm to Low
    Cone9High.put("Wait3", new WaitCommand(2));
  
    /******************************** Cone 6 Balance ************************************/
    //sequential
    Cone6Balance.put("GoToStow", new InstantCommand(armSubsystem::rotateToStow));//Move arm to stow to reset from backdrive pre-match
    Cone6Balance.put("Wait1", new WaitCommand(.5));//Wait .5 sec for stow
    Cone6Balance.put("GoToHigh", new InstantCommand(armSubsystem::rotateToHigh));//Move arm to stow to high
    Cone6Balance.put("Wait2", new WaitCommand(.5));

    //sequential
    Cone6Balance.put("OpenGripper", new InstantCommand(gripperSubsystem::turnOn)); //Open the gripper and drop Cone
    Cone6Balance.put("Wait3", new WaitCommand(2));

    //Marker
    Cone6Balance.put("CloseGripper", new InstantCommand(gripperSubsystem::turnOff)); //Open the gripper and drop Cone
    Cone6Balance.put("Wait4", new WaitCommand(.5));
    Cone6Balance.put("GoToStow", new InstantCommand(armSubsystem::rotateToLow)); //rotate arm to Low

    
    
  }

  public Command getAutonomousCommand() {
    return sendableChooser.getSelected();
  }
}