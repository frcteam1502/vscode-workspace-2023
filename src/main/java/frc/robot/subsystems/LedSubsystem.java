package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedSubsystem extends SubsystemBase {
  public static final double WHITE = 0.93;
  public static final double YELLOW = 0.69;
  public static final double VIOLET = 0.91;
  
  public Spark left_blinkin = new Spark(0);
  public Spark right_blinkin = new Spark(1);

  LedSubsystem(){
    left_blinkin.set(WHITE);
    right_blinkin.set(WHITE);
  }

  public void setYellow(){
    left_blinkin.set(YELLOW);
    right_blinkin.set(YELLOW);
  }

  public void setViolet(){
    left_blinkin.set(VIOLET);
    right_blinkin.set(VIOLET);
  }




}