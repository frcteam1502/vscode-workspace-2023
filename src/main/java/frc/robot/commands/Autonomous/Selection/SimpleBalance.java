package frc.robot.commands.Autonomous.Selection;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Autonomous.Simple.*;
import frc.robot.subsystems.DriveTrain;

public class SimpleBalance extends SequentialCommandGroup {
  public SimpleBalance(DriveTrain drive) {
    addCommands(
      new ParallelRaceGroup(new Move(drive, .5, 0), new WaitCommand(2)),

      new AutoBalance(drive)
    );
  }
}