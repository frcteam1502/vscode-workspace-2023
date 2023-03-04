package frc.robot.commands.Autonomous;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Autonomous.Selection.SimpleBalance;
import frc.robot.subsystems.DriveTrain;

public class Chooser {
    private SendableChooser<Command> sendableChooser;
    private Command defaultCommand;

    public Command getAutonomousCommand() {
        if (sendableChooser != null) {
            return sendableChooser.getSelected();
        } else {
            return defaultCommand;
        }
    }

    public void setUpSendableChooser(DriveTrain driveSubsystem) {
        if (Constants.SystemMap.IsDriveEnabled()) {
            
            // for when there is just one option
            defaultCommand = new SimpleBalance(driveSubsystem);

            if (defaultCommand == null) { // comment out line above to get options
                sendableChooser = new SendableChooser<>();
                sendableChooser.setDefaultOption("Balance", new SimpleBalance(driveSubsystem));
                sendableChooser.addOption("2 Cone", GetTwoConeCommand(driveSubsystem));
                sendableChooser.addOption("Straight and Turn", GetSraightAndTurnCommand(driveSubsystem));
                SmartDashboard.putData(sendableChooser);
            }
        }
      }
      
      private Command GetTwoConeCommand(DriveTrain driveSubsystem) {
        return driveSubsystem.buildAuto(new HashMap<String, Command>(), "twoCone");
      }

      private Command GetSraightAndTurnCommand(DriveTrain driveSubsystem) {
        var map = new HashMap<String, Command>();
        map.put("Stop", new WaitCommand(2));
        return driveSubsystem.buildAuto(map, "twoCone");
      }
}
