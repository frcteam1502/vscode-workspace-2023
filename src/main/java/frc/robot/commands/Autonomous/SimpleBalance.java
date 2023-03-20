package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

public class SimpleBalance extends SequentialCommandGroup {
  public SimpleBalance(DriveTrain drive) {
    addCommands(
        // Deposite the game element
        new ParallelRaceGroup(new Move(drive,-.5, 0), new WaitCommand(.25)),

        //Traverse the charge station
        new ParallelRaceGroup(new Move(drive, .3, 0), new WaitCommand(2)),
        new ParallelRaceGroup(new Move(drive, .5, 0), new WaitCommand(2.1)),

        //Drive backwards onto balance
        new ParallelRaceGroup(new Move(drive, -.3, 0), new WaitCommand(2.44)),

        new ParallelRaceGroup(new AutoBalance(drive), new WaitCommand(7))
    );
  }
}