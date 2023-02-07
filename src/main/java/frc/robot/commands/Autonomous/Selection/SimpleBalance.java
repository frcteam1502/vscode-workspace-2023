package frc.robot.commands.Autonomous.Selection;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Autonomous.Simple.*;
import frc.robot.subsystems.DriveTrain;

public class SimpleBalance extends SequentialCommandGroup {
  public SimpleBalance(DriveTrain drive) {
    addCommands(
      new MoveForwardByTime(drive, .5, 3),

      new MoveForwardByTime(drive, -.5, 1),

      new AutoBalance(drive)
    );
  }
}
