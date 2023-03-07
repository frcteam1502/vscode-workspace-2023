package frc.robot.commands.Autonomous.Selection;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Autonomous.Simple.*;
import frc.robot.subsystems.DriveTrain;

public class SimpleBalance extends SequentialCommandGroup {
  public SimpleBalance(DriveTrain drive) {
    addCommands(
        // Deposite the game element
        new ParallelRaceGroup(new Move(drive,.5,0), new WaitCommand(.25)),
        new ParallelRaceGroup(new Move(drive, -.1, 0), new WaitCommand(1.5)),

        // Traverse the Charging Station
        new ParallelRaceGroup(new Move(drive, .25, 0), new WaitCommand(4))// ,

    // //Drive backwards onto balance
    // new ParallelRaceGroup(new Move(drive, -.1, 0), new WaitCommand(3)),

    // new AutoBalance(drive)
 // new AutoBalance(drive)//,
    );
  }
}